import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Dude} from '../dtos/dude';
import {HttpClient, HttpParams, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class EditDudeService {
  private dudeBaseUri: string = this.globals.backendUri + '/dudes';
  private getByNameUri: string = this.globals.backendUri + '/dudes/nameIs';

  constructor(private httpClient: HttpClient , private globals: Globals) { }
  editDude(editDude: Dude, oldDude: Dude): Observable<Dude> {
    console.log('edit dude with new name ' + editDude.name + ' and old name ' + oldDude.name);
    localStorage.setItem('loggedInDude', JSON.stringify(editDude));
    const params = new HttpParams().set('dude', JSON.stringify(editDude));
    return this.httpClient.post<Dude>(this.dudeBaseUri + '/' + oldDude.name, {params: params});
  }
  checkNameOfDude(name: string): Observable <any> {
    const params = new HttpParams().set('name', name);
    console.log('check dude by name ' + name);
    return this.httpClient.get(this.getByNameUri + name, { params: params});
  }

}
