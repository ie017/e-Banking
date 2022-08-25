import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomerComponent} from "./customer/customer.component";
import {AccountComponent} from "./account/account.component";
import {NewCustomerComponent} from "./new-customer/new-customer.component";
import {EditCustomerComponent} from "./edit-customer/edit-customer.component";

const routes: Routes = [
  {path : "customers", component : CustomerComponent},
  {path : "accounts", component : AccountComponent},
  {path : "addCustomer", component : NewCustomerComponent},
  {path : "editCustomer/:id", component : EditCustomerComponent}
]; /* Dans les routes on trouve toutes les routes qui on a*/


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
