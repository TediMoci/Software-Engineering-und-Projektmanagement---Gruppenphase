import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class BookmarksService {

  private courseBookmarkBaseUri: string = this.globals.backendUri + '/course/bookmark';
  private exerciseBookmarkBaseUri: string = this.globals.backendUri + '/exercise/bookmark';
  private workoutBookmarkBaseUri: string = this.globals.backendUri + '/workout/bookmark';
  private trainingScheduleBookmarkBaseUri: string = this.globals.backendUri + '/trainingSchedule/bookmark';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  // -------------------- set bookmark --------------------
  bookmarkCourse(dudeId: number, courseId: number) {
    console.log('set bookmark of dude with id ' + dudeId + ' for course with id ' + courseId);
    const params = new HttpParams().set('dudeId', JSON.stringify(dudeId)).set('courseId', JSON.stringify(courseId));
    return this.httpClient.put(this.courseBookmarkBaseUri + '/' + dudeId + '/' + courseId, {params: params});
  }

  bookmarkExercise(dudeId: number, exerciseId: number, exerciseVersion: number) {
    console.log('set bookmark of dude with id ' + dudeId + ' for exercise with id ' + exerciseId + ' and version ' + exerciseVersion);
    const params = new HttpParams().set('dudeId', JSON.stringify(dudeId)).set('exerciseId', JSON.stringify(exerciseId)).set('exerciseVersion', JSON.stringify(exerciseVersion));
    return this.httpClient.put(this.exerciseBookmarkBaseUri + '/' + dudeId + '/' + exerciseId + '/' + exerciseVersion, {params: params});
  }

  bookmarkWorkout(dudeId: number, workoutId: number, workoutVersion: number) {
    console.log('set bookmark of dude with id ' + dudeId + ' for workout with id ' + workoutId + ' and version ' + workoutVersion);
    const params = new HttpParams().set('dudeId', JSON.stringify(dudeId)).set('workoutId', JSON.stringify(workoutId)).set('workoutVersion', JSON.stringify(workoutVersion));
    return this.httpClient.put(this.workoutBookmarkBaseUri + '/' + dudeId + '/' + workoutId + '/' + workoutVersion, {params: params});
  }

  bookmarkTrainingSchedule(dudeId: number, trainingScheduleId: number, trainingScheduleVersion: number) {
    console.log('set bookmark of dude with id ' + dudeId + ' for trainingSchedule with id ' + trainingScheduleId + ' and version ' + trainingScheduleVersion);
    const params = new HttpParams().set('dudeId', JSON.stringify(dudeId)).set('trainingScheduleId', JSON.stringify(trainingScheduleId)).set('trainingScheduleVersion', JSON.stringify(trainingScheduleVersion));
    return this.httpClient.put(this.trainingScheduleBookmarkBaseUri + '/' + dudeId + '/' + trainingScheduleId + '/' + trainingScheduleVersion, {params: params});
  }

  // -------------------- remove bookmark --------------------

  deleteBookmarkOfCourse(dudeId: number, courseId: number) {
    console.log('delete bookmark of dude with id ' + dudeId + ' for course with id ' + courseId);
    const params = new HttpParams().set('dudeId', JSON.stringify(dudeId)).set('courseId', JSON.stringify(courseId));
    return this.httpClient.delete(this.courseBookmarkBaseUri + '/' + dudeId + '/' + courseId, {params: params});
  }

  deleteBookmarkOfExercise(dudeId: number, exerciseId: number, exerciseVersion: number) {
    console.log('delete bookmark of dude with id ' + dudeId + ' for exercise with id ' + exerciseId + ' and version' + exerciseVersion);
    const params = new HttpParams().set('dudeId', JSON.stringify(dudeId)).set('exerciseId', JSON.stringify(exerciseId)).set('exerciseVersion', JSON.stringify(exerciseVersion));
    return this.httpClient.delete(this.exerciseBookmarkBaseUri + '/' + dudeId + '/' + exerciseId + '/' + exerciseVersion, {params: params});
  }

  deleteBookmarkOfWorkout(dudeId: number, workoutId: number, workoutVersion: number) {
    console.log('delete bookmark of dude with id ' + dudeId + ' for workout with id ' + workoutId + ' and version' + workoutVersion);
    const params = new HttpParams().set('dudeId', JSON.stringify(dudeId)).set('workoutId', JSON.stringify(workoutId)).set('workoutVersion', JSON.stringify(workoutVersion));
    return this.httpClient.delete(this.workoutBookmarkBaseUri + '/' + dudeId + '/' + workoutId + '/' + workoutVersion, {params: params});
  }

  deleteBookmarkOfTrainingSchedule(dudeId: number, trainingScheduleId: number, trainingScheduleVersion: number) {
    console.log('delete bookmark of dude with id ' + dudeId + ' for trainingSchedule with id ' + trainingScheduleId + ' and version' + trainingScheduleVersion);
    const params = new HttpParams().set('dudeId', JSON.stringify(dudeId)).set('trainingScheduleId', JSON.stringify(trainingScheduleId)).set('trainingScheduleVersion', JSON.stringify(trainingScheduleVersion));
    return this.httpClient.delete(this.trainingScheduleBookmarkBaseUri + '/' + dudeId + '/' + trainingScheduleId + '/' + trainingScheduleVersion, {params: params});
  }
}
