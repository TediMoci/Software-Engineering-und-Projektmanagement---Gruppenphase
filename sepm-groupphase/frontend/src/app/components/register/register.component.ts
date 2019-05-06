import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {AuthRegister} from '../../dtos/auth-register';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  success: boolean = false;
  constructor( private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordConfirmed: ['', [Validators.required, Validators.minLength(8)] ],
      email: ['', [Validators.required]],
      sex: ['', [Validators.required]],
      selfAssessment: ['', [Validators.required]],
      birthday: ['', [Validators.required]],
      height: ['', [Validators.required]],
      weight: ['', [Validators.required]]
    });
  }

  registerUser() {

    if (this.registerForm.invalid) {
      return;
    }

    this.success = true;
  }
}
