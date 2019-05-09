import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {RegisterAsDudeComponent} from './components/register-as-dude/register-as-dude.component';
import {RegisterAsFitnessProviderComponent} from './components/register-as-fitness-provider/register-as-fitness-provider.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {DudeProfileComponent} from './components/dude-profile/dude-profile.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register-as-dude', component: RegisterAsDudeComponent},
  {path: 'register-as-fitness-provider', component: RegisterAsFitnessProviderComponent},
  {path: 'dude-profile', component: DudeProfileComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
