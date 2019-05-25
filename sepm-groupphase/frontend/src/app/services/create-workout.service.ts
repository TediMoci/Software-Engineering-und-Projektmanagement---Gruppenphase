import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {CreateWorkout} from '../dtos/create-workout';

@Injectable({
  providedIn: 'root'
})
export class CreateWorkoutService {

  private workoutBaseUri: string = this.globals.backendUri + '/workout';

  constructor(private httpClient: HttpClient, private globals: Globals) {}

  addWorkout(workout: CreateWorkout): Observable<CreateWorkout> {
    console.log('add workout with name ' + workout.name);
    console.log(workout);
    return this.httpClient.post<CreateWorkout>(this.workoutBaseUri, workout);
  }
}
