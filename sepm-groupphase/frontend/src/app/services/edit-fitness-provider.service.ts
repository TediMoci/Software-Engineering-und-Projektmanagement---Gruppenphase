import { Injectable } from '@angular/core';
import {FitnessProvider} from '../dtos/fitness-provider';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EditFitnessProviderService {

  private fitnessProviderBaseUri: string = this.globals.backendUri + '/fitnessProvider';
  constructor(private httpClient: HttpClient , private globals: Globals) { }

  editFitnessProvider(editFitnessProvider: FitnessProvider, oldFitnessProvider: FitnessProvider): Observable<FitnessProvider>{
    console.log('edit fitnessProvider with new name ' + editFitnessProvider.name + ' and old name ' + oldFitnessProvider.name);
    localStorage.setItem('currentUser', JSON.stringify(editFitnessProvider));
    const params = new HttpParams().set('fitnessProvider', JSON.stringify(editFitnessProvider));
    return this.httpClient.post<FitnessProvider>(this.fitnessProviderBaseUri + '/' + oldFitnessProvider.name, {params: params});
  }

}
