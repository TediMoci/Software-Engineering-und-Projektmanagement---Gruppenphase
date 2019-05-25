import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';
import {Exercise} from '../dtos/Exercise';
import {ExerciseFilter} from "../dtos/exercise-filter";
import {CourseFilter} from "../dtos/course-filter";
import {Course} from "../dtos/course";

@Injectable({
  providedIn: 'root'
})
export class FindService {

  private exerciseBaseUri: string = this.globals.backendUri;

  constructor(private httpClient: HttpClient, private globals: Globals) {  }

  getAllExercisesFilterd(exerciseFilter: ExerciseFilter): Observable<Exercise[]> {
    console.log('get all exercises');
    let params = new HttpParams();
    if(exerciseFilter.filter !=null){
      params = params.set("filter",exerciseFilter.filter)
    }
    if(exerciseFilter.category !=null){
      params = params.set("category",exerciseFilter.category)
    }

    console.log('get all exercises with params: ' + params.toString());

    return this.httpClient.get<Exercise[]>(this.exerciseBaseUri + '/exercise/filtered', {params: params});
  }

  getAllCoursesFilterd(courseFilter: CourseFilter): Observable<Course[]> {
    console.log('get all courses');
    let params = new HttpParams();
    if(courseFilter.filter !=null){
      params = params.set("filter",courseFilter.filter)
    }
    console.log('get all courses with params: ' + params.toString());

    return this.httpClient.get<Course[]>(this.exerciseBaseUri + '/course/filtered', {params: params});
  }
}
