import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Dude} from '../../dtos/dude';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EditExerciseService} from '../../services/edit-exercise.service';
import {Exercise} from '../../dtos/exercise';

@Component({
  selector: 'app-edit-exercise',
  templateUrl: './edit-exercise.component.html',
  styleUrls: ['./edit-exercise.component.scss']
})
export class EditExerciseComponent implements OnInit {

  imagePath: string;
  imagePath2: string;
  userName: string;
  editExForm: FormGroup;
  error: any;
  dude: Dude;
  oldExercise: Exercise;
  submitted: boolean = false;
  endurance: boolean;
  strength: boolean;
  other: boolean;
  name: string;
  equipment: string;
  description: string;
  muscleGroup: string[] = ['Other', 'Chest', 'Back', 'Arms', 'Shoulders', 'Legs', 'Calves', 'Core'];
  isPrivate: boolean;
  isPrivateResult: boolean;
  message: string;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  crop: boolean = false;

  constructor(private editExerciseService: EditExerciseService , private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {

    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.oldExercise = JSON.parse(localStorage.getItem('selectedExercise'));
    this.editExerciseService.setFileStorage(undefined);
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    this.imagePath2 = this.oldExercise.imagePath;
    this.isPrivate = this.oldExercise.isPrivate;
    this.editExForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      equipment: ['', [Validators.required]],
      category: [this.oldExercise.category, [Validators.required]],
      description: ['', [Validators.required]],
      muscleGroup: [this.oldExercise.muscleGroup, [Validators.required]],
      isPrivate: ['']
    });

    this.name = this.oldExercise.name;
    this.equipment = this.oldExercise.equipment;
    this.description = this.oldExercise.description;

    if (this.oldExercise.category === 'Endurance') {
      this.endurance = true;
    } else if (this.oldExercise.category === 'Strength') {
      this.strength = true;
    } else {
      this.other = true;
    }

  }

  editExercise() {
    this.submitted = true;
    if (this.editExForm.controls.isPrivate.value === '') {
      this.isPrivateResult = this.oldExercise.isPrivate;
    } else {
      this.isPrivateResult = this.editExForm.controls.isPrivate.value;
    }
    const exercise: Exercise = new Exercise(
      this.oldExercise.id,
      this.oldExercise.version,
      this.editExForm.controls.name.value,
      this.editExForm.controls.description.value,
      this.editExForm.controls.equipment.value,
      this.editExForm.controls.muscleGroup.value,
      this.editExForm.controls.category.value,
      this.oldExercise.creatorId,
      this.oldExercise.imagePath,
      this.isPrivateResult
    );

    if (this.editExForm.invalid) {
      console.log('input is invalid');
      return;
    }

    this.editExerciseService.editExercise(exercise, this.oldExercise).subscribe(
      (data) => {
        if (this.editExerciseService.getFileStorage() !== undefined) {
          this.editExerciseService.uploadPictureForExercise(data.id, data.version, this.editExerciseService.getFileStorage()).subscribe((dataPicture) => {
            console.log(dataPicture);
            },
            error => {
              this.error = error;
            }
          );
        }
        this.router.navigate(['/myContent']);
      },
      error => {
        this.error = error;
      }
    );
  }


  vanishError() {
    this.error = false;
  }

  imageLoaded() {
    // show cropper
  }
  loadImageFailed() {
    // show message
    this.crop = true;
    this.message = 'Only images are supported.';

  }
  uploadPicture(files) {
    if (files.length === 0) {
      return;
    }
    console.log(files.file);
    const imageFile = new File([files.file], 'file', { type: files.file.type });
    this.imagePath2 = files.base64;
    this.editExerciseService.setFileStorage(imageFile);
  }
  fileChangeEvent(event: any): void {
    this.crop = false;
    this.imageChangedEvent = event;
  }
  imageCropped(image: string) {
    this.croppedImage = image;
  }
  cropPicture() {
    this.uploadPicture(this.croppedImage);
  }
}
