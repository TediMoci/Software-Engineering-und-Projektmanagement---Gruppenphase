import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Globals} from "../global/globals";
import {Observable} from "rxjs";
import {TrainingScheduleWo} from "../dtos/training-schedule-wo";
import {TrainingSchedule} from "../dtos/trainingSchedule";

@Injectable({
  providedIn: 'root'
})
export class TrainingScheduleService {

  private BaseUri: string = this.globals.backendUri + '/trainingSchedule';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getWorkoutsOfTrainingScheduleById(trainingScheduleId: number, trainingScheduleVersion: number): Observable<TrainingScheduleWo[]> {
    console.log('get all workouts belonging to the training schedule with id ' + trainingScheduleId);
    return this.httpClient.get<TrainingScheduleWo[]>(this.BaseUri + '/' + trainingScheduleId + '/' + trainingScheduleVersion + '/workouts');
  }
  getTrainingScheduleByIdandVersion(trainingScheduleId: number, trainingScheduleVersion: number): Observable<TrainingSchedule>{
    console.log('get training schedule with id '+ trainingScheduleId);
    return this.httpClient.get<TrainingSchedule>(this.BaseUri+'/'+trainingScheduleId+'/'+trainingScheduleVersion)
  }
}
