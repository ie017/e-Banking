import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CustomerService} from "../customer/customer.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomerModule} from "../customer/customer.module";
import {catchError, Observable, throwError} from "rxjs";

@Component({
  selector: 'app-edit-customer',
  templateUrl: './edit-customer.component.html',
  styleUrls: ['./edit-customer.component.css']
})
export class EditCustomerComponent implements OnInit {
  customerId! : number;
  editCustomerFormGroup! : FormGroup;
  editCustomer! : Observable<CustomerModule>;
  messageError! : string;
  customer! : CustomerModule;
  constructor(private route : ActivatedRoute, public customerService : CustomerService, private fb : FormBuilder,
              private router : Router) {
    this.customerId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.getCustomer(this.customerId);
    this.editCustomer.subscribe({
      next : (data)=>{
        this.editCustomerFormGroup = this.fb.group({
          name: this.fb.control(data.name, [Validators.required, Validators.minLength(4)]),
          email: this.fb.control(data.email, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
        });
      }
    });
  }

  editCustomerOperation() {
    this.customer = this.editCustomerFormGroup.value;
    this.customer.id = this.customerId;
    this.customerService.updateCustomer(this.customer).subscribe({
      next : (data)=>{
        this.editCustomerFormGroup.reset();
        this.router.navigateByUrl("/customers");
        alert("Operation finished");
      }, error : err => {
        this.router.navigateByUrl("/customers");
        console.log(err);
      }
    })
  }
  getCustomer(id : number){
    this.editCustomer = this.customerService.getCustomer(id).pipe(catchError(err=>{
      this.messageError = err;
      return throwError(err);
    }));
  }
}
