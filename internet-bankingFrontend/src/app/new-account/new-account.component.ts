import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CustomerModule} from "../customer/customer.module";
import {CustomerService} from "../customer/customer.service";
import {Observable} from "rxjs";
import {AccountModule} from "../account/account.module";

@Component({
  selector: 'app-new-account',
  templateUrl: './new-account.component.html',
  styleUrls: ['./new-account.component.css']
})
export class NewAccountComponent implements OnInit {
  newAccountFormGroup! : FormGroup;
  customer! : CustomerModule;

  constructor(private fb : FormBuilder, private customerService : CustomerService) {
    this.newAccountFormGroup = this.fb.group({
      id : this.fb.control(null),
      accountType : this.fb.control(""),
      balance : this.fb.control(null),
      interestRate : this.fb.control(null),
      overDraft : this.fb.control(null)

    })
  }

  ngOnInit(): void {
  }
  getCustomer(){
    let id = this.newAccountFormGroup.value.id;
    this.customerService.getCustomer(id).subscribe({
      next : (data)=>{
        this.customer = data;
      },
      error : (err)=>{
        alert("your id of customer does not exist");
        this.newAccountFormGroup.reset();
      }
    });

  }
  doSaveAccount() {
    if (this.newAccountFormGroup.value.accountType == 'savingaccount'){
      this.customerService.saveSavingaccount(this.customer, this.newAccountFormGroup.value.balance,
      this.newAccountFormGroup.value.interestRate).subscribe({
        next : (data)=>{
          alert("Done");
          this.newAccountFormGroup.reset();
        },
        error : err => {
          console.log(err);
        }
      });
    }else if (this.newAccountFormGroup.value.accountType == 'currentaccount'){
      this.customerService.saveCurrentaccount(this.customer, this.newAccountFormGroup.value.balance,
        this.newAccountFormGroup.value.overDraft).subscribe({
        next : (data)=>{
          alert("Done");
          this.newAccountFormGroup.reset();
        },
        error : err => {
          console.log(err);
        }
      });
    }
  }
}
