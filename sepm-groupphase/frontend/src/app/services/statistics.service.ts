import {Injectable} from '@angular/core';
import {Dude} from '../dtos/dude';
import {Observable} from 'rxjs';
import {Statistics} from '../dtos/statistics';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  private statisticsBaseUri: string = this.globals.backendUri + '/dudes';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

   getAllStatisticsOfLoggedInDude(dude: Dude): Observable<Statistics[]> {
    console.log('get all statistics of formerly active trainingSchedules of dude with name ' + dude.name + ' and id ' + dude.id);
    return this.httpClient.get<Statistics[]>(this.statisticsBaseUri + '/' + dude.id + '/statistics');

  }
}

