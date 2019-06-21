import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {CourseWithRating} from '../dtos/course-with-rating';
import {ExerciseWithRating} from '../dtos/exercise-with-rating';
import {WorkoutWithRating} from '../dtos/workout-with-rating';
import {TrainingScheduleWithRating} from '../dtos/training-schedule-with-rating';

@Injectable({
  providedIn: 'root'
})
export class GetByIDService {

  private BaseUri: string = this.globals.backendUri;
  constructor(private httpClient: HttpClient, private globals: Globals) {  }

  getCourseByID(courseId: number): Observable<CourseWithRating> {
    console.log('Get course with id: ' + courseId);
    return  this.httpClient.get<CourseWithRating>(this.BaseUri + '/course/' + courseId);
  }
  getExerciseByID(exerciseId: number, exerciseVs: number): Observable<ExerciseWithRating> {
    console.log('Get exercise with id: ' + exerciseId);
    return  this.httpClient.get<ExerciseWithRating>(this.BaseUri + '/exercise/' + exerciseId + '/' + exerciseVs);
  }
  getWorkoutByID(workoutId: number, workoutVs: number): Observable<WorkoutWithRating> {
    console.log('Get workout with id: ' + workoutId);
    return  this.httpClient.get<WorkoutWithRating>(this.BaseUri + '/workout/' + workoutId + '/' + workoutVs);
  }
  getTraingScheduleByID(traingscheduleId: number, traingscheduleVs): Observable<TrainingScheduleWithRating> {
    console.log('Get training schedule with id: ' + traingscheduleId);
    return  this.httpClient.get<TrainingScheduleWithRating>(this.BaseUri + '/trainingSchedule/' + traingscheduleId+ '/' + traingscheduleVs);
  }


}
