import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';
import {FitnessProvider} from '../dtos/fitness-provider';

@Injectable({
  providedIn: 'root'
})
export class FitnessProvidersFollowedService {

  private dudeBaseUri: string = this.globals.backendUri + '/dudes';
  constructor(private httpClient: HttpClient, private globals: Globals) { }
  getAllFollowedFitnessProvidersOfLoggedInDude(dude: Dude): Observable<FitnessProvider[]> {
    console.log('get all followed by dude with name ' + dude.name + ' and id ' + dude.id);
    return this.httpClient.get<FitnessProvider[]>(this.dudeBaseUri + '/' + dude.id + '/follow');
  }

  unfollowFitnessProvider(dudeId: number, fitnessProviderId: number) {
     console.log('unfollow fitness provider with dudeId ' + dudeId + ' and fitness provider id ' + fitnessProviderId);
     return this.httpClient.delete(this.dudeBaseUri + '/' + dudeId + '/follow/' + fitnessProviderId);
   }
}
