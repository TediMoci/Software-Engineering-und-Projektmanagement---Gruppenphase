import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {FitnessProviderCoursesService} from '../../services/fitness-provider-courses.service';
import {Router} from '@angular/router';
import {Course} from '../../dtos/course';

@Component({
  selector: 'app-fitness-provider',
  templateUrl: './fitness-provider.component.html',
  styleUrls: ['./fitness-provider.component.scss']
})
export class FitnessProviderComponent implements OnInit {

  imagePath: string;
  imagePath2: string;
  userName: string;
  fitnessProviderName: string;
  description: string;
  address: string;
  email: string;
  phoneNumber: string;
  website: string;
  dude: Dude;
  fitnessProvider: FitnessProvider;
  courses: any;
  error: any;
  constructor(private fitnessProviderCoursesService: FitnessProviderCoursesService, private router: Router) { }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.fitnessProvider = JSON.parse(localStorage.getItem('selectedFitnessProvider'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.fitnessProviderName = this.fitnessProvider.name;
    this.imagePath2 = this.fitnessProvider.imagePath;
    this.description = this.fitnessProvider.description;
    this.address = this.fitnessProvider.address;
    this.email = this.fitnessProvider.email;
    this.phoneNumber = this.fitnessProvider.phoneNumber;
    this.website = this.fitnessProvider.website;

    this.fitnessProviderCoursesService.getAllCoursesOfFP(this.fitnessProvider).subscribe(
      (data) => {
        console.log('get all courses created by fitness provider with name ' + this.fitnessProvider.name + ' and id ' + this.fitnessProvider.id);
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

}
