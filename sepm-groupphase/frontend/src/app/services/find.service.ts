import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';
import {Exercise} from '../dtos/exercise';
import {ExerciseFilter} from '../dtos/exercise-filter';
import {CourseFilter} from '../dtos/course-filter';
import {Course} from '../dtos/course';
import {WorkoutFilter} from '../dtos/workout-filter';
import {Workout} from '../dtos/workout';
import {FitnessProvider} from '../dtos/fitness-provider';
import {FitnessProviderFilter} from '../dtos/fitness-provider-filter';
import {DudeFilter} from '../dtos/dude-filter';

@Injectable({
  providedIn: 'root'
})
export class FindService {

  private BaseUri: string = this.globals.backendUri;

  constructor(private httpClient: HttpClient, private globals: Globals) {  }

  getAllExercisesFilterd(exerciseFilter: ExerciseFilter): Observable<Exercise[]> {
    console.log('get all exercises');
    let params = new HttpParams();
    if (exerciseFilter.filter != null) {
      params = params.set('filter', exerciseFilter.filter);
    }
    if (exerciseFilter.category != null) {
      params = params.set('category', exerciseFilter.category);
    }
    if (exerciseFilter.muscle != null) {
      params = params.set('muscleGroup', exerciseFilter.muscle);
    }

    console.log('get all exercises with params: ' + params.toString());

    return this.httpClient.get<Exercise[]>(this.BaseUri + '/exercise/filtered', {params: params});
  }

  getAllCoursesFilterd(courseFilter: CourseFilter): Observable<Course[]> {
    console.log('get all courses');
    let params = new HttpParams();
    if (courseFilter.filter != null) {
      params = params.set('filter', courseFilter.filter);
    }
    console.log('get all courses with params: ' + params.toString());

    return this.httpClient.get<Course[]>(this.BaseUri + '/course/filtered', {params: params});
  }

  getAllWorkoutsFilterd(workoutFilter: WorkoutFilter): Observable<Workout[]> {
    console.log('get all workouts');
    let params = new HttpParams();
    if (workoutFilter.filter != null) {
      params = params.set('filter', workoutFilter.filter);
    }
    if (workoutFilter.difficulty != null) {
      params = params.set('difficulty', workoutFilter.difficulty);
    }
    if (workoutFilter.calorieLower != null) {
      params = params.set('calorieLower', workoutFilter.calorieLower);
    }
    if (workoutFilter.calorieUpper != null) {
      params = params.set('calorieUpper', workoutFilter.calorieUpper);
    }

    console.log('get all workouts with params: ' + params.toString());

    return this.httpClient.get<Workout[]>(this.BaseUri + '/workout/filtered', {params: params});
  }

  getAllFitnessProviderFiltered(fitnessProviderFilter: FitnessProviderFilter): Observable<FitnessProvider[]> {
    console.log('get all fitness provider');
    let params = new HttpParams();
    if (fitnessProviderFilter.filter != null) {
      params = params.set('filter', fitnessProviderFilter.filter);
    }
    console.log('get all workouts with params: ' + params.toString());

    return this.httpClient.get<FitnessProvider[]>(this.BaseUri + '/fitnessProvider/filtered', {params: params});
  }

  followFitnessProvider(dudeId: number, fitnessProviderId: number) {
    console.log('follow fitness provider with dudeId ' + dudeId +  '; fitnessProviderId ' + fitnessProviderId);
    this.httpClient.put(this.BaseUri + '/dudes/' + dudeId + '/follow/' + fitnessProviderId, null).subscribe();
  }

  getAllDudesFiltered(dudeFilter: DudeFilter): Observable<Dude[]> {
    console.log('get all dudes filtered');
    let params = new HttpParams();
    if (dudeFilter.filter != null) {
      params = params.set('filter', dudeFilter.filter);
    }
    if (dudeFilter.selfAssessment != null) {
      params = params.set('selfAssessment', dudeFilter.selfAssessment);
    }
    console.log('get all dudes with params: ' + params.toString());

    return this.httpClient.get<Dude[]>(this.BaseUri + '/dudes/filtered', {params: params});
  }

  getOneFitnessProvider(creatorId: number): Observable<FitnessProvider> {
    console.log('Get creator of Course with id: ' + creatorId + ', ' + this.BaseUri + '/fitnessProvider/' + creatorId);
    return  this.httpClient.get<FitnessProvider>(this.BaseUri + '/fitnessProvider/' + creatorId);
  }
}
