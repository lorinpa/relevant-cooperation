import { Component, OnInit } from '@angular/core';
import { Subscription }   from 'rxjs/Subscription';
import { ProposalService } from './../../services/proposal.service';
import { MdlDialogService} from '@angular-mdl/core';
import { Proposal, PartnerProposal } from './../../models/proposal';
import { MdlSnackbarService } from "@angular-mdl/core/components";

@Component({
  selector: 'app-public-proposals',
  templateUrl: './public-proposals.component.html'
})
export class PublicProposalsComponent implements OnInit {

  selected_proposal= new PartnerProposal(0, '');
  public_proposals = [];
  publicPropalsSubcription: Subscription;

  constructor(private proposalService: ProposalService, 
   private mdlSnackbarService: MdlSnackbarService ,
   private dialogService: MdlDialogService) { 
      this.publicPropalsSubcription = proposalService.public_proposals$.subscribe(
        proposals => {
          this.public_proposals = proposals;
        }
      );

  }

  ngOnInit() {
    this.getPublicProposals();
  }

  getPublicProposals() {
     var responseData = {};
     var list: Proposal[];
     this.proposalService.getPublicProposals().map(
       res => {
         if (res.ok) {return res['_body'];}
       },
     ).subscribe(
       msg => { 
         list = JSON.parse(msg);
         this.proposalService.publishPublicProposals(list);
         if (list.length === 0) {
           this.mdlSnackbarService.showSnackbar(
              {message:'No Public Proposals Found'});
         }
      },
       err => {
          //  if (err.status === 403) {this.routToLogin();}
        }
      );
   }

   selectProposalById(id: number)  {
      this.selected_proposal= this.public_proposals.find(function(rec){
        if (rec.id === id) return rec;
      });
   }

}
