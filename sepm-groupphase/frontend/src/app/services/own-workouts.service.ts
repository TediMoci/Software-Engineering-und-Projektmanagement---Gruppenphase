import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Dude} from '../dtos/dude';
import {Workout} from '../dtos/workout';

@Injectable({
  providedIn: 'root'
})
export class OwnWorkoutsService {

  private workoutsBaseUri: string = this.globals.backendUri + '/dudes';
  private workoutEditBaseUri: string = this.globals.backendUri + '/workout';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAllWorkoutsOfLoggedInDude(dude: Dude): Observable<Workout[]>{
    console.log('get all workouts created by dude with name ' + dude.name + ' and id ' + dude.id);
    return this.httpClient.get<Workout[]>(this.workoutsBaseUri + '/' + dude.id + '/workouts');
  }

  deleteWorkout(workoutId: number) {
      console.log('delete workout with id ' + workoutId);
      return this.httpClient.delete(this.workoutEditBaseUri + '/' + workoutId);
  }
}
