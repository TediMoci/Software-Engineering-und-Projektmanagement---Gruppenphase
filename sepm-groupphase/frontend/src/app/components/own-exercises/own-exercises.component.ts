import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {OwnExercisesService} from '../../services/own-exercises.service';
import {Exercise} from '../../dtos/Exercise';

@Component({
  selector: 'app-own-exercises',
  templateUrl: './own-exercises.component.html',
  styleUrls: ['./own-exercises.component.scss']
})
export class OwnExercisesComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  exercises: any;
  constructor(private ownExercisesService: OwnExercisesService) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    // this.exercises = this.ownExercisesService.getAllExercisesOfLoggedInDude();
    this.exercises = [{ name: 'Sit up',
      description: 'do something for your abs',
      difficulty_level: 'easy',
      category: 'other',
      equipment: 'your body',
      muscleGroup: 'abs'},
      { name: 'Curls',
        description: 'do something for your bizeps',
        difficulty_level: 'advanced',
        category: 'strength',
        equipment: 'weights',
        muscleGroup: 'bizeps'},
      { name: 'Handless Handstand',
        description: 'learn to fly',
        difficulty_level: 'baby',
        category: 'lightness',
        equipment: 'your body, air',
        muscleGroup: 'mind'}];
  }
  // TODO: sort entrys alphabetically?

  setSelectedExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

  setToEditExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

  setToDeleteExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

}
