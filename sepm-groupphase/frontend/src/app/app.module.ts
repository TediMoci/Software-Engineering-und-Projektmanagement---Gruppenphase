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
import { CreateExerciseComponent } from './components/create-exercise/create-exercise.component';
import { EditFitnessProviderProfileComponent } from './components/edit-fitness-provider-profile/edit-fitness-provider-profile.component';
import { OwnExercisesComponent } from './components/own-exercises/own-exercises.component';
import { OwnWorkoutsComponent } from './components/own-workouts/own-workouts.component';
import { OwnTrainingScheduleComponent } from './components/own-training-schedule/own-training-schedule.component';
import { ExerciseComponent } from './components/exercise/exercise.component';
import { EditExerciseComponent } from './components/edit-exercise/edit-exercise.component';

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
    EditFitnessProviderProfileComponent,
    OwnExercisesComponent,
    OwnWorkoutsComponent,
    OwnTrainingScheduleComponent,
    EditFitnessProviderProfileComponent,
    FitnessProvidersFollowedComponent,
    CreateExerciseComponent,
    ExerciseComponent,
    EditExerciseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    BrowserAnimationsModule,
    NgKnifeModule
  ],
  providers: [httpInterceptorProviders, Globals, {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor,
    multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
