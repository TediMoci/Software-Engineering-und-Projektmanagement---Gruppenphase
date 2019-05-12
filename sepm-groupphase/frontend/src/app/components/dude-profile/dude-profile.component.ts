import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dude-profile',
  templateUrl: './dude-profile.component.html',
  styleUrls: ['./dude-profile.component.scss']
})
export class DudeProfileComponent implements OnInit {

  imagePath: string = 'assets/img/kugelfisch.jpg';
  userName: string = 'get name from dto';
  skill: string = 'get skill from dto';
  rank: string = 'get rank from dto';
  age: number = 24;
  height: number = 230;
  weight: number = 76;
  bmi: number = 21;
  constructor() { }

  ngOnInit() {
  }

}
