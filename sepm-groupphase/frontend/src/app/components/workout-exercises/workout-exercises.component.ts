import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {Exercise} from '../../dtos/exercise';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WorkoutEx} from '../../dtos/workoutEx';
import {Router} from '@angular/router';

@Component({
  selector: 'app-workout-exercises',
  templateUrl: './workout-exercises.component.html',
  styleUrls: ['./workout-exercises.component.scss']
})
export class WorkoutExercisesComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  toRemove: number;
  chosenExercises: WorkoutEx[] = [];
  exercisesFound: Exercise[];
  workoutExForm: FormGroup;
  submitted: boolean = false;
  constructor(private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.exercisesFound = [
      {id:1, version:1, name:'Ex1', description:'description1', equipment:'equipment1', muscleGroup:'muscleGroup1', category:'category1', creatorId:2},
      {id:2, version:1, name:'Ex2', description:'description2', equipment:'equipment2', muscleGroup:'muscleGroup2', category:'category2', creatorId:5},
      {id:3, version:1, name:'Ex3', description:'description3', equipment:'equipment3', muscleGroup:'muscleGroup3', category:'category3', creatorId:26},
      {id:4, version:1, name:'Ex4', description:'description4', equipment:'equipment4', muscleGroup:'muscleGroup4', category:'category4', creatorId:456},
      {id:5, version:1, name:'Ex5', description:'description5', equipment:'equipment5', muscleGroup:'muscleGroup5', category:'category5', creatorId:2}
    ];

    this.workoutExForm = this.formBuilder.group({
      repetitions: ['', [Validators.required]],
      sets: ['', [Validators.required]],
      duration: ['', [Validators.required]]
    });

  }

  setSelectedExercise(element: Exercise){
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

  addToChosenExercises(element:Exercise){
    this.chosenExercises.push(new WorkoutEx(element, 0, 0, 0));
  }

  removeFromChosenExercises(element:WorkoutEx){
    this.toRemove = this.chosenExercises.indexOf(element);
    this.chosenExercises.splice(this.toRemove, 1);
  }

  saveExercisesTemporarily(){
    localStorage.setItem('chosenExercisesForWorkout', JSON.stringify(this.chosenExercises));
    console.log(JSON.parse(localStorage.getItem('chosenExercisesForWorkout')));
    this.router.navigate(['/create-workout']);
  }
}
