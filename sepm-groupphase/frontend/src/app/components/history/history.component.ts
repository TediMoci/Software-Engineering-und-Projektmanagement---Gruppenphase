import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {StatisticsService} from '../../services/statistics.service';
import {Statistics} from '../../dtos/statistics';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnInit {

  imagePath: string;
  userName: string;
  dude: Dude;
  stats: Statistics[];
  error: any;
  constructor(private statisticsService: StatisticsService) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.statisticsService.getAllStatisticsOfLoggedInDude(this.dude).subscribe(
      (data) => {
        console.log('get all statistics of formerly active training schedules of dude with name ' + this.dude.name + ' and id ' + this.dude.id);
        this.stats = data.sort(function (a, b) { // sort data alphabetically
          if (a.trainingScheduleDto.name.toLocaleLowerCase() < b.trainingScheduleDto.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.trainingScheduleDto.name > b.trainingScheduleDto.name) {
            return 1;
          }
          return 0;
        });
      },
      error => {
        this.error = error;
      }
    );
  }

  setSelectedStats(element: Statistics) {
    localStorage.setItem('selectedStatistics', JSON.stringify(element));
  }

  vanishError() {
    this.error = false;
  }

}
