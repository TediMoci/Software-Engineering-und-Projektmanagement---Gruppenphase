import {Component, OnInit} from '@angular/core';
import {Dude} from '../../dtos/dude';
import {CdkDragDrop, moveItemInArray, transferArrayItem, copyArrayItem} from '@angular/cdk/drag-drop';
import {Workout} from '../../dtos/workout';
import {Router} from '@angular/router';
import {FindService} from '../../services/find.service';
import {WorkoutService} from '../../services/workout.service';
import {WorkoutFilter} from '../../dtos/workout-filter';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CreateTrainingSchedule} from '../../dtos/create-trainingSchedule';
import {CreateTrainingScheduleService} from '../../services/create-training-schedule.service';
import {TrainingScheduleWorkoutDtoIn} from '../../dtos/trainingScheduleWorkoutDtoIn';
import {TrainingSchedule} from '../../dtos/trainingSchedule';

@Component({
  selector: 'app-create-training-schedule-manually',
  templateUrl: './create-training-schedule-manually.component.html',
  styleUrls: ['./create-training-schedule-manually.component.scss']
})
export class CreateTrainingScheduleManuallyComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  error: any;

  trainingSchedule: CreateTrainingSchedule;
  trainingScheduleWorkouts: TrainingScheduleWorkoutDtoIn[] = [];

  submitted: boolean = false;
  tsForm: FormGroup;

  generalTSData: boolean;
  selectedWorkout: Workout;
  interval: number = 1;

  inputText: any;
  public filterWorkoutDifficulty: string = 'None';
  public filterWorkoutCaloriesMin: string = '';
  public filterWorkoutCaloriesMax: string = '';
  public inputTextActual: any;
  public filterWorkoutDifficultyActual: string = 'None';
  public filterWorkoutCaloriesMinActual: string = '';
  public filterWorkoutCaloriesMaxActual: string = '';

  searchRes: any;
  exercisesForWorkouts: any;
  workoutFilter: WorkoutFilter;

  d1: any = [];
  d2: any = [];
  d3: any = [];
  d4: any = [];
  d5: any = [];
  d6: any = [];
  d7: any = [];

  intervalDays: string[] = [
    '1 Day',
    '2 Days',
    '3 Days',
    '4 Days',
    '5 Days',
    '6 Days',
    '7 Days'
  ];

  constructor(private router: Router, private findService: FindService, private formBuilder: FormBuilder, private workoutService: WorkoutService, private createTrainingScheduleService: CreateTrainingScheduleService) {
  }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.generalTSData = true;

    this.tsForm = this.formBuilder.group({
      tsName: ['', [Validators.required]],
      tsDifficulty: ['', [Validators.required]],
      tsDescription: ['', [Validators.required]]
    });
  }

  changeView() {
    this.resetResults();
    this.generalTSData = !this.generalTSData;
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
      event.previousContainer.data.splice(event.previousIndex, 1);
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
    this.d1 = [];
    this.d2 = [];
    this.d3 = [];
    this.d4 = [];
    this.d5 = [];
    this.d6 = [];
    this.d7 = [];
    this.resetResults();
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

    this.findService.getAllWorkoutsFilterd(this.workoutFilter).subscribe(
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

  resetResults() {
    this.searchRes = [];
  }

  createTrainingSchedule() {
    this.submitted = true;

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

    this.trainingSchedule = new CreateTrainingSchedule(
      this.tsForm.controls.tsName.value,
      this.tsForm.controls.tsDescription.value,
      this.tsForm.controls.tsDifficulty.value,
      this.interval,
      this.trainingScheduleWorkouts,
      this.dude.id
    );
    this.createTrainingScheduleService.addTrainingSchedule(this.trainingSchedule).subscribe(
      () => {
        console.log(this.trainingSchedule);
        this.router.navigate(['create']);
      },
      error => {
        this.error = error;
      }
    );
  }

  vanishError() {
    this.error = false;
  }

  cancelWorkoutAllocation() {
    this.d1 = [];
    this.d2 = [];
    this.d3 = [];
    this.d4 = [];
    this.d5 = [];
    this.d6 = [];
    this.d7 = [];
    this.resetResults();
    this.generalTSData = !this.generalTSData;
  }
}
