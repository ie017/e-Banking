import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {CustomerModule} from "./customer.module";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  public listSearched! : Array<CustomerModule>;

  constructor(private http : HttpClient) { }
  public getCustomers() : Observable<Array<CustomerModule>>{
    return this.http.get<Array<CustomerModule>>("http://localhost:8080/customers");
  }
  public searchAboutCustomers(cle : string) : Observable<Array<CustomerModule>>{
    return this.http.get<Array<CustomerModule>>("http://localhost:8080/customers?keyword="+cle);
  }
}
