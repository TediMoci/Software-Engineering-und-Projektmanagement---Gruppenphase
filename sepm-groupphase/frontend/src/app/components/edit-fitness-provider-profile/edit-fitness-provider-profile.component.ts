import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {EditFitnessProviderService} from '../../services/edit-fitness-provider.service';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {passwordCheck} from '../../validator/validator';

@Component({
  selector: 'app-edit-fitness-provider-profile',
  templateUrl: './edit-fitness-provider-profile.component.html',
  styleUrls: ['./edit-fitness-provider-profile.component.scss']
})
export class EditFitnessProviderProfileComponent implements OnInit {

  error: any;
  editFPForm: FormGroup;
  submitted: boolean = false;
  oldFitnessProvider: FitnessProvider;
  userName: string;
  password: string;
  address: string;
  description: string;
  email: string;
  phoneNumber: string;
  website: string;

  constructor(private editFitnessProviderService: EditFitnessProviderService, private formBuilder: FormBuilder, private router: Router) {
  }

  ngOnInit() {
    this.oldFitnessProvider = JSON.parse(localStorage.getItem('currentUser'));

    this.userName = this.oldFitnessProvider.name;
    this.email = this.oldFitnessProvider.email;
    this.password = this.oldFitnessProvider.password;
    this.address = this.oldFitnessProvider.address;
    this.description = this.oldFitnessProvider.description;
    this.phoneNumber = this.oldFitnessProvider.phoneNumber;
    this.website = this.oldFitnessProvider.website;

    this.editFPForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      address: ['', [Validators.required]],
      phoneNumber: [''],
      website: [''],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordConfirmed: ['', Validators.required]
    }, {
      validator: passwordCheck('password', 'passwordConfirmed'),
    });
  }

  editFitnessProvider() {
    this.submitted = true;

    const fitnessProvider: FitnessProvider = new FitnessProvider(
      this.oldFitnessProvider.id,
      this.editFPForm.controls.name.value,
      this.editFPForm.controls.password.value,
      this.editFPForm.controls.address.value,
      this.editFPForm.controls.description.value,
      this.editFPForm.controls.email.value,
      this.editFPForm.controls.phoneNumber.value,
      this.editFPForm.controls.website.value,
      this.oldFitnessProvider.imagePath
    );

    if (this.editFPForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editFitnessProviderService.editFitnessProvider(fitnessProvider, this.oldFitnessProvider).subscribe(
      (data) => {
        localStorage.setItem('currentUser', JSON.stringify(data));
        this.router.navigate(['/fitnessProvider-profile']);
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
