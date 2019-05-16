import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';

@Component({
  selector: 'app-bros',
  templateUrl: './bros.component.html',
  styleUrls: ['./bros.component.scss']
})
export class BrosComponent implements OnInit {
  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  constructor() { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
  }

}
