import { Injectable } from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {WorkoutExercise} from '../dtos/workoutExercise';
import {TrainingScheduleWorkout} from '../dtos/trainingScheduleWorkout';


@Injectable({
  providedIn: 'root'
})
export class TrainingScheduleService {
  private BaseUri: string = this.globals.backendUri + '/trainingSchedule';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getWorkoutsOfTSById(TSId: number, TSVersion: number): Observable<TrainingScheduleWorkout[]> {
    console.log('get all workouts belonging to the training schedule with id ' + TSId);
    return this.httpClient.get<TrainingScheduleWorkout[]>(this.BaseUri + '/' + TSId + '/' + TSVersion + '/workouts');
  }
}
