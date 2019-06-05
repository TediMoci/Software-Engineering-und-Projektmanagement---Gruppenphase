import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Globals} from "../global/globals";
import {Observable} from "rxjs";
import {TrainingScheduleWo} from "../dtos/training-schedule-wo";

@Injectable({
  providedIn: 'root'
})
export class TrainingScheduleService {

  private workoutBaseUri: string = this.globals.backendUri + '/trainingSchedule';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getWorkoutsOfTrainingScheduleById(trainingScheduleId: number, trainingScheduleVersion: number): Observable<TrainingScheduleWo[]> {
    console.log('get all workouts belonging to the training schedule with id ' + trainingScheduleId);
    return this.httpClient.get<TrainingScheduleWo[]>(this.workoutBaseUri + '/' + trainingScheduleId + '/' + trainingScheduleVersion + '/workouts');
  }
}
