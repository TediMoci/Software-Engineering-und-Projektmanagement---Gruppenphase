import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {ActiveTrainingSchedule} from "../dtos/active-training-schedule";
import {catchError} from "rxjs/operators";

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

  getActiveSchedule(id:number): Observable<ActiveTrainingSchedule>{
    console.log('checking for active training schedule for Dude with id ' + id);
    return this.httpClient.get<ActiveTrainingSchedule>(this.dudeBaseUri+'/'+id+'/activeTrainingSchedule')

  }

}

