import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {ProfileService} from '../../services/profile.service';

@Component({
  selector: 'app-dude',
  templateUrl: './follower-dude.component.html',
  styleUrls: ['./follower-dude.component.scss']
})
export class FollowerDudeComponent implements OnInit {

  imagePath: string;
  imagePath2: string;
  userName: string;
  fitnessProvider: FitnessProvider;
  dudeName: string;
  description: string;
  sex: any;
  selfAssessment: string;
  dude: Dude;
  error: any;
  constructor() { }

  ngOnInit() {
    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.userName = this.fitnessProvider.name;
    this.imagePath = this.fitnessProvider.imagePath;
    this.dude = JSON.parse(localStorage.getItem('selectedDude'));
    this.dudeName = this.dude.name;
    this.imagePath2 = this.dude.imagePath;
    this.description = this.dude.description;
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
  }

}
