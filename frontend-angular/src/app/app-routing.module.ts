import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MiniaturesComponent} from "./content/miniatures/miniatures.component";

const routes: Routes = [
  { path: '', component: MiniaturesComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
