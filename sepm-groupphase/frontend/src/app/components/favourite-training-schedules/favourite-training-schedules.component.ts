import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FavouritesService} from '../../services/favourites.service';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {BookmarksService} from '../../services/bookmarks.service';

@Component({
  selector: 'app-favourite-training-schedules',
  templateUrl: './favourite-training-schedules.component.html',
  styleUrls: ['./favourite-training-schedules.component.scss']
})
export class FavouriteTrainingSchedulesComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  toDeleteTrainingScheduleBookmark: string;
  favTrainingSchedules: TrainingSchedule[];
  error: any;
  constructor(private favouritesService: FavouritesService, private bookmarksService: BookmarksService) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.favouritesService.getAllTrainingSchedulesBookmarkedByDudeId(this.dude.id).subscribe(
      (data) => {
        console.log('get all trainingSchedules bookmarked by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.favTrainingSchedules = data.sort(function (a, b) { // sort data alphabetically
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

  setSelectedTrainingSchedule(element: TrainingSchedule) {
    localStorage.setItem('selectedTrainingSchedule', JSON.stringify(element));
  }

  setToDeleteTrainingScheduleBookmark(element: TrainingSchedule) {
    localStorage.setItem('selectedTrainingSchedule', JSON.stringify(element));
    this.toDeleteTrainingScheduleBookmark = element.name;
  }

  deleteBookmark() {
    const selectedTs = JSON.parse(localStorage.getItem('selectedTrainingSchedule'));
    this.bookmarksService.deleteBookmarkOfTrainingSchedule(this.dude.id, selectedTs.id, selectedTs.version)
      .subscribe(() => {
          console.log('deleted bookmark of trainingSchedule successfully');
          localStorage.removeItem('selectedTrainingSchedule');
          this.ngOnInit();
        },
        error => {
          this.error = error;
        }
      );
  }
}
