import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {CreateExercise} from '../dtos/create-exercise';

@Injectable({
  providedIn: 'root'
})
export class CreateExerciseService {

  private exerciseBaseUri: string = this.globals.backendUri + '/exercise';

  constructor(private httpClient: HttpClient, private globals: Globals) {}

  addExercise(exercise: CreateExercise): Observable<CreateExercise> {
    console.log('add exercise with name ' + exercise.name);
    return this.httpClient.post<CreateExercise>(this.exerciseBaseUri, exercise);
  }
}
