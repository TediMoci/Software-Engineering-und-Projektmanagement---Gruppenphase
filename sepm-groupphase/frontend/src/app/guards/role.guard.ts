import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.getUserRole() === 'DUDE'){
      return true;
    } else if (this.authService.getUserRole() == 'FITNESS_PROVIDER'){
      return false;
    } else {
      return false;
    }
  }
}
