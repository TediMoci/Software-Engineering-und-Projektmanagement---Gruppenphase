import { Component, OnInit } from '@angular/core';
import {FitnessProviderProfileService} from '../../services/fitness-provider-profile.service';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {Course} from '../../dtos/course';
import {OwnCoursesService} from '../../services/own-courses.service';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-fitness-provider-profile',
  templateUrl: './fitness-provider-profile.component.html',
  styleUrls: ['./fitness-provider-profile.component.scss']
})

export class FitnessProviderProfileComponent implements OnInit {
  error: any;
  imagePath: string;
  userName: string;
  address: string;
  email: string;
  phoneNumber: string;
  website: string;
  numOfFollowers: number = 0;
  courses: Course[];
  description: string;
  currentUser: FitnessProvider;

  message: string;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  crop: boolean = false;
  constructor(private fitnessProviderProfile: FitnessProviderProfileService, private ownCoursesService: OwnCoursesService, private authService: AuthService) { }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.authService.getUserByNameFromFitnessProvider(this.currentUser.name).subscribe((data) => {
        localStorage.setItem('currentUser', JSON.stringify(data));
      },
      error => {
        this.error = error;
      }
    );
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.userName = this.currentUser.name;
    this.imagePath = this.currentUser.imagePath;
    this.address = this.currentUser.address;
    this.email = this.currentUser.email;
    this.phoneNumber = this.currentUser.phoneNumber;
    this.website = this.currentUser.website;
    this.description = this.currentUser.description;

    this.ownCoursesService.getAllCoursesOfLoggedInFP(this.currentUser).subscribe(
      (data) => {
        this.courses = data;
    },
      error => {
        this.error = error;
      }
    );

    this.fitnessProviderProfile.getFollower(this.currentUser.name).subscribe(
      (data) => {
        console.log('number of followers of fitness provider : ' + data + 'and name of fitness provider' + name);
        this.numOfFollowers = data;
      },
      error => {
        this.error = error;
      }
    );
  }

  setChosenCourse(element: Course) {
    localStorage.setItem('selectedCourse', JSON.stringify(element));
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
    this.fitnessProviderProfile.uploadPictureForFitnessProvider(this.currentUser.id, imageFile).subscribe(data => {
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
