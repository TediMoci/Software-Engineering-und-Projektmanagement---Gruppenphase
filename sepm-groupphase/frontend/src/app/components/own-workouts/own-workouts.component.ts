import {Component, OnInit} from '@angular/core';
import {Dude} from '../../dtos/dude';
import {Router} from '@angular/router';
import {OwnWorkoutsService} from '../../services/own-workouts.service';
import {Workout} from '../../dtos/workout';
import {GetWorkout} from '../../dtos/get-workout';

@Component({
  selector: 'app-own-workouts',
  templateUrl: './own-workouts.component.html',
  styleUrls: ['./own-workouts.component.scss']
})
export class OwnWorkoutsComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  workouts: GetWorkout[];
  workoutToDelete: string;
  error: any;

  constructor(private ownWorkoutsService: OwnWorkoutsService, private router: Router) {
  }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    localStorage.setItem('chosenExercisesForEditWorkout', JSON.stringify('empty'));
    localStorage.setItem('firstAccess', JSON.stringify('true'));

    this.ownWorkoutsService.getAllWorkoutsOfLoggedInDude(this.dude).subscribe(
      (data) => {
        console.log('get all workouts created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.workouts = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.name > b.name) {
            return 1;
          }
          return 0;
        });
        console.log(this.workouts);
      },
      error => {
        this.error = error;
      }
    );

  }

  setSelectedWorkout(element: Workout) {
    localStorage.setItem('selectedWorkout', JSON.stringify(element));
  }

  goToEditWorkout(element: Workout) {
    localStorage.setItem('selectedWorkout', JSON.stringify(element));
    this.router.navigate(['/edit-workout']);
  }

  setToDeleteWorkout(element: Workout) {
    localStorage.setItem('selectedWorkout', JSON.stringify(element));
    this.workoutToDelete = element.name;
    console.log('preparing to delete ');
    console.log(element);
  }

  deleteWorkout() {
    this.ownWorkoutsService.deleteWorkout(JSON.parse(localStorage.getItem('selectedWorkout')).id)
      .subscribe(() => {
          console.log('removed workout successfully');
          localStorage.removeItem('selectedWorkout');
          this.ngOnInit();
        },
        error => {
          this.error = error;
        }
      );
  }

}

