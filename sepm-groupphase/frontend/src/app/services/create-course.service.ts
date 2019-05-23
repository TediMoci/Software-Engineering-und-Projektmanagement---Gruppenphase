import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Course} from '../dtos/course';

@Injectable({
  providedIn: 'root'
})
export class CreateCourseService {

  private courseBaseUri: string = this.globals.backendUri + '/course';

  constructor(private httpClient: HttpClient, private globals: Globals) {}

  addCourse(course: Course): Observable<Course> {
    console.log('add course with name ' + course.name);
    return this.httpClient.post<Course>(this.courseBaseUri, course);
  }
}
