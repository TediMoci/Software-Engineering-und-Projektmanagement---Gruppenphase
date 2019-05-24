import {Component, OnInit} from '@angular/core';
import {Dude} from '../../dtos/dude';
import {WorkoutExercise} from '../../dtos/workoutExercise';
import {Workout} from '../../dtos/workout';
import {WorkoutService} from '../../services/workout.service';

@Component({
  selector: 'app-workout',
  templateUrl: './workout.component.html',
  styleUrls: ['./workout.component.scss']
})
export class WorkoutComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  workoutName: string;
  difficulty: string;
  calories: number;
  description: string;
  error: any;
  workout: Workout;
  exercises: WorkoutExercise[];
  dude: Dude;

  constructor(private workoutService: WorkoutService) {
  }

  ngOnInit() {

    this.workout = JSON.parse(localStorage.getItem('selectedWorkout'));
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));

    this.userName = this.dude.name;
    this.workoutName = this.workout.name;
    this.difficulty = this.workout.difficulty;
    this.calories = this.workout.calorieConsumption;
    this.description = this.workout.description;

    this.workoutService.getExercisesOfWorkoutById(this.workout.id, this.workout.version).subscribe(
      (data) => {
        console.log('get all exercises of workout ' + this.workoutName);
        this.exercises = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1;}
          if (a.name > b.name) {return 1;}
          return 0;
        });
        console.log(this.exercises);
      },
      error => {
        this.error = error;
      }
    );

  }

  setSelectedExercise(element: WorkoutExercise){
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

}