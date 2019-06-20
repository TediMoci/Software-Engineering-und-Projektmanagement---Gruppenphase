import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { NgKnifeModule } from 'ng-knife';
import { HttpErrorInterceptor } from './interceptors/http-error.interceptor';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {LoginComponent} from './components/login/login.component';
import {MessageComponent} from './components/message/message.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import {Globals} from './global/globals';
import {RegisterAsDudeComponent} from './components/register-as-dude/register-as-dude.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegisterAsFitnessProviderComponent } from './components/register-as-fitness-provider/register-as-fitness-provider.component';
import { DudeProfileComponent } from './components/dude-profile/dude-profile.component';
import { BrosComponent } from './components/bros/bros.component';
import { CreateComponent } from './components/create/create.component';
import { FindComponent } from './components/find/find.component';
import { HistoryComponent } from './components/history/history.component';
import { MyContentComponent } from './components/my-content/my-content.component';
import { FavouritesComponent } from './components/favourites/favourites.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { FitnessProviderProfileComponent } from './components/fitness-provider-profile/fitness-provider-profile.component';
import { LoginAsFitnessProviderComponent } from './components/login-as-fitness-provider/login-as-fitness-provider.component';
import { FollowerComponent } from './components/follower/follower.component';
import { FitnessProvidersFollowedComponent } from './components/fitness-providers-followed/fitness-providers-followed.component';
import { CreateCourseComponent } from './components/create-course/create-course.component';
import { CreateExerciseComponent } from './components/create-exercise/create-exercise.component';
import { EditFitnessProviderProfileComponent } from './components/edit-fitness-provider-profile/edit-fitness-provider-profile.component';
import { OwnExercisesComponent } from './components/own-exercises/own-exercises.component';
import { OwnWorkoutsComponent } from './components/own-workouts/own-workouts.component';
import { OwnTrainingScheduleComponent } from './components/own-training-schedule/own-training-schedule.component';
import { ExerciseComponent } from './components/exercise/exercise.component';
import { EditExerciseComponent } from './components/edit-exercise/edit-exercise.component';
import { WorkoutComponent } from './components/workout/workout.component';
import { EditWorkoutComponent } from './components/edit-workout/edit-workout.component';
import { MyContentFitnessProviderComponent } from './components/my-content-fitness-provider/my-content-fitness-provider.component';
import { CreateForFitnessProviderComponent } from './components/create-for-fitness-provider/create-for-fitness-provider.component';
import { CreateWorkoutComponent } from './components/create-workout/create-workout.component';
import { WorkoutExercisesComponent } from './components/workout-exercises/workout-exercises.component';
import { CourseComponent } from './components/course/course.component';
import { EditCourseComponent } from './components/edit-course/edit-course.component';
import { ImageCropperModule } from 'ngx-image-cropper';
import { OwnCoursesComponent } from './components/own-courses/own-courses.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { CreateExercisesForWorkoutComponent } from './components/create-exercises-for-workout/create-exercises-for-workout.component';
import { EditWorkoutExercisesComponent } from './components/edit-workout-exercises/edit-workout-exercises.component';
import { CreateTrainingScheduleManuallyComponent } from './components/create-training-schedule-manually/create-training-schedule-manually.component';
import { TrainingScheduleComponent } from './components/training-schedule/training-schedule.component';
import { EditTrainingScheduleComponent } from './components/edit-training-schedule/edit-training-schedule.component';
import { FitnessProviderComponent } from './components/fitness-provider/fitness-provider.component';
import { CourseDudeViewComponent } from './components/course-dude-view/course-dude-view.component';
import { FollowerDudeComponent } from './components/follower-dude/follower-dude.component';
import { CreateTrainingScheduleRandomlyComponent } from './components/create-training-schedule-randomly/create-training-schedule-randomly.component';

import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatStepperModule,
} from '@angular/material';
import { FavouriteExercisesComponent } from './components/favourite-exercises/favourite-exercises.component';
import { FavouriteWorkoutsComponent } from './components/favourite-workouts/favourite-workouts.component';
import { FavouriteTrainingSchedulesComponent } from './components/favourite-training-schedules/favourite-training-schedules.component';
import { FavouriteCoursesComponent } from './components/favourite-courses/favourite-courses.component';
import {StatisticTrainingscheduleComponent} from './components/statistic-trainingschedule/statistic-trainingschedule.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    MessageComponent,
    RegisterAsDudeComponent,
    RegisterAsFitnessProviderComponent,
    DudeProfileComponent,
    BrosComponent,
    CreateComponent,
    FindComponent,
    HistoryComponent,
    MyContentComponent,
    FavouritesComponent,
    EditProfileComponent,
    FitnessProviderProfileComponent,
    LoginAsFitnessProviderComponent,
    FollowerComponent,
    FitnessProvidersFollowedComponent,
    CreateCourseComponent,
    OwnExercisesComponent,
    OwnWorkoutsComponent,
    OwnTrainingScheduleComponent,
    EditFitnessProviderProfileComponent,
    CreateExerciseComponent,
    ExerciseComponent,
    EditExerciseComponent,
    WorkoutComponent,
    EditWorkoutComponent,
    MyContentFitnessProviderComponent,
    CreateForFitnessProviderComponent,
    CreateWorkoutComponent,
    WorkoutExercisesComponent,
    CreateForFitnessProviderComponent,
    CourseComponent,
    EditCourseComponent,
    OwnCoursesComponent,
    CreateExercisesForWorkoutComponent,
    EditWorkoutExercisesComponent,
    CreateTrainingScheduleManuallyComponent,
    TrainingScheduleComponent,
    EditTrainingScheduleComponent,
    FitnessProviderComponent,
    CourseDudeViewComponent,
    FollowerDudeComponent,
    CreateTrainingScheduleRandomlyComponent,
    FavouriteExercisesComponent,
    FavouriteWorkoutsComponent,
    FavouriteTrainingSchedulesComponent,
    FavouriteCoursesComponent,
    StatisticTrainingscheduleComponent
  ],
  imports: [
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    BrowserAnimationsModule,
    NgKnifeModule,
    DragDropModule,
    ImageCropperModule
  ],
  providers: [httpInterceptorProviders, Globals, WorkoutExercisesComponent, EditWorkoutExercisesComponent, {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor,
    multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
