import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {GetWorkout} from '../dtos/get-workout';
import {Exercise} from '../dtos/exercise';
import {Course} from '../dtos/course';
import {TrainingSchedule} from '../dtos/trainingSchedule';
import {CourseWithRating} from '../dtos/course-with-rating';
import {ExerciseWithRating} from '../dtos/exercise-with-rating';
import {TrainingScheduleWithRating} from '../dtos/training-schedule-with-rating';

@Injectable({
  providedIn: 'root'
})
export class FavouritesService {

  private favouritesBaseUri: string = this.globals.backendUri + '/dudes';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAllCoursesBookmarkedByDudeId(dudeId: number): Observable<CourseWithRating[]> {
    console.log('get all courses bookmarked by dude with id ' + dudeId);
    return this.httpClient.get<CourseWithRating[]>(this.favouritesBaseUri + '/' + dudeId + '/bookmarks/courses');
  }

  getAllExercisesBookmarkedByDudeId(dudeId: number): Observable<ExerciseWithRating[]> {
    console.log('get all exercises bookmarked by dude with id ' + dudeId);
    return this.httpClient.get<ExerciseWithRating[]>(this.favouritesBaseUri + '/' + dudeId + '/bookmarks/exercises');
  }

  getAllWorkoutsBookmarkedByDudeId(dudeId: number): Observable<GetWorkout[]> {
    console.log('get all workouts bookmarked by dude with id ' + dudeId);
    return this.httpClient.get<GetWorkout[]>(this.favouritesBaseUri + '/' + dudeId + '/bookmarks/workouts');
  }

  getAllTrainingSchedulesBookmarkedByDudeId(dudeId: number): Observable<TrainingScheduleWithRating[]> {
    console.log('get all trainingSchedules bookmarked by dude with id ' + dudeId);
    return this.httpClient.get<TrainingScheduleWithRating[]>(this.favouritesBaseUri + '/' + dudeId + '/bookmarks/trainingSchedules');
  }
}
