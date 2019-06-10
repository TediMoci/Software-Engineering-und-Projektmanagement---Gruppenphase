import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  error: any;
  constructor() { }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    localStorage.setItem('previousRoute', JSON.stringify('/'));
    localStorage.setItem('previousPreviousRoute', JSON.stringify('/'));
  }
}

