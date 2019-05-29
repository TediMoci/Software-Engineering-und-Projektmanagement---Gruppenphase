import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';

@Component({
  selector: 'app-fitness-providers-followed',
  templateUrl: './fitness-providers-followed.component.html',
  styleUrls: ['./fitness-providers-followed.component.scss']
})
export class FitnessProvidersFollowedComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  constructor() { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
  }

}
