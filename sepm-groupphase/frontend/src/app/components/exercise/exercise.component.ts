import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {Exercise} from '../../dtos/exercise';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.scss']
})
export class ExerciseComponent implements OnInit {

  imagePath: string;
  imagePath2: string;
  userName: string;
  exerciseName: string;
  equipment: string;
  category: string;
  muscleGroup: string;
  description: string;
  exercise: Exercise;
  dude: Dude;
  constructor() { }

  ngOnInit() {

    this.exercise = JSON.parse(localStorage.getItem('selectedExercise'));
    console.log(this.exercise);
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.imagePath = this.dude.imagePath;
    this.imagePath2 = this.exercise.imagePath;
    this.userName = this.dude.name;
    this.exerciseName = this.exercise.name;
    this.equipment = this.exercise.equipment;
    this.muscleGroup = this.exercise.muscleGroup;
    this.category = this.exercise.category;
    this.description = this.exercise.description;
  }

}
