import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {Router} from '@angular/router';
import {EditCourseService} from '../../services/edit-course.service';
import {Course} from '../../dtos/course';
import {Exercise} from '../../dtos/exercise';

@Component({
  selector: 'app-edit-course',
  templateUrl: './edit-course.component.html',
  styleUrls: ['./edit-course.component.scss']
})
export class EditCourseComponent implements OnInit {

  error: any;
  imagePath: string;
  imagePath2: string;
  userName: string;
  submitted: boolean = false;
  courseName: string;
  description: string;
  oldCourse: Course;
  editCourseForm: FormGroup;
  fitnessProvider: FitnessProvider;

  message: string;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  crop: boolean = false;
  constructor(private editCourseService: EditCourseService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {

    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.oldCourse = JSON.parse(localStorage.getItem('selectedCourse'));

    this.userName = this.fitnessProvider.name;
    this.imagePath = this.fitnessProvider.imagePath;
    this.imagePath2 = this.oldCourse.imagePath;
    this.editCourseForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]]
    });

    this.description = this.oldCourse.description;
    this.courseName = this.oldCourse.name;

  }

  editCourse() {
    this.submitted = true;

    const course: Course = new Course(
      this.oldCourse.id,
      this.editCourseForm.controls.name.value,
      this.editCourseForm.controls.description.value,
      this.oldCourse.creatorId,
      this.oldCourse.imagePath
    );

    if (this.editCourseForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editCourseService.editCourse(course, this.oldCourse).subscribe(
      (data) => {
        localStorage.setItem('selectedCourse', JSON.stringify(data));
        if (this.editCourseService.getFileStorage() !== undefined) {
          console.log(this.editCourseService.getFileStorage());
          this.editCourseService.uploadPictureForCourse(data.id, this.editCourseService.getFileStorage()).subscribe(
            () => {
            },
            error => {
              this.error = error;
            }
          );
        }
        this.router.navigate(['/myContent-FP']);
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
    this.imagePath2 = files.base64;
    const imageFile = new File([files.file], 'file', { type: files.file.type });
    console.log(imageFile);
    this.editCourseService.setFileStorage(imageFile);
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

