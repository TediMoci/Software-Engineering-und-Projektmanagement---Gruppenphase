import { Injectable } from '@angular/core';
import {CreateTrainingSchedule} from '../dtos/create-trainingSchedule';
import {Observable} from 'rxjs';
import {TrainingSchedule} from '../dtos/trainingSchedule';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class CreateTrainingScheduleService {

  private trainingScheduleBaseUri: string = this.globals.backendUri + '/trainingSchedule';

  constructor(private httpClient: HttpClient, private globals: Globals) {}

  addTrainingSchedule(trainingSchedule: CreateTrainingSchedule): Observable<TrainingSchedule> {
    console.log('add trainingSchedule with name ' + trainingSchedule.name);
    return this.httpClient.post<TrainingSchedule>(this.trainingScheduleBaseUri, trainingSchedule);
  }
}
