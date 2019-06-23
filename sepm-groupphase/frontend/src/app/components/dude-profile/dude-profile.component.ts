import {Component, OnInit} from '@angular/core';
import {LoginComponent} from '../login/login.component';
import {Globals} from '../../global/globals';
import {Dude} from '../../dtos/dude';
import {ProfileService} from '../../services/profile.service';
import {AuthService} from '../../services/auth.service';
import {Workout} from '../../dtos/workout';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {TrainingScheduleService} from '../../services/training-schedule.service';
import {WorkoutService} from '../../services/workout.service';
import {GetActiveTrainingSchedule} from '../../dtos/get-active-training-schedule';
import {ExerciseDone} from '../../dtos/exercise-done';

@Component({
  selector: 'app-dude-profile',
  templateUrl: './dude-profile.component.html',
  styleUrls: ['./dude-profile.component.scss'],
  providers: [LoginComponent]
})
export class DudeProfileComponent implements OnInit {

  error: any;
  imagePath: string;
  userName: string;
  sex: any;
  skill: string;
  age: number;
  height: number;
  weight: number;
  bmi: number;
  description: string;
  email: string;
  isPrivate: boolean;
  dude: Dude;
  dateNow: Date;
  globalTimeDelta: number;

  message: string;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  crop: boolean = false;
  timeStamp: any;

  // Active Schedule variables
  activeTs: GetActiveTrainingSchedule;
  ActiveTsId: number;
  ActiveTsVersion: number;
  startDate: Date;
  // original Schedule
  trainingSchedule: TrainingSchedule;
  tsName: string;
  tsDescription: string;
  tsDifficulty: number;
  tsWorkouts: any;
  tsIntervalLenght: number;
  tsTrue: boolean = false;
  // Sorted Workouts by day
  selectedWorkout: any = [];
  exercisesForWorkouts: any;
  exercisesForWorkoutsStatus: Array<ExerciseDone>;
  workoutsPerDay: Array<any> = [];
  // Display variables
  tabs: Array<string>;
  // Internal logic and ngModels
  doneChecked: boolean;
  selectedDay: number;
  noSchedule: boolean = false;

