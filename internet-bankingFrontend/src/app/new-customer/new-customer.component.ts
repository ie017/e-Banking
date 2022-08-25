import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomerService} from "../customer/customer.service";
import {CustomerModule} from "../customer/customer.module";


@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrls: ['./new-customer.component.css']
})
export class NewCustomerComponent implements OnInit {
  newCustomerFormGroup! : FormGroup;
  addCustomer! : CustomerModule;
  constructor(private fb : FormBuilder, public customerService : CustomerService) { }

  ngOnInit(): void {
    this.newCustomerFormGroup = this.fb.group({
      name : this.fb.control(null,[Validators.required, Validators.minLength(4)]),
      email : this.fb.control(null,[Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    });
  }

  addNewCustomer() {
    this.addCustomer = this.newCustomerFormGroup.value;
    this.customerService.insertNewCustomer(this.addCustomer).subscribe({
      next : data=>{
        alert("Your object is saved");
        this.newCustomerFormGroup.reset();
      },
      error : err => {
        console.log(err);
      }
    })
  }
}
