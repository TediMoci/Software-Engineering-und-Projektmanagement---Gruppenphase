import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {CreateExercise} from '../dtos/create-exercise';
import {Exercise} from '../dtos/exercise';

@Injectable({
  providedIn: 'root'
})
export class CreateExerciseService {

  private exerciseBaseUri: string = this.globals.backendUri + '/exercise';
  private file: File;
  constructor(private httpClient: HttpClient, private globals: Globals) {}

  addExercise(exercise: CreateExercise): Observable<Exercise> {
    console.log('add exercise with name ' + exercise.name);
    return this.httpClient.post<Exercise>(this.exerciseBaseUri, exercise);
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
