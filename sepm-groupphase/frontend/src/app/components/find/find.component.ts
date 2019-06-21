import { Component, OnInit } from '@angular/core';
import {FindService} from '../../services/find.service';
import {Dude} from '../../dtos/dude';
import {Exercise} from '../../dtos/exercise';
import {AuthService} from '../../services/auth.service';
import {FitnessProvider} from '../../dtos/fitness-provider';
import {ExerciseFilter} from '../../dtos/exercise-filter';
import {CourseFilter} from '../../dtos/course-filter';
import {WorkoutFilter} from '../../dtos/workout-filter';
import {Course} from '../../dtos/course';
import {Workout} from '../../dtos/workout';
import {WorkoutService} from '../../services/workout.service';
import {FitnessProviderFilter} from '../../dtos/fitness-provider-filter';
import {DudeFilter} from '../../dtos/dude-filter';
import {BookmarksService} from '../../services/bookmarks.service';
import {TrainingSchedule} from '../../dtos/trainingSchedule';
import {TrainingScheduleFilter} from '../../dtos/training-schedule-filter';
import {TrainingScheduleService} from '../../services/training-schedule.service';
import {RatingService} from '../../services/rating.service';
import {subscribeOn} from 'rxjs/operators';

@Component({
  selector: 'app-find',
  templateUrl: './find.component.html',
  styleUrls: ['./find.component.scss']
})

export class FindComponent implements OnInit {

  // Inputs from html
  public category: string = 'Exercise';
  public inputText: any;
  public filterExerciseCategory: string = 'None';
  public filterWorkoutDifficulty: string = 'None';
  public filterWorkoutCaloriesMin: string = '';
  public filterWorkoutCaloriesMax: string = '';
  public filterDudeSelfAssessment: string = 'None';
  public filterExerciseMuscle: string = 'None';
  public filterTrainingDifficulty: string = 'None';
  public filterTrainingInterval: string = 'None';

  // Transfer Variables
  public inputTextActual: any;
  public filterExerciseCategoryActual: string = 'None';
  public filterWorkoutDifficultyActual: string = 'None';
  public filterWorkoutCaloriesMinActual: string = '';
  public filterWorkoutCaloriesMaxActual: string = '';
  public filterDudeSelfAssessmentActual: string = 'None';
  public filterExerciseMuscleActual: string = 'None';
  public filterTrainingDifficultyActual: string = 'None';
  public filterTrainingIntervalActual: string = 'None';

  entries: Array<any>;
  entriesTS: Array<any>;
  exercisesForWorkouts: any;
  workoutsForTrainingSchedules: any;

  imagePath: string;
  userName: string;
  isDude: boolean;
  error: any;
  errorBookmark: any;
  dude: Dude;
  bookmarkedName: string;
  selectedItem: any;
  ratingForItem: number = 0;

  // Filter Objects
  courceFilter: CourseFilter;
  fitnessProvider: FitnessProvider;
  exerciseFilter: ExerciseFilter;
  workoutFilter: WorkoutFilter;
  fitnessProviderFilter: FitnessProviderFilter;
  dudeFilter: DudeFilter;
  trainingScheduleFilter: TrainingScheduleFilter;
  sortFilter: string = 'Name';

  interval: number;
  d1: Array<any>;
  d2: Array<any>;
  d3: Array<any>;
  d4: Array<any>;
  d5: Array<any>;
  d6: Array<any>;
  d7: Array<any>;
  exercisesForWorkouts1: any;
  exercisesForWorkouts2: any;
  exercisesForWorkouts3: any;
  exercisesForWorkouts4: any;
  exercisesForWorkouts5: any;
  exercisesForWorkouts6: any;
  exercisesForWorkouts7: any;
  empty: any;

  // Router Objects
  selectedFP: FitnessProvider;

  followedFP: String;

  // Enums
  muscleGroup: string[] = ['Other', 'Chest', 'Back', 'Arms', 'Shoulders', 'Legs', 'Calves', 'Core'];

  constructor(private findService: FindService, private authService: AuthService, private workoutService: WorkoutService,
              private bookmarksService: BookmarksService, private trainingScheduleService: TrainingScheduleService,
              private ratingService: RatingService) {}

