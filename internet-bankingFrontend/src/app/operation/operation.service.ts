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
}
