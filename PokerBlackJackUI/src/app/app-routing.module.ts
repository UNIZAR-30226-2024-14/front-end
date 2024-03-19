import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { BlackjackComponent } from './components/blackjack/blackjack.component';
import { PokerComponent } from './components/poker/poker.component';

const routes: Routes = [
  {
    path:'',
    component:HomeComponent,
  },
  {
    path:'signup',
    component:SignupComponent,
  },
  {
    path:'login',
    component:LoginComponent,
  },
  {
    path:'blackjack',
    component:BlackjackComponent,
  },
  {
    path:'poker',
    component:PokerComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
