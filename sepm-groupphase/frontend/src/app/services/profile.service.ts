import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {GetActiveTrainingSchedule} from '../dtos/get-active-training-schedule';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private dudeBaseUri: string = this.globals.backendUri + '/dudes';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAge(birthday: Date, name: string): Observable<number> {
    console.log('calculate age of dude with name ' + name);
    const params = new HttpParams().set('birthday', JSON.stringify(birthday));
    return this.httpClient.get<number>(this.dudeBaseUri + '/age', {params: params});
  }

  getBmi(height: number, weight: number, name: string): Observable<number> {
    console.log('calculate bmi of dude with name ' + name);
    const params = new HttpParams().set('height', JSON.stringify(height)).set('weight', JSON.stringify(weight));
    return this.httpClient.get<number>(this.dudeBaseUri + '/bmi', {params: params});
  }

  getActiveSchedule(id: number): Observable<GetActiveTrainingSchedule> {
    console.log('checking for active training schedule for Dude with id ' + id);
    return this.httpClient.get<GetActiveTrainingSchedule>(this.dudeBaseUri + '/' + id + '/activeTrainingSchedule');

  }

}

