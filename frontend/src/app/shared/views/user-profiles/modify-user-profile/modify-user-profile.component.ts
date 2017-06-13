
import { RowController } from './../../../components/pa/editable/row-controller';

//import { MlDialog } from './../../../components/ml/components/dialog/mlDialog';
import { MdlSnackbarService } from '@angular-mdl/core/components';
import { Sorter } from './../../../utils/sorter';
import { SortKeyword } from './../../../pipes/sort-keyword';
import { MlToggle } from './../../../components/ml/components/controls/toggle/mlToggle';
import { KeywordPipe } from './../../../pipes/keyword-pipe';
import { MlRipple } from './../../../components/ml/components/ripple/mlRipple';
import { MlTableTextCell } from './../../../components/ml/components/table/mlTable';
import { MlTab, MlTabPanel} from './../../../components/ml/components/tabs/mlTabs';
import { MlTextfieldLabel } from './../../../components/ml/components/controls/textfield/mlTextfieldLabel';
import { MlTextfieldExpand } from './../../../components/ml/components/controls/textfield/mlTextfieldExpand';
import { MlTextfield } from './../../../components/ml/components/controls/textfield/mlTextfield';
import { ChangeDetectionStrategy, Input } from '@angular/core';
import { Keyword, KeywordList } from './../../../models/keyword';
import { KeywordServiceService } from './../../../services/keyword-service.service';
import { UserProfile } from './../../../models/user-profile';
import { UserProfileService } from './../../../services/user-profile-service.service';
import { Component, OnInit } from '@angular/core';
import { Form, FormGroup, FormControl, Validators} from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { SortByDirective } from "app/shared/directives/sort-by.directive";
import { MdlDialogService } from '@angular-mdl/core';
import { ISubscription } from "rxjs/Subscription";
//import { unique_value } from "app/shared/validators/uniqueValue";
//import { DoCheck } from '@angular/core';


@Component({
  selector: 'modify-user-profile',
  templateUrl: './modify-user-profile.component.html',
  styleUrls: ['./modify-user-profile.component.css'],
  providers: [Sorter],
  changeDetection: ChangeDetectionStrategy.Default
})
export class ModifyUserProfileComponent implements OnInit {

  //@Input() keywords: KeywordList;
 // user_id: number;
  userProfile: UserProfile;
  //keywords: Keyword[];
  aliasTf:FormControl;
  passwordTf: FormControl;
  emailTf: FormControl;
  locationTf: FormControl;
  update_profile_form: FormGroup;
  filterProvidedSkills:FormControl;
  filterProvidedSkillsForm:FormGroup;
  filterBusConcepts:FormControl;
  filterBusConceptsForm:FormGroup;
  filterPartnerSkills:FormControl;
  filterPartnerSkillsForm:FormGroup;
  displayAddProvideSkillButton:boolean;
  displayAddBusConceptButton:boolean;
  displayAddPartnerSkillButton: boolean;
  rowController: RowController;
  sortDir: number = -1; // sort direction
  showProvided:boolean;

  kwlist;

  //private subscription: ISubscription;
  

  constructor(private userProfileService: UserProfileService, 
              private keywordService: KeywordServiceService,
              private profileService: UserProfileService,
              private route: ActivatedRoute, 
              private router: Router,
              private mdlSnackbarService: MdlSnackbarService,
              private dialogService: MdlDialogService) { 
                this.rowController = new RowController();
              }
  

  setUserProfile(up: UserProfile) {
    this.userProfile = up;
  }

  ngOnInit() {
    try {
      
      this.userProfile = new UserProfile(0, '');
     // this.keywords = new KeywordList();
     
      this.getProfileData();
      this.getRemoteKeywords();
      this.getRemoteProvidedServices();
      this.getRemoteBusinessConcepts();
      this.getRemotePartnerServices();
    
      //this.keywords = this.keywordService.getList();
      
      this.aliasTf = new FormControl(this.userProfile.name, [Validators.required, Validators.minLength(4), Validators.maxLength(25)]);
      this.passwordTf = new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(25)]);
      this.locationTf = new FormControl(this.userProfile.location, [Validators.required, Validators.minLength(0), Validators.maxLength(128)]);
      this.emailTf = new FormControl('', [Validators.required, Validators.pattern(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)]);
      this.update_profile_form = new FormGroup({
        aliasTf: this.aliasTf,
        passwordTf: this.passwordTf,
        locationTf: this.locationTf,
        emailTf: this.emailTf
      });

