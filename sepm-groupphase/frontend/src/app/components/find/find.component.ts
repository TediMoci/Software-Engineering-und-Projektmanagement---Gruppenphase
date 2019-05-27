import { Component, OnInit } from '@angular/core';
import {from, Observable} from 'rxjs';
import {FindService} from '../../services/find.service';
import {Dude} from '../../dtos/dude';
import {Exercise} from '../../dtos/exercise';
import {ExerciseFilter} from '../../dtos/exercise-filter';
import {AuthService} from '../../services/auth.service';
import {FitnessProvider} from '../../dtos/fitness-provider';

@Component({
  selector: 'app-find',
  templateUrl: './find.component.html',
  styleUrls: ['./find.component.scss']
})

// todo: add filter to different categories
// todo: display entities form backend
export class FindComponent implements OnInit {

  // Inputs from html
  public category: string = "Exercise";
  public inputText: any;
  public filterExerciseCategory:string = "None";
  public filterCourse: any;


  // Transfer Variables
  public inputTextActual: any;
  public filterExerciseCategoryActual: string = "None";

  exercises: any;

  imagePath: string;
  userName: string;
  error: any;
  dude: Dude;
  fitnessProvider: FitnessProvider;
  exerciseFilter: ExerciseFilter;



  constructor(private findService: FindService, private authService: AuthService) {}

  ngOnInit() {
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'DUDE') {
      this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
      this.userName = this.dude.name;
      this.imagePath = '/assets/img/kugelfisch.jpg';
    } if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'FITNESS_PROVIDER') {
      this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
      this.userName = this.fitnessProvider.name;
      this.imagePath = '/assets/img/kugelfisch2.jpg';
    }
  }

  startSearch(category: string){
    console.log("searchvalue: " + this.inputText);

    if(this.inputText === undefined){
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
        this.findService.getAllExercisesFilterd(this.exerciseFilter).subscribe(
          (data) => {
            console.log('get all exercises');
            this.exercises = data.sort(function (a, b) { // sort data alphabetically
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
  setSelectedExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

}
