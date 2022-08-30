import { Component, OnInit } from '@angular/core';
import {catchError, Observable, throwError} from "rxjs";
import {AccountModule} from "./account.module";
import {AccountService} from "./account.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {CustomerModule} from "../customer/customer.module";
import {CustomerService} from "../customer/customer.service";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  newAccountFormGroup! : FormGroup;
  searchFormGroup! : FormGroup;
  getCustomer! : Observable<CustomerModule>;
  customer! : CustomerModule;
  messageError! : string;
  accounts! : Observable<Array<AccountModule>>;
  customerId! : number;
  accountList! : Observable<Array<AccountModule>>;
  constructor(public accountService : AccountService, private route : Router,private fb : FormBuilder,
              public customerService : CustomerService) { }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      customerId : this.fb.control(null),
    });
  }
  goToOperations(id: string) {
    this.route.navigateByUrl("/operations/"+id);
  }

  doDeleteAccount(id: string) {
    this.accountService.deleteAccount(id).subscribe({
      next : (data)=>{
        alert("Your account has deleted");
        this.accountList = this.accountService.getAccounts(this.customerId).pipe(catchError(err=>{
          this.messageError = err;
          return throwError(err);
        }));
      }, error : err => {
        console.log(err);
      }
    });
  }

  doSearchAccount() {
    this.customerId = this.searchFormGroup.value.customerId;
    this.accountList = this.accountService.getAccounts(this.customerId).pipe(catchError(err=>{
      this.messageError = err;
      return throwError(err);
    }));
    this.searchFormGroup.reset();
  }
}
