import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterAsDudeComponent} from './components/register-as-dude/register-as-dude.component';
import {RegisterAsFitnessProviderComponent} from './components/register-as-fitness-provider/register-as-fitness-provider.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {DudeProfileComponent} from './components/dude-profile/dude-profile.component';
import {BrosComponent} from './components/bros/bros.component';
import {CreateComponent} from './components/create/create.component';
import {FindComponent} from './components/find/find.component';
import {HistoryComponent} from './components/history/history.component';
import {MyContentComponent} from './components/my-content/my-content.component';
import {FavouritesComponent} from './components/favourites/favourites.component';
import {EditProfileComponent} from './components/edit-profile/edit-profile.component';
import {FitnessProviderProfileComponent} from './components/fitness-provider-profile/fitness-provider-profile.component';
import {LoginAsFitnessProviderComponent } from './components/login-as-fitness-provider/login-as-fitness-provider.component';
import {RoleGuard} from './guards/role.guard';
import {FitnessProviderRoleGuard} from './guards/fitness-provider-role.guard';
import {FollowerComponent} from './components/follower/follower.component';
import {FitnessProvidersFollowedComponent} from './components/fitness-providers-followed/fitness-providers-followed.component';
import {CreateExerciseComponent} from './components/create-exercise/create-exercise.component';
import {EditFitnessProviderProfileComponent} from './components/edit-fitness-provider-profile/edit-fitness-provider-profile.component';
import {OwnExercisesComponent} from './components/own-exercises/own-exercises.component';
import {OwnWorkoutsComponent} from './components/own-workouts/own-workouts.component';
import {OwnTrainingScheduleComponent} from './components/own-training-schedule/own-training-schedule.component';
import {ExerciseComponent} from './components/exercise/exercise.component';

const routes: Routes = [

  {path: '', component: LoginComponent},
  {path: 'login-as-fitness-provider', component: LoginAsFitnessProviderComponent},
  {path: 'register-as-dude', component: RegisterAsDudeComponent},
  {path: 'register-as-fitness-provider', component: RegisterAsFitnessProviderComponent},
  {path: 'dude-profile', canActivate: [RoleGuard], component: DudeProfileComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'bros', canActivate: [RoleGuard], component: BrosComponent},
  {path: 'create', canActivate: [AuthGuard], component: CreateComponent},
  {path: 'create-exercise', component: CreateExerciseComponent},
  {path: 'find', canActivate: [AuthGuard], component: FindComponent},
  {path: 'history', canActivate: [RoleGuard], component: HistoryComponent},
  {path: 'myContent', canActivate: [AuthGuard], component: MyContentComponent},
  {path: 'favourites', canActivate: [RoleGuard], component: FavouritesComponent},
  {path: 'edit-dude', canActivate: [RoleGuard], component: EditProfileComponent},
  {path: 'edit-fitnessProvider', canActivate: [FitnessProviderRoleGuard], component: EditFitnessProviderProfileComponent},
  {path: 'fitnessProvider-profile', canActivate: [FitnessProviderRoleGuard], component: FitnessProviderProfileComponent},
  {path: 'follower', canActivate: [FitnessProviderRoleGuard], component: FollowerComponent},
  {path: 'fitnessProviders', canActivate: [RoleGuard], component: FitnessProvidersFollowedComponent},
  {path: 'myExercises', canActivate: [RoleGuard], component: OwnExercisesComponent},
  {path: 'myWorkouts', canActivate: [RoleGuard], component: OwnWorkoutsComponent},
  {path: 'myTrainingSchedules', canActivate: [RoleGuard], component: OwnTrainingScheduleComponent},
  {path: 'exercise', canActivate: [AuthGuard], component: ExerciseComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
