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
    console.log('get all exercises');

    const params = new HttpParams();
    params.set('name', JSON.stringify(exercise.name));
    params.set('description', JSON.stringify(exercise.description));
    params.set('equipment', JSON.stringify(exercise.equipment));
    params.set('muscleGroup', JSON.stringify(exercise.muscleGroup));
    params.set('category', JSON.stringify(exercise.category));
    params.set('difficulty_level', JSON.stringify(exercise.difficulty_level));

    return this.httpClient.get<Exercise[]>(this.exerciseBaseUri, {params: params});
  }
}
