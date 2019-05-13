import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {passwordCheck} from '../../validator/validator';
import {RegisterAsFitnessProviderService} from '../../services/register-as-fitness-provider.service';
import {RegisterAsFitnessProvider} from '../../dtos/register-as-fitness-provider';

@Component({
  selector: 'app-register-as-fitness-provider',
  templateUrl: './register-as-fitness-provider.component.html',
  styleUrls: ['./register-as-fitness-provider.component.scss']
})
export class RegisterAsFitnessProviderComponent implements OnInit {
  
  error: any;
  registerForm: FormGroup;
  submitted: boolean = false;
  constructor(private registerAsFitnessProviderService: RegisterAsFitnessProviderService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      address: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordConfirmed: ['', Validators.required]
    }, {
      validator: passwordCheck('password', 'passwordConfirmed')
    });
  }

  registerFitnessProvider() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }

    const fitnessProvider: RegisterAsFitnessProvider = new RegisterAsFitnessProvider(this.registerForm.controls.name.value,
      this.registerForm.controls.password.value,
      this.registerForm.controls.email.value,
      this.registerForm.controls.address.value
    );

    this.registerAsFitnessProviderService.addFitnessProvider(fitnessProvider).subscribe(
      () => {
        console.log(fitnessProvider);
        this.router.navigate(['']);
      },
      error => {
        this.error = error;
      }
    );
  }

  vanishError() {
    this.error = false;
  }
}
