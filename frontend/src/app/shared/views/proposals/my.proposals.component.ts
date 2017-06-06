import { Proposal } from './../../models/proposal';
import { ProposalService } from 'app/shared/services/proposal.service';
import { Form, FormGroup, FormControl, Validators} from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Subscription }   from 'rxjs/Subscription';
import { MdlDialogService} from '@angular-mdl/core';


@Component({
  selector: 'my-proposals',
  templateUrl: './my.proposals.component.html'
})
export class MyProposalsComponent implements OnInit {

  titleTF: FormControl;
  messageTF: FormControl;

  visibility: FormControl;
  visibility_label = "Private";
  proposalForm:FormGroup;

  selected_proposal= new Proposal(0, '');

  my_proposals = [];
  myPropalsSubcription: Subscription;
  constructor(private proposalService: ProposalService, private dialogService: MdlDialogService) {
      this.myPropalsSubcription = proposalService.my_proposals$.subscribe(
        proposals => {
          this.my_proposals = proposals;
        }
      );

   }

  ngOnInit() {
    this.getMyProposals();
    this.titleTF = new FormControl('',[Validators.required, Validators.minLength(10), Validators.maxLength(128)]);
    this.messageTF = new FormControl('', [Validators.required, Validators.minLength(10)]);
    this.visibility = new FormControl(false);
    this.proposalForm = new FormGroup({titleTF: this.titleTF, messageTF: this.messageTF, visibility: this.visibility});
    this.visibility.valueChanges.subscribe(value => {
      this.visibility_label = value === true ? "Private" : "Public";
    });
 }

  getMyProposals() {
     var responseData = {};
     var list: Proposal[];
     this.proposalService.getMyProposals().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => { 
         list = JSON.parse(msg);
         this.proposalService.publishMyProposals(list);
      },
       err => {
          //  if (err.status === 403) {this.routToLogin();}
        }
      );
   }

   updateProposal() {
     
     this.selected_proposal.title = this.titleTF.value;
     this.selected_proposal.message = this.messageTF.value;
     this.selected_proposal.is_private = this.visibility.value;
     
     this.proposalService.updateProposal(this.selected_proposal).map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => { 
         var updateObj = JSON.parse(msg);
         var len = this.my_proposals.length;
         var rec = null;
         for (var index=0; index < len; index++) {
            rec = this.my_proposals[index];
            if (rec.id === this.selected_proposal.id) {
              this.my_proposals[index]['title']=updateObj['title'];
              this.my_proposals[index]['message'] = updateObj['message'];
              break;
            }
         }
         this.proposalService.publishMyProposals(this.my_proposals);
         this.closePopups();
      },
       err => {
          //  if (err.status === 403) {this.routToLogin();}
        }
      );
   }

  selectProposalById(id: number)  {
      this.selected_proposal= this.my_proposals.find(function(rec){
        if (rec.id === id) return rec;
      });
      this.titleTF.setValue(this.selected_proposal.title);
      this.messageTF.setValue(this.selected_proposal.message);
      this.visibility.setValue(this.selected_proposal.is_private);
      this.visibility_label = this.selected_proposal.is_private === true ?  "Private" : "Public";
   }


   deleteProposal(id:number) {
  
     this.proposalService.deleteProposal(id).map(
       res => {
         if (res.ok) { return res['_body']; }
       },
     ).subscribe(
       msg => {
          this.my_proposals = this.my_proposals.filter(function (rec) {
             if (rec.id !== id) return rec;
           });
          this.proposalService.publishMyProposals(this.my_proposals);
        
        },
        err => {
           //  if (err.status === 403) {this.routToLogin();}
        }
     );
   }

   clearSelectedProposal() {
     this.selected_proposal = new Proposal(-1,'');
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

    toggleVisbilityLabel() {
        this.visibility_label = this.visibility_label === "Private" ?  "Public" : "Private";
       //this.visibility_label = this.visibility.value === true ?  "Private" : "Public";
    }


}
