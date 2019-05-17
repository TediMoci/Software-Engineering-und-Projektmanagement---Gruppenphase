import { Component, OnInit } from '@angular/core';
import {FitnessProviderProfileService} from '../../services/fitness-provider-profile.service';
import {FitnessProvider} from '../../dtos/fitness-provider';

@Component({
  selector: 'app-fitness-provider-profile',
  templateUrl: './fitness-provider-profile.component.html',
  styleUrls: ['./fitness-provider-profile.component.scss']
})

export class FitnessProviderProfileComponent implements OnInit {
  error: any;
  imagePath: string = 'assets/img/kugelfisch2.jpg';
  userName: string;
  address: string;
  email: string;
  phoneNumber: string;
  website: string;
  numOfFollowers: number = 0;
  courses: any;
  description: string;
  currentUser: FitnessProvider;
  constructor(private fitnessProviderProfile: FitnessProviderProfileService) { }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.userName = this.currentUser.name;
    this.address = this.currentUser.address;
    this.email = this.currentUser.email;
    this.phoneNumber = this.currentUser.phoneNumber;
    this.website = this.currentUser.website;
    this.description = this.currentUser.description;
    this.fitnessProviderProfile.getFollower(this.userName).subscribe(
      (data) => {
        console.log('number of followers of fitness provider : ' + data + 'and name of fitness provider' + name);
        this.numOfFollowers = data;
      },
      error => {
        this.error = error;
      }
    );
  }

  vanishError() {
    this.error = false;
  }
}
