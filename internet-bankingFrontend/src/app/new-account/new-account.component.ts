import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountService} from "../account/account.service";
import {CustomerComponent} from "../customer/customer.component";
import {catchError, Observable, throwError} from "rxjs";
import {CustomerModule} from "../customer/customer.module";
import {CustomerService} from "../customer/customer.service";
import {AccountModule} from "../account/account.module";

@Component({
  selector: 'app-new-account',
  templateUrl: './new-account.component.html',
  styleUrls: ['./new-account.component.css']
})
export class NewAccountComponent implements OnInit {
  newAccountFormGroup! : FormGroup;
  searchFormGroup! : FormGroup;
  getCustomer! : Observable<CustomerModule>;
  customer! : CustomerModule;
  messageError! : string;
  accounts! : Observable<Array<AccountModule>>;
  constructor(private fb : FormBuilder, public accountService : AccountService,
              public customerService : CustomerService) { }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      customerId : this.fb.control(null),
    });

  }
  doSearchCustomersById() {
    let id = this.searchFormGroup.value.customerId;
    this.getCustomer =  this.customerService.searchAboutCustomerById(id).pipe(catchError(err=>{
      this.messageError = err;
      alert(this.messageError);
      return throwError(err);
    }));
    this.setValues();
    this.accountService.CustomerId = this.customer.id;
    this.accounts = this.accountService.getAccounts().pipe(catchError(err=>{
      this.messageError = err;
      return throwError(err);
    }));
  }
  setValues(){
    this.getCustomer.subscribe({
      next : (data)=>{
        this.customer = data;
      }, error : (err)=>{
        console.log(err);
      }
    })
  }


}
