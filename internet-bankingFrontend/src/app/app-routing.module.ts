import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomerComponent} from "./customer/customer.component";
import {AccountComponent} from "./account/account.component";
import {NewCustomerComponent} from "./new-customer/new-customer.component";
import {EditCustomerComponent} from "./edit-customer/edit-customer.component";
import {OperationComponent} from "./operation/operation.component";
import {NewAccountComponent} from "./new-account/new-account.component";

const routes: Routes = [
  {path : "customers", component : CustomerComponent},
  {path : "accounts", component : AccountComponent},
  {path : "addCustomer", component : NewCustomerComponent},
  {path : "editCustomer/:id", component : EditCustomerComponent},
  {path : "operations/:id", component : OperationComponent},
  {path : "addAccount", component : NewAccountComponent}
]; /* Dans les routes on trouve toutes les routes qui on a*/


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
