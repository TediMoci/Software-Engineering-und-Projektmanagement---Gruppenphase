import { Injectable } from '@angular/core';
import {Exercise} from '../dtos/exercise';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Course} from '../dtos/course';
import {Workout} from '../dtos/workout';
import {TrainingSchedule} from '../dtos/trainingSchedule';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private BaseUri: string = this.globals.backendUri;
  private file: File;
  constructor(private httpClient: HttpClient, private globals: Globals) { }


  rateExercise( dudeId: number, exercise: Exercise, rating: number): Observable<Exercise> {
    console.log('rating exercise with id ' + exercise.id + ' ' + rating + '/5');
    console.log(this.BaseUri + '/exercise/rating' + '/' + dudeId + '/' + exercise.id + '/' + rating );
    return this.httpClient.post<Exercise>(
      this.BaseUri + '/exercise/rating' + '/' + dudeId + '/' + exercise.id + '/' + rating , null);
  }
  rateCourse( dudeId: number, course: Course, rating: number): Observable<Course> {
    console.log('rating course with id ' + course.id + ' ' + rating + '/5');
    console.log(this.BaseUri + '/course/rating' + '/' + dudeId + '/' + course.id + '/' + rating );
    return this.httpClient.post<Course>(
      this.BaseUri + '/course/rating' + '/' + dudeId + '/' + course.id + '/' + rating , null);
  }
  rateWorkout( dudeId: number, workout: Workout, rating: number): Observable<Workout> {
    console.log('rating workout with id ' + workout.id + ' ' + rating + '/5');
    console.log(this.BaseUri + '/workout/rating' + '/' + dudeId + '/' + workout.id + '/' + rating );
    return this.httpClient.post<Workout>(
      this.BaseUri + '/workout/rating' + '/' + dudeId + '/' + workout.id + '/' + rating , null);
  }
  rateTrainingSchedule( dudeId: number, trainingSchedule: TrainingSchedule, rating: number): Observable<TrainingSchedule> {
    console.log('rating trainingSchedule with id ' + trainingSchedule.id + ' ' + rating + '/5');
    console.log(this.BaseUri + '/trainingSchedule/rating' + '/' + dudeId + '/' + trainingSchedule.id + '/' + rating );
    return this.httpClient.post<TrainingSchedule>(
      this.BaseUri + '/trainingSchedule/rating' + '/' + dudeId + '/' + trainingSchedule.id + '/' + rating , null);
  }

}
