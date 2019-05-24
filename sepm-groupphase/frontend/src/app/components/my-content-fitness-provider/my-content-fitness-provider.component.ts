import { Component, OnInit } from '@angular/core';
import {FitnessProvider} from '../../dtos/fitness-provider';

@Component({
  selector: 'app-my-content-fitness-provider',
  templateUrl: './my-content-fitness-provider.component.html',
  styleUrls: ['./my-content-fitness-provider.component.scss']
})
export class MyContentFitnessProviderComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch2.jpg';
  userName: string;
  fitnessProvider: FitnessProvider;
  constructor() { }

  ngOnInit() {

    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.userName = this.fitnessProvider.name;
  }

}
