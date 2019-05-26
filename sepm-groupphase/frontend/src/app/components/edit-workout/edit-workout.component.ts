import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WorkoutExerciseDtoIn} from '../../dtos/workoutExerciseDtoIn';
import {Dude} from '../../dtos/dude';
import {OwnWorkoutsComponent} from '../own-workouts/own-workouts.component';
import {Router} from '@angular/router';
import {Workout} from '../../dtos/workout';
import {EditWorkout} from '../../dtos/edit-workout';
import {WorkoutService} from '../../services/workout.service';
import {WorkoutExercise} from '../../dtos/workoutExercise';
import {WorkoutEx} from '../../dtos/workoutEx';
import {EditWorkoutService} from '../../services/edit-workout.service';

@Component({
  selector: 'app-edit-workout',
  templateUrl: './edit-workout.component.html',
  styleUrls: ['./edit-workout.component.scss']
})
export class EditWorkoutComponent implements OnInit {

  error: any;
  imagePath: string = 'assets/img/kugelfisch.jpg';
  imagePathExercise: string = 'assets/img/exercise.png';
  userName: string;
  editWorkoutForm: FormGroup;
  submitted: boolean = false;
  prevRoute: string;


  workoutExercises: WorkoutExercise[] = [];
  newAddedExercises: WorkoutEx[];
  newAddedExercisesIn: WorkoutExerciseDtoIn[] = [];
  workout: Workout;

  dude: Dude;
  name: string;
  description: string;
  calorie: number;
  beginner: boolean;
  advanced: boolean;
  pro: boolean;

  constructor(private editWorkoutService: EditWorkoutService, private workoutService: WorkoutService, private ownWorkoutService: OwnWorkoutsComponent , private formBuilder: FormBuilder, private router: Router ) {
  }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.prevRoute = JSON.parse(localStorage.getItem('previousRoute'));
    this.workout = JSON.parse(localStorage.getItem('selectedWorkout'));

    if (this.prevRoute === '/edit-workout-exercises' || this.prevRoute === '/create-exercise-for-workout') {
      this.name = JSON.parse(localStorage.getItem('nameForWorkout'));
      this.description = JSON.parse(localStorage.getItem('descriptionForWorkout'));
      this.calorie = JSON.parse(localStorage.getItem('calorieConsumption'));
    } else {
      this.name = this.workout.name;
      this.calorie = this.workout.calorieConsumption;
      this.description = this.workout.description;
    }

    if (JSON.parse(localStorage.getItem('chosenExercisesForEditWorkout')) === 'empty') {
      this.workoutService.getExercisesOfWorkoutById(this.workout.id, this.workout.version).subscribe((data) => {
          this.workoutExercises = data;
        },
        error => {
          this.error = error;
        }
      );
    } // else {
      // this.workoutExercises = JSON.parse(localStorage.getItem('chosenExercisesForEditWorkout'));
   // }
    if (this.workout.difficulty === 1) {
      this.beginner = true;
    } if (this.workout.difficulty === 2) {
      this.advanced = true;
    } if (this.workout.difficulty === 3) {
      this.pro = true;
    }

    this.editWorkoutForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      difficulty: ['', [Validators.required]],
      description: ['', [Validators.required]],
      calorieConsumption: ['', [Validators.required]]
    });
  }

  editExercises() {
    localStorage.setItem('nameForWorkout', JSON.stringify(this.editWorkoutForm.controls.name.value));
    localStorage.setItem('descriptionForWorkout', JSON.stringify(this.editWorkoutForm.controls.description.value));
    localStorage.setItem('calorieConsumption', JSON.stringify(this.editWorkoutForm.controls.calorieConsumption.value));
  }
  editWorkout() {
    localStorage.setItem('previousRoute', JSON.stringify('/'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/'));

    this.submitted = true;
    this.newAddedExercises = JSON.parse(localStorage.getItem('chosenExercisesForEditWorkout'));

    for (let counter = 0; counter < this.newAddedExercises.length; counter++) {
      const currentEx = this.newAddedExercises[counter].exercise;
      this.newAddedExercisesIn.push(new WorkoutExerciseDtoIn(
        currentEx.id,
        currentEx.version,
        this.newAddedExercises[counter].exDuration,
        this.newAddedExercises[counter].repetitions,
        this.newAddedExercises[counter].sets
      ));
    }

    const editWorkout: EditWorkout = new EditWorkout(
      this.workout.id,
      this.workout.version,
      this.editWorkoutForm.controls.nameForWorkout.value,
      this.editWorkoutForm.controls.descriptionForWorkout.value,
      this.editWorkoutForm.controls.difficultyLevelWorkout.value,
      this.editWorkoutForm.controls.calorieConsumption.value,
      this.newAddedExercisesIn,
      this.workout.creatorId
    );

    if (this.editWorkoutForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editWorkoutService.editWorkout(editWorkout).subscribe(
      (data) => {
        console.log(data);
        this.router.navigate(['myWorkouts']);
      },
      error => {
        this.error = error;
      }
    );
  }
  vanishError() {
    this.error = false;
  }

}
