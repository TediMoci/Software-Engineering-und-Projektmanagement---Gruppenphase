import { Injectable } from '@angular/core';
import {Dude} from '../dtos/dude';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {CreateExercise} from '../dtos/create-exercise';
import {WorkoutExercise} from '../dtos/workoutExercise';
import {Workout} from '../dtos/workout';

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

  getWorkoutByIdAndVersion(workoutId: number, workoutVersion: number): Observable<Workout> {
    console.log('get all workout with this id: ' + workoutId + ' and this version: ' + workoutVersion);
    return this.httpClient.get<Workout>(this.workoutBaseUri + '/' + workoutId + '/' + workoutVersion);
  }
}