  ngOnInit() {
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'DUDE') {
      this.dude = JSON.parse(localStorage.getItem('loggedInDude'));
      this.userName = this.dude.name;
      this.imagePath = this.dude.imagePath;
      this.isDude = true;
    } if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'FITNESS_PROVIDER') {
      this.fitnessProvider = JSON.parse(localStorage.getItem('currentUser'));
      this.userName = this.fitnessProvider.name;
      this.imagePath = this.fitnessProvider.imagePath;
      this.isDude = false;
    }
  }

  startSearch(category: string) {
    console.log('searchvalue: ' + this.inputText);

    this.sortFilter = 'Name';

    if (this.inputText === undefined) {
      this.inputTextActual = null;
    } else {
      this.inputTextActual = this.inputText;
    }

    switch (category) {
      case 'Exercise':

        if (this.filterExerciseCategory === 'None') {
          this.filterExerciseCategoryActual = null;
        } else {
          this.filterExerciseCategoryActual = this.filterExerciseCategory;
        }

        if (this.filterExerciseMuscle === 'None') {
          this.filterExerciseMuscleActual = null;
        } else {
          this.filterExerciseMuscleActual = this.filterExerciseMuscle;
        }

        this.exerciseFilter = new ExerciseFilter(
          this.inputTextActual,
          this.filterExerciseCategoryActual,
          this.filterExerciseMuscleActual
        );
        console.log('name: ' + this.exerciseFilter.filter);
        if (this.isDude) {
          this.findService.getAllExercisesFilterd(this.exerciseFilter, this.dude.id).subscribe(
            (data) => {
              console.log('get all exercises');
              this.entries = data.sort(function (a, b) { // sort data alphabetically
                if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
                if (a.name > b.name) {return 1; }
                return 0;
              });
            },
            error => {
              this.error = error;
            }
          );
        } else {
          this.findService.getAllExercisesFilterd(this.exerciseFilter, 0).subscribe(
            (data) => {
              console.log('get all exercises');
              this.entries = data.sort(function (a, b) { // sort data alphabetically
                if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
                if (a.name > b.name) {return 1; }
                return 0;
              });
            },
            error => {
              this.error = error;
            }
          );
        }
        break;
      case 'Course':

        this.courceFilter = new CourseFilter(
          this.inputTextActual);

        console.log('name: ' + this.courceFilter.filter);
        this.findService.getAllCoursesFilterd(this.courceFilter).subscribe(
          (data) => {
            console.log('get all courses');
            this.entries = data.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                return -1;
              }
              if (a.name > b.name) {
                return 1;
              }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 'Workout':
        console.log('min: ' + this.filterWorkoutCaloriesMin);
        console.log('max: ' + this.filterWorkoutCaloriesMax);

        switch (this.filterWorkoutDifficulty) {
          case 'None': this.filterWorkoutDifficultyActual = null; break;
          case  'Beginner': this.filterWorkoutDifficultyActual = '1'; break;
          case  'Advanced': this.filterWorkoutDifficultyActual = '2'; break;
          case  'Pro': this.filterWorkoutDifficultyActual = '3'; break;
        }

        if (this.filterWorkoutCaloriesMin === '') {
          this.filterWorkoutCaloriesMinActual = null;
        } else {
          this.filterWorkoutCaloriesMinActual = this.filterWorkoutCaloriesMin;
        }

        if (this.filterWorkoutCaloriesMax === '') {
          this.filterWorkoutCaloriesMaxActual = null;
        } else {
          this.filterWorkoutCaloriesMaxActual = this.filterWorkoutCaloriesMax;
        }

        this.workoutFilter = new WorkoutFilter(
          this.inputTextActual,
          this.filterWorkoutDifficultyActual,
          this.filterWorkoutCaloriesMinActual,
          this.filterWorkoutCaloriesMaxActual);

        console.log('name: ' + this.workoutFilter.calorieLower);
        if (this.isDude) {
          this.findService.getAllWorkoutsFilterd(this.workoutFilter, this.dude.id).subscribe(
            (data) => {
              console.log('get all courses');
              this.entries = data.sort(function (a, b) { // sort data alphabetically
                if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                  return -1;
                }
                if (a.name > b.name) {
                  return 1;
                }
                return 0;
              });
            },
            error => {
              this.error = error;
            }
          );
        } else {
          this.findService.getAllWorkoutsFilterd(this.workoutFilter, 0).subscribe(
            (data) => {
              console.log('get all courses');
              this.entries = data.sort(function (a, b) { // sort data alphabetically
                if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                  return -1;
                }
                if (a.name > b.name) {
                  return 1;
                }
                return 0;
              });
            },
            error => {
              this.error = error;
            }
          );
        }
        break;
      case 'Dudes':

        switch (this.filterDudeSelfAssessment) {
          case 'None': this.filterDudeSelfAssessmentActual = null; break;
          case  'Beginner': this.filterDudeSelfAssessmentActual = '1'; break;
          case  'Advanced': this.filterDudeSelfAssessmentActual = '2'; break;
          case  'Pro': this.filterDudeSelfAssessmentActual = '3'; break;
        }
        this.dudeFilter = new DudeFilter(
          this.inputTextActual,
          this.filterDudeSelfAssessmentActual);

        console.log('name: ' + this.dudeFilter.filter);
        this.findService.getAllDudesFiltered(this.dudeFilter).subscribe(
          (data) => {
            console.log('get all dudes');
            this.entries = data.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                return -1;
              }
              if (a.name > b.name) {
                return 1;
              }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 'Fitness Provider':

        this.fitnessProviderFilter = new FitnessProviderFilter(
          this.inputTextActual);

        console.log('name: ' + this.fitnessProviderFilter.filter);
        this.findService.getAllFitnessProviderFiltered(this.fitnessProviderFilter).subscribe(
          (data) => {
            console.log('get all courses');
            this.entries = data.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                return -1;
              }
              if (a.name > b.name) {
                return 1;
              }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 'Training Schedule':
        console.log('difficulty: ' + this.filterTrainingDifficulty);
        console.log('interval: ' + this.filterTrainingInterval);

        switch (this.filterTrainingDifficulty) {
          case 'None': this.filterTrainingDifficultyActual = null; break;
          case  'Beginner': this.filterTrainingDifficultyActual = '1'; break;
          case  'Advanced': this.filterTrainingDifficultyActual = '2'; break;
          case  'Pro': this.filterTrainingDifficultyActual = '3'; break;
        }

        switch (this.filterTrainingInterval) {
          case 'None': this.filterTrainingIntervalActual = null; break;
          case  '1': this.filterTrainingIntervalActual = '1'; break;
          case  '2': this.filterTrainingIntervalActual = '2'; break;
          case  '3': this.filterTrainingIntervalActual = '3'; break;
          case  '4': this.filterTrainingIntervalActual = '4'; break;
          case  '5': this.filterTrainingIntervalActual = '5'; break;
          case  '6': this.filterTrainingIntervalActual = '6'; break;
          case  '7': this.filterTrainingIntervalActual = '7'; break;
        }

        this.trainingScheduleFilter = new TrainingScheduleFilter(
          this.inputTextActual,
          this.filterTrainingDifficultyActual,
          this.filterTrainingIntervalActual);

        if (this.isDude) {
          this.findService.getAllTrainingSchedulesFiltered(this.trainingScheduleFilter, this.dude.id).subscribe(
            (data) => {
              console.log('get all training schedules');
              this.entriesTS = data.sort(function (a, b) { // sort data alphabetically
                if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                  return -1;
                }
                if (a.name > b.name) {
                  return 1;
                }
                return 0;
              });
            },
            error => {
              this.error = error;
            }
          );
        } else {
          this.findService.getAllTrainingSchedulesFiltered(this.trainingScheduleFilter, 0).subscribe(
            (data) => {
              console.log('get all training schedules');
              this.entriesTS = data.sort(function (a, b) { // sort data alphabetically
                if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
                  return -1;
                }
                if (a.name > b.name) {
                  return 1;
                }
                return 0;
              });
            },
            error => {
              this.error = error;
            }
          );
        }
        break;
    }
  }

  getSelectedWorkoutExercises(workout: Workout) {
    this.workoutService.getExercisesOfWorkoutById(workout.id, workout.version).subscribe(
      (data) => {
        console.log('get all exercises of workout ' + workout.name);
        this.exercisesForWorkouts = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
          if (a.name > b.name) {return 1; }
          return 0;
        });
        console.log(this.exercisesForWorkouts.toString().toString());
      },
      error => {
        this.error = error;
      }
    );
  }

  getSelectedTrainingScheduleWorkouts(trainingSchedule: TrainingSchedule) {
    this.interval = trainingSchedule.intervalLength;
    this.getAllWorkoutsPerDay(trainingSchedule);
  }

  getSelectedTSWorkoutExercises(id: number, version: number) {
    this.workoutService.getExercisesOfWorkoutById(id, version).subscribe(
      (data) => {
        console.log('get all exercises of workout ' + id);
        this.exercisesForWorkouts = data.sort(function (a, b) { // sort data alphabetically
          if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
            return -1;
          }
          if (a.name > b.name) {
            return 1;
          }
          return 0;
        });
      },
      error => {
        this.error = error;
      }
    );
  }

  rateItem(item: any) {

    if (this.category === 'Exercise') {
      console.log('rating ' + typeof item + item.name  );
      this.ratingService.rateExercise(this.dude.id, item, this.ratingForItem).subscribe(
        (dataFavorite) => {this.startSearch(this.category); },
        errorFavorite => {
          this.errorBookmark = errorFavorite;
        });
    }
    if (this.category === 'Course') {
      console.log('rating ' + typeof item + item.name  );
      this.ratingService.rateCourse(this.dude.id, item, this.ratingForItem).subscribe(
        (dataFavorite) => {this.startSearch(this.category); },
        errorFavorite => {
          this.errorBookmark = errorFavorite;
        });
    }
    if (this.category === 'Workout') {
      console.log('rating ' + typeof item + item.name  );
      this.ratingService.rateWorkout(this.dude.id, item, this.ratingForItem).subscribe(
        (dataFavorite) => {this.startSearch(this.category); },
        errorFavorite => {
          this.errorBookmark = errorFavorite;
        });
    }
    if (this.category === 'Training Schedule') {
      console.log('rating ' + typeof item + item.name  );
      this.ratingService.rateTrainingSchedule(this.dude.id, item, this.ratingForItem).subscribe(
        (dataFavorite) => {this.startSearch(this.category); },
        errorFavorite => {
          this.errorBookmark = errorFavorite;
        });
    }
    this.ratingForItem  = 0;
  }

  convertDifficulty(element: any) {
    switch (element) {
      case 1: return 'Beginner';
      case 2: return 'Advanced';
      case 3: return 'Pro';
    }
  }

  closeExercises() {
    this.exercisesForWorkouts = this.empty;
  }

  setSelectedExercise(element: Exercise) {
    localStorage.setItem('selectedExercise', JSON.stringify(element));
  }
  setSelectedCourse(element: Course) {
    localStorage.setItem('selectedCourse', JSON.stringify(element));
  }
  setSelectedWorkout(element: Workout) {
    localStorage.setItem('selectedWorkout', JSON.stringify(element));
  }
  setSelectedTrainingSchedule(element: TrainingSchedule) {
    localStorage.setItem('selectedTrainingSchedule', JSON.stringify(element));
  }

  bookmarkCourse(element: Course) {
    this.bookmarkedName = element.name;
    this.bookmarksService.bookmarkCourse(this.dude.id, element.id).subscribe(
      (dataBookmark) => {},
        errorBookmark => {
        this.errorBookmark = errorBookmark;
      }
    );
  }

  bookmarkExercise(element: Exercise) {
    this.bookmarkedName = element.name;
    this.bookmarksService.bookmarkExercise(this.dude.id, element.id, element.version).subscribe(
      (dataBookmark) => {},
      errorBookmark => {
        this.errorBookmark = errorBookmark;
      }
    );
  }

  bookmarkWorkout(element: Workout) {
    this.bookmarkedName = element.name;
    this.bookmarksService.bookmarkWorkout(this.dude.id, element.id, element.version).subscribe(
      (dataBookmark) => {},
      errorBookmark => {
        this.errorBookmark = errorBookmark;
      }
    );
  }

  bookmarkTrainingSchedule(element: TrainingSchedule) {
    this.bookmarkedName = element.name;
    this.bookmarksService.bookmarkTrainingSchedule(this.dude.id, element.id, element.version).subscribe(
      (dataBookmark) => {},
      errorBookmark => {
        this.errorBookmark = errorBookmark;
      }
    );
  }

  setSelectedFPofCourse(element: Course) {
    this.findService.getOneFitnessProvider(element.creatorId).subscribe(
      (data) => {
        this.selectedFP = data;
        console.log('Loaded FP: ' + this.selectedFP.name);
        localStorage.setItem('selectedFitnessProvider', JSON.stringify(data));
        console.log('FP in LS' + localStorage.getItem('selectedFitnessProvider'));
      },
      error => {
        this.error = error;
      }
    );
  }

  sortAlphabetically() {
    if (this.entries != null) {
      this.entries.sort(function (a, b) { // sort data alphabetically
        if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
          return -1;
        }
        if (a.name > b.name) {
          return 1;
        }
        return 0;
      });
    }
    if (this.entriesTS != null) {
      this.entriesTS.sort(function (a, b) { // sort data alphabetically
        if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {
          return -1;
        }
        if (a.name > b.name) {
          return 1;
        }
        return 0;
      });
    }
  }

  sortEntriesByRating() {
    if (this.entries != null) {
      this.entries.sort(function (a, b) { // sort data by rating
        if (a.rating > b.rating) {return -1; }
        if (a.rating < b.rating) {return 1; }
        return 0;
      });
    }
    if (this.entriesTS != null) {
      this.entriesTS.sort(function (a, b) { // sort data by rating
        if (a.rating > b.rating) {
          return -1;
        }
        if (a.rating < b.rating) {
          return 1;
        }
        return 0;
      });
    }
  }

  resetResults() {
    this.entries = null;
    this.entriesTS = null;
  }

  followSelectedFitnessProvider(fitnessProvider: FitnessProvider) {
    this.findService.followFitnessProvider(JSON.parse(localStorage.getItem('loggedInDude')).id, fitnessProvider.id);
    this.followedFP = fitnessProvider.name;
  }

  vanishError() {
    this.error = false;
  }

  vanishErrorBookmark() {
    this.errorBookmark = false;
  }

  getAllWorkoutsPerDay(trainingSchedule: TrainingSchedule) {
    switch (trainingSchedule.intervalLength) {
      case 1:
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 2:
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 3:
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 4:
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 4).subscribe(
          (data4) => {
            this.d4 = data4.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 5:
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 4).subscribe(
          (data4) => {
            this.d4 = data4.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 5).subscribe(
          (data5) => {
            this.d5 = data5.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 6:
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 4).subscribe(
          (data4) => {
            this.d4 = data4.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 5).subscribe(
          (data5) => {
            this.d5 = data5.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            }); Array.from(data5);
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 6).subscribe(
          (data6) => {
            this.d6 = data6.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
      case 7:
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 1).subscribe(
          (data1) => {
            this.d1 = data1.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 2).subscribe(
          (data2) => {
            this.d2 = data2.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 3).subscribe(
          (data3) => {
            this.d3 = data3.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 4).subscribe(
          (data4) => {
            this.d4 = data4.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 5).subscribe(
          (data5) => {
            this.d5 = data5.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 6).subscribe(
          (data6) => {
            this.d6 = data6.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        this.trainingScheduleService.getAllWorkoutsByDay(trainingSchedule.id, trainingSchedule.version, 7).subscribe(
          (data7) => {
            this.d7 = data7.sort(function (a, b) { // sort data alphabetically
              if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) {return -1; }
              if (a.name > b.name) {return 1; }
              return 0;
            });
          },
          error => {
            this.error = error;
          }
        );
        break;
    }
  }
}
