import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Dude} from '../dtos/dude';
import {stringify} from 'querystring';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private dudeBaseUri: string = this.globals.backendUri + '/dudes';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAge(birthday: Date, name: string): Observable<number> {
    console.log('calculate age of dude with name ' + name);
    const params = new HttpParams().set('birthday', stringify(birthday));
    return this.httpClient.get<number>(this.dudeBaseUri + '/age', {params: params});
  }

  getBmi(height: number, weight: number, name: string): Observable<number> {
    console.log('calculate bmi of dude with name ' + name);
    const params = new HttpParams().set('height', stringify(height)).set('weight', stringify(weight));
    return this.httpClient.get<number>(this.dudeBaseUri + '/bmi', {params: params});
  }

}

