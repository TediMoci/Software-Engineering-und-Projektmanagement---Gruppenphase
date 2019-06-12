import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {TrainingSchedule} from '../../dtos/trainingSchedule';

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
  trainingSchedule: TrainingSchedule;
  constructor() { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.trainingSchedule = JSON.parse(localStorage.getItem('selectedTrainingSchedule'));
    this.tsName = this.trainingSchedule.name;
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
  }

}
