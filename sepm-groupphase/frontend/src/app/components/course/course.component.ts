import { Component, OnInit } from '@angular/core';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {Course} from '../../dtos/course';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch2.jpg';
  imagePath2: string = 'assets/img/exercise.png';
  userName: string;
  fitnessProvider: FitnessProvider;
  courseName: string;
  description: string;
  course: Course;
  constructor() { }

  ngOnInit() {

    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.course = JSON.parse(localStorage.getItem('selectedCourse'));
    this.userName = this.fitnessProvider.name;
    this.courseName = this.course.name;
    this.description = this.course.description;
  }

}
