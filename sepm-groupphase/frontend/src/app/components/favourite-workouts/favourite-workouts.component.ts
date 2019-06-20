import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FavouritesService} from '../../services/favourites.service';
import {GetWorkout} from '../../dtos/get-workout';
import {BookmarksService} from '../../services/bookmarks.service';

@Component({
  selector: 'app-favourite-workouts',
  templateUrl: './favourite-workouts.component.html',
  styleUrls: ['./favourite-workouts.component.scss']
})
export class FavouriteWorkoutsComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  toDeleteWorkoutBookmark: string;
  favWorkouts: GetWorkout[];
  error: any;
  constructor(private favouritesService: FavouritesService, private bookmarksService: BookmarksService) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.favouritesService.getAllWorkoutsBookmarkedByDudeId(this.dude.id).subscribe(
      (data) => {
        console.log('get all workouts bookmarked by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.favWorkouts = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.name.toLocaleLowerCase() > b.name.toLocaleLowerCase()) {
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

  vanishError() {
    this.error = false;
  }

  setSelectedWorkout(element: GetWorkout) {
    localStorage.setItem('selectedWorkout', JSON.stringify(element));
  }

  setToDeleteWorkoutBookmark(element: GetWorkout) {
    localStorage.setItem('selectedWorkout', JSON.stringify(element));
    this.toDeleteWorkoutBookmark = element.name;
  }

  deleteBookmark() {
    const selectedWa = JSON.parse(localStorage.getItem('selectedWorkout'));
    this.bookmarksService.deleteBookmarkOfWorkout(this.dude.id, selectedWa.id, selectedWa.version)
      .subscribe(() => {
          console.log('deleted bookmark of workout successfully');
          localStorage.removeItem('selectedWorkout');
          this.ngOnInit();
        },
        error => {
          this.error = error;
        }
      );
  }
}
