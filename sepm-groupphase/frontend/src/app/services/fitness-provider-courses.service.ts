import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {FitnessProvider} from '../dtos/fitness-provider';
import {Course} from '../dtos/course';

@Injectable({
  providedIn: 'root'
})
export class FitnessProviderCoursesService {

  private fitnessProviderBaseUri: string = this.globals.backendUri + '/fitnessProvider';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAllCoursesOfFP(fitnessProvider: FitnessProvider): Observable<Course[]> {
    console.log('get all courses created by fitness provider with name ' + fitnessProvider.name + ' and id ' + fitnessProvider.id);
    return this.httpClient.get<Course[]>(this.fitnessProviderBaseUri + '/' + fitnessProvider.id + '/courses');
  }

  getFitnessProviderById(id: number): Observable<FitnessProvider> {
    console.log('get fitnessProvider with id ' + id);
    return this.httpClient.get<FitnessProvider>(this.fitnessProviderBaseUri + '/' + id);
  }
}
