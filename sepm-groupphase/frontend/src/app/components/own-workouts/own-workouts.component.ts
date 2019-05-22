import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {Router} from '@angular/router';
import {Workout} from '../../dtos/workout';
import {OwnWorkoutsService} from '../../services/own-workouts.service';

@Component({
  selector: 'app-own-workouts',
  templateUrl: './own-workouts.component.html',
  styleUrls: ['./own-workouts.component.scss']
})
export class OwnWorkoutsComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  workouts: any;
  workoutToDelete: string;
  error: any;
  constructor(private ownWorkoutsService: OwnWorkoutsService, private router: Router) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;

    this.ownWorkoutsService.getAllWorkoutsOfLoggedInDude().subscribe(
      (data) => {
        console.log('get all workouts created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.workouts = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1;}
          if (a.name > b.name) {return 1;}
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
  }

  deleteWorkout(){

  }

}

