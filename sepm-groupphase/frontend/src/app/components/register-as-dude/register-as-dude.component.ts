import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {passwordCheck} from '../../validator/validator';
import {RegisterAsDude} from '../../dtos/register-as-dude';
import {RegisterAsDudeService} from '../../services/register-as-dude.service';


@Component({
  selector: 'app-register-as-dude',
  templateUrl: './register-as-dude.component.html',
  styleUrls: ['./register-as-dude.component.scss']
})
export class RegisterAsDudeComponent implements OnInit {
  error: any;
  registerForm: FormGroup;
  submitted: boolean = false;
  constructor(private registerAsDudeService: RegisterAsDudeService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      sex: ['', [Validators.required]],
      selfAssessment: ['', [Validators.required]],
      birthday: ['', [Validators.required]],
      height: ['', [Validators.required]],
      weight: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordConfirmed: ['', Validators.required],
      isPrivate: ['', Validators.required]
    }, {
      validator: passwordCheck('password', 'passwordConfirmed'),
    });
  }


  registerUser() {
    this.submitted = true;

    const dude: RegisterAsDude = new RegisterAsDude(this.registerForm.controls.name.value,
      this.registerForm.controls.password.value,
      this.registerForm.controls.sex.value,
      this.registerForm.controls.email.value,
      this.registerForm.controls.selfAssessment.value,
      this.registerForm.controls.birthday.value,
      this.registerForm.controls.height.value,
      this.registerForm.controls.weight.value,
      this.registerForm.controls.isPrivate.value
    );

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.registerAsDudeService.addDude(dude).subscribe(
      () => {
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
