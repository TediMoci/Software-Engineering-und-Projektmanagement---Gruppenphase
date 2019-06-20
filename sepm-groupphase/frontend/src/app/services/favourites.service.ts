import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {GetWorkout} from '../dtos/get-workout';
import {Exercise} from '../dtos/exercise';
import {Course} from '../dtos/course';
import {TrainingSchedule} from '../dtos/trainingSchedule';

@Injectable({
  providedIn: 'root'
})
export class FavouritesService {

  private favouritesBaseUri: string = this.globals.backendUri + '/dudes';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAllCoursesBookmarkedByDudeId(dudeId: number): Observable<Course[]> {
    console.log('get all courses bookmarked by dude with id ' + dudeId);
    return this.httpClient.get<Course[]>(this.favouritesBaseUri + '/' + dudeId + '/bookmarks/courses');
  }

  getAllExercisesBookmarkedByDudeId(dudeId: number): Observable<Exercise[]> {
    console.log('get all exercises bookmarked by dude with id ' + dudeId);
    return this.httpClient.get<Exercise[]>(this.favouritesBaseUri + '/' + dudeId + '/bookmarks/exercises');
  }

  getAllWorkoutsBookmarkedByDudeId(dudeId: number): Observable<GetWorkout[]> {
    console.log('get all workouts bookmarked by dude with id ' + dudeId);
    return this.httpClient.get<GetWorkout[]>(this.favouritesBaseUri + '/' + dudeId + '/bookmarks/workouts');
  }

  getAllTrainingSchedulesBookmarkedByDudeId(dudeId: number): Observable<TrainingSchedule[]> {
    console.log('get all trainingSchedules bookmarked by dude with id ' + dudeId);
    return this.httpClient.get<TrainingSchedule[]>(this.favouritesBaseUri + '/' + dudeId + '/bookmarks/trainingSchedules');
  }
}
