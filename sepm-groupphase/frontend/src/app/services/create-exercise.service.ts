import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Exercise} from '../dtos/Exercise';

@Injectable({
  providedIn: 'root'
})
export class CreateExerciseService {

  private exerciseBaseUri: string = this.globals.backendUri + '/exercise';

  constructor(private httpClient: HttpClient, private globals: Globals) {}

  addExercise(exercise: Exercise): Observable<Exercise> {
    console.log('add exercise with name ' + exercise.name);
    return this.httpClient.post<Exercise>(this.exerciseBaseUri, exercise);
  }
}
