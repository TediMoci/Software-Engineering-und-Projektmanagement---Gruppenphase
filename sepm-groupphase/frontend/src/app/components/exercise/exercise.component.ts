import { Component, OnInit } from '@angular/core';
import {Dude} from '../../dtos/dude';
import {Exercise} from '../../dtos/exercise';
import {ExerciseWithRating} from '../../dtos/exercise-with-rating';
import {RatingService} from '../../services/rating.service';
import {GetByIDService} from '../../services/get-by-id.service';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.scss']
})
export class ExerciseComponent implements OnInit {

  imagePath: string;
  imagePath2: string;
  userName: string;
  exerciseName: string;
  equipment: string;
  category: string;
  muscleGroup: string;
  description: string;
  exercise: ExerciseWithRating;
  dude: Dude;
  isPrivate: boolean;
  rating: number;
  ratingForItem: number = 0;
  error: any;
  constructor(private ratingService: RatingService, private getByIDService: GetByIDService) { }

  ngOnInit() {

    this.exercise = JSON.parse(localStorage.getItem('selectedExercise'));
    console.log(this.exercise);
    this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
    this.imagePath = this.dude.imagePath;
    this.imagePath2 = this.exercise.imagePath;
    this.userName = this.dude.name;
    this.exerciseName = this.exercise.name;
    this.equipment = this.exercise.equipment;
    this.muscleGroup = this.exercise.muscleGroup;
    this.category = this.exercise.category;
    this.description = this.exercise.description;
    this.isPrivate = this.exercise.isPrivate;
    this.rating = this.exercise.rating;
  }

  convertPrivate() {
    if (this.isPrivate === true) {
      return 'Private';
    } else {
      return 'Public';
    }
  }

  rateItem(item: any) {
    console.log('rating ' + typeof item + item.name  );
    this.ratingService.rateExercise(this.dude.id, item, this.ratingForItem).subscribe(
      (dataFavorite) => {
        this.getExercise(this.exercise.id, this.exercise.version);
        },
      errorFavorite => {
        this.error = errorFavorite;
      });
  }

  getExercise(id: number, version: number) {
    this.getByIDService.getExerciseByID(id, version).subscribe(
      (data) => {
        this.exercise = data;
        localStorage.setItem('selectedExercise', JSON.stringify(data));
        this.ngOnInit();
      },
      errorFavorite => {
        this.error = errorFavorite;
      });
  }

}
