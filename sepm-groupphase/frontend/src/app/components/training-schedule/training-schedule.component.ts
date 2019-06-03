import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {FormControl} from '@angular/forms';


export interface ExampleTab {
  label: string;
  content: any;
}

@Component({
  selector: 'app-training-schedule',
  templateUrl: './training-schedule.component.html',
  styleUrls: ['./training-schedule.component.scss']
})

export class TrainingScheduleComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  tsName: string;
  tabs = ["Day 1"];
  tabContent: any;
  selected= new FormControl(0);
  trainingSchedule: TrainingSchedule;
  displayedWorkouts: Array<any>;

  constructor() {}

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.trainingSchedule = JSON.parse(localStorage.getItem('selectedTrainingSchedule'));
    this.tsName = this.trainingSchedule.name;
    this.userName = this.dude.name;
    console.log(this.trainingSchedule.trainingScheduleWorkouts)
    //this.tabContent = this.getContent(this.selected.value);

  }


  getContent(selected:number){
    //todo: not working cause day is undefined
    let array:Array<any> = null;
    console.log(this.trainingSchedule.trainingScheduleWorkouts[1].day)
    for(let e of this.trainingSchedule.trainingScheduleWorkouts){
      if(e.day == selected+1){
        array.push(e);
      }
    }
    return array;
  }



}
