import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isLoggedIn()){
      if (this.authService.getUserRole() === 'DUDE'){
        return true;
      } else if (this.authService.getUserRole() == 'FITNESS_PROVIDER'){
        this.router.navigate(['/fitnessProvider-profile']);
        return false;
      } else {
        this.router.navigate(['']);
        return false;
      }
    } else {
      this.router.navigate(['']);
      return false;
    }

  }
}
