import { Component, OnInit } from '@angular/core';
import {from, Observable} from 'rxjs';
import {FindService} from '../../services/find.service';
import {Dude} from '../../dtos/dude';
import {Exercise} from "../../dtos/Exercise";
import {ExerciseFilter} from "../../dtos/exercise-filter";

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
  public filterCourse: any;


  //Transfer Variables
  public inputTextActual: any;
  public filterExerciseCategoryActual:string = "None";

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
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
        console.log("Exercise not implemented yet");

        if(this.filterExerciseCategory=="None"){
          this.filterExerciseCategoryActual = null;
        } else {
          this.filterExerciseCategoryActual = this.filterExerciseCategory;
        }


        this.exerciseFilter = new ExerciseFilter(
          this.inputTextActual,
          this.filterExerciseCategoryActual);
        console.log("name: "+this.exerciseFilter.filter);
        this.findService.getAllExercisesFilterd(this.exerciseFilter);
            break;
      case "Course": console.log("Course not implemented yet");
            break;
      case "Workout": console.log("Workout not implemented yet");
        break;
      case "Dudes": console.log("Dudes not implemented yet");
        break;
      case "Fitness Provider": console.log("Fitness Provider not implemented yet");
        break;
    }
  }

}
