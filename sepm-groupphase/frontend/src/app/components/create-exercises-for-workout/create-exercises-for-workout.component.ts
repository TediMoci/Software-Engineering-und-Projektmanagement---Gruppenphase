import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Dude} from '../../dtos/dude';
import {CreateExerciseService} from '../../services/create-exercise.service';
import {Router} from '@angular/router';
import {CreateExercise} from '../../dtos/create-exercise';
import {WorkoutExercisesComponent} from '../workout-exercises/workout-exercises.component';
import {WorkoutEx} from '../../dtos/workoutEx';
import {Exercise} from '../../dtos/exercise';
import {EditWorkoutExercisesComponent} from '../edit-workout-exercises/edit-workout-exercises.component';

@Component({
  selector: 'app-create-exercises-for-workout',
  templateUrl: './create-exercises-for-workout.component.html',
  styleUrls: ['./create-exercises-for-workout.component.scss']
})
export class CreateExercisesForWorkoutComponent implements OnInit {
  error: any;
  imagePath: string;
  imagePathExercise: string = 'assets/img/exercise.png';
  userName: string;
  registerForm: FormGroup;
  submitted: boolean = false;
  dude: Dude;
  currentChosenExercises: WorkoutEx[];
  muscleGroup: string[] = ['Other', 'Chest', 'Back', 'Arms', 'Shoulders', 'Legs', 'Calves', 'Core'];
  message: string;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  crop: boolean = false;
  constructor(private workoutExercisesComponent: WorkoutExercisesComponent , private editWorkoutExercisesComponent: EditWorkoutExercisesComponent, private createExerciseService: CreateExerciseService , private formBuilder: FormBuilder, private router: Router ) {
  }

  ngOnInit() {
    localStorage.setItem('previousRoute', JSON.stringify('/create-exercise-for-workout'));
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.registerForm = this.formBuilder.group({
      nameForExercise: ['', [Validators.required]],
      equipmentExercise: [''],
      categoryExercise: ['', [Validators.required]],
      descriptionForExercise: ['', [Validators.required]],
      muscleGroupExercise: ['',  [Validators.required]],
      isPrivate: ['', [Validators.required]]
    });

    if (JSON.parse(localStorage.getItem('previousPreviousRoute')) === '/workout-exercises') {
      this.currentChosenExercises = JSON.parse(localStorage.getItem('chosenExercisesForWorkout')) || [];
      console.log('gotten from localstorage');
      console.log(this.currentChosenExercises);
    } else {
      this.currentChosenExercises = JSON.parse(localStorage.getItem('chosenExercisesForEditWorkout')) || [];
      console.log('gotten from localstorage edit');
      console.log(this.currentChosenExercises);
    }
  }

  addExercise() {

    this.submitted = true;
    console.log(this.registerForm);
    const exercise: CreateExercise = new CreateExercise(
      this.registerForm.controls.nameForExercise.value,
      this.registerForm.controls.equipmentExercise.value,
      this.registerForm.controls.categoryExercise.value,
      this.registerForm.controls.descriptionForExercise.value,
      this.registerForm.controls.muscleGroupExercise.value,
      this.dude.id,
      this.registerForm.controls.isPrivate.value
    );

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }
    this.createExerciseService.addExercise(exercise).subscribe(
      (data) => {
        if (JSON.parse(localStorage.getItem('previousPreviousRoute')) === '/workout-exercises') {
          console.log('newEx');
          console.log(data);
          this.addToLocalStorage(data);
          this.router.navigate(['/workout-exercises']);
        } else {
          this.addToLocalStorageEdit(data);
          this.router.navigate(['/edit-workout-exercises']);
        }
        },
      error => {
        this.error = error;
      }
    );
  }

  addToLocalStorage(newExercise: Exercise) {
    this.currentChosenExercises.push(new WorkoutEx(newExercise, 1, 1, 1));
    console.log('updated currentEx');
    console.log(this.currentChosenExercises);
    localStorage.setItem('chosenExercisesForWorkout', JSON.stringify(this.currentChosenExercises));
  }

  addToLocalStorageEdit(newExercise: Exercise) {
    this.currentChosenExercises.push(new WorkoutEx(newExercise, 1, 1, 1));
    console.log('updated currentEx');
    console.log(this.currentChosenExercises);
    localStorage.setItem('chosenExercisesForEditWorkout', JSON.stringify(this.currentChosenExercises));
  }

  Back() {
    if (JSON.parse(localStorage.getItem('previousPreviousRoute')) === '/workout-exercises') {
      this.router.navigate(['workout-exercises']);
    } else {
      this.router.navigate(['edit-workout-exercises']);
    }
  }

  vanishError() {
    this.error = false;
  }

  imageLoaded() {
    // show cropper
  }
  loadImageFailed() {
    // show message
    this.crop = true;
    this.message = 'Only images are supported.';

  }

  uploadPicture(files) {
    if (files.length === 0) {
      return;
    }
    console.log(files.file);
    this.imagePathExercise = files.base64;
    const imageFile = new File([files.file], 'file', { type: files.file.type });
    console.log(imageFile);
    this.createExerciseService.setFileStorage(imageFile);
  }
  fileChangeEvent(event: any): void {
    this.crop = false;
    this.imageChangedEvent = event;
  }
  imageCropped(image: string) {
    this.croppedImage = image;
  }
  cropPicture() {
    this.uploadPicture(this.croppedImage);
  }

}
