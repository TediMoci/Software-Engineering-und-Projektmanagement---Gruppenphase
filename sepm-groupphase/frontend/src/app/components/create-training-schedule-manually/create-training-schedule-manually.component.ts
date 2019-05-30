import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {CdkDragDrop, moveItemInArray, transferArrayItem, copyArrayItem} from '@angular/cdk/drag-drop';
import {Workout} from '../../dtos/workout';
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-training-schedule-manually',
  templateUrl: './create-training-schedule-manually.component.html',
  styleUrls: ['./create-training-schedule-manually.component.scss']
})
export class CreateTrainingScheduleManuallyComponent implements OnInit {

  imagePath: string = '/assets/img/kugelfisch.jpg';
  userName: string;
  dude: Dude;
  generalTSData: boolean;
  selectedWorkout: Workout;
  //searchRes: Workout[];
  interval: number = 1;
  intervalDays: string[] = [
    '1 Day',
    '2 Days',
    '3 Days',
    '4 Days',
    '5 Days',
    '6 Days',
    '7 Days'
  ];

  searchRes = [
    'Get to work',
    'Pick up groceries',
    'Go home',
    'Fall asleep'
  ];

  d1 = [
    'test',
    'test2',
    'test3',
    'test4'
  ];

  d2 = [
    'a',
    'b',
    'c',
    'd'
  ];

  d3 = [
    '1',
    '2',
    '3',
    '4'
  ];

  d4 = [
    'Hallo',
    'Hi'
  ];
  d5 = [
    'Grinzing',
    'Penzing',
    'Hitzing'
  ];

  d6 = [
    'orange',
    'purple',
    'yellow'
  ];

  d7 = [
    'Get up',
    'Brush teeth',
    'Take a shower',
    'Check e-mail',
    'Walk dog'
  ];

  constructor(private router: Router) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.generalTSData = true;
  }

  changeView() {
    this.generalTSData = !this.generalTSData;
  }

  setSelectedWorkout(element: Workout){
    this.selectedWorkout = element;
  }

  drop(event: CdkDragDrop<string[]>) {
    console.log('standard drop method call');
    console.log(event.previousContainer.id);

    if(event.isPointerOverContainer) {
      if (event.previousContainer === event.container) {
        moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
      } else {
        if (event.previousContainer.id === 'cdk-drop-list-0') {
          // copy items taken from search results list
          copyArrayItem(event.previousContainer.data,
            event.container.data,
            event.previousIndex,
            event.currentIndex);
        } else {
          transferArrayItem(event.previousContainer.data,
            event.container.data,
            event.previousIndex,
            event.currentIndex);
        }
      }
    } else {
      // remove item when dragged outside of the dragging areas
      event.previousContainer.data.splice(event.previousIndex, 1);
    }
  }

  setChosenInterval(days: string) {
    switch (days) {
      case '1 Day':
        this.interval = 1;
        break;
      case '2 Days':
        this.interval = 2;
        break;
      case '3 Days':
        this.interval = 3;
        break;
      case '4 Days':
        this.interval = 4;
        break;
      case '5 Days':
        this.interval = 5;
        break;
      case '6 Days':
        this.interval = 6;
        break;
      case '7 Days':
        this.interval = 7;
        break;
    }
  }

}
