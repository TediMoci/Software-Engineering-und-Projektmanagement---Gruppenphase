import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Dude} from '../../dtos/dude';
import {FindService} from '../../services/find.service';
import {CreateTrainingScheduleService} from '../../services/create-training-schedule.service';
import {CreateTraingsPlanRandom} from '../../dtos/create-traings-plan-random';
import {CreateTrainingScheduleRandomService} from '../../services/create-training-schedule-random.service';

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
  traingSchedule: CreateTraingsPlanRandom;

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
              private createTrainingScheduleRandomService: CreateTrainingScheduleRandomService) { }
  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;

    this.tsForm = this.formBuilder.group({
      tsName: ['', [Validators.required]],
      tsMinTarget: ['', [Validators.required]],
      tsMaxTarget: ['', [Validators.required]],
      tsInterval: ['', [Validators.required]],
      tsRepetitions: ['', Validators.required],
      tsOnlyMyDifficulty: [false]
    });
  }

  createTrainingSchedule() {
    this.submitted = true;

    this.traingSchedule = new CreateTraingsPlanRandom(
      this.tsForm.controls.tsName.value,
      'No description given',
      this.dude.selfAssessment,
      this.tsForm.controls.tsMinTarget.value,
      this.tsForm.controls.tsMaxTarget.value,
      this.tsForm.controls.tsInterval.value,
      this.tsForm.controls.tsRepetitions.value,
      this.tsForm.controls.tsOnlyMyDifficulty.value
    );

    console.log('Trying to create random training schedule ' + JSON.stringify(this.traingSchedule));

    this.createTrainingScheduleRandomService.addRandomTrainingSchedule(this.traingSchedule).subscribe(
      () => {
      },
      (error) => {
        this.error = error;
      }
    );

    return null;
  }
  vanishError() {
    this.error = false;
  }
}
