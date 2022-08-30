import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {OperationModule} from "./operation.module";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OperationService {

  constructor(private http : HttpClient) { }

  getOperations(id : string) : Observable<Array<OperationModule>>{
    return this.http.get<Array<OperationModule>>("http://localhost:8080/getallaccountoperation/"+id);
  }

  saveOperation (accountId : string,amount : number, operation : string, description : string, destinationId : string) : Observable<OperationModule>{
    if (operation == 'debit'){
      let data = {id : accountId,amount : amount, description : description};
      return this.http.post<OperationModule>("http://localhost:8080/debit",data);
    } else if (operation == 'credit'){
      let data = {id : accountId,amount : amount, description : description};
      return this.http.post<OperationModule>("http://localhost:8080/credit",data);
    } else {
      let data = {id : accountId,amount : amount, description : description,destinationId : destinationId};
      return this.http.post<OperationModule>("http://localhost:8080/transfer",data);
    }
  }
}
