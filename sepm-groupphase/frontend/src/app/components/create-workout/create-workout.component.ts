import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Dude} from '../../dtos/dude';
import {Router} from '@angular/router';
import {CreateWorkout} from '../../dtos/create-workout';
import {WorkoutEx} from '../../dtos/workoutEx';
import {CreateWorkoutService} from '../../services/create-workout.service';
import {WorkoutExerciseDtoIn} from '../../dtos/workoutExerciseDtoIn';

@Component({
  selector: 'app-create-workout',
  templateUrl: './create-workout.component.html',
  styleUrls: ['./create-workout.component.scss']
})
export class CreateWorkoutComponent implements OnInit {

  error: any;
  imagePath: string = 'assets/img/kugelfisch.jpg';
  imagePathExercise: string = 'assets/img/exercise.png';
  userName: string;
  registerForm: FormGroup;
  submitted: boolean = false;
  addExercisesBoolean: boolean = false;
  exercisesWorkout: WorkoutEx[];
  exercisesWorkoutIn: WorkoutExerciseDtoIn[] = [];
  dude: Dude;
  name: string;
  description: string;
  calorie: number;
  prevRoute: string;

  constructor(private createWorkoutService: CreateWorkoutService , private formBuilder: FormBuilder, private router: Router ) {
  }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.prevRoute = JSON.parse(localStorage.getItem('previousRoute'));
    console.log(this.prevRoute);

     if (this.prevRoute === '/workout-exercises' || this.prevRoute === '/create-exercise-for-workout') {
      this.name = JSON.parse(localStorage.getItem('nameForWorkout'));
      this.description = JSON.parse(localStorage.getItem('descriptionForWorkout'));
      this.calorie = JSON.parse(localStorage.getItem('calorieConsumption'));
    }

     localStorage.setItem('previousRoute', JSON.stringify('/create-workout'));

    this.registerForm = this.formBuilder.group({
      nameForWorkout: ['', [Validators.required]],
      difficultyLevelWorkout: ['', [Validators.required]],
      descriptionForWorkout: ['', [Validators.required]],
      calorieConsumption: ['', [Validators.required]]
    });
  }

  addExercises() {
    localStorage.setItem('nameForWorkout', JSON.stringify(this.registerForm.controls.nameForWorkout.value));
    localStorage.setItem('descriptionForWorkout', JSON.stringify(this.registerForm.controls.descriptionForWorkout.value));
    localStorage.setItem('calorieConsumption', JSON.stringify(this.registerForm.controls.calorieConsumption.value));
  }
    addWorkout() {
    localStorage.setItem('previousRoute', JSON.stringify('/'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/'));

    this.submitted = true;
    this.exercisesWorkout = JSON.parse(localStorage.getItem('chosenExercisesForWorkout'));

      for (let counter = 0; counter < this.exercisesWorkout.length; counter++) {
        const currentEx = this.exercisesWorkout[counter].exercise;
        this.exercisesWorkoutIn.push(new WorkoutExerciseDtoIn(
          currentEx.id,
          currentEx.version,
          this.exercisesWorkout[counter].exDuration,
          this.exercisesWorkout[counter].repetitions,
          this.exercisesWorkout[counter].sets
        ));
      }
    console.log(this.exercisesWorkoutIn);

    const workout: CreateWorkout = new CreateWorkout(
      this.registerForm.controls.nameForWorkout.value,
      this.registerForm.controls.descriptionForWorkout.value,
      this.registerForm.controls.difficultyLevelWorkout.value,
      this.registerForm.controls.calorieConsumption.value,
      this.exercisesWorkoutIn,
      this.dude.id
    );

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }
    console.log(workout.workoutExerciseDtoIn);

    this.createWorkoutService.addWorkout(workout).subscribe(
      (data) => {
        console.log(data);
        this.router.navigate(['create']);
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
