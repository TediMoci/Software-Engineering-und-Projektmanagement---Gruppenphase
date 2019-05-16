import {Component, OnInit} from '@angular/core';
import {EditDudeService} from '../../services/edit-dude.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {EditFitnessProviderService} from '../../services/edit-fitness-provider.service';
import {Dude} from '../../dtos/dude';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {passwordCheck} from '../../validator/validator';

@Component({
  selector: 'app-edit-fitness-provider-profile',
  templateUrl: './edit-fitness-provider-profile.component.html',
  styleUrls: ['./edit-fitness-provider-profile.component.scss']
})
export class EditFitnessProviderProfileComponent implements OnInit {

  error: any;
  editForm: FormGroup;
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

    this.editForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      address: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required]],
      website: ['', [Validators.required]],
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
      this.editForm.controls.name.value,
      this.editForm.controls.password.value,
      this.editForm.controls.address.value,
      this.editForm.controls.description.value,
      this.editForm.controls.email.value,
      this.editForm.controls.phoneNumber.value,
      this.editForm.controls.website.value
    );

    if (this.editForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editFitnessProviderService.editFitnessProvider(fitnessProvider, this.oldFitnessProvider).subscribe(
      () => {
        this.router.navigate(['/fitnessProvider-profile']);
      },
      error => {
        this.error = error;
      }
    );

  }
}
