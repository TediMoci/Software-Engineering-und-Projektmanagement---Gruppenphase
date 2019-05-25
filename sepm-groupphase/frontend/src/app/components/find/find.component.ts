import { Component, OnInit } from '@angular/core';
import {from, Observable} from 'rxjs';
import {FindService} from '../../services/find.service';
import {Dude} from '../../dtos/dude';
import {Exercise} from "../../dtos/Exercise";
import {ExerciseFilter} from "../../dtos/exercise-filter";
import {CourseFilter} from "../../dtos/course-filter";

@Component({
  selector: 'app-find',
  templateUrl: './find.component.html',
  styleUrls: ['./find.component.scss']
})
//todo: add filter to different categories
//todo: display entities form backend
export class FindComponent implements OnInit {

  //Inputs from html
  public category: string = "Exercise";
  public inputText: any;
  public filterExerciseCategory:string = "None";



  //Transfer Variables
  public inputTextActual: any;
  public filterExerciseCategoryActual:string = "None";

  entries: any;
  exercises: any;
  cources: any;

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  error: any;
  dude: Dude;

  //Filter Objects
  courceFilter: CourseFilter;
  exerciseFilter: ExerciseFilter;



  constructor(private findService:FindService) {}

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;

  }

  startSearch(category: string){
    console.log("searchvalue: " + this.inputText);

    if(this.inputText == undefined){
      this.inputTextActual = null;
    }else {
      this.inputTextActual = this.inputText;
    }

    switch (category) {
      case "Exercise":

        if(this.filterExerciseCategory=="None"){
          this.filterExerciseCategoryActual = null;
        } else {
          this.filterExerciseCategoryActual = this.filterExerciseCategory;
        }

        this.exerciseFilter = new ExerciseFilter(
          this.inputTextActual,
          this.filterExerciseCategoryActual);
        console.log("name: "+this.exerciseFilter.filter);
        this.findService.getAllExercisesFilterd(this.exerciseFilter).subscribe(
          (data) => {
            console.log('get all exercises');
            this.entries = data.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                return -1;
              }
              if (a.name > b.name) {
                return 1;
              }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case "Course":

        this.courceFilter = new CourseFilter(
          this.inputTextActual);

        console.log("name: "+this.courceFilter.filter);
        this.findService.getAllCoursesFilterd(this.courceFilter).subscribe(
          (data) => {
            console.log('get all courses');
            this.entries = data.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                return -1;
              }
              if (a.name > b.name) {
                return 1;
              }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case "Workout": console.log("Workout not implemented yet");
        break;
      case "Dudes": console.log("Dudes not implemented yet");
        break;
      case "Fitness Provider": console.log("Fitness Provider not implemented yet");
        break;
    }
  }
  setSelectedExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

}
