import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';
import {Exercise} from '../dtos/Exercise';

@Injectable({
  providedIn: 'root'
})
export class OwnExercisesService {

  private exerciseBaseUri: string = this.globals.backendUri + '/dudes';
  private dude: Dude = JSON.parse(localStorage.getItem('loggedInDude'));
  constructor(private httpClient: HttpClient, private globals: Globals) { }
  getAllExercisesOfLoggedInDude(): Observable<Exercise[]> {
    console.log('get all created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
    const params = new HttpParams().set('id', JSON.stringify(this.dude.id));
    return this.httpClient.get<Exercise[]>(this.exerciseBaseUri + '/' + this.dude.id + '/exercises', {params: params});
  }
}
