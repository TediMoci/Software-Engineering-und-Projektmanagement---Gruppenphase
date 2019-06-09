import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {TrainingScheduleWorkoutDtoIn} from '../../dtos/trainingScheduleWorkoutDtoIn';
import {TrainingScheduleWorkout} from '../../dtos/trainingScheduleWorkout';
import {TrainingScheduleWork} from '../../dtos/trainingScheduleWork';
import {TrainingScheduleService} from '../../services/training-schedule.service';
import {EditTrainingSchedule} from '../../dtos/edit-training-schedule';
import {EditTrainingScheduleService} from '../../services/edit-training-schedule.service';

@Component({
  selector: 'app-edit-training-schedule',
  templateUrl: './edit-training-schedule.component.html',
  styleUrls: ['./edit-training-schedule.component.scss']
})
export class EditTrainingScheduleComponent implements OnInit {
  error: any;
  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  editTSForm: FormGroup;
  submitted: boolean = false;
  prevRoute: string;

  trainingScheduleWorkouts: TrainingScheduleWorkout[] = [];
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
      this.difficulty = JSON.parse(localStorage.getItem('difficultyEditTS'));
    } else {
      this.name = this.oldTrainingSchedule.name;
      this.description = this.oldTrainingSchedule.description;
      this.difficulty = JSON.stringify(this.oldTrainingSchedule.difficulty);
    }

    if (JSON.parse(localStorage.getItem('chosenWorkoutsForEditTS')) === 'empty') {
      this.trainingScheduleService.getWorkoutsOfTSById(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version).subscribe((data) => {
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
    localStorage.setItem('difficultyEdit', JSON.stringify(this.editTSForm.controls.difficultyLevelEditTS.value));
  }

  editTrainingSchedule() {
    localStorage.setItem('previousRoute', JSON.stringify('/'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/'));
    this.submitted = true;

    if (JSON.parse(localStorage.getItem('chosenWorkoutsForEditTS')) === 'empty') {
      for (let counter1 = 0; counter1 < this.trainingScheduleWorkouts.length; counter1++){
        const ex = this.trainingScheduleWorkouts[counter1];
        this.newAddedWorkoutsIn.push(new TrainingScheduleWorkoutDtoIn(
          ex.id,
          ex.version,
          ex.day
        ));
      }
    } else {
      this.newAddedWorkouts = JSON.parse(localStorage.getItem('chosenWorkoutsForEditTS'));

      for (let counter = 0; counter < this.newAddedWorkouts.length; counter++) {
        const currentEx = this.newAddedWorkouts[counter].workout;
        this.newAddedWorkoutsIn.push(new TrainingScheduleWorkoutDtoIn(
          currentEx.id,
          currentEx.version,
          this.newAddedWorkouts[counter].day
        ));
      }
      console.log(this.newAddedWorkoutsIn);
    }

    const editTrainingSchedule: EditTrainingSchedule = new EditTrainingSchedule(
      this.oldTrainingSchedule.id,
      this.oldTrainingSchedule.version,
      this.editTSForm.controls.nameForEditTS.value,
      this.editTSForm.controls.description.value,
      this.editTSForm.controls.difficulty.value,
      this.newAddedWorkoutsIn,
      this.oldTrainingSchedule.creatorId
    );

    if (this.editTSForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editTrainingScheduleService.editTrainingSchedule(editTrainingSchedule).subscribe(
      (data) => {
        console.log(data);
        localStorage.removeItem('nameForEditTS');
        localStorage.removeItem('descriptionForEditTS');
        localStorage.removeItem('chosenWorkoutsForEditTS');
        localStorage.removeItem('difficultyEdit');
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

