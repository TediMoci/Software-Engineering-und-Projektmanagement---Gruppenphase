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
  private dude: Dude = JSON.parse(localStorage.getItem('loggedInDude'));
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAllWorkoutsOfLoggedInDude(): Observable<Workout[]>{
    console.log('get all workouts created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
    return this.httpClient.get<Workout[]>(this.workoutsBaseUri + '/' + this.dude.id + '/workouts');
  }
}
