import { Component, OnInit } from '@angular/core';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {Course} from '../../dtos/course';
import {Dude} from '../../dtos/dude';

@Component({
  selector: 'app-course-dude-view',
  templateUrl: './course-dude-view.component.html',
  styleUrls: ['./course-dude-view.component.scss']
})
export class CourseDudeViewComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  imagePath2: string = 'assets/img/exercise.png';
  userName: string;
  dude: Dude;
  fitnessProviderName: string;
  fitnessProvider: FitnessProvider;
  courseName: string;
  description: string;
  course: Course;
  constructor() { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.fitnessProvider = JSON.parse(localStorage.getItem('selectedFitnessProvider'));
    this.fitnessProviderName = this.fitnessProvider.name;
    this.course = JSON.parse(localStorage.getItem('selectedCourse'));
    this.courseName = this.course.name;
    this.description = this.course.description;
  }

}