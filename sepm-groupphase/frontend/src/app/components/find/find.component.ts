import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {Dude} from '../../dtos/dude';

@Component({
  selector: 'app-find',
  templateUrl: './find.component.html',
  styleUrls: ['./find.component.scss']
})
export class FindComponent implements OnInit {
  category: string = "Exercise";
  public inputText: any;
  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;


  constructor() { }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    console.log("searchvalue: " + this.inputText)

  }

}
