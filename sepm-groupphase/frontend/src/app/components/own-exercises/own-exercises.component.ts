import {Component, OnInit} from '@angular/core';
import {Dude} from '../../dtos/dude';
import {OwnExercisesService} from '../../services/own-exercises.service';
import {Router} from '@angular/router';
import {Exercise} from '../../dtos/exercise';

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
  exerciseToDelete;

  constructor(private ownExercisesService: OwnExercisesService, private router: Router) {
  }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;

    this.ownExercisesService.getAllExercisesOfLoggedInDude(this.dude).subscribe(
      (data) => {
        console.log('get all exercises created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.exercises = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.name > b.name) {
            return 1;
          }
          return 0;
        });
      },
      error => {
        this.error = error;
      }
    );

  }

  setSelectedExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

  goToEditExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
    this.router.navigate(['/edit-exercise']);
  }

  setToDeleteExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
    this.exerciseToDelete = element.name;
    console.log(element);
  }

  deleteExercise() {
    this.ownExercisesService.deleteExercise(JSON.parse(localStorage.getItem('selectedExercise')).id)
      .subscribe(() => {
          console.log('removed exercise successfully');
          localStorage.removeItem('selectedExercise');
          this.ngOnInit();
        },
        error => {
          this.error = error;
        }
      );
  }
}
