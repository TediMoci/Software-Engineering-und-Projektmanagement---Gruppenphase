import {Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {RegisterAsDude} from '../dtos/register-as-dude';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
@Injectable({
  providedIn: 'root'
})
export class RegisterAsDudeService {
  private dudeBaseUri: string = this.globals.backendUri + '/dudes';
  constructor(private httpClient: HttpClient , private globals: Globals) { }

  addDude(dude: RegisterAsDude): Observable<RegisterAsDude> {
    console.log('add dude with name ' + dude.name);
    return this.httpClient.post<RegisterAsDude>(this.dudeBaseUri, dude);
  }
}
