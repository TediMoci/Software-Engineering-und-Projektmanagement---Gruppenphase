import { Component, OnInit } from '@angular/core';
import {Exercise} from '../../dtos/exercise';
import {Dude} from '../../dtos/dude';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.scss']
})
export class ExerciseComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  imagePath2: string = '/assets/img/exercise.png';
  userName: string;
  exerciseName: string;
  equipment: string;
  difficulty: string;
  category: string;
  muscleGroup: string;
  description: string;
  exercise: Exercise;
  dude: Dude;
  constructor() { }

  ngOnInit() {

    this.exercise = JSON.parse(localStorage.getItem('selectedExercise'));
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.exerciseName = this.exercise.name;
    this.equipment = this.exercise.equipment;
    this.muscleGroup = this.exercise.muscleGroup;
    this.category = this.exercise.category;
    this.description = this.exercise.description;
  }

}
