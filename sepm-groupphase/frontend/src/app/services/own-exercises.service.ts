import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';
import {CreateExercise} from '../dtos/create-exercise';
import {Exercise} from '../dtos/exercise';

@Injectable({
  providedIn: 'root'
})
export class OwnExercisesService {

  private exerciseBaseUri: string = this.globals.backendUri + '/dudes';
  private exerciseEditBaseUri: string = this.globals.backendUri + '/exercise';
  constructor(private httpClient: HttpClient, private globals: Globals) { }
  getAllExercisesOfLoggedInDude(dude: Dude): Observable<CreateExercise[]> {
    console.log('get all created by dude with name ' + dude.name + ' and id ' + dude.id);
    return this.httpClient.get<CreateExercise[]>(this.exerciseBaseUri + '/' + dude.id + '/exercises');
  }

  deleteExercise(exerciseId: number){
    console.log('delete exercise with id ' + exerciseId);
    return this.httpClient.delete(this.exerciseEditBaseUri + '/' + exerciseId);
  }
}
