import { MdlDialogService} from '@angular-mdl/core';
import { Proposal, PartnerProposal } from './../../models/proposal';
import { ProposalService } from './../../services/proposal.service';
import { Component, OnInit } from '@angular/core';
import { Subscription }   from 'rxjs/Subscription';


@Component({
  selector: 'partner-proposals',
  templateUrl: './partner.proposals.component.html'
})
export class PartnerProposalsComponent implements OnInit {

  selected_proposal= new PartnerProposal(0, '');
  partner_proposals = [];
  partnerPropalsSubcription: Subscription;

  
  constructor(private proposalService: ProposalService,  private dialogService: MdlDialogService) {
    this.partnerPropalsSubcription = proposalService.partner_proposals$.subscribe(
        proposals => {
          this.partner_proposals = proposals;
        }
      );
   }

  ngOnInit() {
     this.getPartnerProposals();
     
  }

  getPartnerProposals() {
     var responseData = {};
     var list: Proposal[];
     this.proposalService.getPartnerProposals().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => { 
         list = JSON.parse(msg);
         this.proposalService.publishPartnerProposals(list);
      },
       err => {
          //  if (err.status === 403) {this.routToLogin();}
        }
      );
   }

   selectProposalById(id: number)  {
      this.selected_proposal= this.partner_proposals.find(function(rec){
        if (rec.id === id) return rec;
      });
   }

   dismissPartnerProposal(id:number) {
     this.proposalService.dismissPartnerProposal(id).map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => { 
         this.partner_proposals = this.partner_proposals.filter(function(rec) {
           if (rec.id !== id) return rec;
         });
         this.proposalService.publishPartnerProposals(this.partner_proposals );
      },
       err => {
          //  if (err.status === 403) {this.routToLogin();}
        }
      );
   }

}
