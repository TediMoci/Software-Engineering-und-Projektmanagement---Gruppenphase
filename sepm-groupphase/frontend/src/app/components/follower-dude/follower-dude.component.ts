import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FitnessProvider} from '../../dtos/fitness-provider';

@Component({
  selector: 'app-dude',
  templateUrl: './follower-dude.component.html',
  styleUrls: ['./follower-dude.component.scss']
})
export class FollowerDudeComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch2.jpg';
  imagePath2: string = 'assets/img/kugelfisch.jpg';
  userName: string;
  fitnessProvider: FitnessProvider;
  dudeName: string;
  description: string;
  email: string;
  sex: any;
  selfAssessment: string;
  birthday: Date;
  height: number;
  weight: number;
  dude: Dude;
  constructor() { }

  ngOnInit() {
    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.userName = this.fitnessProvider.name;
    this.dude = JSON.parse(localStorage.getItem('selectedDude'));
    this.dudeName = this.dude.name;
    this.description = this.dude.description;
    this.email = this.dude.email;
    this.sex = this.dude.sex;
    switch (this.dude.selfAssessment) {
      case 1: {
        this.selfAssessment = 'Beginner';
        break;
      }
      case 2: {
        this.selfAssessment = 'Advanced';
        break;
      }
      default: {
        this.selfAssessment = 'Pro';
        break;
      }
    }
    this.birthday = this.dude.birthday;
    this.height = this.dude.height;
    this.weight = this.dude.weight;
  }

}
