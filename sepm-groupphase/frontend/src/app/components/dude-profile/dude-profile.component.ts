import {Component, OnInit, Input, AfterViewInit} from '@angular/core';
import {LoginComponent} from '../login/login.component';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../../global/globals';
import {Dude} from '../../dtos/dude';
import {ProfileService} from '../../services/profile.service';
import {Workout} from "../../dtos/workout";
import {TrainingSchedule} from "../../dtos/trainingSchedule";
import {TrainingScheduleService} from "../../services/training-schedule.service";
import {ActiveTrainingSchedule} from "../../dtos/active-training-schedule";
import {WorkoutService} from "../../services/workout.service";

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

  //🐡 Active Schedule Stuff
  activeTs: ActiveTrainingSchedule;
  ActiveTsId: number;
  ActiveTsVersion: number;
  //🐡 Actual Schedule
  trainingSchedule: TrainingSchedule;
  tsName:string;
  tsDiscription:string;
  tsDifficulty: number;
  tsWorkouts: any;
  tsDuration: number;
  tsTrue: boolean = false;
  //🐡 Sorted Workouts by day
  selectedWorkout:any = [];
  exercisesForWorkouts:any;
  workoutsPerDay: Array<any> = [];
  //🐡 Display Stuff
  tabs: Array<string>;
  tabContent: any;


  constructor(private globals: Globals,private workoutService: WorkoutService, private trainingScheduleService:TrainingScheduleService, private profileService: ProfileService, private httpClient: HttpClient) {
  }

  ngOnInit() {

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
      (data)=> {
        console.log('checking for active training schedule ' + JSON.stringify(data));
        this.activeTs=data;
        this.ActiveTsId = this.activeTs.trainingScheduleId;
        this.ActiveTsVersion = this.activeTs.trainingScheduleVersion;
        this.trainingScheduleService.getTrainingScheduleByIdandVersion(this.ActiveTsId,this.ActiveTsVersion)
          .subscribe(
            (data)=>{
              console.log("loaded Ts: " + JSON.stringify(data));
              this.trainingSchedule = data;
              this.tsTrue = true;
              this.tsName = this.trainingSchedule.name;
              this.tsDiscription= this.trainingSchedule.description;
              this.tsDifficulty= this.trainingSchedule.difficulty;
              this.tsDuration = this.initDuration(this.trainingSchedule.intervalLength,this.activeTs.intervalRepetitions)
              this.tabs = this.initTabs(this.tsDuration);

              this.trainingScheduleService.getWorkoutsOfTrainingScheduleById(this.trainingSchedule.id,this.trainingSchedule.version).subscribe(
                (data) => {
                  console.log('get all workouts created of training schedule with id ' + this.trainingSchedule.id);
                  this.tsWorkouts= data.sort(function (a, b) { // sort data alphabetically
                    if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                      return -1;
                    }
                    if (a.name > b.name) {
                      return 1;
                    }
                    return 0;
                  });
                  console.log('loaded ' + JSON.stringify(this.tsWorkouts))
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

  getSelectedWorkoutExercises(workout: Workout){
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

  initDuration(intervalLenght:number, intervalReps:number){
    return intervalLenght * intervalReps;
  }

  initTabs(days: number){
    let tabs: Array<string> = [];
    for(var _i = 1 ; _i <= days; _i++){
      tabs.push('Day ' + _i);
    }
    console.log(tabs.toString());
    return tabs;
  }
  intOverview(){
    let elemsForDay: Array<Workout> = [];
    for (let i = 0; i < this.tsDuration; i++){
      for(let element of this.tsWorkouts){
        if((i+1)%7 == element.day){
          console.log("Day "+ (i+1) + ": " + element.name)
          elemsForDay.push(element)
        }
      }
      this.workoutsPerDay[i] = elemsForDay;
      elemsForDay = [];
    }
  }
  getContent(selected:number){
    let array:Array<any> = [];
    console.log("Getting all workouts of day " + selected)
    for(let e of this.tsWorkouts){
      if(e.day == (selected+1)){
        array.push(e);
      }
    }
    return array;
  }

  convertDifficulty(element:any){
    switch (element) {
      case 1: return "Beginner";
      case 2:return "Advanced";
      case 3:return "Pro";
    }
  }

  deactivate(){
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

}
