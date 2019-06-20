import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {FollowerService} from '../../services/follower.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-follower',
  templateUrl: './follower.component.html',
  styleUrls: ['./follower.component.scss']
})
export class FollowerComponent implements OnInit {

  imagePath: string;
  userName: string;
  fitnessProvider: FitnessProvider;
  followers: any;
  error: any;
  constructor(private followerService: FollowerService, private router: Router) { }

  ngOnInit() {

    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.userName = this.fitnessProvider.name;
    this.imagePath = this.fitnessProvider.imagePath;
    this.followerService.getAllFollowersOfFP(this.fitnessProvider).subscribe(
      (data) => {
        console.log('get all followers of fitness provider with name ' + this.fitnessProvider.name + ' and id ' + this.fitnessProvider.id);
        this.followers = data.sort(function (a, b) { // sort data alphabetically
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

  setSelectedDude(element: Dude) {
    localStorage.setItem('selectedDude', JSON.stringify(element));
  }
  vanishError() {
    this.error = false;
  }
}
