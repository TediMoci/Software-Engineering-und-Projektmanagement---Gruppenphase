import {Component, OnInit} from '@angular/core';
import {Dude} from '../../dtos/dude';
import {WorkoutExercise} from '../../dtos/workoutExercise';
import {Workout} from '../../dtos/workout';
import {WorkoutService} from '../../services/workout.service';
import {WorkoutWithRating} from '../../dtos/workout-with-rating';
import {GetByIDService} from '../../services/get-by-id.service';
import {version} from 'punycode';
import {RatingService} from '../../services/rating.service';

@Component({
  selector: 'app-workout',
  templateUrl: './workout.component.html',
  styleUrls: ['./workout.component.scss']
})
export class WorkoutComponent implements OnInit {

  imagePath: string;
  userName: string;
  workoutName: string;
  difficulty: string;
  calories: number;
  description: string;
  error: any;
  workout: WorkoutWithRating;
  exercises: WorkoutExercise[];
  dude: Dude;
  isPrivate: boolean;
  rating: number;
  ratingForItem: number = 0;
  constructor(private workoutService: WorkoutService, private getByIDService: GetByIDService, private ratingService: RatingService) {
  }

  ngOnInit() {

    this.workout = JSON.parse(localStorage.getItem('selectedWorkout'));
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));

    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.workoutName = this.workout.name;
    this.isPrivate = this.workout.isPrivate;
    if (this.workout.difficulty === 1) {
      this.difficulty = 'Beginner';
    } if (this.workout.difficulty === 2) {
      this.difficulty = 'Advanced';
    } if (this.workout.difficulty === 3) {
      this.difficulty = 'Pro';
    }
    this.calories = this.workout.calorieConsumption;
    this.description = this.workout.description;
    this.rating = this.workout.rating;

    this.workoutService.getExercisesOfWorkoutById(this.workout.id, this.workout.version).subscribe(
      (data) => {
        console.log('get all exercises of workout ' + this.workoutName);
        this.exercises = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
          if (a.name > b.name) {return 1; }
          return 0;
        });
        console.log(this.exercises);
      },
      error => {
        this.error = error;
      }
    );

  }

  setSelectedExercise(element: WorkoutExercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

  rateItem(item: any) {
    console.log('rating ' + typeof item + item.name  );
    this.ratingService.rateWorkout(this.dude.id, item, this.ratingForItem).subscribe(
      (dataFavorite) => {
        this.getWorkout(this.workout.id, this.workout.version);
      },
      errorFavorite => {
        this.error = errorFavorite;
      });
  }

  getWorkout(id: number, vs: number) {
    this.getByIDService.getWorkoutByID(id, vs).subscribe(
      (data) => {
        this.workout = data;
        localStorage.setItem('selectedWorkout', JSON.stringify(data));
        this.ngOnInit();
      },
      errorFavorite => {
        this.error = errorFavorite;
      });
  }
  convertPrivate() {
    if (this.isPrivate === true) {
      return 'Private';
    } else {
      return 'Public';
    }
  }
  vanishError() {
    this.error = false;
  }
}
