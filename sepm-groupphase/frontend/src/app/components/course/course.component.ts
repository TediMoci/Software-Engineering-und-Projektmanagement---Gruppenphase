import { Component, OnInit } from '@angular/core';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {Course} from '../../dtos/course';
import {FindService} from '../../services/find.service';
import {CourseWithRating} from '../../dtos/course-with-rating';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseComponent implements OnInit {

  imagePath: string;
  imagePath2: string;
  userName: string;
  fitnessProvider: FitnessProvider;
  courseName: string;
  description: string;
  course: CourseWithRating;
  rating: number;
  constructor(private findService: FindService) { }

  ngOnInit() {

    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.course = JSON.parse(localStorage.getItem('selectedCourse'));
    this.userName = this.fitnessProvider.name;
    this.imagePath = this.fitnessProvider.imagePath;
    this.courseName = this.course.name;
    this.imagePath2 = this.course.imagePath;
    this.description = this.course.description;
    this.rating = this.course.rating;
  }

}
