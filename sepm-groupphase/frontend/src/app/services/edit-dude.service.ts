import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Dude} from '../dtos/dude';
import {HttpClient, HttpParams, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class EditDudeService {
  private dudeBaseUri: string = this.globals.backendUri + '/dudes';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  editDude(editDude: Dude, oldDude: Dude): Observable<Dude> {
    console.log('edit dude with new name ' + editDude.name + ' and old name ' + oldDude.name);
    return this.httpClient.put<Dude>(this.dudeBaseUri + '/' + oldDude.name, editDude);
  }
}
