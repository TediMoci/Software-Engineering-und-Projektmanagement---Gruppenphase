import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {CreateCourse} from '../dtos/create-course';

@Injectable({
  providedIn: 'root'
})
export class CreateCourseService {

  private courseBaseUri: string = this.globals.backendUri + '/course';

  constructor(private httpClient: HttpClient, private globals: Globals) {}

  addCourse(course: CreateCourse): Observable<CreateCourse> {
    console.log('add course with name ' + course.name);
    return this.httpClient.post<CreateCourse>(this.courseBaseUri, course);
  }
}
