import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {CustomerModule} from "./customer.module";
import {ValidationErrors} from "@angular/forms";
import {AccountService} from "../account/account.service";
import {AccountModule} from "../account/account.module";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http : HttpClient, private accountService : AccountService) { }

  public getCustomers() : Observable<Array<CustomerModule>>{
    return this.http.get<Array<CustomerModule>>("http://localhost:8080/customers");
  }
  public getCustomer(id : number) : Observable<CustomerModule>{
    return this.http.get<CustomerModule>("http://localhost:8080/customer/"+id);
  }
  public searchAboutCustomers(cle : string) : Observable<Array<CustomerModule>>{
    return this.http.get<Array<CustomerModule>>("http://localhost:8080/customers?keyword="+cle);
  }
  public insertNewCustomer(customer : CustomerModule) : Observable<CustomerModule>{
    return this.http.post<CustomerModule>("http://localhost:8080/savecustomer", customer);
  }

  public geterrorMessage(name: string, errors: ValidationErrors) {
    if (errors['required']){
      return name + ' is required';
    } else if(errors['minlength']){
      return name + ' should have at least '+errors['minlength']['requiredLength']+ ' Characters';
    } else if(errors['pattern']){
      return name + ' should respect the pattern '+errors['pattern']['pattern'];
    } else return "";
  }

  public removeCustomer(id: number) : Observable<CustomerModule>{
    return this.http.delete<CustomerModule>("http://localhost:8080/deletecustomer/"+id);
  }

  public updateCustomer(customer : CustomerModule) : Observable<CustomerModule> {
    return this.http.put<CustomerModule>("http://localhost:8080/updatecustomer/"+customer.id, customer);
  }

  searchAboutCustomerById(id: number) : Observable<CustomerModule>{
    return this.http.get<CustomerModule>("http://localhost:8080/customer/"+id);
  }

  saveSavingaccount(customer : CustomerModule, balance : number, interestRate : number) : Observable<AccountModule>{
    let data = {interestRate : interestRate, balance : balance, customerDto : customer}
    return this.http.post<AccountModule>("http://localhost:8080/savesavingaccount/", data);
  }

  saveCurrentaccount(customer : CustomerModule, balance : number, overDraft: number) : Observable<AccountModule> {
    let data = {overDraft : overDraft, balance : balance, customerDto : customer}
    return this.http.post<AccountModule>("http://localhost:8080/savecurrentaccount/", data);
  }
}
