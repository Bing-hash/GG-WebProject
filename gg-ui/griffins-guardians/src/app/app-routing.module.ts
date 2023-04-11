import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BoilermakerComponent } from './boilermaker/boilermaker.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/runners'},
  { path: 'runners', component: BoilermakerComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
