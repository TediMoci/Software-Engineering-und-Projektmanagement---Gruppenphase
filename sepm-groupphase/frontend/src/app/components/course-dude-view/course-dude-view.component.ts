import { Component, OnInit } from '@angular/core';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {Course} from '../../dtos/course';
import {Dude} from '../../dtos/dude';
import {CourseWithRating} from '../../dtos/course-with-rating';
import {FindService} from '../../services/find.service';

@Component({
  selector: 'app-course-dude-view',
  templateUrl: './course-dude-view.component.html',
  styleUrls: ['./course-dude-view.component.scss']
})
export class CourseDudeViewComponent implements OnInit {

  imagePath: string;
  imagePath2: string;
  userName: string;
  dude: Dude;
  fitnessProviderName: string;
  fitnessProvider: FitnessProvider;
  courseName: string;
  description: string;
  course: CourseWithRating;
  rating: number;
  error: any;
  constructor(private findService: FindService) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.course = JSON.parse(localStorage.getItem('selectedCourse'));
    this.courseName = this.course.name;
    this.imagePath2 = this.course.imagePath;
    this.description = this.course.description;
    this.rating = this.course.rating;
    this.findService.getOneFitnessProvider(this.course.creatorId).subscribe(
      (data) => {
        this.fitnessProvider = data;
        this.fitnessProviderName = this.fitnessProvider.name;
        console.log('Loaded FP: ' + this.fitnessProvider.name);
        localStorage.setItem('selectedFitnessProvider', JSON.stringify(data));
        console.log('FP in LS' + localStorage.getItem('selectedFitnessProvider'));
      },
      error => {
        this.error = error;
      }
    );
  }
}
