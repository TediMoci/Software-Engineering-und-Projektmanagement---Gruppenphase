import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Exercise} from '../../dtos/Exercise';
import {Router} from '@angular/router';
import {CreateExerciseService} from '../../services/create-exercise.service';
@Component({
  selector: 'app-create-exercise',
  templateUrl: './create-exercise.component.html',
  styleUrls: ['./create-exercise.component.scss']
})
export class CreateExerciseComponent implements OnInit {
  error: any;
  imagePath: string = 'assets/img/kugelfisch.jpg';
  imagePathExercise: string = 'assets/img/exercise.png';
  userName: string;
  registerForm: FormGroup;
  submitted: boolean = false;
  dude: Dude;

  constructor(private createExerciseService: CreateExerciseService , private formBuilder: FormBuilder, private router: Router ) {
  }

  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.registerForm = this.formBuilder.group({
      nameForExercise: ['', [Validators.required]],
      equipmentExercise: [''],
      categoryExercise: ['', [Validators.required]],
      descriptionForExercise: ['', [Validators.required]],
      muscleGroupExercise: ['']
    });
  }


  addExercise() {

    this.submitted = true;
    console.log(this.registerForm);

    const exercise: Exercise = new Exercise(
      this.registerForm.controls.nameForExercise.value,
      this.registerForm.controls.equipmentExercise.value,
      this.registerForm.controls.categoryExercise.value,
      this.registerForm.controls.descriptionForExercise.value,
      this.registerForm.controls.muscleGroupExercise.value,
      this.dude.id
    );

    if (this.registerForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.createExerciseService.addExercise(exercise).subscribe(
      () => {
        console.log(exercise);
        this.router.navigate(['create']);
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
