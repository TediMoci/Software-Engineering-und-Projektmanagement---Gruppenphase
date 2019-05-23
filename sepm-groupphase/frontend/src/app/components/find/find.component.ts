import { Component, OnInit } from '@angular/core';
import {from, Observable} from 'rxjs';
import {FindService} from '../../services/find.service';
import {Dude} from '../../dtos/dude';
import {Exercise} from "../../dtos/Exercise";

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

  exercise:Exercise;


  constructor(private findService:FindService) {}

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    console.log("searchvalue: " + this.inputText)
  }

  startSearch(category: string){
    switch (category) {
      case "Exercise": console.log("Exercise not implemented yet");//this.findService.getAllExercisesFilterd(this.exercise);
            break;
      case "Course": console.log("Course not implemented yet");
            break;
      case "Workout": console.log("Workout not implemented yet");
        break;
      case "Dudes": console.log("Dudes not implemented yet");
        break;
      case "Fitness Provider": console.log("Fitness Provider not implemented yet");
        break;
    }
  }

}
