import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {TrainingScheduleWorkoutDtoIn} from '../../dtos/trainingScheduleWorkoutDtoIn';

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


  newAddedWorkoutsIn: TrainingScheduleWorkoutDtoIn[] = [];
  oldTrainingSchedule: TrainingSchedule;

  dude: Dude;
  name: string;
  description: string;
  difficulty: string;

  beginner: boolean = false;
  advanced: boolean = false;
  pro: boolean = false;

  constructor(private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.prevRoute = JSON.parse(localStorage.getItem('previousTS'));
    this.oldTrainingSchedule = JSON.parse(localStorage.getItem('selectedTrainingSchedule'));

    if (this.oldTrainingSchedule.difficulty === 1) {
      this.beginner = true;
    } else if (this.oldTrainingSchedule.difficulty === 2) {
      this.advanced = true;
    } else {
      this.pro = true;
    }

    this.editTSForm = this.formBuilder.group({
      nameForEditTS: ['', [Validators.required]],
      difficultyLevelEditTS: [this.difficulty, [Validators.required]],
      descriptionForEditTS: ['', [Validators.required]],
    });

    this.name = this.oldTrainingSchedule.name;
    this.description = this.oldTrainingSchedule.description;
    this.difficulty = JSON.stringify(this.oldTrainingSchedule.difficulty);
  }

  editTrainingSchedule() {
    this.submitted = true;

    const newTrainingSchedule: TrainingSchedule = new TrainingSchedule(
      this.oldTrainingSchedule.id,
      this.oldTrainingSchedule.version,
      this.editTSForm.controls.name.value,
      this.editTSForm.controls.description.value,
      this.editTSForm.controls.difficulty.value,
      this.oldTrainingSchedule.trainingScheduleWorkouts,
      this.oldTrainingSchedule.creatorId
    );

    if (this.editTSForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editExerciseService.editExercise(exercise, this.oldExercise).subscribe(
      (data) => {
        localStorage.setItem('selectedExercise', JSON.stringify(data));
        this.router.navigate(['/myExercises']);
      },
      error => {
        this.error = error;
      }
    );
  }
}
