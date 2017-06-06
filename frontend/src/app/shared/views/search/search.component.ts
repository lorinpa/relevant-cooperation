import { Router } from '@angular/router';

import { MlTextfieldArea } from './../../components/ml/components/controls/textfield/mlTextfieldArea';
import { MlTable, MlTableTextCell } from './../../components/ml/components/table/mlTable';
import { MlChipButton } from './../../components/ml/components/chip/mlChipButton';
import { MlTitle } from './../../components/ml/components/title/mlTitle';
import { MlGrid, MlGridCell } from './../../components/ml/components/grid/mlGrid';
import { MlTooltip } from './../../components/ml/components/tooltip/mlTooltip';

import { Keyword } from './../../models/keyword';
import { KeywordServiceService } from './../../services/keyword-service.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MdlSnackbarService } from '@angular-mdl/core';
import { Form, FormGroup, FormControl, Validators} from '@angular/forms';
import { MdlDialogService } from '@angular-mdl/core';
import { Proposal } from "app/shared/models/proposal";
import { ProposalService } from "app/shared/services/proposal.service";


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  
})
export class SearchComponent implements OnInit {

  //@ViewChild('spinner1') spinner1;
  working:boolean;
  searchResults = [];

  searchBusResults = [];
  searchForm: FormGroup;
  proposalForm: FormGroup;

  proposalTitleTF: FormControl;
  proposalMessageTA: FormControl;
  visibility: FormControl;
  visibility_label = "Private";

  searchBusConcepetsForm: FormGroup;


  constructor(private keywordService: KeywordServiceService, 
      private proposalService: ProposalService,
      private dialogService: MdlDialogService,
      private router: Router, 
      private mdlSnackbarService: MdlSnackbarService) { }

  ngOnInit() {
    this.working = false;
    this.searchForm = new FormGroup({});
    this.searchBusConcepetsForm  = new FormGroup({});
    this.proposalTitleTF = new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(128)])
    this.proposalMessageTA = new FormControl('', [Validators.required, Validators.minLength(10)]);
    this.visibility = new FormControl(false);
    this.proposalForm = new FormGroup({proposalTitleTF: this.proposalTitleTF, 
      proposalMessageTA: this.proposalMessageTA, visibility:this.visibility});
    
    this.visibility.valueChanges.subscribe(value => {
      this.visibility_label = value === true ? "Private" : "Public";
    });
  }


  doSearch() {
     this.keywordService.searchByTerm().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => { 
         var results = JSON.parse(msg); 
         this.searchResults = [];
         this.searchResults = results; 
          if (this.searchResults.length === 0) {
            this.mdlSnackbarService.showSnackbar({message:'No Results Found: Search by Partner Skill/Service'});
         }  
        },
        err => { this.displayError();}
        );
   }

   doBusSearch() {
     this.keywordService.searchByBusConcepts().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => { 
         var results = JSON.parse(msg); 
         this.searchBusResults = [];
         this.searchBusResults = results;   
         if (this.searchBusResults.length === 0) {
            this.mdlSnackbarService.showSnackbar({message:'No Results Found: Search by Business Concept'});
         }
        },
        err => { this.displayError();}
        );
   }

    addProposal() {
     this.working = true;
     var title = this.proposalTitleTF.value;
     var message =  this.proposalMessageTA.value;
     var partners = this.createPartnerIdSet();
     var proposal = new Proposal(0, title);
     proposal.is_private = this.visibility.value;
     proposal.message = message;
     proposal.partners = partners;
     this.proposalService.addProposal(proposal).map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => { 
         var result = JSON.parse(msg); 
          this.closePopups();
          this.mdlSnackbarService.showSnackbar(
            {
              message:'Proposal Saved. Notifications sent to partners.',
              action: {
                    handler: () => {   
                       this.router.navigate(['/proposals']);
                    },
                    text: 'Got It.'
              }
            });
        
        },
        err => { this.displayError(); this.working = false;}
        );
   }

  displayError() {
     this.mdlSnackbarService.showSnackbar({
                  message: "An unexpected error occurred.",
                  action: {
                    handler: () => {   
                     
                    },
                    text: 'Got It.'
                  }
                });
  }


 closePopups() {

   var dlgs = this.dialogService['openDialogs'];
      if (dlgs && dlgs.length > 0) {
          var len = dlgs.length;
          var d = null;
          for (var index =0; index < len; index++) {
            d = dlgs[index];
            d.dialogRef.hide();
          }
      }
 }

 createPartnerIdSet() {
   var ids = [];
   var len = this.searchResults.length;
   var rec = null;
   
   for (var index=0; index < len; index++) {
        rec = this.searchResults[index];
        ids.push(rec['up_id']);
   }

   var len = this.searchBusResults.length;
   for (var index=0; index < len; index++) {
        rec = this.searchBusResults[index];
        ids.push(rec['up_id']);
   }

   var partnerIds = new Set(ids);
   return Array.from(partnerIds);   

 }

 toggleVisbilityLabel() {
        this.visibility_label = this.visibility.value === true ?  "Public" : "Private";
 }

 setDefaultVisibility() {
   this.visibility.setValue(true);
   this.visibility_label = "Private";
 }

}
