import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Workout} from '../dtos/workout';

@Injectable({
  providedIn: 'root'
})
export class TrainingScheduleWorkoutsService {

  private workoutBaseUri: string = this.globals.backendUri + '/workout';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getWorkoutsByName(name: string, dudeId: number): Observable<Workout[]> {
    console.log('get all workouts by name ' + name);
    const params = new HttpParams().set('name', name);
    return this.httpClient.get<Workout[]>(this.workoutBaseUri + '/' + dudeId, {params: params});
  }
}
