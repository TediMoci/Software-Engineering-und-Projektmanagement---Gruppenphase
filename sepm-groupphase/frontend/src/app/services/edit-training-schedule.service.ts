import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {EditTrainingSchedule} from '../dtos/edit-training-schedule';

@Injectable({
  providedIn: 'root'
})
export class EditTrainingScheduleService {

  private BaseUri: string = this.globals.backendUri + '/trainingSchedule';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  editTrainingSchedule(trainingSchedule: EditTrainingSchedule): Observable<EditTrainingSchedule> {
    console.log('edit training schedule with id ' + trainingSchedule.id + ' and version ' + trainingSchedule.version);
    return this.httpClient.put<EditTrainingSchedule>(this.BaseUri + '/' + trainingSchedule.id, trainingSchedule);
  }
}
