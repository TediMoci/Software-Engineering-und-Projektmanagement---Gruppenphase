import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {WorkoutExercise} from '../dtos/workoutExercise';

@Injectable({
  providedIn: 'root'
})
export class WorkoutService {

  private workoutBaseUri: string = this.globals.backendUri + '/workout';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getExercisesOfWorkoutById(workoutId: number, workoutVersion: number): Observable<WorkoutExercise[]> {
    console.log('get all exercises belonging to the workout with id ' + workoutId);
    return this.httpClient.get<WorkoutExercise[]>(this.workoutBaseUri + '/' + workoutId + '/' + workoutVersion + '/exercises');
  }
}
