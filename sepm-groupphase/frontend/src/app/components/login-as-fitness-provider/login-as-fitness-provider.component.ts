import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {AuthRequest} from '../../dtos/auth-request';
import {FitnessProvider} from '../../dtos/fitness-provider';

@Component({
  selector: 'app-login-as-fitness-provider',
  templateUrl: './login-as-fitness-provider.component.html',
  styleUrls: ['./login-as-fitness-provider.component.scss']
})
export class LoginAsFitnessProviderComponent implements OnInit {
  loginForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // Error flag
  error: any;
  fitnessProvider: FitnessProvider;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  loginUser() {
    this.submitted = true;
    this.authService.getUserByNameFromFitnessProvider(this.loginForm.controls.username.value).subscribe((data) => {
        if (this.loginForm.valid) {
          const authRequest: AuthRequest = new AuthRequest(this.loginForm.controls.username.value, this.loginForm.controls.password.value);
          this.authenticateUser(authRequest);
          // receives the current user
          this.fitnessProvider = data;
          // save the currentUser in local storage to use it in other components
          localStorage.setItem('currentUser', JSON.stringify(this.fitnessProvider));
        } else {
          console.log('Invalid input');
        }
      },
      error => {
        this.error = error;
      }
    );
  }

  /**
   * Send authentication data to the authService. If the authentication was successfully, the user will be forwarded to the message page
   * @param authRequest authentication data from the user login form
   */
  authenticateUser(authRequest: AuthRequest) {
    console.log('Try to authenticate user: ' + authRequest.username);
    this.authService.loginUser(authRequest).subscribe(
      () => {
        console.log('Successfully logged in user: ' + authRequest.username);
        this.router.navigate(['/fitnessProvider-profile']);
      },
      error => {
        console.log('Could not log in due to: ' + error);
        this.error = error;
      }
    );
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  ngOnInit() {
  }
}
