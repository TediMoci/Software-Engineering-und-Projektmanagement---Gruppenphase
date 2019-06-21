import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {WorkoutFilter} from '../../dtos/workout-filter';
import {FindService} from '../../services/find.service';
import {Workout} from '../../dtos/workout';
import {WorkoutService} from '../../services/workout.service';
import {CdkDragDrop, moveItemInArray, transferArrayItem, copyArrayItem} from '@angular/cdk/drag-drop';
import {TrainingScheduleWorkoutDtoIn} from '../../dtos/trainingScheduleWorkoutDtoIn';
import {EditTrainingScheduleService} from '../../services/edit-training-schedule.service';
import {TrainingScheduleService} from '../../services/training-schedule.service';


@Component({
  selector: 'app-edit-training-schedule',
  templateUrl: './edit-training-schedule.component.html',
  styleUrls: ['./edit-training-schedule.component.scss']
})
export class EditTrainingScheduleComponent implements OnInit {
  imagePath: string;
  error: any;
  userName: string;
  submitted: boolean = false;
  isPrivate: boolean;
  isPrivateResult: boolean;

  editTSForm: FormGroup;
  prevRoute: string;

  dude: Dude;
  name: string;
  description: string;
  difficulty: string;
  interval: number;

  oldTrainingSchedule: TrainingSchedule;

  generalTSData: boolean;

  searchRes: any;
  exercisesForWorkouts: any;
  workoutFilter: WorkoutFilter;

  inputText: any;
  public filterWorkoutDifficulty: string = 'None';
  public filterWorkoutCaloriesMin: string = '';
  public filterWorkoutCaloriesMax: string = '';
  public inputTextActual: any;
  public filterWorkoutDifficultyActual: string = 'None';
  public filterWorkoutCaloriesMinActual: string = '';
  public filterWorkoutCaloriesMaxActual: string = '';

  d1: any = [];
  d2: any = [];
  d3: any = [];
  d4: any = [];
  d5: any = [];
  d6: any = [];
  d7: any = [];

  trainingScheduleWorkouts: TrainingScheduleWorkoutDtoIn[] = [];

  constructor(private formBuilder: FormBuilder, private router: Router, private findService: FindService, private workoutService: WorkoutService, private trainingScheduleService: TrainingScheduleService, private editTrainingScheduleService: EditTrainingScheduleService) {
  }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.prevRoute = JSON.parse(localStorage.getItem('previousRoute'));

    this.d1 = [];
    this.d2 = [];
    this.d3 = [];
    this.d4 = [];
    this.d5 = [];
    this.d6 = [];
    this.d7 = [];

    this.oldTrainingSchedule = JSON.parse(localStorage.getItem('selectedTrainingSchedule'));

    this.name = this.oldTrainingSchedule.name;
    this.description = this.oldTrainingSchedule.description;
    this.difficulty = JSON.stringify(this.oldTrainingSchedule.difficulty);
    this.interval = this.oldTrainingSchedule.intervalLength;
    this.isPrivate = this.oldTrainingSchedule.isPrivate;
    this.generalTSData = true;

