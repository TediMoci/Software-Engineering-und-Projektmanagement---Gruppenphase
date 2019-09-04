import { Component, OnInit } from '@angular/core';
import {FitnessProvider} from '../../dtos/fitness-provider';

@Component({
  selector: 'app-create-for-fitness-provider',
  templateUrl: './create-for-fitness-provider.component.html',
  styleUrls: ['./create-for-fitness-provider.component.scss']
})
export class CreateForFitnessProviderComponent implements OnInit {

  imagePath: string;
  userName: string;
  fitnessProvider: FitnessProvider;
  error: any;

  constructor() { }

  ngOnInit() {

    this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
    this.userName = this.fitnessProvider.name;
    this.imagePath = this.fitnessProvider.imagePath;
  }
  vanishError() {
    this.error = false;
  }

}
