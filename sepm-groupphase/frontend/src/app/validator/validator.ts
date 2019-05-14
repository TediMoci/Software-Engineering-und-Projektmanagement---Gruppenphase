import { FormGroup} from '@angular/forms';
import {RegisterAsDudeService} from '../services/register-as-dude.service';

export function passwordCheck(passwordHelp: string, confirmedPasswordHelp: string) {
  return (formGroup: FormGroup) => {
    const password = formGroup.controls[passwordHelp];
    const confirmedPassword = formGroup.controls[confirmedPasswordHelp];

    if (password.value !== confirmedPassword.value) {
      confirmedPassword.setErrors({ passwordCheck: true });
    } else {
      confirmedPassword.setErrors(null);
    }
  };
}

export function checkName (nameHelp: string) {
  return (registerAsDudeService: RegisterAsDudeService, formGroup: FormGroup) => {
    const name = formGroup.controls[nameHelp];
    registerAsDudeService.checkNameOfDude(name.value).subscribe((res) => {
      if (res  !== 2) {
        console.log('check name by id ' + res);
        name.setErrors({ nameCheck: true });
      } else {
        name.setErrors(null);
      }
      }, error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  };
}
