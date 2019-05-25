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

  imagePath: string = '/assets/img/kugelfisch.jpg';
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
    this.registerForm = this.formBuilder.group({
      name: [''],
    });
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.workoutExForm = this.formBuilder.group({
      repetitions: ['', [Validators.required]],
      sets: ['', [Validators.required]],
      duration: ['', [Validators.required]]
    });

  }

  setSelectedExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }
  addToChosenExercisesFromCreateExercise() {
    this.newExercises =  JSON.parse(localStorage.getItem('addNewExerciseForWorkout'));
    console.log(this.newExercises);
    this.chosenExercises.push(new WorkoutEx(this.newExercises, 1, 1, 1));
    console.log(this.chosenExercises);

  }

  addToChosenExercises(element: Exercise) {
    console.log(element );
    this.chosenExercises.push(new WorkoutEx(element, 1, 1, 1));
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
   console.log(this.chosenExercises[this.index]);
  }

  saveExercisesTemporarily() {
    localStorage.setItem('chosenExercisesForWorkout', JSON.stringify(this.chosenExercises));
    console.log(JSON.parse(localStorage.getItem('chosenExercisesForWorkout')));
    this.router.navigate(['/create-workout']);
  }

  vanishError() {
    this.error = false;
  }
}
