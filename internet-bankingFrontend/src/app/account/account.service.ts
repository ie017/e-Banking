import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountModule} from "./account.module";


@Injectable({
  providedIn: 'root'
})
export class AccountService {
  constructor(private http : HttpClient) { }

  getAccounts(id : number): Observable<Array<AccountModule>>{
    return this.http.get<Array<AccountModule>>("http://localhost:8080/getbankAccountsOfUser/"+id);
  }

  deleteAccount(id: string) : Observable<AccountModule>{
    return this.http.delete<AccountModule>("http://localhost:8080/deletebankaccount/"+id);
  }
}
