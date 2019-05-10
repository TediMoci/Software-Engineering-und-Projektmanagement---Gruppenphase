import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {RegisterAsFitnessProvider} from '../dtos/register-as-fitness-provider';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterAsFitnessProviderService {
  private fitnessProviderBaseUri: string = this.globals.backendUri + '/fitnessProvider';
  constructor(private httpClient: HttpClient , private globals: Globals) { }

  addFitnessProvider(fitnessProvider: RegisterAsFitnessProvider): Observable<RegisterAsFitnessProvider> {
    console.log('add fitness-provider with name ' + fitnessProvider.name);
    return this.httpClient.post<RegisterAsFitnessProvider>(this.fitnessProviderBaseUri, fitnessProvider);
  }
}
