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
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  editCourse(course: Course, oldCourse: Course): Observable<Course> {
    console.log('edit course with id ' + course.id);
    return this.httpClient.put<Course>(this.courseBaseUri + '/' + oldCourse.id, course);
  }
}