      this.filterProvidedSkills = new FormControl("",[Validators.minLength(3), Validators.maxLength(30)]);
      
      this.filterProvidedSkillsForm = new FormGroup({filterProvidedSkills:this.filterProvidedSkills});

      this.filterBusConcepts = new FormControl("");
      this.filterBusConceptsForm = new FormGroup({filterBusConcepts:this.filterBusConcepts});

      this.filterPartnerSkills = new FormControl("");
      this.filterPartnerSkillsForm = new FormGroup({filterPartnerSkills:this.filterPartnerSkills});

      this.displayAddProvideSkillButton = false;
      this.displayAddBusConceptButton = false;
      this.displayAddPartnerSkillButton = false;
     
    } catch (Error) {
      console.log("got a nasty error");
    }
  }
 
  toggleSortDirection() {this.sortDir = this.sortDir === 1 ? -1 : 1;}
 
  providedSkillExist() {
    var search_value = this.filterProvidedSkills.value.trim().length;
    if (search_value > 0) {
      var result: Keyword;
      result = this.userProfile.keywords.find(rec => rec.keyword===search_value);
      if (result) {
        this.displayAddProvideSkillButton = false;
      } else {
        this.displayAddProvideSkillButton = true;
      }
    } else {
      this.displayAddProvideSkillButton = false;
    }
  }

  providedBusConceptsExist() {
    var search_value = this.filterBusConcepts.value.trim().length;
    if (search_value > 0) {
      var result: Keyword;
      result = this.userProfile.keywords.find(rec => rec.keyword===search_value);
      if (result) {
        this.displayAddBusConceptButton = false;
      } else {
        this.displayAddBusConceptButton = true;
      }
    } else {
      this.displayAddBusConceptButton = false;
    }
  }

  partnerSkillExist() {
      var search_value = this.filterPartnerSkills.value.trim().length;
      if (search_value > 0) {
        var result: Keyword;
        result = this.userProfile.keywords.find(rec => rec.keyword===search_value);
        if (result) {
          this.displayAddPartnerSkillButton = false;
        } else {
          this.displayAddPartnerSkillButton = true;
        }
      } else {
        this.displayAddPartnerSkillButton = false;
      }
  }

  addNewProvided() {
    var kw = this.filterProvidedSkills.value.trim().toLowerCase();
    var newProvidedService : Keyword;
    this.keywordService.addProvidedService(kw).map(
       res => {
         if (res.ok) { return res['_body'];}
       },
       err => { },
     ).subscribe(
       msg => {
         newProvidedService  =JSON.parse(msg);
         this.userProfile.addProvidedService(newProvidedService);
        // this.keywords.addKeyword(newProvidedService);
        this.userProfile.addKeyword(newProvidedService);
        this.rebuildKeywordList();
        },
        err => {
            if (err.status === 403) { this.routToLogin();}
        }
     );
    this.displayAddProvideSkillButton = false;
    this.filterProvidedSkills.setValue('');  
  }

  addProvidedServiceRelationship(kw:Keyword) {
    if (!this.userProfile.provided_services.find(rec => rec.id === kw.id)) {
        this.keywordService.addProvidedServiceRelationship(kw.id).map(
          res => {
            if (res.ok) { return res['_body'];}
          },
          err => { }
        ).subscribe(
          msg => {this.userProfile.addProvidedService(kw);},
          err => {
            if (err.status === 403) {         
              this.routToLogin();}       
          }
        );
    }
  }
  removeProvidedService(id:number) {
    this.keywordService.deleteProvidedService(id).map(
       res => {
         if (res.ok) { return res['_body'];}
       },
       err => {}
    ).subscribe(
       msg => {
         var remoteId  =JSON.parse(msg);
         this.userProfile.removeProvidedService(id);
        },
        err => {
            if (err.status === 403) { this.routToLogin();}
        }
     );
  }
  addNewBusConcept() {
    var kw = this.filterBusConcepts.value.trim().toLowerCase();
    var newBusinessConcept : Keyword;
    this.keywordService.addBusinessConcept(kw).map(
       res => {
         if (res.ok) { return res['_body'];}
       },
       err => { }
     ).subscribe(
       msg => {
         newBusinessConcept  =JSON.parse(msg);
         this.userProfile.addBusinessConcept(newBusinessConcept);
         //this.keywords.addKeyword(newBusinessConcept);
         this.userProfile.addKeyword(newBusinessConcept);
         this.rebuildKeywordList();
        },
        err => {
            if (err.status === 403) { this.routToLogin();}
        }
     );
     this.displayAddBusConceptButton = false;
     this.filterBusConcepts.setValue('');  
  }
  addBusConceptRelationship(kw:Keyword) {
    if (!this.userProfile.business_concepts.find(rec => rec.id === kw.id)) {
        this.keywordService.addUserBusinessConcept(kw.id).map(
          res => {
            if (res.ok) { return res['_body'];}
          },
          err => { }
        ).subscribe(
          msg => {this.userProfile.addBusinessConcept(kw);},
          err => {
            if (err.status === 403) {this.routToLogin();}
          }
        );
    }
  }
  removeBusinessConcept(id:number) {
    this.keywordService.deleteUserBusinessConcept(id).map(
       res => {
         if (res.ok) { return res['_body'];}
       },
       err => {}
    ).subscribe(
       msg => {
         var remoteId  =JSON.parse(msg);
         this.userProfile.removeBusinessConcept(id);
        },
        err => {
            if (err.status === 403) { this.routToLogin();}
        }
     );
  }
   addNewPartnerSkill() {
    var kw = this.filterPartnerSkills.value.trim().toLowerCase();
    var newPartnerSkill: Keyword;
    //this.keywords.addKeyword(kw);
    this.keywordService.addPartnerService(kw).map(
       res => {
         if (res.ok) { return res['_body'];}
       },
       err => { }
     ).subscribe(
       msg => {
         newPartnerSkill  =JSON.parse(msg);
         this.userProfile.addPartnerService(newPartnerSkill);
         this.userProfile.addKeyword(newPartnerSkill);
         this.rebuildKeywordList();
         //this.keywords.addKeyword(newPartnerSkill);        
        },
        err => {
            if (err.status === 403) { this.routToLogin();}
        }
     );
    
    this.displayAddPartnerSkillButton = false;
    this.filterPartnerSkills.setValue('');  
  }
  addPartnerServiceRelationship(kw:Keyword) {
    if (!this.userProfile.partner_services.find(rec => rec.id === kw.id)) {
        this.keywordService.addUserPartnerService(kw.id).map(
          res => {
            if (res.ok) { return res['_body'];}
          },
          err => { }
        ).subscribe(
          msg => {this.userProfile.addPartnerService(kw);},
          err => {
            if (err.status === 403) {this.routToLogin();}
          }
        );
    }
  }
  
  removePartnerService(id:number) {
    this.keywordService.deleteUserPartnerService(id).map(
       res => {
         if (res.ok) { return res['_body'];}
       },
       err => {}
    ).subscribe(
       msg => {
         var remoteId  =JSON.parse(msg);
         this.userProfile.removePartnerService(id);
        },
        err => {
            if (err.status === 403) { this.routToLogin();}
        }
     );
  }
   getRemoteKeywords() {
      this.keywordService.getKeywords().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
       err => { console.log("getRemoteKeywords captured error: "+ err); }
     ).subscribe(
       msg => {
         this.userProfile.keywords = JSON.parse(msg);
         this.kwlist = JSON.parse(msg); 
        },
       err => {
         if (err.status === 403) {this.routToLogin();}
        }
       );
   }
   getRemoteProvidedServices() {
     this.keywordService.getUserProvidedServices().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => {this.userProfile.provided_services = JSON.parse(msg);},
       err => {
            if (err.status === 403) {this.routToLogin();}
        }
      );
   }
   getRemoteBusinessConcepts() {
     this.keywordService.getUserBusinessConcepts().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => {this.userProfile.business_concepts = JSON.parse(msg);},
       err => {
            if (err.status === 403) {this.routToLogin();}
        }
      );
   }
  getRemotePartnerServices() {
     this.keywordService.getUserPartnerServices().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => {this.userProfile.partner_services = JSON.parse(msg);},
       err => {
            if (err.status === 403) {this.routToLogin();}
        }
      );
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
             this.userProfile.name = responseData['name'];
             this.aliasTf.setValue(responseData['name']);
             up = new UserProfile(-1, responseData['name']);
         }
         if (responseData['location']) {
           this.userProfile.location = responseData['location'];
           this.locationTf.setValue(responseData['location']);
           up.location = responseData['location'];
        }
        if (responseData['email']) {
           this.userProfile.email = responseData['email'];
           this.emailTf.setValue(responseData['email']);
           up.email = responseData['email'];
        }
        if (up) this.profileService.publishUserProfile(up);
      },
       err => {
            if (err.status === 403) {this.routToLogin();}
        }
      );
   }

   cancelUpdateUserName() {
      this.aliasTf.clearValidators();
      this.aliasTf.setValue(this.userProfile.name);
      this.rowController.resetActive();
   }

   remoteUpdateLocation() {
     var location = this.locationTf.value;
     this.userProfileService.updateLocation(location).map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => {
         var jsonObj = JSON.parse(msg);
         this.userProfile.location = jsonObj['location'];
         this.mdlSnackbarService.showSnackbar({message:'Location Updated'});
         this.rowController.resetActive();
      },
       err => {
            if (err.status === 403) {this.routToLogin();}
        }
      );
   }

   remoteUpdatePassword() {
     var passwd = this.passwordTf.value;
     this.userProfileService.updatePassword(passwd).map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => {
         var jsonObj = JSON.parse(msg);
         this.mdlSnackbarService.showSnackbar({message:'Password Updated'});
         this.rowController.resetActive();
      },
       err => {
            if (err.status === 403) {this.routToLogin();}
             if (err.status === 406) { 
               this.mdlSnackbarService.showSnackbar({
                  message: "Unable to change password. Please try again.",
                  action: {
                    handler: () => {   
                      this.passwordTf.clearValidators();
                    },
                    text: 'Got It.'
                  }
                });
             }
        }
      );
   }

   remoteUpdateUserName() {
     var name = this.aliasTf.value;
     this.userProfileService.updateName(name).map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => {
         var jsonObj = JSON.parse(msg);
         this.userProfile.name = jsonObj['name'];
         this.userProfileService.publishUserProfile(this.userProfile);
         this.mdlSnackbarService.showSnackbar({message:'UserName Updated'});
         this.rowController.resetActive();
      },
       err => {
            if (err.status === 403) {this.routToLogin();}
            if (err.status === 406) {
              var errBody = JSON.parse(err['_body']);
              if (errBody && errBody['error'] && errBody['error'] === UserProfileService.NOT_UNIQUE) {
                var errorMessage = name + ' is not available. Please try another user name.';
                this.mdlSnackbarService.showSnackbar({
                  message: errorMessage,
                  action: {
                    handler: () => {
                      // this.mdlSnackbarService.showToast('You hit the ok Button');
                      this.aliasTf.clearValidators();
                      this.aliasTf.setValue(this.userProfile.name);
                    },
                    text: 'Got It.'
                  }
                });
              }
            }
        }
      );
       
   }

   remoteUpdateEmail() {
     var email = this.emailTf.value;
     this.userProfileService.updateEmail(email).map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => {
         var jsonObj = JSON.parse(msg);
         this.userProfile.email = jsonObj['email'];
         this.userProfileService.updateToken(jsonObj['token']);
         this.mdlSnackbarService.showSnackbar({message:'Email Address Updated'});
         this.rowController.resetActive();
      },
       err => {
            if (err.status === 403) {this.routToLogin();}
            if (err.status === 406) {
              var errBody = JSON.parse(err['_body']);
              if (errBody && errBody['error'] && errBody['error'] === UserProfileService.NOT_UNIQUE) {
                var errorMessage = email + ' is not available. Please try another email address.';
                this.mdlSnackbarService.showSnackbar({
                  message: errorMessage,
                  action: {
                    handler: () => {
                      this.emailTf.clearValidators();
                      this.emailTf.setValue(this.userProfile.email);
                    },
                    text: 'Got It.'
                  }
                });
              }
            }
            if (err.status !== 403 && err.status !== 406) {
                this.mdlSnackbarService.showSnackbar({
                  message: "Unable to update Email Address. Please try again.",
                  action: {
                    handler: () => {
                     
                    },
                    text: 'Got It.'
                  }
                });
            }
        }
      );
       
   }

   routToLogin() {
      this.mdlSnackbarService.showSnackbar({message:'Please Login'});
      // close an open pop-up dialogs
      var dlgs = this.dialogService['openDialogs'];
      if (dlgs && dlgs.length > 0) {
          var len = dlgs.length;
          var d = null;
          for (var index =0; index < len; index++) {
            d = dlgs[index];
            d.dialogRef.hide();
          }
      }
              
      this.router.navigate(['login']);
   }

   rebuildKeywordList() {
     this.kwlist = [];
     var len = this.userProfile.keywords.length;
     var tmp = [];
     var kw = null;
     for (var index = 0; index < len; index++) {
       
       kw = this.userProfile.keywords[index];
       tmp.push(kw);
     }
     this.kwlist = tmp;
   }

  
}
