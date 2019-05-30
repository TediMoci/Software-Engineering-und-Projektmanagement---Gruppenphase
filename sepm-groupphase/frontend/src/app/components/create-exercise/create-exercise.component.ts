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
  message: string;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  crop: boolean = false;
  constructor(private createExerciseService: CreateExerciseService , private formBuilder: FormBuilder, private router: Router ) {
  }

  ngOnInit() {
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
      () => {
        console.log(exercise);
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
    console.log(this.imgURL);
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
