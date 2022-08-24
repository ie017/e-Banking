import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomerComponent} from "./customer/customer.component";
import {AccountComponent} from "./account/account.component";

const routes: Routes = [
  {path : "customers", component : CustomerComponent},
  {path : "accounts", component : AccountComponent}
]; /* Dans les routes on trouve toutes les routes qui on a*/


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
