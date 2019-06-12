import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {LoginComponent} from '../login/login.component';
import {Globals} from '../../global/globals';
import {Dude} from '../../dtos/dude';
import {ProfileService} from '../../services/profile.service';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-dude-profile',
  templateUrl: './dude-profile.component.html',
  styleUrls: ['./dude-profile.component.scss'],
  providers: [LoginComponent]
})
export class DudeProfileComponent implements OnInit {

  error: any;
  imagePath: string;
  userName: string;
  sex: any;
  skill: string;
  age: number;
  height: number;
  weight: number;
  bmi: number;
  description: string;
  email: string;

  dude: Dude;

  message: string;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  crop: boolean = false;
  constructor(private globals: Globals, private profileService: ProfileService, private authService: AuthService) {
  }
  ngOnInit() {
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.authService.getUserByNameFromDude(this.dude.name).subscribe((data) => {
      localStorage.setItem('loggedInDude', JSON.stringify(data));
      },
      error => {
        this.error = error;
      }
    );
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.userName = this.dude.name;
    this.imagePath = this.dude.imagePath;
    if (this.dude.selfAssessment === 1) {
      this.skill = 'Beginner';
    } else if (this.dude.selfAssessment === 2) {
      this.skill = 'Advanced';
    } else {
      this.skill = 'Pro';
    }

    this.height = this.dude.height;
    this.weight = this.dude.weight;
    this.sex = this.dude.sex;
    this.description = this.dude.description;
    this.email = this.dude.email;

    this.profileService.getAge(this.dude.birthday, this.dude.name).subscribe(
      (data) => {
        console.log('calculate age of dude with name ' + this.dude.name);
        this.age = data;
      },
      error => {
        this.error = error;
      }
    );

    this.profileService.getBmi(this.dude.height, this.dude.weight, this.dude.name).subscribe(
      (data) => {
        console.log('calculate bmi of dude with name ' + this.dude.name);
        this.bmi = data;
      },
        error => {
        this.error = error;
        }
    );

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
    const imageFile = new File([files.file], 'file', { type: files.file.type });
    this.profileService.uploadPictureDudes(this.dude.id, imageFile).subscribe(data => {
      console.log('upload picture' + data);
      },
      error => {
        this.error = error;
      }
    );
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
