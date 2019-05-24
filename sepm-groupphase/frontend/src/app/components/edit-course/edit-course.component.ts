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
  imagePath: string = 'assets/img/kugelfisch2.jpg';
  imagePath2: string = 'assets/img/exercise.png';
  userName: string;
  submitted: boolean = false;
  courseName: string;
  description: string;
  oldCourse: Course;
  editCourseForm: FormGroup;
  fitnessProvider: FitnessProvider;
  constructor(private editCourseService: EditCourseService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {

    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.oldCourse = JSON.parse(localStorage.getItem('selectedCourse'));

    this.userName = this.fitnessProvider.name;
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
      this.oldCourse.creatorId
    );

    if (this.editCourseForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editCourseService.editCourse(course, this.oldCourse).subscribe(
      (data) => {
        localStorage.setItem('selectedCourse', JSON.stringify(data));
        this.router.navigate(['/myCourses']);
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

