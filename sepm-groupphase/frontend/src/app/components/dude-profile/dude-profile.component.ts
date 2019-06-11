import {Component, OnInit} from '@angular/core';
import {LoginComponent} from '../login/login.component';
import {Globals} from '../../global/globals';
import {Dude} from '../../dtos/dude';
import {ProfileService} from '../../services/profile.service';
import {Workout} from '../../dtos/workout';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {TrainingScheduleService} from '../../services/training-schedule.service';
import {ActiveTrainingSchedule} from '../../dtos/active-training-schedule';
import {WorkoutService} from '../../services/workout.service';
import {GetActiveTrainingSchedule} from '../../dtos/get-active-training-schedule';

@Component({
  selector: 'app-dude-profile',
  templateUrl: './dude-profile.component.html',
  styleUrls: ['./dude-profile.component.scss'],
  providers: [LoginComponent]
})
export class DudeProfileComponent implements OnInit {

  error: any;
  imagePath: string = 'assets/img/kugelfisch.jpg';
  userName: string;
  sex: any;
  skill: string;
  age: number;
  height: number;
  weight: number;
  bmi: number;
  description: string;
  email: string;
  dude: Dude;
  dateNow: Date;
  globalTimeDelta: number;

  // Active Schedule variables
  activeTs: GetActiveTrainingSchedule;
  ActiveTsId: number;
  ActiveTsVersion: number;
  startDate: Date;
  // original Schedule
  trainingSchedule: TrainingSchedule;
  tsName: string;
  tsDiscription: string;
  tsDifficulty: number;
  tsWorkouts: any;
  tsDuration: number;
  tsIntervalLenght: number;
  tsTrue: boolean = false;
  // Sorted Workouts by day
  selectedWorkout: any = [];
  exercisesForWorkouts: any;
  workoutsPerDay: Array<any> = [];
  // Display variables
  tabs: Array<string>;

  constructor(private globals: Globals,
              private workoutService: WorkoutService,
              private trainingScheduleService: TrainingScheduleService,
              private profileService: ProfileService) {
  }

  ngOnInit() {
    this.dateNow = new Date();
    console.log('Current Date: ' + this.dateNow);
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));

    this.userName = this.dude.name;

    if (this.dude.selfAssessment === 1) {
      this.skill = 'Beginner';
    } else if (this.dude.selfAssessment === 2) {
      this.skill = 'Advanced';
    } else {
      this.skill = 'Pro';
    }

    this.height = this.dude.height;
    this.weight = this.dude.weight;
    this.sex = this.dude.sex;
    this.description = this.dude.description;
    this.email = this.dude.email;

    this.profileService.getAge(this.dude.birthday, this.dude.name).subscribe(
      (data) => {
        console.log('calculate age of dude with name ' + this.dude.name);
        this.age = data;
      },
      error => {
        this.error = error;
      }
    );

    this.profileService.getBmi(this.dude.height, this.dude.weight, this.dude.name).subscribe(
      (data) => {
        console.log('calculate bmi of dude with name ' + this.dude.name);
        this.bmi = data;
      },
        error => {
        this.error = error;
        }
    );

    this.profileService.getActiveSchedule(this.dude.id).subscribe(
      (data) => {
        console.log('checking for active training schedule ' + JSON.stringify(data));
        this.activeTs = data;
        console.log(this.activeTs);
        this.ActiveTsId = this.activeTs.trainingScheduleId;
        this.ActiveTsVersion = this.activeTs.trainingScheduleVersion;
        this.startDate = new Date(this.activeTs.startDate);
        console.log('Active ts start date: ' + this.startDate);
        this.trainingScheduleService.getTrainingScheduleByIdandVersion(this.ActiveTsId, this.ActiveTsVersion)
          .subscribe(
            (data2) => {
              console.log('loaded Ts: ' + JSON.stringify(data2));
              this.trainingSchedule = data2;
              this.tsTrue = true;
              this.tsName = this.trainingSchedule.name;
              this.tsDiscription = this.trainingSchedule.description;
              this.tsDifficulty = this.trainingSchedule.difficulty;
              this.tsIntervalLenght = this.trainingSchedule.intervalLength;
              this.globalTimeDelta = this.getDateDifference( this.dateNow, this.startDate);
              this.tabs = this.initTabs(this.trainingSchedule.intervalLength, this.globalTimeDelta);
              this.trainingScheduleService.getWorkoutsOfTrainingScheduleById(
                this.trainingSchedule.id,
                this.trainingSchedule.version).subscribe(
                (data3) => {
                  console.log('get all workouts created of training schedule with id ' + this.trainingSchedule.id);
                  this.tsWorkouts = data3.sort(function (a, b) { // sort data alphabetically
                    if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                      return -1;
                    }
                    if (a.name > b.name) {
                      return 1;
                    }
                    return 0;
                  });
                  console.log('loaded ' + JSON.stringify(this.tsWorkouts));
                  this.intOverview();
                },
                error => {
                  this.error = error;
                }
              );
            },
            error => {
              this.error = error;
            }
          );
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

  initDuration(intervalLength: number, intervalReps: number) {
    return intervalLength * intervalReps;
  }

  initTabs(interval: number, daysPassed: number) {
    const tabs: Array<string> = [];
    for (let _i = 1 ; _i <= interval; _i++) {
      const prog = this.prog(interval, daysPassed);
      tabs.push('Day ' + (_i + prog ));
    }
    console.log(tabs.toString());
    return tabs;
  }

  intOverview() {
    let elemsForDay: Array<Workout> = [];
    for (let i = 0; i < this.tsIntervalLenght; i++) {
      for (const element of this.tsWorkouts) {
        if ((i + 1) % 7 === element.day) {
          console.log('Day ' + (i + 1) + ': ' + element.name);
          elemsForDay.push(element);
        }
      }
      this.workoutsPerDay[i] = elemsForDay;
      elemsForDay = [];
    }
  }

  convertDifficulty(element: number) {
    switch (element) {
      case 1: return 'Beginner';
      case 2: return 'Advanced';
      case 3: return 'Pro';
    }
  }

  deactivate() {
    this.trainingScheduleService.deleteActiveTrainingScheduleBYId(this.dude.id)
      .subscribe(() => {
          console.log('ended schedule successfully');
          this.tsTrue = false;
          this.ngOnInit();
        },
        error => {
          this.error = error;
        }
      );
  }

  vanishError() {
    this.error = false;
  }
  getDateDifference(date1: Date, date2: Date) {
    const diff = Math.abs(date1.getTime() - date2.getTime())
    const delta =  Math.ceil( diff / (1000 * 3600 * 24));

    return delta - 1;

  }
  prog(interval: number, delta: number) {
    return Math.floor(delta / interval) * interval;
  }


}
