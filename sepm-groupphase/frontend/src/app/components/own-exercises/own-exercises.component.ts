import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {OwnExercisesService} from '../../services/own-exercises.service';
import {CreateExercise} from '../../dtos/create-exercise';
import {Router} from '@angular/router';

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
  exerciseToDelete: string;
  error: any;
  constructor(private ownExercisesService: OwnExercisesService, private router: Router) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;

    this.ownExercisesService.getAllExercisesOfLoggedInDude().subscribe(
      (data) => {
        console.log('get all exercises created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.exercises = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1;}
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

  setSelectedExercise(element: CreateExercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

  goToEditExercise(element: CreateExercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
    this.router.navigate(['/edit-exercise']);
  }

  setToDeleteExercise(element: CreateExercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
    this.exerciseToDelete = element.name;
  }

  deleteExercise(){

  }

}
