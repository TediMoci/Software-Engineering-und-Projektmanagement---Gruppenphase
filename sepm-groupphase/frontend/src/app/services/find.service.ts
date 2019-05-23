import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';
import {Exercise} from '../dtos/Exercise';

@Injectable({
  providedIn: 'root'
})
export class FindService {

  private exerciseBaseUri: string = this.globals.backendUri + '/exercise';

  constructor(private httpClient: HttpClient, private globals: Globals) { }
  getAllExercisesFilterd(exercise: Exercise): Observable<Exercise[]> {
    //todo: handle NULL values
    console.log('get all exercises');
    const params = new HttpParams()
    .set('name', JSON.stringify(exercise.name))
    .set('description', JSON.stringify(exercise.description))
    .set('equipment', JSON.stringify(exercise.equipment))
    .set('muscleGroup', JSON.stringify(exercise.muscleGroup))
    .set('category', JSON.stringify(exercise.category))
    .set('difficulty_level', JSON.stringify(exercise.difficulty_level))
    console.log('get all exercises with params: ' + params.toString());

    return this.httpClient.get<Exercise[]>(this.exerciseBaseUri, {params: params});
  }
}
