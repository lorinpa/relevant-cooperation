import { UserProfile } from './../../../models/user-profile';
import { UserProfileService } from './../../../services/user-profile-service.service';

import { User } from './../../../models/user';
import { UserService } from './../../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params, UrlSegment } from '@angular/router';
import { Form, FormGroup, FormControl, Validators} from '@angular/forms';
import {} from '@angular-mdl';
import { MdlSnackbarService } from "@angular-mdl/core/components";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/switchMap';


@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  passwordTextField = new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(25)]);
  emailField = new FormControl('', [Validators.required, Validators.pattern(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)]);
  loginForm:FormGroup;
 

  constructor(private userService: UserService, 
              private router: Router, 
              private route: ActivatedRoute,
              private mdlSnackbarService: MdlSnackbarService ,
              private profileService: UserProfileService
              
              ) { }

  onSubmit() {
    var credentials = this.loginForm.value;
    if (credentials) {
      var email = credentials.emailField;
      var password = credentials.passwordTextField;
      var user = new User(0,email,password);
      try {
      this.userService.login(user).subscribe(
        (result) => {
          if (result) {
            this.getProfileData();
            this.mdlSnackbarService.showSnackbar( {message:'Welcome: Log in Success.'});
            this.router.navigate(['']);}
        },
        (err) => {
            this.mdlSnackbarService.showSnackbar(
              {message:'Login Attempt Failed'});
        });
      } catch(Error) {
        console.log("got a login error");
      }
    }
  }

  ngOnInit() {
    var logout = false;
    this.route.url.subscribe((segments: UrlSegment[]) => logout = segments[0].path === "login" ? false : true);
    if (logout) { 
      this.userService.logout().subscribe(msg => {
        this.mdlSnackbarService.showSnackbar({message:'You are now Logged Out.'});
        this.router.navigate(['']);
        this.profileService.publishUserProfile(new UserProfile(-1,''));
      }, err => {
        this.mdlSnackbarService.showSnackbar({message:'Unable to Log You Out. Try Again.'});
      }); 
    }
        this.loginForm = new FormGroup({
          passwordTextField: this.passwordTextField,
          emailField:this.emailField
        });

    if (this.route.url['_value'][0].parameters['email']) {
        this.emailField.setValue(this.route.url['_value'][0].parameters['email']);
    }

    if (this.route.url['_value'][0].parameters['passwd']) {
        this.passwordTextField.setValue(this.route.url['_value'][0].parameters['passwd']);
    }
   
  }

  getProfileData() {
     var responseData = {};
     this.profileService.getProfileData().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => { 
         responseData = JSON.parse(msg);
         var up: UserProfile;
         if (responseData['name']) {
             up = new UserProfile(-1, responseData['name']);
             //this.profileService.currentUserProfile.name = responseData['name'];
             //this.profileService.publishUserName(responseData['name']);
         }
         if (responseData['location']) {
           up.location = responseData['location'];
          // this.profileService.currentUserProfile.location= responseData['location'];
        }
        if (responseData['email']) {
          // this.profileService.currentUserProfile.email = responseData['email'];
          up.email = responseData['email'];

        }
         if (up) this.profileService.publishUserProfile(up);
      },
       err => {
          //  if (err.status === 403) {this.routToLogin();}
        }
      );
   }

}
