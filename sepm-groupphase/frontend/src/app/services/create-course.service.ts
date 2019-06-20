import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {CreateCourse} from '../dtos/create-course';
import {Course} from '../dtos/course';

@Injectable({
  providedIn: 'root'
})
export class CreateCourseService {

  private courseBaseUri: string = this.globals.backendUri + '/course';
  private file: File;
  constructor(private httpClient: HttpClient, private globals: Globals) {}

  addCourse(course: CreateCourse): Observable<Course> {
    console.log('add course with name ' + course.name);
    return this.httpClient.post<Course>(this.courseBaseUri, course);
  }

  uploadPictureForCourse(id: number, base64: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', base64);
    console.log(formData.get('file'));
    return this.httpClient.post<string>(this.courseBaseUri + '/' + id + '/uploadImage', formData);
  }

  setFileStorage(file: File) {
    this.file = file;
  }
  getFileStorage(): File {
    return this.file;
  }
}
