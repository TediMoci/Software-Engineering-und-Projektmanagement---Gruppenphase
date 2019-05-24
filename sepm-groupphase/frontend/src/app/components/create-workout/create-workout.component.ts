import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Dude} from '../../dtos/dude';
import {Router} from '@angular/router';
import {CreateWorkout} from '../../dtos/create-workout';
import {WorkoutEx} from '../../dtos/workoutEx';
import {CreateWorkoutService} from '../../services/create-workout.service';

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
  dude: Dude;
  name: string;
  description: string;
  calorie: number;
  constructor(private createWorkoutService: CreateWorkoutService , private formBuilder: FormBuilder, private router: Router ) {
  }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    if (this.addExercisesBoolean) {
      this.name = JSON.parse(localStorage.getItem('nameForWorkout'));
      this.description = JSON.parse(localStorage.getItem('descriptionForWorkout'));
      this.calorie = JSON.parse(localStorage.getItem('calorieConsumption'));
    }
    this.registerForm = this.formBuilder.group({
      nameForWorkout: ['', [Validators.required]],
      difficultyLevelWorkout: ['', [Validators.required]],
      descriptionForWorkout: ['', [Validators.required]],
      calorieConsumption: ['', [Validators.required]]
    });
  }

  addExercises() {
    this.addExercisesBoolean = true;
    localStorage.setItem('nameForWorkout', JSON.stringify(this.registerForm.controls.nameForWorkout.value));
    localStorage.setItem('descriptionForWorkout', JSON.stringify(this.registerForm.controls.descriptionForWorkout.value));
    localStorage.setItem('calorieConsumption', JSON.stringify(this.registerForm.controls.calorieConsumption.value));
  }
  addWorkout() {
    this.submitted = true;
    this.exercisesWorkout = JSON.parse(localStorage.getItem('chosenExercisesForWorkout'));
    console.log(this.exercisesWorkout);

    const workout: CreateWorkout = new CreateWorkout(
      this.registerForm.controls.nameForWorkout.value,
      this.registerForm.controls.descriptionForWorkout.value,
      this.registerForm.controls.difficultyLevelWorkout.value,
      this.registerForm.controls.calorieConsumption.value,
      this.exercisesWorkout,
      this.dude.id
    );

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }
    console.log(workout.workoutExercises);

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
