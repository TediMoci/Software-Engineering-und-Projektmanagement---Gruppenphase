import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';

@Injectable({
  providedIn: 'root'
})
export class OwnExercisesService {

  private exerciseBaseUri: string = this.globals.backendUri + '/exercise';
  private dude: Dude = JSON.parse(localStorage.getItem('loggedInDude'));
  constructor(private httpClient: HttpClient, private globals: Globals) { }
  getAllExercisesOfLoggedInDude(): Observable<any> {
    console.log('get all created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
    // const params = new HttpParams().set('id', JSON.stringify(this.dude.id));
    const params = new HttpParams().set('creator', JSON.stringify(this.dude));
    return this.httpClient.get<number>(this.exerciseBaseUri + '/own', {params: params});
  }
}
