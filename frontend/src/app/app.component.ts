import { MlSpinner } from './shared/components/ml/components/spinner/mlSpinner';
import { UserProfileService } from './shared/services/user-profile-service.service';
import { UserProfile } from './shared/models/user-profile';
import { UserService } from './shared/services/user.service';
import { MlRadio } from './shared/components/ml/components/controls/radio/mlRadio';
import { MlContentLoader } from './shared/components/ml/components/loader/mlContentLoader';
import { MlTitle } from './shared/components/ml/components/title/mlTitle';
import { MlTab } from './shared/components/ml/components/tabs/mlTabs';
import { MlIcon } from './shared/components/ml/components/icon/mlIcon';
import { MlLayout, MlContent, MlHeader, MlHeaderTab,
  MlHeaderRow, MlSpacer, MlNav, MlHeaderTabs, MlDrawer, MlNavItem
} from './shared/components/ml/components/layout/mlLayout';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MlSelectfield, MlSelectfieldItem } from './shared/components/ml/components/controls/selectfield/mlSelectfield';
import { MlSwitch } from './shared/components/ml/components/controls/switch/mlSwitch';
import { MlTable } from './shared/components/ml/components/table/mlTable';
import { MlMenu, MlMenuItem } from './shared/components/ml/components/menu/mlMenu';

import { Component, OnInit } from '@angular/core';
import { MlButton } from './shared/components/ml/components/controls/button/mlButton';
import { MlRipple } from './shared/components/ml/components/ripple/mlRipple';
import { MlValidationError } from './shared/components/ml/components/controls/error/mlValidationError';
import { Subscription }   from 'rxjs/Subscription';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  userProfile= new UserProfile(-1,'');



  userProfileSubscription: Subscription;
  constructor(private userService: UserService, public profileService: UserProfileService ) 
  {
      this.userProfileSubscription = profileService.up$.subscribe(
        up => {
          this.userProfile = up;
        }
      )

  }

  ngOnInit() {
      if (this.isLoggedIn()) this.getProfileData();
  }

   ngOnDestroy() {
    this.userProfileSubscription.unsubscribe();
  }

  isLoggedIn() {
    return this.userService.isLoggedIn();
  }
  
    isLoading = false;

  onLoading($event){
    this.isLoading = $event;
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
         }
         if (responseData['location']) {
           up.location = responseData['location'];
        }
        if (responseData['email']) {
           up.email= responseData['email'];
        }
        if (up) this.profileService.publishUserProfile(up);
      },
       err => {
          //  if (err.status === 403) {this.routToLogin();}
        }
      );
   }
}
