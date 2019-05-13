import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {AuthRequest} from '../../dtos/auth-request';

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
    this.authService.getUserByNameAndPasswordFromFitnessProvider(this.loginForm.controls.username.value, this.loginForm.controls.password.value).subscribe((data) => {
      console.log(data);
        if (this.loginForm.valid) {
          const authRequest: AuthRequest = new AuthRequest(this.loginForm.controls.username.value, this.loginForm.controls.password.value);
          this.authenticateUser(authRequest);
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
        this.router.navigate(['/fitnessProvider-profile']); // TODO: route to fitnessProvider or dude-profile according to who is logged in
      },
      error => {
        console.log('Could not log in due to: ' + error.message);
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
