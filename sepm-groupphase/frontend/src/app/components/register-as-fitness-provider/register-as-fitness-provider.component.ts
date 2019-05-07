import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {passwordCheck} from '../../validator/validator';

@Component({
  selector: 'app-register-as-fitness-provider',
  templateUrl: './register-as-fitness-provider.component.html',
  styleUrls: ['./register-as-fitness-provider.component.scss']
})
export class RegisterAsFitnessProviderComponent implements OnInit {
  registerForm: FormGroup;
  submitted: boolean = false;
  constructor( private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required]],
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
    this.router.navigate(['/login']);
  }
}
