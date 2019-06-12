import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';

@Component({
  selector: 'app-edit-training-schedule',
  templateUrl: './edit-training-schedule.component.html',
  styleUrls: ['./edit-training-schedule.component.scss']
})
export class EditTrainingScheduleComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  constructor() { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
  }
}
