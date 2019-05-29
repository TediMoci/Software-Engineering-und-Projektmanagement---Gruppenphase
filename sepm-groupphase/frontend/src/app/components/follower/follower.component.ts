import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FitnessProvider} from '../../dtos/fitness-provider';

@Component({
  selector: 'app-follower',
  templateUrl: './follower.component.html',
  styleUrls: ['./follower.component.scss']
})
export class FollowerComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch2.jpg';
  userName: string;
  fitnessProvider: FitnessProvider;
  constructor() { }

  ngOnInit() {

    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.userName = this.fitnessProvider.name;
  }

}
