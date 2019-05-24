import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';
import {Exercise} from '../dtos/Exercise';
import {ExerciseFilter} from "../dtos/exercise-filter";

@Injectable({
  providedIn: 'root'
})
export class FindService {

  private exerciseBaseUri: string = this.globals.backendUri + '/exercise';

  constructor(private httpClient: HttpClient, private globals: Globals) { }
  getAllExercisesFilterd(exerciseFilter: ExerciseFilter): Observable<Exercise[]> {
    //todo: handle NULL values
    console.log('get all exercises');
    const params = new HttpParams()
    .set('name', JSON.stringify(exerciseFilter.filter))
    .set('description', JSON.stringify(exerciseFilter.category))

    console.log('get all exercises with params: ' + params.toString());

    return this.httpClient.get<Exercise[]>(this.exerciseBaseUri, {params: params});
  }
}
