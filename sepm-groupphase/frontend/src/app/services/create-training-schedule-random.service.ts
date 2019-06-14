import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {TrainingSchedule} from '../dtos/trainingSchedule';
import {CreateTraingsPlanRandom} from '../dtos/create-traings-plan-random';

@Injectable({
  providedIn: 'root'
})
export class CreateTrainingScheduleRandomService {

  private trainingScheduleBaseUri: string = this.globals.backendUri + '/trainingSchedule';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  addRandomTrainingSchedule(rts: CreateTraingsPlanRandom): Observable<TrainingSchedule> {
    console.log('add trainingSchedule with params ' + JSON.stringify(rts));
    return this.httpClient.post<TrainingSchedule>(
      this.trainingScheduleBaseUri + '/' + rts.interval + '/' + rts.repetitions + '/' + rts.minTarget + '/' + rts.maxTarget, rts);
  }
}
