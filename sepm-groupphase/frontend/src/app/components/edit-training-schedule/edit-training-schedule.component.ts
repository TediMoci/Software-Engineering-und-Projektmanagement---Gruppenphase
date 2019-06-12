import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {TrainingScheduleWorkoutDtoIn} from '../../dtos/trainingScheduleWorkoutDtoIn';
import {TrainingScheduleWorkout} from '../../dtos/trainingScheduleWorkout';
import {TrainingScheduleWork} from '../../dtos/trainingScheduleWork';
import {TrainingScheduleService} from '../../services/training-schedule.service';
import {EditTrainingScheduleService} from '../../services/edit-training-schedule.service';
import {TrainingScheduleWo} from '../../dtos/training-schedule-wo';

@Component({
  selector: 'app-edit-training-schedule',
  templateUrl: './edit-training-schedule.component.html',
  styleUrls: ['./edit-training-schedule.component.scss']
})
export class EditTrainingScheduleComponent implements OnInit {
  imagePath: string;
  error: any;
  userName: string;
  editTSForm: FormGroup;
  submitted: boolean = false;
  prevRoute: string;

  trainingScheduleWorkouts: TrainingScheduleWo[] = [];
  newAddedWorkouts: TrainingScheduleWork[];
  newAddedWorkoutsIn: TrainingScheduleWorkoutDtoIn[] = [];
  oldTrainingSchedule: TrainingSchedule;

  dude: Dude;
  name: string;
  description: string;
  difficulty: string;

  beginner: boolean = false;
  advanced: boolean = false;
  pro: boolean = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private trainingScheduleService: TrainingScheduleService, private editTrainingScheduleService: EditTrainingScheduleService) { }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.prevRoute = JSON.parse(localStorage.getItem('previousRoute'));
    this.oldTrainingSchedule = JSON.parse(localStorage.getItem('selectedTrainingSchedule'));

    if (this.oldTrainingSchedule.difficulty === 1) {
      this.beginner = true;
    } else if (this.oldTrainingSchedule.difficulty === 2) {
      this.advanced = true;
    } else {
      this.pro = true;
    }

    if (this.prevRoute === '/edit-training-workouts') {
      this.name = JSON.parse(localStorage.getItem('nameForEditTS'));
      this.description = JSON.parse(localStorage.getItem('descriptionForEditTS'));
      this.difficulty = JSON.parse(localStorage.getItem('difficultyLevelEditTS'));
    } else {
      this.name = this.oldTrainingSchedule.name;
      this.description = this.oldTrainingSchedule.description;
      this.difficulty = JSON.stringify(this.oldTrainingSchedule.difficulty);
    }

    if (JSON.parse(localStorage.getItem('chosenWorkoutsForEditTS')) === 'empty') {
      this.trainingScheduleService.getWorkoutsOfTrainingScheduleById(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version).subscribe((data) => {
          this.trainingScheduleWorkouts = data;
          localStorage.setItem('gottenWorkouts', JSON.stringify(this.trainingScheduleWorkouts));
        },
        error => {
          this.error = error;
        }
      );
    }

    this.editTSForm = this.formBuilder.group({
      nameForEditTS: ['', [Validators.required]],
      difficultyLevelEditTS: [this.difficulty, [Validators.required]],
      descriptionForEditTS: ['', [Validators.required]],
    });

    localStorage.setItem('previousRoute', JSON.stringify('/edit-training-schedule'));
  }

  editWorkouts() {
    localStorage.setItem('nameForEditTS', JSON.stringify(this.editTSForm.controls.nameForEditTS.value));
    localStorage.setItem('descriptionForEditTS', JSON.stringify(this.editTSForm.controls.descriptionForEditTS.value));
    localStorage.setItem('difficultyLevelEditTS', JSON.stringify(this.editTSForm.controls.difficultyLevelEditTS.value));
  }

  editTrainingSchedule() {
    localStorage.setItem('previousRoute', JSON.stringify('/'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/'));
    this.submitted = true;

    if (JSON.parse(localStorage.getItem('chosenWorkoutsForEditTS')) === 'empty') {
      for (let counter1 = 0; counter1 < this.trainingScheduleWorkouts.length; counter1++){
        const work = this.trainingScheduleWorkouts[counter1];
        this.newAddedWorkoutsIn.push(new TrainingScheduleWorkoutDtoIn(
          work.id,
          work.version,
          work.day
        ));
      }
    } else {
      this.newAddedWorkouts = JSON.parse(localStorage.getItem('chosenWorkoutsForEditTS'));

      for (let counter = 0; counter < this.newAddedWorkouts.length; counter++) {
        const currentWork = this.newAddedWorkouts[counter].workout;
        this.newAddedWorkoutsIn.push(new TrainingScheduleWorkoutDtoIn(
          currentWork.id,
          currentWork.version,
          this.newAddedWorkouts[counter].day
        ));
      }
      console.log(this.newAddedWorkoutsIn);
    }

    const trainingSchedule: TrainingSchedule = new TrainingSchedule(
      this.oldTrainingSchedule.id,
      this.oldTrainingSchedule.version,
      this.editTSForm.controls.nameForEditTS.value,
      this.editTSForm.controls.descriptionForEditTS.value,
      this.editTSForm.controls.difficultyLevelEditTS.value,
      this.oldTrainingSchedule.intervalLength,
      this.newAddedWorkoutsIn,
      this.oldTrainingSchedule.creatorId
    );

    if (this.editTSForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editTrainingScheduleService.editTrainingSchedule(trainingSchedule).subscribe(
      (data) => {
        console.log(data);
        localStorage.removeItem('nameForEditTS');
        localStorage.removeItem('descriptionForEditTS');
        localStorage.removeItem('chosenWorkoutsForEditTS');
        localStorage.removeItem('difficultyLevelEditTS');
        this.router.navigate(['myTrainingSchedules']);
      },
      error => {
        this.error = error;
      }
    );
  }
  vanishError() {
    this.error = false;
  }
  backToCreateTrainingSchedule() {
    localStorage.setItem('previousRoute', JSON.stringify('/'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/'));
    localStorage.removeItem('nameForEditTS');
    localStorage.removeItem('descriptionForEditTS');
    localStorage.removeItem('chosenWorkoutsForEditTS');
    this.router.navigate(['myTrainingSchedules']);
  }
}

