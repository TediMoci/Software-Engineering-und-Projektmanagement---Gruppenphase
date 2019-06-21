import { Injectable } from '@angular/core';
import {Exercise} from '../dtos/exercise';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private BaseUri: string = this.globals.backendUri;
  private file: File;
  constructor(private httpClient: HttpClient, private globals: Globals) { }


  rateExercise( dudeId: number, exercise: Exercise, rating: number): Observable<Exercise> {
    console.log('rating exercise with id ' + exercise.id + ' ' + rating + '/5');
    console.log(this.BaseUri + '/exercise/rating' + '/' + dudeId + '/' + exercise.id + '/' + rating );
    return this.httpClient.post<Exercise>(
      this.BaseUri + '/exercise/rating' + '/' + dudeId + '/' + exercise.id + '/' + rating , null);
  }

}
