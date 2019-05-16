import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class FitnessProviderRoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
      if (this.authService.getUserRole() === 'FITNESS_PROVIDER') {
        return true;
      } else if (this.authService.getUserRole() == 'DUDE') {
        this.router.navigate(['/dude-profile']);
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
