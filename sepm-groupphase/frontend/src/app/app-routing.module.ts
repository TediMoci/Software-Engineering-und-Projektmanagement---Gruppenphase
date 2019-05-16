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

const routes: Routes = [

  {path: '', component: LoginComponent},
  {path: 'login-as-fitness-provider', component: LoginAsFitnessProviderComponent},
  {path: 'register-as-dude', component: RegisterAsDudeComponent},
  {path: 'register-as-fitness-provider', component: RegisterAsFitnessProviderComponent},
  {path: 'dude-profile', canActivate: [AuthGuard, RoleGuard], component: DudeProfileComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'bros', canActivate: [AuthGuard], component: BrosComponent},
  {path: 'create', canActivate: [AuthGuard], component: CreateComponent},
  {path: 'find', canActivate: [AuthGuard], component: FindComponent},
  {path: 'history', canActivate: [AuthGuard], component: HistoryComponent},
  {path: 'myContent', canActivate: [AuthGuard], component: MyContentComponent},
  {path: 'favourites', canActivate: [AuthGuard], component: FavouritesComponent},
  {path: 'edit', canActivate: [AuthGuard], component: EditProfileComponent},
  {path: 'fitnessProvider-profile', canActivate: [AuthGuard], component: FitnessProviderProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
