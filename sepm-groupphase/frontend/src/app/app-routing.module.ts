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

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'register-as-dude', component: RegisterAsDudeComponent},
  {path: 'register-as-fitness-provider', component: RegisterAsFitnessProviderComponent},
  {path: 'dude-profile', component: DudeProfileComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'bros', component: BrosComponent},
  {path: 'create', component: CreateComponent},
  {path: 'find', component: FindComponent},
  {path: 'history', component: HistoryComponent},
  {path: 'myContent', component: MyContentComponent},
  {path: 'favourites', component: FavouritesComponent},
  {path: 'edit', component: EditProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
