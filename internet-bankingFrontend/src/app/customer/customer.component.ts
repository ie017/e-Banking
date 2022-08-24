import { Component, OnInit } from '@angular/core';
import {CustomerService} from "./customer.service";
import {HttpClient} from "@angular/common/http";
import {CustomerModule} from "./customer.module";
import {catchError, Observable, throwError} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {
  listCustomer! : Observable<Array<CustomerModule>>;
  messageError! : String;
  searchFormGroup! : FormGroup;
  constructor(private serviceCustomer : CustomerService, private fb : FormBuilder) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword : this.fb.control(""),
    });
    this.doSearchCustomers();
  }
  getAllCustomers(){
    this.listCustomer = this.serviceCustomer.getCustomers().pipe(catchError(err=>{
      this.messageError = err;
      return throwError(err);
    }));
  }

  doSearchCustomers() {
    let cle = this.searchFormGroup.value.keyword;
    this.listCustomer =  this.serviceCustomer.searchAboutCustomers(cle).pipe(catchError(err=>{
      this.messageError = err;
      return throwError(err);
    }));
  }
}
