import { Component, OnInit } from '@angular/core';
import {FindService} from '../../services/find.service';
import {Dude} from '../../dtos/dude';
import {Exercise} from '../../dtos/exercise';
import {AuthService} from '../../services/auth.service';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {ExerciseFilter} from '../../dtos/exercise-filter';
import {CourseFilter} from '../../dtos/course-filter';
import {WorkoutFilter} from '../../dtos/workout-filter';
import {Course} from '../../dtos/course';
import {Workout} from '../../dtos/workout';
import {WorkoutService} from '../../services/workout.service';
import {FitnessProviderFilter} from '../../dtos/fitness-provider-filter';
import {DudeFilter} from '../../dtos/dude-filter';

@Component({
  selector: 'app-find',
  templateUrl: './find.component.html',
  styleUrls: ['./find.component.scss']
})

// todo: add filter to different categories
// todo: display entities form backend
export class FindComponent implements OnInit {

  // Inputs from html
  public category: string = 'Exercise';
  public inputText: any;
  public filterExerciseCategory: string = 'None';
  public filterWorkoutDifficulty: string = 'None';
  public filterWorkoutCaloriesMin: string = '';
  public filterWorkoutCaloriesMax: string = '';
  public filterDudeSelfAssessment: string = 'None';

  // Transfer Variables
  public inputTextActual: any;
  public filterExerciseCategoryActual: string = 'None';
  public filterWorkoutDifficultyActual: string = 'None';
  public filterWorkoutCaloriesMinActual: string = '';
  public filterWorkoutCaloriesMaxActual: string = '';
  public filterDudeSelfAssessmentActual: string = 'None';


  entries: any;
  exercisesForWorkouts: any;

  imagePath: string;
  userName: string;
  error: any;
  dude: Dude;

  // Filter Objects
  courceFilter: CourseFilter;
  fitnessProvider: FitnessProvider;
  exerciseFilter: ExerciseFilter;
  workoutFilter: WorkoutFilter;
  fitnessProviderFilter: FitnessProviderFilter;
  dudeFilter: DudeFilter;



  constructor(private findService: FindService, private authService: AuthService, private workoutService: WorkoutService) {}

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

  startSearch(category: string) {
    console.log('searchvalue: ' + this.inputText);

    if (this.inputText === undefined) {
      this.inputTextActual = null;
    } else {
      this.inputTextActual = this.inputText;
    }

    switch (category) {
      case 'Exercise':

        if (this.filterExerciseCategory === 'None') {
          this.filterExerciseCategoryActual = null;
        } else {
          this.filterExerciseCategoryActual = this.filterExerciseCategory;
        }

        this.exerciseFilter = new ExerciseFilter(
          this.inputTextActual,
          this.filterExerciseCategoryActual);
        console.log('name: ' + this.exerciseFilter.filter);
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
      case 'Course':

        this.courceFilter = new CourseFilter(
          this.inputTextActual);

        console.log('name: ' + this.courceFilter.filter);
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
      case 'Workout':
        console.log('min: ' + this.filterWorkoutCaloriesMin);
        console.log('max: ' + this.filterWorkoutCaloriesMax);

        switch (this.filterWorkoutDifficulty) {
          case 'None': this.filterWorkoutDifficultyActual = null; break;
          case  'Beginner': this.filterWorkoutDifficultyActual = '1'; break;
          case  'Advanced': this.filterWorkoutDifficultyActual = '2'; break;
          case  'Pro': this.filterWorkoutDifficultyActual = '3'; break;
        }

        if (this.filterWorkoutCaloriesMin === '') {
          this.filterWorkoutCaloriesMinActual = null;
        } else {
          this.filterWorkoutCaloriesMinActual = this.filterWorkoutCaloriesMin;
        }

        if (this.filterWorkoutCaloriesMax === '') {
          this.filterWorkoutCaloriesMaxActual = null;
        } else {
          this.filterWorkoutCaloriesMaxActual = this.filterWorkoutCaloriesMax;
        }

        this.workoutFilter = new WorkoutFilter(
          this.inputTextActual,
          this.filterWorkoutDifficultyActual,
          this.filterWorkoutCaloriesMinActual,
          this.filterWorkoutCaloriesMaxActual);

        console.log('name: ' + this.workoutFilter.calorieLower);
        this.findService.getAllWorkoutsFilterd(this.workoutFilter).subscribe(
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
      case 'Dudes':

        switch (this.filterDudeSelfAssessment) {
          case 'None': this.filterDudeSelfAssessmentActual = null; break;
          case  'Beginner': this.filterDudeSelfAssessmentActual = '1'; break;
          case  'Advanced': this.filterDudeSelfAssessmentActual = '2'; break;
          case  'Pro': this.filterDudeSelfAssessmentActual = '3'; break;
        }
        this.dudeFilter = new DudeFilter(
          this.inputTextActual,
          this.filterDudeSelfAssessmentActual);

        console.log('name: ' + this.dudeFilter.filter);
        this.findService.getAllDudesFiltered(this.dudeFilter).subscribe(
          (data) => {
            console.log('get all dudes');
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
      case 'Fitness Provider':

        this.fitnessProviderFilter = new FitnessProviderFilter(
          this.inputTextActual);

        console.log('name: ' + this.fitnessProviderFilter.filter);
        this.findService.getAllFitnessProviderFiltered(this.fitnessProviderFilter).subscribe(
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
    }
  }
  getSelectedWorkoutExercises(workout: Workout) {
    this.workoutService.getExercisesOfWorkoutById(workout.id, workout.version).subscribe(
      (data) => {
        console.log('get all exercises of workout ' + workout.name);
        this.exercisesForWorkouts = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
          if (a.name > b.name) {return 1; }
          return 0;
        });
        console.log(this.exercisesForWorkouts.toString().toString());
      },
      error => {
        this.error = error;
      }
    );
  }

  convertDifficulty(element: any) {
    switch (element) {
      case 1: return 'Beginner';
      case 2: return 'Advanced';
      case 3: return 'Pro';
    }
  }

  setSelectedExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }
  setSelectedCourse(element: Course) {
    localStorage.setItem('selectedCourse', JSON.stringify(element));
  }
  setSelectedWorkout(element: Workout) {
    localStorage.setItem('selectedWorkout', JSON.stringify(element));
    console.log(localStorage.getItem('selectedWorkout'));
  }
  resetResults() {
    this.entries = null;
  }

}
