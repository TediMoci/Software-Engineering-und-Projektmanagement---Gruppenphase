import {Component, OnInit} from '@angular/core';
import {Dude} from '../../dtos/dude';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TrainingScheduleService} from '../../services/training-schedule.service';
import {Workout} from '../../dtos/workout';
import {WorkoutService} from '../../services/workout.service';
import {ActiveTrainingSchedule} from '../../dtos/active-training-schedule';
import {GetActiveTrainingSchedule} from '../../dtos/get-active-training-schedule';

@Component({
  selector: 'app-training-schedule',
  templateUrl: './training-schedule.component.html',
  styleUrls: ['./training-schedule.component.scss']
})

export class TrainingScheduleComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  tsName: string;
  tabs: Array<string>;
  tabContent: any;
  selected = new FormControl(0);
  trainingSchedule: TrainingSchedule;
  tsWorkouts: any;
  error: any;
  exercisesForWorkouts: any;
  toSaveActiveTs: ActiveTrainingSchedule;
  newActiveTs: GetActiveTrainingSchedule;
  selectedWorkout: any = [];

  workoutsPerDay: Array<any> = [];

  // input for active
  submitted: boolean = false;
  tsForm: FormGroup;
  intervalRepetitions: number;

  constructor(private trainingScheduleService: TrainingScheduleService,
              private workoutService: WorkoutService,
              private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.trainingSchedule = JSON.parse(localStorage.getItem('selectedTrainingSchedule'));
    this.tsName = this.trainingSchedule.name;
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.tabs = this.initTabs(this.trainingSchedule.intervalLength);
    console.log(this.trainingSchedule.trainingScheduleWorkouts);

    this.tsForm = this.formBuilder.group({
      adaptive: [false],
      repetitions: ['', Validators.required]
    });

    this.trainingScheduleService.getWorkoutsOfTrainingScheduleById(this.trainingSchedule.id, this.trainingSchedule.version).subscribe(
      (data) => {
        console.log('get all workouts created of training schedule with id ' + this.trainingSchedule.id);
        this.tsWorkouts = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.name > b.name) {
            return 1;
          }
          return 0;
        });
        console.log('loaded ' + JSON.stringify(this.tsWorkouts));
        this.tabContent = this.getContent(this.selected.value);
        this.intOverview();
      },
      error => {
        this.error = error;
      }
    );
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
        console.log();
      },
      error => {
        this.error = error;
      }
    );
  }

  makeTsActive() {
    this.submitted = true;
    this.toSaveActiveTs = new ActiveTrainingSchedule(
      this.dude.id,
      this.trainingSchedule.id,
      this.trainingSchedule.version,
      this.tsForm.controls.repetitions.value,
      this.tsForm.controls.adaptive.value);

    console.log('Make trainingSchedule active: ' + JSON.stringify(this.toSaveActiveTs));
    this.trainingScheduleService.saveActiveSchedule(this.toSaveActiveTs).subscribe(
      (data) => {
        this.newActiveTs = data;
        console.log(this.newActiveTs);
      },
      error => {
        this.error = error;
      }
    );
  }

  intOverview() {
    let elemsForDay: Array<Workout> = [];
    for (let i = 0; i < this.trainingSchedule.intervalLength; i++) {
      for (const e of this.tsWorkouts) {
        if (i + 1 === e.day) {
          console.log('Day ' + (i + 1) + ': ' + e.name);
          elemsForDay.push(e);
        }
      }
      this.workoutsPerDay[i] = elemsForDay;
      elemsForDay = [];
    }
  }

  initTabs(days: number) {
    const tabs: Array<string> = [];
    for (let _i = 1 ; _i <= days; _i++) {
      tabs.push('Day ' + _i);
    }
    console.log(tabs.toString());
    return tabs;
  }

  getContent(selected: number) {
    const array: Array<any> = [];
    console.log('Getting all workouts of day ' + selected);
    for (const e of this.tsWorkouts) {
      if (e.day === selected + 1) {
        array.push(e);
      }
    }
    return array;
  }

  convertDifficulty(element: any) {
    switch (element) {
      case 1: return 'Beginner';
      case 2: return 'Advanced';
      case 3: return 'Pro';
    }
  }
  vanishError() {
    this.error = false;
  }

}
