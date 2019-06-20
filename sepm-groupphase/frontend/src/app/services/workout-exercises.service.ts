import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Exercise} from '../dtos/exercise';

@Injectable({
  providedIn: 'root'
})
export class WorkoutExercisesService {

  private exerciseBaseUri: string = this.globals.backendUri + '/exercise';
  private dudeBaseUri: string = this.globals.backendUri + '/dudes';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getExercisesByName(name: string, dudeId: number): Observable<Exercise[]> {
    console.log('get all exercises by name ' + name);
    const params = new HttpParams().set('name', name);
    return this.httpClient.get<Exercise[]>(this.exerciseBaseUri + '/' + dudeId, {params: params});
  }

}
