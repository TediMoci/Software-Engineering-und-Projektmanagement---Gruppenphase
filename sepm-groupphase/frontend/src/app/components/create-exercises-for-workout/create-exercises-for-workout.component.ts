import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Dude} from '../../dtos/dude';
import {CreateExerciseService} from '../../services/create-exercise.service';
import {Router} from '@angular/router';
import {CreateExercise} from '../../dtos/create-exercise';
import {WorkoutExercisesComponent} from '../workout-exercises/workout-exercises.component';

@Component({
  selector: 'app-create-exercises-for-workout',
  templateUrl: './create-exercises-for-workout.component.html',
  styleUrls: ['./create-exercises-for-workout.component.scss']
})
export class CreateExercisesForWorkoutComponent implements OnInit {
  error: any;
  imagePath: string = 'assets/img/kugelfisch.jpg';
  imagePathExercise: string = 'assets/img/exercise.png';
  userName: string;
  registerForm: FormGroup;
  submitted: boolean = false;
  dude: Dude;

  constructor(private workoutExercisesComponent: WorkoutExercisesComponent , private createExerciseService: CreateExerciseService , private formBuilder: FormBuilder, private router: Router ) {
  }

  ngOnInit() {
    localStorage.setItem('previousRoute', JSON.stringify('/create-exercise-for-workout'));
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.registerForm = this.formBuilder.group({
      nameForExercise: ['', [Validators.required]],
      equipmentExercise: [''],
      categoryExercise: ['', [Validators.required]],
      descriptionForExercise: ['', [Validators.required]],
      muscleGroupExercise: ['']
    });
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
      this.dude.id
    );

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }
    this.createExerciseService.addExercise(exercise).subscribe(
      (data) => {
        console.log(data);

        this.workoutExercisesComponent.addToChosenExercises(data);
        this.router.navigate(['workout-exercises']);
        },
      error => {
        this.error = error;
      }
    );
  }

  vanishError() {
    this.error = false;
  }

}
