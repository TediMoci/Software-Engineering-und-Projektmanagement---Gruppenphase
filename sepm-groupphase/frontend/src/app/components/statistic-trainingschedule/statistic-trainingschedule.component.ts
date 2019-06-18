import { Component, OnInit } from '@angular/core';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {Statistics} from '../../dtos/statistics';
import {Dude} from '../../dtos/dude';
import {FormControl} from '@angular/forms';
import {Workout} from '../../dtos/workout';
import {TrainingScheduleService} from '../../services/training-schedule.service';
import {WorkoutService} from '../../services/workout.service';
import {TrainingScheduleDto} from '../../dtos/trainingSchedule-dto';

@Component({
  selector: 'app-statistic-trainingschedule',
  templateUrl: './statistic-trainingschedule.component.html',
  styleUrls: ['./statistic-trainingschedule.component.scss']
})
export class StatisticTrainingscheduleComponent implements OnInit {

  totalHours: number;
  totalDays: number;
  totalCalories: number;
  totalIntervalRepetitions: number;
  strengthPercent: number;
  endurancePercent: number;
  otherPercent: number;
  nameOfStatistic: string;
  statistics: Statistics;

  imagePath: string;
  userName: string;
  dude: Dude;

  tabs: Array<string>;
  tabContent: any;
  selected = new FormControl(0);
  trainingSchedule: TrainingScheduleDto;
  tsWorkouts: any;
  exercisesForWorkouts: any;
  selectedWorkout: any = [];
  error: any;
  workoutsPerDay: Array<any> = [];

  constructor(private trainingScheduleService: TrainingScheduleService, private workoutService: WorkoutService) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.statistics = JSON.parse(localStorage.getItem('selectedStatistics'));

    this.nameOfStatistic = this.statistics.trainingScheduleDto.name;
    this.totalHours = this.statistics.totalHours;
    this.totalDays = this.statistics.totalDays;
    this.totalCalories = this.statistics.totalCalorieConsumption;
    this.totalIntervalRepetitions = this.statistics.totalIntervalRepetitions;
    this.strengthPercent = this.statistics.strengthPercent;
    this.endurancePercent = this.statistics.endurancePercent;
    this.otherPercent = this.statistics.otherPercent;
    this.trainingSchedule = this.statistics.trainingScheduleDto;

    this.tabs = this.initTabs(this.trainingSchedule.intervalLength);

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

  intOverview() {
    let elemsForDay: Array<Workout> = [];
    for (let i = 0; i < this.trainingSchedule.intervalLength; i++) {
      for (const element of this.tsWorkouts) {
        if (i + 1 === element.day) {
          console.log('Day ' + ( i + 1) + ': ' + element.name);
          elemsForDay.push(element);
        }
      }
      this.workoutsPerDay[i] = elemsForDay;
      elemsForDay = [];
    }
  }

  convertDifficulty(element: any) {
    switch (element) {
      case 1: return 'Beginner';
      case 2: return 'Advanced';
      case 3: return 'Pro';
    }
  }
  getContent(selected: number) {
    const array: Array <any> = [];
    console.log('Getting all workouts of day ' + selected);
    for (const e of this.tsWorkouts) {
      if (e.day === selected + 1) {
        array.push(e);
      }
    }
    return array;
  }

  initTabs(days: number) {
    const tabs: Array<string> = [];
    for (let _i = 1 ; _i <= days; _i++) {
      tabs.push('Day ' + _i);
    }
    console.log(tabs.toString());
    return tabs;
  }

  vanishError() {
    this.error = false;
  }
}
