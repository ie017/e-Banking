import { Component, OnInit } from '@angular/core';
import {catchError, Observable, throwError} from "rxjs";
import {AccountModule} from "./account.module";
import {AccountService} from "./account.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  accountList! : Observable<Array<AccountModule>>;
  messageError! : string;
  constructor(public accountSevice : AccountService, private route : Router) { }

  ngOnInit(): void {
    this.getListAccounts();
  }
  getListAccounts(){
    this.accountList = this.accountSevice.getAccounts().pipe(catchError(err=>{
      this.messageError = err;
      return throwError(err);
    }));
  }

  goToOperations(id: string) {
    this.route.navigateByUrl("/operations/"+id);
  }

  doDeleteAccount(id: string) {
    this.accountSevice.deleteAccount(id).subscribe({
      next : (data)=>{
        alert("Your account has deleted");
        this.getListAccounts();
      }, error : err => {
        console.log(err);
      }
    });
  }
}
