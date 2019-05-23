import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {CreateCourseService} from '../../services/create-course.service';
import {Router} from '@angular/router';
import {Course} from '../../dtos/course';

@Component({
  selector: 'app-create-course',
  templateUrl: './create-course.component.html',
  styleUrls: ['./create-course.component.scss']
})
export class CreateCourseComponent implements OnInit {

  error: any;
  imagePath: string = 'assets/img/kugelfisch.jpg';
  imagePathCourse: string = 'assets/img/exercise.png';
  userName: string;
  registerForm: FormGroup;
  submitted: boolean = false;
  fitnessProvider: FitnessProvider;

  constructor(private createCourseService: CreateCourseService , private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));

    this.userName = this.fitnessProvider.name;
    this.registerForm = this.formBuilder.group({
      nameForCourse: ['', [Validators.required]],
      descriptionForCourse: ['', [Validators.required]]
    });
  }

  addCourse() {

    this.submitted = true;
    console.log(this.registerForm);

    const course: Course = new Course(
      this.registerForm.controls.nameForCourse.value,
      this.registerForm.controls.descriptionForCourse.value,
      this.fitnessProvider.id,
    );

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.createCourseService.addCourse(course).subscribe(
      () => {
        console.log(course);
        this.router.navigate(['create-for-FP']);
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
