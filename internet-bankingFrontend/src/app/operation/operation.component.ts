import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {catchError, Observable, throwError} from "rxjs";
import {OperationModule} from "./operation.module";
import {OperationService} from "./operation.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.css']
})
export class OperationComponent implements OnInit {
  accountId! : string;
  operationsList! : Observable<Array<OperationModule>>;
  messageError! : string;
  operationFormGroup! : FormGroup;
  selectedOperation! : string
  constructor(private activatedRoute : ActivatedRoute, public operationService : OperationService,
              private fb : FormBuilder) {
    this.accountId = this.activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.operationFormGroup = this.fb.group({
      operationType : this.fb.control(null),
      destination : this.fb.control(""),
      amount : this.fb.control(null),
      description : this.fb.control(""),
    });
    this.doGetAllOperations(this.accountId);
  }

  doGetAllOperations(id : string){
    this.operationsList = this.operationService.getOperations(id).pipe(catchError(err=>{
      this.messageError = err;
      return throwError(err);
    }));
  }

  doOperation() {
    this.selectedOperation = this.operationFormGroup.value.operationType;
    if (this.selectedOperation == 'debit'){
      this.operationService.saveOperation(this.accountId,Number(this.operationFormGroup.value.amount),this.operationFormGroup.value.operationType
        ,this.operationFormGroup.value.description,"").subscribe({
        next : (data)=>{
          alert("Done");
          this.operationFormGroup.reset();
        },
        error : err => {
          console.log(err);
        }
      });
    } else if (this.selectedOperation == 'credit'){
      this.operationService.saveOperation(this.accountId,Number(this.operationFormGroup.value.amount),this.operationFormGroup.value.operationType
        ,this.operationFormGroup.value.description,"").subscribe({
        next: (data) => {
          alert("Done");
          this.operationFormGroup.reset();
        },
        error: err => {
          console.log(err);
        }
      });
    } else {
      this.operationService.saveOperation(this.accountId,Number(this.operationFormGroup.value.amount),this.operationFormGroup.value.operationType
        ,this.operationFormGroup.value.description,`${this.operationFormGroup.value.destination}`).subscribe({
        next : (data)=>{
          alert("Done");
          this.operationFormGroup.reset();
        },
        error : err => {
          console.log(err);
        }
      });
    }
  }
}
