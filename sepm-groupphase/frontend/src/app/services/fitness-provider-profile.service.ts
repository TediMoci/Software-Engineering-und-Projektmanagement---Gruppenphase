import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {RegisterAsFitnessProvider} from '../dtos/register-as-fitness-provider';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FitnessProviderProfileService {
  private fitnessProviderBaseUri: string = this.globals.backendUri + '/fitnessProvider';
  constructor(private httpClient: HttpClient , private globals: Globals) { }

  getFollower(name: string): Observable<number> {
    console.log('get followers from fitness-provider with name ' + name);
    const params = new HttpParams().set('name', name);
    return this.httpClient.get<number>(this.fitnessProviderBaseUri + '/' + name + '/followers' , {params: params});
  }
}