  constructor(private globals: Globals, private profileService: ProfileService, private workoutService: WorkoutService,
              private trainingScheduleService: TrainingScheduleService, private authService: AuthService) {}
  ngOnInit() {

    this.dateNow = new Date();
    console.log('Current Date: ' + this.dateNow);
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.setLinkPicture(this.dude.imagePath);
    console.log(this.imagePath);
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
    this.isPrivate = this.dude.isPrivate;
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
        this.noSchedule = false;
        this.activeTs = data;
        console.log(this.activeTs);
        this.ActiveTsId = this.activeTs.trainingScheduleId;
        this.ActiveTsVersion = this.activeTs.trainingScheduleVersion;
        this.startDate = new Date(this.activeTs.startDate);
        console.log('Active ts start date: ' + this.startDate);
        this.trainingScheduleService.getTrainingScheduleByIdandVersion(this.ActiveTsId, this.ActiveTsVersion)
          .subscribe(
            (data2) => {
              console.log('This is the copied training schedule: ');
              console.log('loaded Ts: ' + JSON.stringify(data2));
              this.trainingSchedule = data2;
              this.tsTrue = true;
              this.tsName = this.trainingSchedule.name;
              this.tsDescription = this.trainingSchedule.description;
              this.tsDifficulty = this.trainingSchedule.difficulty;
              this.tsIntervalLenght = this.trainingSchedule.intervalLength;
              this.globalTimeDelta = this.getDateDifference(this.dateNow, this.startDate);
              this.tabs = this.initTabs(this.trainingSchedule.intervalLength, this.globalTimeDelta);
              console.log('Trying to get trainingScheduleWorkouts');
              if (this.activeTs.adaptive === true) {
                this.trainingScheduleService.getWorkoutsOfCopyTrainingScheduleByIdAndVersion(
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
              } else {
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
              }
              this.profileService.getExercisesDoneByDudeId(this.dude.id).subscribe(
                (data3) => {
                  console.log('get done value of all exercises of dude with id ' + this.dude.id );
                  this.exercisesForWorkoutsStatus = data3;
                  console.log('loaded: ' + JSON.stringify(this.exercisesForWorkoutsStatus) );
                },
                error => {
                  this.error = error;
                }
              );
              this.trainingScheduleService.getWorkoutsOfTrainingScheduleById(
                this.trainingSchedule.id,
                this.trainingSchedule.version).subscribe(
                (data4) => {
                  console.log('get all workouts created of training schedule with id ' + this.trainingSchedule.id);
                  this.tsWorkouts = data4.sort(function (a, b) { // sort data alphabetically
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
        this.noSchedule = true;
      }
    );
  }

  getSelectedWorkoutExercises(workout: Workout) {
    this.workoutService.getExercisesOfWorkoutById(workout.id, workout.version).subscribe(
      (data) => {
        console.log('get all exercises of workout ' + workout.name);
        this.exercisesForWorkouts = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.name > b.name) {
            return 1;
          }
          return 0;
        });
        console.log();
      },
      error => {
        this.error = error;
      }
    );
  }

  getExercisesStatus(id: number, selectedDay: number) {
    for (const elem of this.exercisesForWorkoutsStatus) {
      if (elem.exerciseId === id && elem.day === selectedDay) {
        return elem.done;
      }
    }
  }

  setExercisesStatus(id: number, version: number,  selectedDay: number, value: boolean) {
    const exerciseDone: ExerciseDone = new ExerciseDone(
      this.activeTs.id,
      this.dude.id,
      this.trainingSchedule.id,
      this.trainingSchedule.version,
      id,
      version,
      this.selectedWorkout.id,
      this.selectedWorkout.version,
      this.selectedDay,
      value
    );

    this.trainingScheduleService.markExercisesAsDone(exerciseDone).subscribe(
      (data) => {
        console.log('Changed workout ' + id + ' of day ' + selectedDay + ' to ' + value.valueOf());
        for (const elem of this.exercisesForWorkoutsStatus) {
          if (elem.exerciseId === id && elem.day === selectedDay) {
            elem.done = value;
          }
        }
      },
      error => {
        this.error = error;
      });
    for (const elem of this.exercisesForWorkoutsStatus) {
      if (elem.exerciseId === id && elem.day === selectedDay) {
        elem.done = value;
      }
    }

  }

  initDuration(intervalLength: number, intervalReps: number) {
    return intervalLength * intervalReps;
  }

  initTabs(interval: number, daysPassed: number) {
    const tabs: Array<string> = [];
    for (let _i = 1; _i <= interval; _i++) {
      const prog = this.prog(interval, daysPassed);
      tabs.push('Day ' + (_i + prog));
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
      case 1:
        return 'Beginner';
      case 2:
        return 'Advanced';
      case 3:
        return 'Pro';
    }
  }

  deactivate() {
    this.trainingScheduleService.deleteActiveTrainingScheduleBYId(this.dude.id)
      .subscribe(() => {
          console.log('ended schedule successfully');
          this.tsTrue = false;
          this.noSchedule = true;
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
    const diff = Math.abs(date1.getTime() - date2.getTime());
    const delta =  Math.ceil( diff / (1000 * 3600 * 24));

    return delta - 1;

  }

  prog(interval: number, delta: number) {
    return Math.floor(delta / interval) * interval;
  }

  imageLoaded() {
    // show cropper
  }

  loadImageFailed() {
    // show message
    this.crop = true;
    this.message = 'Only images are supported.';

  }

  fileChangeEvent(event: any): void {
    this.crop = false;
    this.imageChangedEvent = event;
  }

  imageCropped(image: string) {
    this.croppedImage = image;
  }

  cropPicture() {
    if (this.croppedImage.length === 0) {
      return;
    }
    const imageFile = new File([this.croppedImage.file], 'file', { type: this.croppedImage.file.type });
    this.profileService.uploadPictureDudes(this.dude.id, imageFile).subscribe((data) => {
      console.log('i am here ' + data);
      },
      error => {
        this.error = error;
      }
    );
    this.authService.getUserByNameFromDude(this.dude.name).subscribe((data) => {
        this.dude.imagePath = data.imagePath;
        this.setLinkPicture(this.dude.imagePath);
        localStorage.setItem('loggedInDude', JSON.stringify(this.dude));
      },
      error => {
        this.error = error;
      }
    );
  }

   getLinkPicture() {
    if (this.timeStamp) {
      return this.imagePath + '?' + this.timeStamp;
    }
    return this.imagePath;
  }

  convertPrivate() {
    if (this.isPrivate === true) {
      return 'Private';
    } else {
      return 'Public';
    }
  }

   setLinkPicture(url: string) {
    this.imagePath = url;
    this.timeStamp = (new Date()).getTime();
    console.log(this.timeStamp);
  }
}
