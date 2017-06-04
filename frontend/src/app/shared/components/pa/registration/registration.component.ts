import { MlSpinner } from './../../ml/components/spinner/mlSpinner';
import { UserService } from './../../../services/user.service';
import { Component, OnInit, ViewChild, AfterContentInit } from '@angular/core';
import { Form, FormGroup, FormControl, Validators} from '@angular/forms';
import {} from '@angular-mdl';
import { MdlSnackbarService } from "@angular-mdl/core/components";
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { User } from "app/shared/models/user";



@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit, AfterContentInit {
  @ViewChild('spinner1') spinner1;
  working = false;
  registerForm: FormGroup;
   emailField = new FormControl('', [Validators.required, Validators.pattern(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)]);


  constructor(private mdlSnackbarService: MdlSnackbarService,
   private router: Router,
   private userService: UserService) { }

  ngOnInit() {
      
      this.registerForm = new FormGroup({emailField: this.emailField});
      
  }

   ngAfterContentInit() {
     //if (this.spinner1) this.spinner1.stop();
   }

  register() {
    var email = this.emailField.value;
    this.working = true;
    var userRequest = new User(0, email, '');
    this.spinner1.start();
    this.userService.registerAccount(userRequest).subscribe(
        (result) => {
            this.spinner1.stop();
            this.notifyMessageSent();

           
        },
        (err) => {
            this.mdlSnackbarService.showSnackbar(
              {message:'Unable to complete request.'});
        });
  }

  notifyMessageSent() {
    this.mdlSnackbarService.showSnackbar({
      message: "A message has been sent to your email address.",
      action: {
        handler: () => {
           this.emailField.setValue('');
           this.emailField.clearValidators();
           this.working = false;
           this.router.navigate(['/login']);
        },
        text: 'Close.'
      }
    });
  }

}
