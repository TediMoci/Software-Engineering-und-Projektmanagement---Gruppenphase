import { FormGroup } from '@angular/forms';

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