    this.editTSForm = this.formBuilder.group({
      nameForEditTS: ['', [Validators.required]],
      difficultyLevelEditTS: [this.difficulty, [Validators.required]],
      descriptionForEditTS: ['', [Validators.required]],
      isPrivate: ['']
    });
    this.getAllWorkoutsPerDay();
  }

  logFormEditTS() {
    localStorage.setItem('nameForEditTS', JSON.stringify(this.editTSForm.controls.nameForEditTS.value));
    localStorage.setItem('descriptionForEditTS', JSON.stringify(this.editTSForm.controls.descriptionForEditTS.value));
    localStorage.setItem('difficultyLevelEditTS', JSON.stringify(this.editTSForm.controls.difficultyLevelEditTS.value));
    this.name = JSON.parse(localStorage.getItem('nameForEditTS'));
    this.description = JSON.parse(localStorage.getItem('descriptionForEditTS'));
    this.difficulty = JSON.parse(localStorage.getItem('difficultyLevelEditTS'));
    localStorage.removeItem('nameForEditTS');
    localStorage.removeItem('descriptionForEditTS');
    localStorage.removeItem('difficultyLevelEditTS');
  }

  getAllWorkoutsPerDay() {
    switch (this.oldTrainingSchedule.intervalLength) {
      case 1:
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1;
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 2:
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2;
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 3:
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3;
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 4:
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 4).subscribe(
          (data4) => {
            this.d4 = data4;
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 5:
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 4).subscribe(
          (data4) => {
            this.d4 = data4;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 5).subscribe(
          (data5) => {
            this.d5 = data5;
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 6:
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 4).subscribe(
          (data4) => {
            this.d4 = data4;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 5).subscribe(
          (data5) => {
            this.d5 = data5;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 6).subscribe(
          (data6) => {
            this.d6 = data6;
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 7:
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 4).subscribe(
          (data4) => {
            this.d4 = data4;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 5).subscribe(
          (data5) => {
            this.d5 = data5;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 6).subscribe(
          (data6) => {
            this.d6 = data6;
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(this.oldTrainingSchedule.id, this.oldTrainingSchedule.version, 7).subscribe(
          (data7) => {
            this.d7 = data7;
          },
          error => {
            this.error = error;
          }
        );
        break;
    }
  }

  changeView() {
    this.resetResults();
    this.generalTSData = !this.generalTSData;
    this.logFormEditTS();
  }

  resetResults() {
    this.searchRes = [];
  }

  search() {
    if (this.inputText === undefined) {
      this.inputTextActual = null;
    } else {
      this.inputTextActual = this.inputText;
    }

    switch (this.filterWorkoutDifficulty) {
      case 'None':
        this.filterWorkoutDifficultyActual = null;
        break;
      case  'Beginner':
        this.filterWorkoutDifficultyActual = '1';
        break;
      case  'Advanced':
        this.filterWorkoutDifficultyActual = '2';
        break;
      case  'Pro':
        this.filterWorkoutDifficultyActual = '3';
        break;
    }

    if (this.filterWorkoutCaloriesMin === '') {
      this.filterWorkoutCaloriesMinActual = null;
    } else {
      this.filterWorkoutCaloriesMinActual = this.filterWorkoutCaloriesMin;
    }

    if (this.filterWorkoutCaloriesMax === '') {
      this.filterWorkoutCaloriesMaxActual = null;
    } else {
      this.filterWorkoutCaloriesMaxActual = this.filterWorkoutCaloriesMax;
    }

    this.workoutFilter = new WorkoutFilter(
      this.inputTextActual,
      this.filterWorkoutDifficultyActual,
      this.filterWorkoutCaloriesMinActual,
      this.filterWorkoutCaloriesMaxActual);

    this.findService.getAllWorkoutsFilterd(this.workoutFilter, this.dude.id).subscribe(
      (data) => {
        this.searchRes = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.name > b.name) {
            return 1;
          }
          return 0;
        });
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
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.name > b.name) {
            return 1;
          }
          return 0;
        });
        console.log(this.exercisesForWorkouts.toString().toString());
      },
      error => {
        this.error = error;
      }
    );
  }

  convertDifficulty(element: any) {
    switch (element) {
      case 1:
        return 'Beginner';
      case 2:
        return 'Advanced';
      case 3:
        return 'Pro';
    }
  }

  drop(event: CdkDragDrop<string[]>) {

    if (event.isPointerOverContainer) {
      if (event.previousContainer === event.container) {
        moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
      } else {
        if (event.previousContainer.id === 'searchRes1' ||
          event.previousContainer.id === 'searchRes2' ||
          event.previousContainer.id === 'searchRes3' ||
          event.previousContainer.id === 'searchRes4' ||
          event.previousContainer.id === 'searchRes5' ||
          event.previousContainer.id === 'searchRes6' ||
          event.previousContainer.id === 'searchRes7'
        ) {
          // copy items taken from search results list
          copyArrayItem(event.previousContainer.data,
            event.container.data,
            event.previousIndex,
            event.currentIndex);
        } else {
          transferArrayItem(event.previousContainer.data,
            event.container.data,
            event.previousIndex,
            event.currentIndex);
        }
      }
    } else {
      // remove item when dragged outside of the dragging areas
      console.log('delete entry');
      if (!(event.previousContainer.id === 'searchRes1' ||
        event.previousContainer.id === 'searchRes2' ||
        event.previousContainer.id === 'searchRes3' ||
        event.previousContainer.id === 'searchRes4' ||
        event.previousContainer.id === 'searchRes5' ||
        event.previousContainer.id === 'searchRes6' ||
        event.previousContainer.id === 'searchRes7')) {
        event.previousContainer.data.splice(event.previousIndex, 1);
      }
    }
  }

  cancelWorkoutAllocation() {
    this.getAllWorkoutsPerDay();
    this.resetResults();
    this.generalTSData = !this.generalTSData;
  }

  vanishError() {
    this.error = false;
  }

  editTrainingSchedule() {
    this.submitted = true;
    if (this.editTSForm.controls.isPrivate.value === '') {
      this.isPrivateResult = this.oldTrainingSchedule.isPrivate;
    } else {
      this.isPrivateResult = this.editTSForm.controls.isPrivate.value;
    }

    switch (this.interval) {
      case 1:
        if (this.d1 !== null) {
          for (let counter = 0; counter < this.d1.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d1[counter].id,
              this.d1[counter].version,
              1
            ));
          }
        }
        break;
      case 2:
        if (this.d1 !== null) {
          for (let counter = 0; counter < this.d1.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d1[counter].id,
              this.d1[counter].version,
              1
            ));
          }
        }
        if (this.d2 !== null) {
          for (let counter = 0; counter < this.d2.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d2[counter].id,
              this.d2[counter].version,
              2
            ));
          }
        }
        break;
      case 3:
        if (this.d1 !== null) {
          for (let counter = 0; counter < this.d1.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d1[counter].id,
              this.d1[counter].version,
              1
            ));
          }
        }
        if (this.d2 !== null) {
          for (let counter = 0; counter < this.d2.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d2[counter].id,
              this.d2[counter].version,
              2
            ));
          }
        }
        if (this.d3 !== null) {
          for (let counter = 0; counter < this.d3.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d3[counter].id,
              this.d3[counter].version,
              3
            ));
          }
        }
        break;
      case 4:
        if (this.d1 !== null) {
          for (let counter = 0; counter < this.d1.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d1[counter].id,
              this.d1[counter].version,
              1
            ));
          }
        }
        if (this.d2 !== null) {
          for (let counter = 0; counter < this.d2.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d2[counter].id,
              this.d2[counter].version,
              2
            ));
          }
        }
        if (this.d3 !== null) {
          for (let counter = 0; counter < this.d3.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d3[counter].id,
              this.d3[counter].version,
              3
            ));
          }
        }
        if (this.d4 !== null) {
          for (let counter = 0; counter < this.d4.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d4[counter].id,
              this.d4[counter].version,
              4
            ));
          }
        }
        break;
      case 5:
        if (this.d1 !== null) {
          for (let counter = 0; counter < this.d1.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d1[counter].id,
              this.d1[counter].version,
              1
            ));
          }
        }
        if (this.d2 !== null) {
          for (let counter = 0; counter < this.d2.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d2[counter].id,
              this.d2[counter].version,
              2
            ));
          }
        }
        if (this.d3 !== null) {
          for (let counter = 0; counter < this.d3.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d3[counter].id,
              this.d3[counter].version,
              3
            ));
          }
        }
        if (this.d4 !== null) {
          for (let counter = 0; counter < this.d4.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d4[counter].id,
              this.d4[counter].version,
              4
            ));
          }
        }
        if (this.d5 !== null) {
          for (let counter = 0; counter < this.d5.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d5[counter].id,
              this.d5[counter].version,
              5
            ));
          }
        }
        break;
      case 6:
        if (this.d1 !== null) {
          for (let counter = 0; counter < this.d1.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d1[counter].id,
              this.d1[counter].version,
              1
            ));
          }
        }
        if (this.d2 !== null) {
          for (let counter = 0; counter < this.d2.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d2[counter].id,
              this.d2[counter].version,
              2
            ));
          }
        }
        if (this.d3 !== null) {
          for (let counter = 0; counter < this.d3.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d3[counter].id,
              this.d3[counter].version,
              3
            ));
          }
        }
        if (this.d4 !== null) {
          for (let counter = 0; counter < this.d4.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d4[counter].id,
              this.d4[counter].version,
              4
            ));
          }
        }
        if (this.d5 !== null) {
          for (let counter = 0; counter < this.d5.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d5[counter].id,
              this.d5[counter].version,
              5
            ));
          }
        }
        if (this.d6 !== null) {
          for (let counter = 0; counter < this.d6.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d6[counter].id,
              this.d6[counter].version,
              6
            ));
          }
        }
        break;
      case 7:
        if (this.d1 !== null) {
          for (let counter = 0; counter < this.d1.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d1[counter].id,
              this.d1[counter].version,
              1
            ));
          }
        }
        if (this.d2 !== null) {
          for (let counter = 0; counter < this.d2.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d2[counter].id,
              this.d2[counter].version,
              2
            ));
          }
        }
        if (this.d3 !== null) {
          for (let counter = 0; counter < this.d3.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d3[counter].id,
              this.d3[counter].version,
              3
            ));
          }
        }
        if (this.d4 !== null) {
          for (let counter = 0; counter < this.d4.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d4[counter].id,
              this.d4[counter].version,
              4
            ));
          }
        }
        if (this.d5 !== null) {
          for (let counter = 0; counter < this.d5.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d5[counter].id,
              this.d5[counter].version,
              5
            ));
          }
        }
        if (this.d6 !== null) {
          for (let counter = 0; counter < this.d6.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d6[counter].id,
              this.d6[counter].version,
              6
            ));
          }
        }
        if (this.d7 !== null) {
          for (let counter = 0; counter < this.d7.length; counter++) {
            this.trainingScheduleWorkouts.push(new TrainingScheduleWorkoutDtoIn(
              this.d7[counter].id,
              this.d7[counter].version,
              7
            ));
          }
        }
        break;
    }

    const trainingSchedule: TrainingSchedule = new TrainingSchedule(
      this.oldTrainingSchedule.id,
      this.oldTrainingSchedule.version,
      this.editTSForm.controls.nameForEditTS.value,
      this.editTSForm.controls.descriptionForEditTS.value,
      this.editTSForm.controls.difficultyLevelEditTS.value,
      this.oldTrainingSchedule.intervalLength,
      this.trainingScheduleWorkouts,
      this.oldTrainingSchedule.creatorId,
      this.isPrivateResult
    );

    if (this.editTSForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editTrainingScheduleService.editTrainingSchedule(trainingSchedule).subscribe(
      () => {
        console.log(trainingSchedule);
        this.router.navigate(['myContent']);
      },
      error => {
        this.error = error;
      }
    );
  }

  backToMyTrainingSchedules() {
    this.resetResults();
    this.generalTSData = !this.generalTSData;
    this.router.navigate(['myTrainingSchedules']);
  }
}

