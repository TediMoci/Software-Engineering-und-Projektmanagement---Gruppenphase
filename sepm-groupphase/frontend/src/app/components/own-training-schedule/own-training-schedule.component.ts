import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {Router} from '@angular/router';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {OwnTrainingSchedulesService} from '../../services/own-training-schedules.service';

@Component({
  selector: 'app-own-training-schedule',
  templateUrl: './own-training-schedule.component.html',
  styleUrls: ['./own-training-schedule.component.scss']
})
export class OwnTrainingScheduleComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  trainingSchedules: any;
  error: any;
  trainingScheduleToDelete;

  constructor(private ownTrainingSchedulesService: OwnTrainingSchedulesService, private router: Router) {
  }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    localStorage.setItem('chosenWorkoutsForEditTS', JSON.stringify('empty'));
    localStorage.setItem('firstAccess', JSON.stringify('true'));

    this.ownTrainingSchedulesService.getAllTrainingSchedulesOfLoggedInDude(this.dude).subscribe(
      (data) => {
        console.log('get all trainingSchedules created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.trainingSchedules = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.name > b.name) {
            return 1;
          }
          return 0;
        });
        console.log('loaded ' + JSON.stringify(this.trainingSchedules[1]))
      },
      error => {
        this.error = error;
      }
    );

  }

  setSelectedTrainingSchedule (element: TrainingSchedule) {
    console.log('Selected ' + JSON.stringify( JSON.stringify(element)))
    localStorage.setItem('selectedTrainingSchedule', JSON.stringify(element));

  }

  goToEditTrainingSchedule(element: TrainingSchedule) {
    localStorage.setItem('selectedTrainingSchedule', JSON.stringify(element));
    this.router.navigate(['/edit-training-schedule']);
  }

  goToEditTrainingSchedule2(element: TrainingSchedule) {
    localStorage.setItem('selectedTrainingSchedule', JSON.stringify(element));
    this.router.navigate(['/edit-trainingschedule']);
  }

  setToDeleteTrainingSchedule(element: TrainingSchedule) {
    localStorage.setItem('selectedTrainingSchedule', JSON.stringify(element));
    this.trainingScheduleToDelete = element.name;
    console.log(element);
  }

  deleteTrainingSchedule() {
    this.ownTrainingSchedulesService.deleteTrainingSchedule(JSON.parse(localStorage.getItem('selectedTrainingSchedule')).id)
      .subscribe(() => {
          console.log('removed trainingSchedule successfully');
          localStorage.removeItem('selectedTrainingSchedule');
          this.ngOnInit();
        },
        error => {
          this.error = error;
        }
      );
  }
}
