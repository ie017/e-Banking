import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {catchError, Observable, throwError} from "rxjs";
import {OperationModule} from "./operation.module";
import {OperationService} from "./operation.service";

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.css']
})
export class OperationComponent implements OnInit {
  operationId! : string;
  operationsList! : Observable<Array<OperationModule>>;
  messageError! : string;

  constructor(private activatedRoute : ActivatedRoute, public operationService : OperationService) {
    this.operationId = this.activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.doGetAllOperations(this.operationId);
  }
  doGetAllOperations(id : string){
    this.operationsList = this.operationService.getOperations(id).pipe(catchError(err=>{
      this.messageError = err;
      return throwError(err);
    }));
  }
}
