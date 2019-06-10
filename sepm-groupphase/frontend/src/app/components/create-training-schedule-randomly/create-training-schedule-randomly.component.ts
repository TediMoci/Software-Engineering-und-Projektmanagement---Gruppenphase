import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Dude} from '../../dtos/dude';
import {FindService} from '../../services/find.service';
import {CreateTrainingScheduleService} from '../../services/create-training-schedule.service';

@Component({
  selector: 'app-create-training-schedule-randomly',
  templateUrl: './create-training-schedule-randomly.component.html',
  styleUrls: ['./create-training-schedule-randomly.component.scss']
})
export class CreateTrainingScheduleRandomlyComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  error: any;
  submitted: boolean = false;
  interval: number = 1;
  tsForm: FormGroup;

  intervalDays: string[] = [
    '1 Day',
    '2 Days',
    '3 Days',
    '4 Days',
    '5 Days',
    '6 Days',
    '7 Days'
  ];
  constructor(private router: Router, private findService: FindService,
              private formBuilder: FormBuilder,
              private createTrainingScheduleService: CreateTrainingScheduleService) { }
  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;

    this.tsForm = this.formBuilder.group({
      tsMinTarget: ['', [Validators.required]],
      tsMaxTarget: ['', [Validators.required]]
    });
  }

  createTrainingSchedule() {
    return null;
  }

  showCurrentDays(): string {
    switch (this.interval) {
      case 1:
        return '1 Day';
      case 2:
        return '2 Days';
      case 3:
        return '3 Days';
      case 4:
        return '4 Days';
      case 5:
        return '5 Days';
      case 6:
        return '6 Days';
      case 7:
        return '7 Days';
    }
  }

  setChosenInterval(days: string) {
    switch (days) {
      case '1 Day':
        this.interval = 1;
        break;
      case '2 Days':
        this.interval = 2;
        break;
      case '3 Days':
        this.interval = 3;
        break;
      case '4 Days':
        this.interval = 4;
        break;
      case '5 Days':
        this.interval = 5;
        break;
      case '6 Days':
        this.interval = 6;
        break;
      case '7 Days':
        this.interval = 7;
        break;
    }

  }
}
