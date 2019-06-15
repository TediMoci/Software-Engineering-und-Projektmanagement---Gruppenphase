import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';

@Component({
  selector: 'app-my-content',
  templateUrl: './my-content.component.html',
  styleUrls: ['./my-content.component.scss']
})
export class MyContentComponent implements OnInit {
  imagePath: string;
  userName: string;
  dude: Dude;
  constructor() { }

  ngOnInit() {

    localStorage.setItem('previousRoute', JSON.stringify('/myContent'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/'));
    localStorage.removeItem('chosenExercisesForEditWorkout');
    localStorage.removeItem('chosenExercisesForWorkout');
    localStorage.removeItem('selectedWorkout');
    localStorage.removeItem('gottenExercises');
    localStorage.removeItem('firstAccess');
    localStorage.removeItem('nameForEditWorkout');
    localStorage.removeItem('descriptionForEditWorkout');
    localStorage.removeItem('calorieConsumptionForEditWorkout');
    localStorage.removeItem('difficultyEdit');

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
  }

}
