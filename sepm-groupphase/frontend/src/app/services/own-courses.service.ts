import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {FitnessProvider} from '../dtos/fitness-provider';
import {Course} from '../dtos/course';

@Injectable({
  providedIn: 'root'
})
export class OwnCoursesService {

  private courseBaseUri: string = this.globals.backendUri + '/fitnessProvider';
  private courseEditBaseUri: string = this.globals.backendUri + '/course';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAllCoursesOfLoggedInFP(fitnessProvider: FitnessProvider): Observable<Course[]> {
    console.log('get all courses created by fitnessprovider with name ' + fitnessProvider.name + ' and id ' + fitnessProvider.id);
    return this.httpClient.get<Course[]>(this.courseBaseUri + '/' + fitnessProvider.id + '/courses');
  }

  deleteCourse(courseId: number){
    console.log('delete course with id ' + courseId);
    return this.httpClient.delete(this.courseEditBaseUri + '/' + courseId);
  }

}

