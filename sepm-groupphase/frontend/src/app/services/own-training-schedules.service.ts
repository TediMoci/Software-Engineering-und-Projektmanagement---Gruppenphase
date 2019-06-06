import { Injectable } from '@angular/core';
import {Dude} from '../dtos/dude';
import {Observable} from 'rxjs';
import {TrainingSchedule} from '../dtos/trainingSchedule';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class OwnTrainingSchedulesService {

  private trainingSchedulesBaseUri: string = this.globals.backendUri + '/dudes';
  private trainingSchedulesEditBaseUri: string = this.globals.backendUri + '/trainingSchedule';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAllTrainingSchedulesOfLoggedInDude (dude: Dude): Observable<TrainingSchedule[]> {
    console.log('get all training schedules created by dude with name ' + dude.name + ' and id ' + dude.id);
    return this.httpClient.get<TrainingSchedule[]>(this.trainingSchedulesBaseUri + '/' + dude.id + '/trainingSchedules');
  }

  deleteTrainingSchedule (trainingScheduleId: number) {
    console.log('delete training schedule with id ' + trainingScheduleId);
    return this.httpClient.delete(this.trainingSchedulesEditBaseUri + '/' + trainingScheduleId);

  }
}
