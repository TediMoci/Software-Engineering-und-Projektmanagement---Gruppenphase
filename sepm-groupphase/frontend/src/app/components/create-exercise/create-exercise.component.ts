import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CreateExercise} from '../../dtos/create-exercise';
import {Router} from '@angular/router';
import {CreateExerciseService} from '../../services/create-exercise.service';
@Component({
  selector: 'app-create-exercise',
  templateUrl: './create-exercise.component.html',
  styleUrls: ['./create-exercise.component.scss']
})
export class CreateExerciseComponent implements OnInit {
  error: any;
  imagePath: string = 'assets/img/kugelfisch.jpg';
  imgURL: string = 'assets/img/exercise.png';
  userName: string;
  registerForm: FormGroup;
  submitted: boolean = false;
  dude: Dude;
  muscleGroup: string[] = ['Other', 'Chest', 'Back', 'Arms', 'Shoulders', 'Legs', 'Calves', 'Core'];
  exerciseID: number;
  message: string;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  crop: boolean = false;
  constructor(private createExerciseService: CreateExerciseService , private formBuilder: FormBuilder, private router: Router ) {
  }

  ngOnInit() {
    this.createExerciseService.setFileStorage(undefined);
    localStorage.removeItem('exerciseID');
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.imagePath = this.dude.imagePath;
    this.userName = this.dude.name;
    this.registerForm = this.formBuilder.group({
      nameForExercise: ['', [Validators.required]],
      equipmentExercise: [''],
      categoryExercise: ['', [Validators.required]],
      descriptionForExercise: ['', [Validators.required]],
      muscleGroupExercise: ['', [Validators.required]],
      isPrivate: ['', [Validators.required]]
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
      this.dude.id,
      this.registerForm.controls.isPrivate.value
    );

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.createExerciseService.addExercise(exercise).subscribe(
      (data) => {
        if (this.createExerciseService.getFileStorage() !== undefined) {
          console.log('execute upload picture method');
          console.log(this.createExerciseService.getFileStorage());
          this.createExerciseService.uploadPictureForExercise(data.id, 1, this.createExerciseService.getFileStorage()).subscribe(
            () => {
            },
            error => {
              this.error = error;
            }
          );
        }
        this.router.navigate(['create']);
      },
      error => {
        this.error = error;
      }
    );
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
    this.imgURL = files.base64;
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
  vanishError() {
    this.error = false;
  }
  cropPicture() {
    this.uploadPicture(this.croppedImage);
  }

}
