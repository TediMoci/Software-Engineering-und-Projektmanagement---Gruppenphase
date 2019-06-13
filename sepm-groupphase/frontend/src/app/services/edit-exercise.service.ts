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
  private file: File;
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  editExercise(exercise: Exercise, oldExercise: Exercise): Observable<Exercise> {
    console.log('edit exercise with id ' + exercise.id);
    return this.httpClient.put<Exercise>(this.exerciseBaseUri + '/' + oldExercise.id, exercise);
  }

  uploadPictureForExercise(id: number, version: number, base64: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', base64);
    console.log(formData.get('file'));
    return this.httpClient.post<string>(this.exerciseBaseUri + '/' + id + '/' + version +  '/uploadImage', formData);
  }


  setFileStorage(file: File) {
    this.file = file;
  }
  getFileStorage(): File {
    return this.file;
  }
}

