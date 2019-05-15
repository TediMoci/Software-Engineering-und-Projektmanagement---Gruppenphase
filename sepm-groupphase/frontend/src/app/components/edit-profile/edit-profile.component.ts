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
    this.oldDude = this.editDudeService.getOldDude('Test'); // change to use name of currently logged in dude

    console.log(this.oldDude);

    this.userName = this.oldDude.name;
    this.description = this.oldDude.description;
    this.email = this.oldDude.email;
    this.birthday = this.oldDude.birthday;
    this.height = this.oldDude.height;
    this.weight = this.oldDude.weight;

    if (this.oldDude.sex === 'male') {
      this.male = true;
    } else if (this.oldDude.sex === 'female') {
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
      sex: ['', [Validators.required]],
      selfAssessment: ['', [Validators.required]],
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

    const dude: Dude = new Dude(this.editForm.controls.name.value,
      this.editForm.controls.password.value,
      this.editForm.controls.sex.value,
      this.editForm.controls.description.value,
      this.editForm.controls.email.value,
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
      () => {
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

