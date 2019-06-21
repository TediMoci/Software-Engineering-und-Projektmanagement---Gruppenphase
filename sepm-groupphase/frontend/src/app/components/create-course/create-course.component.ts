import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {CreateCourseService} from '../../services/create-course.service';
import {Router} from '@angular/router';
import {CreateCourse} from '../../dtos/create-course';

@Component({
  selector: 'app-create-course',
  templateUrl: './create-course.component.html',
  styleUrls: ['./create-course.component.scss']
})
export class CreateCourseComponent implements OnInit {

  error: any;
  imagePath: string;
  imagePathCourse: string = 'assets/img/exercise.png';
  userName: string;
  registerForm: FormGroup;
  submitted: boolean = false;
  fitnessProvider: FitnessProvider;

  message: string;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  crop: boolean = false;
  constructor(private createCourseService: CreateCourseService , private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.createCourseService.setFileStorage(undefined);
    this.userName = this.fitnessProvider.name;
    this.imagePath = this.fitnessProvider.imagePath;
    this.registerForm = this.formBuilder.group({
      nameForCourse: ['', [Validators.required]],
      descriptionForCourse: ['', [Validators.required]]
    });
  }

  addCourse() {

    this.submitted = true;
    console.log(this.registerForm);

    const course: CreateCourse = new CreateCourse(
      this.registerForm.controls.nameForCourse.value,
      this.registerForm.controls.descriptionForCourse.value,
      this.fitnessProvider.id,
    );

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.createCourseService.addCourse(course).subscribe(
      (data) => {
        console.log(course);
        if (this.createCourseService.getFileStorage() !== undefined) {
          console.log(this.createCourseService.getFileStorage());
          this.createCourseService.uploadPictureForCourse(data.id, this.createCourseService.getFileStorage()).subscribe(
            () => {
            },
            error => {
              this.error = error;
            }
          );
        }
        this.router.navigate(['create-for-FP']);
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
    this.imagePathCourse = files.base64;
    const imageFile = new File([files.file], 'file', { type: files.file.type });
    console.log(imageFile);
    this.createCourseService.setFileStorage(imageFile);
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
