import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FavouritesService} from '../../services/favourites.service';
import {Course} from '../../dtos/course';
import {BookmarksService} from '../../services/bookmarks.service';

@Component({
  selector: 'app-favourite-courses',
  templateUrl: './favourite-courses.component.html',
  styleUrls: ['./favourite-courses.component.scss']
})
export class FavouriteCoursesComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  error: any;
  favCourses: Course[];
  toDeleteCourseBookmark: string;
  constructor(private favouritesService: FavouritesService, private bookmarksService: BookmarksService) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.favouritesService.getAllCoursesBookmarkedByDudeId(this.dude.id).subscribe(
      (data) => {
        console.log('get all courses bookmarked by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.favCourses = data.sort(function (a, b) { // sort data alphabetically
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

  setSelectedCourse(element: Course) {
    localStorage.setItem('selectedCourse', JSON.stringify(element));
  }

  setToDeleteCourseBookmark(element: Course) {
    localStorage.setItem('selectedCourse', JSON.stringify(element));
    this.toDeleteCourseBookmark = element.name;
  }

  deleteBookmark() {
    this.bookmarksService.deleteBookmarkOfCourse(this.dude.id, JSON.parse(localStorage.getItem('selectedCourse')).id)
      .subscribe(() => {
          console.log('deleted bookmark of course successfully');
          localStorage.removeItem('selectedCourse');
          this.ngOnInit();
        },
        error => {
          this.error = error;
        }
      );
  }
}
