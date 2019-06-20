import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Dude} from '../../dtos/dude';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {FitnessProvidersFollowedService} from '../../services/fitness-providers-followed.service';

@Component({
  selector: 'app-fitness-providers-followed',
  templateUrl: './fitness-providers-followed.component.html',
  styleUrls: ['./fitness-providers-followed.component.scss']
})
export class FitnessProvidersFollowedComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  fitnessProviders: any;
  error: any;
  fitnessProviderToUnfollow;

  constructor(private fitnessProvidersFollowedService: FitnessProvidersFollowedService, private router: Router) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.fitnessProvidersFollowedService.getAllFollowedFitnessProvidersOfLoggedInDude(this.dude).subscribe(
      (data) => {
        console.log('get all fitness providers followed by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.fitnessProviders = data.sort(function (a, b) { // sort data alphabetically
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

  setSelectedFitnessProvider(element: FitnessProvider) {
    localStorage.setItem('selectedFitnessProvider', JSON.stringify(element));
  }

  setToUnfollowFitnessProvider(element: FitnessProvider) {
    localStorage.setItem('selectedFitnessProvider', JSON.stringify(element));
    this.fitnessProviderToUnfollow = element.name;
    console.log(element);
  }

  unfollowFitnessProvider() {
    this.fitnessProvidersFollowedService.unfollowFitnessProvider(this.dude.id
      , JSON.parse(localStorage.getItem('selectedFitnessProvider')).id)
      .subscribe(() => {
          console.log('unfollowed fitness provider successfully');
          localStorage.removeItem('selectedFitnessProvider');
          this.ngOnInit();
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
