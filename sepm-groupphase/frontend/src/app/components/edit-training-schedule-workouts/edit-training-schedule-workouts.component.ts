import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TrainingScheduleWorkout} from '../../dtos/trainingScheduleWorkout';
import {TrainingScheduleWork} from '../../dtos/trainingScheduleWork';
import {Router} from '@angular/router';
import {WorkoutEx} from '../../dtos/workoutEx';
import {Exercise} from '../../dtos/exercise';
import {Workout} from '../../dtos/workout';

@Component({
  selector: 'app-edit-training-schedule-workouts',
  templateUrl: './edit-training-schedule-workouts.component.html',
  styleUrls: ['./edit-training-schedule-workouts.component.scss']
})
export class EditTrainingScheduleWorkoutsComponent implements OnInit {
  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;

  registerForm: FormGroup;
  index: number;
  gottenWorkouts: TrainingScheduleWorkout[] = [];
  chosenWorkouts: TrainingScheduleWork[] = [];

  trainingWorkForm: FormGroup;
  submitted: boolean = false;
  workoutName: string;
  workouts: any;
  error: any;

  constructor(private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    if ((JSON.parse(localStorage.getItem('previousRoute')) === '/edit-training-schedule') && (JSON.parse(localStorage.getItem('previousPreviousRoute')) === '/edit-training-workouts')) {
      if (localStorage.getItem('chosenWorkoutsForEditTS') !== null) {
        console.log('return edit-training-schedule ' +  this.chosenWorkouts);
        this.chosenWorkouts = JSON.parse(localStorage.getItem('chosenWorkoutsForEditTS'));
      }
    }

    localStorage.setItem('previousRoute', JSON.stringify('/edit-training-workouts'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/edit-training-workouts'));

    if (JSON.parse(localStorage.getItem('firstAccess')) === 'true') {
      this.gottenWorkouts = JSON.parse(localStorage.getItem('gottenWorkouts'));
      if (!(this.gottenWorkouts === null)) {
        for (let counter = 0; counter < this.gottenWorkouts.length; counter++) {
          this.chosenWorkouts.push(new TrainingScheduleWork(
            new Workout(
              this.gottenWorkouts[counter].id,
              this.gottenWorkouts[counter].version,
              this.gottenWorkouts[counter].name,
              this.gottenWorkouts[counter].description,
              this.gottenWorkouts[counter].difficulty,
              this.gottenWorkouts[counter].calorieConsumption,
              this.gottenWorkouts[counter].creatorId),
            this.gottenWorkouts[counter].day));
        }
      }
      localStorage.setItem('firstAccess', JSON.stringify('false'));
      localStorage.setItem('chosenWorkoutsForEditTS', JSON.stringify(this.chosenWorkouts));

      console.log('got gottenWorkouts from edit training schedule');
      console.log(localStorage.getItem('chosenWorkoutsForEditTS'));
    }

    this.registerForm = this.formBuilder.group({
      name: [''],
    });

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.trainingWorkForm = this.formBuilder.group({
      day: ['', [Validators.required]]
    });
  }

  addToChosenWorkouts(element: Workout) {
    console.log(element);
    console.log('current status' +  localStorage.getItem('chosenWorkoutsForEditTS'));
    this.chosenWorkouts.push(new TrainingScheduleWork(element, 1));
    localStorage.setItem('chosenWorkoutsForEditTS', JSON.stringify(this.chosenWorkouts));
    console.log('add workout' + localStorage.getItem('chosenWorkoutsForEditTS'));
  }

  removeFromChosenWorkouts(element: TrainingScheduleWork) {
    this.index = this.chosenWorkouts.indexOf(element);
    this.chosenWorkouts.splice(this.index, 1);
    console.log('delete' +  this.chosenWorkouts);
    localStorage.setItem('chosenWorkoutsForEditTS', JSON.stringify(this.chosenWorkouts));
    console.log('delete' + localStorage.getItem('chosenWorkoutsForEditTS') );
  }

  setWorkData(element: TrainingScheduleWork) {
    this.index = this.chosenWorkouts.indexOf(element);
    if (!(this.trainingWorkForm.controls.day.value === '')) {
      this.chosenWorkouts[this.index].day = this.trainingWorkForm.controls.day.value;
    }
  }

  saveWorkoutsTemporarily() {
    console.log('save workouts temporarily');
    localStorage.setItem('chosenWorkoutsForEditTS', JSON.stringify(this.chosenWorkouts));
    console.log(JSON.parse(localStorage.getItem('chosenWorkoutsForEditTS')));
  }

  backToCreateTrainingSchedule() {
    localStorage.removeItem('chosenWorkoutsForEditTS');
    this.router.navigate(['/edit-training-schedule']);
  }

  findWorkoutsByName() {

  }

  vanishError() {
    this.error = false;
  }

}
