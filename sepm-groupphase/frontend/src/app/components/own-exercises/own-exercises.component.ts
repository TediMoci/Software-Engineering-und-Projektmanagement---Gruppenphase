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
  error: any;
  constructor(private ownExercisesService: OwnExercisesService) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;

    this.ownExercisesService.getAllExercisesOfLoggedInDude().subscribe(
      (data) => {
        console.log('get all exercises created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.exercises = data.sort(function (a, b) { // sort data alphabetically
          if (a.name < b.name) {return -1;}
          if (a.name > b.name) {return 1;}
          return 0;
        });
        console.log(this.exercises);
      },
      error => {
        this.error = error;
      }
    );

  }

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
