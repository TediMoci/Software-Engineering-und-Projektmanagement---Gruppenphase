import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Exercise} from '../dtos/exercise';
import {Observable} from 'rxjs';
import {EditWorkout} from '../dtos/edit-workout';

@Injectable({
  providedIn: 'root'
})
export class EditWorkoutService {

  private workoutBaseUri: string = this.globals.backendUri + '/workout';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  editWorkout(workout: EditWorkout): Observable<EditWorkout> {
    console.log('edit workout with id ' + workout.id + ' and version ' + workout.version);
    return this.httpClient.put<EditWorkout>(this.workoutBaseUri + '/' + workout.id, workout);
  }
}
