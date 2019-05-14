import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bros',
  templateUrl: './bros.component.html',
  styleUrls: ['./bros.component.scss']
})
export class BrosComponent implements OnInit {
  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string = 'Username';
  constructor() { }

  ngOnInit() {
  }

}
