import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WorkoutEx} from '../../dtos/workoutEx';
import {WorkoutExercisesService} from '../../services/workout-exercises.service';
import {Router} from '@angular/router';
import {Exercise} from '../../dtos/exercise';
import {WorkoutExercise} from '../../dtos/workoutExercise';

@Component({
  selector: 'app-edit-workout-exercises',
  templateUrl: './edit-workout-exercises.component.html',
  styleUrls: ['./edit-workout-exercises.component.scss']
})
export class EditWorkoutExercisesComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  registerForm: FormGroup;
  index: number;
  gottenExercises: WorkoutExercise[] = [];
  chosenExercises: WorkoutEx[] = [];
  workoutExForm: FormGroup;
  submitted: boolean = false;
  exerciseName: string;
  exercises: any;
  error: any;

  constructor(private workoutExercisesService: WorkoutExercisesService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {

    if (JSON.parse(localStorage.getItem('previousRoute')) === '/create-exercise-for-workout') {
      if (localStorage.getItem('chosenExercisesForEditWorkout') !== null) {
        console.log('return from create-exercise-for-workout ' +  this.chosenExercises);
        this.chosenExercises = JSON.parse(localStorage.getItem('chosenExercisesForEditWorkout'));
      }
    }

    if ((JSON.parse(localStorage.getItem('previousRoute')) === '/edit-workout') && (JSON.parse(localStorage.getItem('previousPreviousRoute')) === '/edit-workout-exercises')) {
      if (localStorage.getItem('chosenExercisesForEditWorkout') !== null) {
        console.log('return edit-workout ' +  this.chosenExercises);
        this.chosenExercises = JSON.parse(localStorage.getItem('chosenExercisesForEditWorkout'));
      }
    }

    localStorage.setItem('previousRoute', JSON.stringify('/edit-workout-exercises'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/edit-workout-exercises'));

    if (JSON.parse(localStorage.getItem('firstAccess')) === 'true') {
      this.gottenExercises = JSON.parse(localStorage.getItem('gottenExercises'));
      if (!(this.gottenExercises === null)) {
        for (let counter = 0; counter < this.gottenExercises.length; counter++) {
            this.chosenExercises.push(new WorkoutEx(
              new Exercise(
                this.gottenExercises[counter].id,
                this.gottenExercises[counter].version,
                this.gottenExercises[counter].name,
                this.gottenExercises[counter].description,
                this.gottenExercises[counter].equipment,
                this.gottenExercises[counter].muscleGroup,
                this.gottenExercises[counter].category,
                this.gottenExercises[counter].creatorId,
                this.gottenExercises[counter].imagePath),
                this.gottenExercises[counter].repetitions,
                this.gottenExercises[counter].sets,
                this.gottenExercises[counter].exDuration,
               ));
        }
      }
      localStorage.setItem('firstAccess', JSON.stringify('false'));
      localStorage.setItem('chosenExercisesForEditWorkout', JSON.stringify(this.chosenExercises));

      console.log('got gottenExercises from edit workout');
      console.log(localStorage.getItem('chosenExercisesForEditWorkout'));
    }

    this.registerForm = this.formBuilder.group({
      name: [''],
    });
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.workoutExForm = this.formBuilder.group({
      repetitions: ['', [Validators.required]],
      sets: ['', [Validators.required]],
      duration: ['', [Validators.required]]
    });
  }

  addToChosenExercises(element: Exercise) {
    console.log(element);
    console.log('current status' +  localStorage.getItem('chosenExercisesForEditWorkout'));
    this.chosenExercises.push(new WorkoutEx(element, 1, 1, 1));
    localStorage.setItem('chosenExercisesForEditWorkout', JSON.stringify(this.chosenExercises));
    console.log('add exercise' + localStorage.getItem('chosenExercisesForEditWorkout'));
  }

  findExercisesByName() {
    this.exerciseName = this.registerForm.value.name;
    this.workoutExercisesService.getExercisesByName(this.exerciseName).subscribe(
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
    localStorage.setItem('chosenExercisesForEditWorkout', JSON.stringify(this.chosenExercises));
    console.log('delete' + localStorage.getItem('chosenExercisesForEditWorkout') );

  }

  setExData(element: WorkoutEx) {
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
    localStorage.setItem('chosenExercisesForEditWorkout', JSON.stringify(this.chosenExercises));
    console.log(JSON.parse(localStorage.getItem('chosenExercisesForEditWorkout')));
  }

  backToCreateWorkout() {
    localStorage.removeItem('chosenExercisesForEditWorkout');
    this.router.navigate(['/edit-workout']);
  }

  vanishError() {
    this.error = false;
  }

}
