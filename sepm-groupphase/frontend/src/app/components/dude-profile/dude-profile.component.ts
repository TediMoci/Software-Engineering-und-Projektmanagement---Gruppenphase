import {Component, OnInit, Input, AfterViewInit} from '@angular/core';
import {LoginComponent} from '../login/login.component';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../../global/globals';
import {Dude} from '../../dtos/dude';
import {ProfileService} from '../../services/profile.service';

@Component({
  selector: 'app-dude-profile',
  templateUrl: './dude-profile.component.html',
  styleUrls: ['./dude-profile.component.scss'],
  providers: [LoginComponent]
})
export class DudeProfileComponent implements OnInit {

  error: any;
  imagePath: string = 'assets/img/kugelfisch.jpg';
  userName: string;
  sex: any;
  skill: string;
  rank: string;
  age: number;
  height: number;
  weight: number;
  bmi: number;
  description: string;

  dude: Dude;

  constructor(private globals: Globals, private profileService: ProfileService, private httpClient: HttpClient) {
  }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));

    this.userName = this.dude.name;

    if (this.dude.selfAssessment === 1) {
      this.skill = 'Beginner';
    } else if (this.dude.selfAssessment === 2) {
      this.skill = 'Advanced';
    } else {
      this.skill = 'Pro';
    }
    if (this.dude.status === 1) {
      this.rank = 'New Dude';
    } else if (this.dude.status == 2) {
      this.rank = 'Experienced Dude';
    } else {
      this.rank = 'Ancient Dude';
    }

    this.height = this.dude.height;
    this.weight = this.dude.weight;
    this.sex = this.dude.sex;
    this.description = this.dude.description;

    this.profileService.getAge(this.dude.birthday, this.dude.name).subscribe(
      (data) => {
        console.log('calculate age of dude with name ' + this.dude.name);
        this.age = data;
      },
      error => {
        this.error = error;
      }
    );

    this.profileService.getBmi(this.dude.height, this.dude.weight, this.dude.name).subscribe(
      (data) => {
        console.log('calculate bmi of dude with name ' + this.dude.name);
        this.bmi = data;
      },
        error => {
        this.error = error;
        }
    );

  }

}
