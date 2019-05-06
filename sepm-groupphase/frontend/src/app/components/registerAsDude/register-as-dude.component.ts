import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {passwordCheck } from '../../validator/validator';
import {AuthService} from '../../services/auth.service';
import {AuthRegister} from '../../dtos/auth-register';

@Component({
  selector: 'app-register-as-dude',
  templateUrl: './register-as-dude.component.html',
  styleUrls: ['./register-as-dude.component.scss']
})
export class RegisterAsDudeComponent implements OnInit {
  registerForm: FormGroup;
  submitted: boolean = false;
  constructor( private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required]],
      gender: ['', [Validators.required]],
      selfAssessment: ['', [Validators.required]],
      birthday: ['', [Validators.required]],
      height: ['', [Validators.required]],
      weight: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordConfirmed: ['', Validators.required]
    }, {
      validator: passwordCheck('password', 'passwordConfirmed')
    });
  }

  registerUser() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      console.log(this.registerForm.get('gender').value);
      return;
    }
    this.router.navigate(['/login']);
  }
}
