import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FavouritesService} from '../../services/favourites.service';
import {Exercise} from '../../dtos/exercise';
import {BookmarksService} from '../../services/bookmarks.service';

@Component({
  selector: 'app-favourite-exercises',
  templateUrl: './favourite-exercises.component.html',
  styleUrls: ['./favourite-exercises.component.scss']
})
export class FavouriteExercisesComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  favExercises: Exercise[];
  toDeleteExerciseBookmark: string;
  error: any;
  constructor(private favouritesService: FavouritesService, private bookmarksService: BookmarksService) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.favouritesService.getAllExercisesBookmarkedByDudeId(this.dude.id).subscribe(
      (data) => {
        console.log('get all exercises bookmarked by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.favExercises = data.sort(function (a, b) { // sort data alphabetically
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

  setSelectedExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }

  setToDeleteExerciseBookmark(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
    this.toDeleteExerciseBookmark = element.name;
  }

  deleteBookmark() {
    const selectedEx = JSON.parse(localStorage.getItem('selectedExercise'));
    this.bookmarksService.deleteBookmarkOfExercise(this.dude.id, selectedEx.id, selectedEx.version)
      .subscribe(() => {
          console.log('deleted bookmark of exercise successfully');
          localStorage.removeItem('selectedExercise');
          this.ngOnInit();
        },
        error => {
          this.error = error;
        }
      );
  }
}
