import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Course} from '../dtos/course';

@Injectable({
  providedIn: 'root'
})
export class EditCourseService {

  private courseBaseUri: string = this.globals.backendUri + '/course';
  private file: File;
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  editCourse(course: Course, oldCourse: Course): Observable<Course> {
    console.log('edit course with id ' + course.id);
    return this.httpClient.put<Course>(this.courseBaseUri + '/' + oldCourse.id, course);
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
