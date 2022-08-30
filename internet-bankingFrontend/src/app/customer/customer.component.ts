import { Component, OnInit } from '@angular/core';
import {CustomerService} from "./customer.service";
import {HttpClient} from "@angular/common/http";
import {CustomerModule} from "./customer.module";
import {catchError, Observable, throwError} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {
  listCustomer! : Observable<Array<CustomerModule>>;
  messageError! : String;
  searchFormGroup! : FormGroup;
  constructor(private serviceCustomer : CustomerService, private fb : FormBuilder, private router : Router) {}

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

  doRemoveCustomer(id: number) {
    this.serviceCustomer.removeCustomer(id).subscribe({
      next : (data)=>{
        alert("Your customer has deleted");
        this.doSearchCustomers();
      }, error : err => {
        console.log(err);
      }
    });
  }

  doUpdateCustomer(customer : CustomerModule) {
    this.router.navigateByUrl("/editCustomer/"+customer.id);
  }

}
