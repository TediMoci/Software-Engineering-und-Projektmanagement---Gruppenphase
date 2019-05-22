import {Injectable} from '@angular/core';
import {Exercise} from '../dtos/exercise';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EditExerciseService {

  private exerciseBaseUri: string = this.globals.backendUri + '/exercise';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  editExercise(exercise: Exercise, oldExercise: Exercise): Observable<Exercise> {
    console.log('edit exercise with new name ' + exercise.name + ' and old name ' + oldExercise.name);
    return this.httpClient.put<Exercise>(this.exerciseBaseUri + '/' + oldExercise.name, exercise);
  }
}

