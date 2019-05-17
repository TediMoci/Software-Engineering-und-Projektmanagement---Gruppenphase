import {Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {RegisterAsDude} from '../dtos/register-as-dude';
import {HttpClient, HttpParams, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Dude} from '../dtos/dude';

@Injectable({
  providedIn: 'root'
})
export class RegisterAsDudeService {

  private dudeBaseUri: string = this.globals.backendUri + '/dudes';
  private userBaseUri: string = this.globals.backendUri + '/user';

  constructor(private httpClient: HttpClient , private globals: Globals) { }

  addDude(dude: RegisterAsDude): Observable<RegisterAsDude> {
    console.log('add dude with name ' + dude.name);
    console.log('add dude with sex ' + dude.sex);
    return this.httpClient.post<RegisterAsDude>(this.dudeBaseUri, dude);
  }

  checkNameOfDude(name: string): Observable <any> {
    const params = new HttpParams().set('name', name);
    console.log('check dude by name ' + name);
    return this.httpClient.get(this.userBaseUri, { params: params});
  }

}
