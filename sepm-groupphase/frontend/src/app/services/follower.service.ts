import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {FitnessProvider} from '../dtos/fitness-provider';
import {Observable} from 'rxjs';
import {Dude} from '../dtos/dude';

@Injectable({
  providedIn: 'root'
})
export class FollowerService {

  private fitnessProviderBaseUri: string = this.globals.backendUri + '/fitnessProvider';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getAllFollowersOfFP(fitnessProvider: FitnessProvider): Observable<Dude[]> {
    console.log('get all followers of fitness provider with name ' + fitnessProvider.name + ' and id ' + fitnessProvider.id);
    return this.httpClient.get<Dude[]>(this.fitnessProviderBaseUri + '/' + fitnessProvider.id + '/allFollowers');
  }
}
