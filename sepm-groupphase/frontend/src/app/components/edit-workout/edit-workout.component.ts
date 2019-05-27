import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WorkoutExerciseDtoIn} from '../../dtos/workoutExerciseDtoIn';
import {Dude} from '../../dtos/dude';
import {Router} from '@angular/router';
import {Workout} from '../../dtos/workout';
import {EditWorkout} from '../../dtos/edit-workout';
import {WorkoutService} from '../../services/workout.service';
import {WorkoutExercise} from '../../dtos/workoutExercise';
import {WorkoutEx} from '../../dtos/workoutEx';
import {EditWorkoutService} from '../../services/edit-workout.service';
import {OwnWorkoutsService} from '../../services/own-workouts.service';
import {EditWorkoutExercisesComponent} from '../edit-workout-exercises/edit-workout-exercises.component';

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
  difficulty: number;
  calorie: number;
  beginner: boolean;
  advanced: boolean;
  pro: boolean;

  constructor(private editWorkoutService: EditWorkoutService, private editWorkoutExercisesComponent: EditWorkoutExercisesComponent, private workoutService: WorkoutService, private ownWorkoutService: OwnWorkoutsService , private formBuilder: FormBuilder, private router: Router ) {
  }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.prevRoute = JSON.parse(localStorage.getItem('previousRoute'));
    this.workout = JSON.parse(localStorage.getItem('selectedWorkout'));

    if (this.prevRoute === '/edit-workout-exercises' || this.prevRoute === '/create-exercise-for-workout') {
      this.name = JSON.parse(localStorage.getItem('nameForEditWorkout'));
      this.description = JSON.parse(localStorage.getItem('descriptionForEditWorkout'));
      this.calorie = JSON.parse(localStorage.getItem('calorieConsumptionForEditWorkout'));
      this.difficulty = JSON.parse(localStorage.getItem('difficultyEdit'));
    } else {
      this.name = this.workout.name;
      this.calorie = this.workout.calorieConsumption;
      this.description = this.workout.description;
      this.difficulty = this.workout.difficulty;
    }

    if (JSON.parse(localStorage.getItem('chosenExercisesForEditWorkout')) === 'empty') {
      this.workoutService.getExercisesOfWorkoutById(this.workout.id, this.workout.version).subscribe((data) => {
          this.workoutExercises = data;
          console.log(this.workoutExercises);
          localStorage.setItem('gottenExercises', JSON.stringify(this.workoutExercises));
        },
        error => {
          this.error = error;
        }
      );
    }

    if (this.difficulty === 1) {
      this.beginner = true;
    } if (this.difficulty === 2) {
      this.advanced = true;
    } if (this.difficulty === 3) {
      this.pro = true;
    }

    this.editWorkoutForm = this.formBuilder.group({
      nameForEditWorkout: ['', [Validators.required]],
      difficultyLevelEditWorkout: [this.difficulty, [Validators.required]],
      descriptionForEditWorkout: ['', [Validators.required]],
      calorieConsumptionEditWorkout: ['', [Validators.required]]
    });

  }

  editExercises() {
    localStorage.setItem('nameForEditWorkout', JSON.stringify(this.editWorkoutForm.controls.nameForEditWorkout.value));
    localStorage.setItem('descriptionForEditWorkout', JSON.stringify(this.editWorkoutForm.controls.descriptionForEditWorkout.value));
    localStorage.setItem('calorieConsumptionForEditWorkout', JSON.stringify(this.editWorkoutForm.controls.calorieConsumptionEditWorkout.value));
    localStorage.setItem('difficultyEdit', JSON.stringify(this.editWorkoutForm.controls.difficultyLevelEditWorkout.value));
  }
  editWorkout() {
    localStorage.setItem('previousRoute', JSON.stringify('/'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/'));

    this.submitted = true;
    this.newAddedExercises = JSON.parse(localStorage.getItem('chosenExercisesForEditWorkout'));

    if (this.newAddedExercises === null) {
      this.newAddedExercisesIn = [];
    } else {
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
    }

    const editWorkout: EditWorkout = new EditWorkout(
      this.workout.id,
      this.workout.version,
      this.editWorkoutForm.controls.nameForEditWorkout.value,
      this.editWorkoutForm.controls.descriptionForEditWorkout.value,
      this.editWorkoutForm.controls.difficultyLevelEditWorkout.value,
      this.editWorkoutForm.controls.calorieConsumptionEditWorkout.value,
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
        localStorage.removeItem('nameForEditWorkout');
        localStorage.removeItem('descriptionForEditWorkout');
        localStorage.removeItem('calorieConsumptionForEditWorkout');
        localStorage.removeItem('chosenExercisesForEditWorkout');
        localStorage.removeItem('difficultyEdit');
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

  backToCreateWorkout() {
    localStorage.setItem('previousRoute', JSON.stringify('/'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/'));
    localStorage.removeItem('nameForEditWorkout');
    localStorage.removeItem('descriptionForEditWorkout');
    localStorage.removeItem('calorieConsumptionForEditWorkout');
    localStorage.removeItem('chosenExercisesForEditWorkout');
    this.router.navigate(['myWorkouts']);
  }

}
