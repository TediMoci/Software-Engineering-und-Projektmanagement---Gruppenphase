import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';
import {CreateExercise} from '../dtos/create-exercise';

@Injectable({
  providedIn: 'root'
})
export class OwnExercisesService {

  private exerciseBaseUri: string = this.globals.backendUri + '/dudes';
  private dude: Dude = JSON.parse(localStorage.getItem('loggedInDude'));
  constructor(private httpClient: HttpClient, private globals: Globals) { }
  getAllExercisesOfLoggedInDude(): Observable<CreateExercise[]> {
    console.log('get all created by dude with name ' + this.dude.name + ' and id ' + this.dude.id);
    return this.httpClient.get<CreateExercise[]>(this.exerciseBaseUri + '/' + this.dude.id + '/exercises');
  }
}
