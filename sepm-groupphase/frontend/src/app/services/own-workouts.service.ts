import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Dude} from '../dtos/dude';
import {WorkoutExerciseDtoOut} from '../dtos/workoutExerciseDtoOut';
import {GetWorkout} from '../dtos/get-workout';

@Injectable({
  providedIn: 'root'
})
export class OwnWorkoutsService {

  private workoutsBaseUri: string = this.globals.backendUri + '/dudes';
  private workoutBaseUri: string = this.globals.backendUri + '/workout';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAllWorkoutsOfLoggedInDude(dude: Dude): Observable<GetWorkout[]>{
    console.log('get all workouts created by dude with name ' + dude.name + ' and id ' + dude.id);
    return this.httpClient.get<GetWorkout[]>(this.workoutsBaseUri + '/' + dude.id + '/workouts');
  }
  deleteWorkout(workoutId: number) {
      console.log('delete workout with id ' + workoutId);
      return this.httpClient.delete(this.workoutBaseUri + '/' + workoutId);
  }
  editWorkout(newWorkout: GetWorkout): Observable<GetWorkout> {
    console.log('update workout with id ' + newWorkout.id);
    const params = new HttpParams().set('newWorkout', JSON.stringify(newWorkout));
    return this.httpClient.put<GetWorkout>(this.workoutsBaseUri + '/' + newWorkout.id, {params: params});
  }

}
