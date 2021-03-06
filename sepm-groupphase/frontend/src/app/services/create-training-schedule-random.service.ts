import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {TrainingSchedule} from '../dtos/trainingSchedule';
import {CreateTrainingScheduleRandom} from '../dtos/create-training-schedule-random';

@Injectable({
  providedIn: 'root'
})
export class CreateTrainingScheduleRandomService {

  private trainingScheduleBaseUri: string = this.globals.backendUri + '/trainingSchedule';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  addRandomTrainingSchedule(rts: CreateTrainingScheduleRandom): Observable<TrainingSchedule> {
    console.log('add trainingSchedule with params ' + JSON.stringify(rts));
    return this.httpClient.post<TrainingSchedule>(
      this.trainingScheduleBaseUri + '/random',  rts);
  }
}
