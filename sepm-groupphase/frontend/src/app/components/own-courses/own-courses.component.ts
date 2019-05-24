import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {OwnCoursesService} from '../../services/own-courses.service';
import {Course} from '../../dtos/course';

@Component({
  selector: 'app-own-courses',
  templateUrl: './own-courses.component.html',
  styleUrls: ['./own-courses.component.scss']
})
export class OwnCoursesComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch2.jpg';
  userName: string;
  fitnessProvider: FitnessProvider;
  courses: any;
  error: any;
  courseToDelete;
  constructor(private ownCoursesService: OwnCoursesService, private router: Router) { }

  ngOnInit() {

    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.userName = this.fitnessProvider.name;

    this.ownCoursesService.getAllCoursesOfLoggedInFP(this.fitnessProvider).subscribe(
      (data) => {
        console.log('get all courses created by fitnessprovider with name ' + this.fitnessProvider.name + ' and id ' + this.fitnessProvider.id);
        this.courses = data.sort(function (a, b) { // sort data alphabetically
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

  setSelectedCourse(element: Course) {
    localStorage.setItem('selectedCourse', JSON.stringify(element));
  }

  goToEditCourse(element: Course) {
    localStorage.setItem('selectedCourse', JSON.stringify(element));
    this.router.navigate(['/edit-course']);
  }

  setToDeleteCourse(element: Course) {
    localStorage.setItem('selectedCourse', JSON.stringify(element));
    this.courseToDelete = element.name;
    console.log(element);
  }

  deleteCourse() {
    this.ownCoursesService.deleteCourse(JSON.parse(localStorage.getItem('selectedCourse')).id)
      .subscribe(() => {
          console.log('removed course successfully');
          localStorage.removeItem('selectedCourse');
          this.ngOnInit();
        },
        error => {
          this.error = error;
        }
      );
  }

}

