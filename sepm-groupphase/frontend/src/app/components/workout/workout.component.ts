import {Component, OnInit} from '@angular/core';
import {Exercise} from '../../dtos/Exercise';
import {Dude} from '../../dtos/dude';
import {Workout} from '../../dtos/workout';

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
  workout: Workout;
  exercises: Exercise;
  dude: Dude;

  constructor() {
  }

  ngOnInit() {

    this.workout = JSON.parse(localStorage.getItem('selectedWorkout'));
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.workoutName = this.workout.name;
    this.difficulty = this.workout.difficulty;
    this.calories = this.workout.calorieConsumption;
    this.description = this.workout.description;
  }

}
