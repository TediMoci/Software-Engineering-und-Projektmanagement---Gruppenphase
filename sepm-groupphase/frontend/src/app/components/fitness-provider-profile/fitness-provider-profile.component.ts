import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fitness-provider-profile',
  templateUrl: './fitness-provider-profile.component.html',
  styleUrls: ['./fitness-provider-profile.component.scss']
})
export class FitnessProviderProfileComponent implements OnInit {
  imagePath: string = 'assets/img/kugelfisch2.jpg';
  userName: string = 'userName from dto';
  adress: string = 'adress from dto';
  email: string = 'email from dto';
  phoneNumber: string = 'number from dto';
  website: string = 'website from dto';
  numOfFollowers: number = 200;
  courses: any = 'courses from dto';
  description: string = 'Some time in the future we should be able to read some content from a dto and then show a real description here.';
  constructor() { }

  ngOnInit() {
  }

}
