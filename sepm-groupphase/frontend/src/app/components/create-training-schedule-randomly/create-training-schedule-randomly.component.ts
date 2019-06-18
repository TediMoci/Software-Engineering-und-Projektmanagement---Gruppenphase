import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Dude} from '../../dtos/dude';
import {FindService} from '../../services/find.service';
import {CreateTrainingScheduleRandom} from '../../dtos/create-training-schedule-random';
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
  tsForm: FormGroup;
  trainingSchedule: CreateTrainingScheduleRandom;

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
      tsMinTarget: ['', [Validators.required, Validators.min(1), Validators.max(9999)]],
      tsMaxTarget: ['', [Validators.required, Validators.min(1), Validators.max(9999)]],
      tsInterval: ['', [Validators.required]],
      tsDuration: ['', [Validators.required, Validators.min(1), Validators.max(1440)]],
      tsOnlyMyDifficulty: [false],
      isPrivate: ['', [Validators.required]]
    });
  }

  createTrainingSchedule() {
    this.submitted = true;

    if (this.tsForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.trainingSchedule = new CreateTrainingScheduleRandom(
      this.tsForm.controls.tsName.value,
      'No description given',
      this.dude.selfAssessment,
      this.dude.id,
      this.tsForm.controls.tsInterval.value,
      this.tsForm.controls.tsDuration.value,
      this.tsForm.controls.tsMinTarget.value,
      this.tsForm.controls.tsMaxTarget.value,
      this.tsForm.controls.tsOnlyMyDifficulty.value.toString(),
      this.tsForm.controls.isPrivate.value
    );

    console.log('Trying to create random training schedule ' + JSON.stringify(this.trainingSchedule));

    this.createTrainingScheduleRandomService.addRandomTrainingSchedule(this.trainingSchedule).subscribe(
      (data) => {
        localStorage.setItem('selectedTrainingSchedule', JSON.stringify(data));
        this.router.navigate(['/trainingSchedule']);
      },
      (error) => {
        this.error = error;
      }
    );

  }
  vanishError() {
    this.error = false;
  }

  convertDifficultyBack(element: string) {
    switch (element) {
      case 'Beginner': return 1;
      case 'Advanced': return 2;
      case 'Pro': return 3;
    }
  }
}
