import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {Exercise} from '../../dtos/exercise';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WorkoutEx} from '../../dtos/workoutEx';
import {Router} from '@angular/router';
import {WorkoutExercisesService} from '../../services/workout-exercises.service';

@Component({
  selector: 'app-workout-exercises',
  templateUrl: './workout-exercises.component.html',
  styleUrls: ['./workout-exercises.component.scss']
})
export class WorkoutExercisesComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  registerForm: FormGroup;
  index: number;
  chosenExercises: WorkoutEx[] = [];
  workoutExForm: FormGroup;
  submitted: boolean = false;
  exerciseName: string;
  exercises: any;
  newExercises: any;
  error: any;

  constructor(private workoutExercisesService: WorkoutExercisesService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {

    if (JSON.parse(localStorage.getItem('previousRoute')) === '/create-exercise-for-workout') {
      if (localStorage.getItem('chosenExercisesForWorkout') !== null) {
        console.log('return from create-exercise-for-workout ');
        this.chosenExercises = JSON.parse(localStorage.getItem('chosenExercisesForWorkout'));
        console.log(this.chosenExercises);
      }
    }

    if ((JSON.parse(localStorage.getItem('previousRoute')) === '/create-workout') && (JSON.parse(localStorage.getItem('previousPreviousRoute')) === '/workout-exercises')) {
      if (localStorage.getItem('chosenExercisesForWorkout') !== null) {
        console.log('return create-workout ' +  this.chosenExercises);
        this.chosenExercises = JSON.parse(localStorage.getItem('chosenExercisesForWorkout'));
        console.log(this.chosenExercises);
      }
    }

    localStorage.setItem('previousRoute', JSON.stringify('/workout-exercises'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/workout-exercises'));

    this.registerForm = this.formBuilder.group({
      name: [''],
    });
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.workoutExForm = this.formBuilder.group({
      repetitions: ['', [Validators.required, Validators.max(100)]],
      sets: ['', [Validators.required, Validators.max(15)]],
      duration: ['', [Validators.required, Validators.max(1440)]]
    });
  }

  setSelectedExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

  addToChosenExercises(element: Exercise) {
    console.log(element);
    console.log('current status' +  localStorage.getItem('chosenExercisesForWorkout'));
    this.chosenExercises.push(new WorkoutEx(element, 1, 1, 1));
    localStorage.setItem('chosenExercisesForWorkout', JSON.stringify(this.chosenExercises));
    console.log('add exercise' + localStorage.getItem('chosenExercisesForWorkout'));
  }

  findExercisesByName() {
    this.exerciseName = this.registerForm.value.name;
    this.workoutExercisesService.getExercisesByName(this.exerciseName,this.dude.id).subscribe(
      (data) => {
        console.log('get all exercises by name ' + this.exerciseName);
        console.log(data);
        this.exercises = data;
      },
      error => {
        this.error = error;
      }
    );
  }

  removeFromChosenExercises(element: WorkoutEx) {
    this.index = this.chosenExercises.indexOf(element);
    this.chosenExercises.splice(this.index, 1);
    console.log('delete' +  this.chosenExercises);
    localStorage.setItem('chosenExercisesForWorkout', JSON.stringify(this.chosenExercises));
    console.log('delete' + localStorage.getItem('chosenExercisesForWorkout') );

  }

  setExData(element: WorkoutEx) {
    this.submitted = true;
   this.index = this.chosenExercises.indexOf(element);
   if (!(this.workoutExForm.controls.repetitions.value === '')) {
     this.chosenExercises[this.index].repetitions = this.workoutExForm.controls.repetitions.value;
   }
   if (!(this.workoutExForm.controls.sets.value === '')) {
     this.chosenExercises[this.index].sets = this.workoutExForm.controls.sets.value;
   }
   if (!(this.workoutExForm.controls.duration.value === '')) {
     this.chosenExercises[this.index].exDuration = this.workoutExForm.controls.duration.value;
   }
  }

  saveExercisesTemporarily() {
    console.log('save exercises temporarily');
    localStorage.setItem('chosenExercisesForWorkout', JSON.stringify(this.chosenExercises));
    console.log(JSON.parse(localStorage.getItem('chosenExercisesForWorkout')));
  }

  backToCreateWorkout() {
    localStorage.removeItem('chosenExercisesForWorkout');
    this.router.navigate(['/create-workout']);
  }

  vanishError() {
    this.error = false;
  }
}
