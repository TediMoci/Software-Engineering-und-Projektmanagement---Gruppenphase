import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {passwordCheck, checkName} from '../../validator/validator';
import {Dude} from '../../dtos/dude';
import {EditDudeService} from '../../services/edit-dude.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {
  error: any;
  editForm: FormGroup;
  submitted: boolean = false;
  userName: string;
  description: string;
  email: string;
  birthday: any;
  height: number;
  weight: number;
  oldDude: any;
  male: boolean = false;
  female: boolean = false;
  other: boolean = false;
  beginner: boolean = false;
  advanced: boolean = false;
  password: any;
  pro: boolean = false;

  constructor(private editDudeService: EditDudeService, private formBuilder: FormBuilder, private router: Router) {
  }

  ngOnInit() {

    this.oldDude = JSON.parse(localStorage.getItem('loggedInDude'));

    this.userName = this.oldDude.name;
    this.description = this.oldDude.description;
    this.email = this.oldDude.email;
    this.birthday = this.oldDude.birthday;
    this.height = this.oldDude.height;
    this.weight = this.oldDude.weight;
    this.password = this.oldDude.password;

    if (this.oldDude.sex === 'Male') {
      this.male = true;
    } else if (this.oldDude.sex === 'Female') {
      this.female = true;
    } else {
      this.other = true;
    }

    if (this.oldDude.selfAssessment === 1) {
      this.beginner = true;
    } else if (this.oldDude.selfAssessment === 2) {
      this.advanced = true;
    } else {
      this.pro = true;
    }
    this.editForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      sex: [this.oldDude.sex, [Validators.required]],
      selfAssessment: [this.oldDude.selfAssessment, [Validators.required]],
      birthday: ['', [Validators.required]],
      height: ['', [Validators.required]],
      weight: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordConfirmed: ['', Validators.required]
    }, {
      validator: passwordCheck('password', 'passwordConfirmed'),
    });
  }

  editUser() {
    this.submitted = true;

    const dude: Dude = new Dude(
      this.oldDude.id,
      this.editForm.controls.name.value,
      this.editForm.controls.password.value,
      this.editForm.controls.description.value,
      this.editForm.controls.email.value,
      this.editForm.controls.sex.value,
      this.oldDude.status,
      this.editForm.controls.selfAssessment.value,
      this.editForm.controls.birthday.value,
      this.editForm.controls.height.value,
      this.editForm.controls.weight.value,
    );

    if (this.editForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editDudeService.editDude(dude, this.oldDude).subscribe(
      (data) => {
        localStorage.setItem('loggedInDude', JSON.stringify(data));
        this.router.navigate(['/dude-profile']);
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

