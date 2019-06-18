import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {AuthRequest} from '../../dtos/auth-request';
import {Dude} from '../../dtos/dude';
import {ProfileService} from '../../services/profile.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // Error flag
  error: any;
  loginDude: Dude;

  constructor(private formBuilder: FormBuilder, private profile: ProfileService, private authService: AuthService, private router: Router) {
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
    this.authService.getUserByNameFromDude(this.loginForm.controls.username.value).subscribe((data) => {
        if (this.loginForm.valid) {
          const authRequest: AuthRequest = new AuthRequest(this.loginForm.controls.username.value, this.loginForm.controls.password.value);
          this.authenticateUser(authRequest);
          this.loginDude = data;
          localStorage.setItem('loggedInDude', JSON.stringify(this.loginDude));
          console.log('Dude logged in: ' + this.loginDude.name + ', ' + this.loginDude.id);
        } else {
          console.log('Invalid input');
        }
      },
      error => {
        this.error = error;
        console.log(error);
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
        this.router.navigate(['/dude-profile']);
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
