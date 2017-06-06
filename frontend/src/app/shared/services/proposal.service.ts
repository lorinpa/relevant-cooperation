import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';
import { Subject }    from 'rxjs/Subject';
import { UserService } from './user.service';
import { Http , Headers } from '@angular/http';
import { Proposal } from "app/shared/models/proposal";



@Injectable()
export class ProposalService {

  private my_proposals = new Subject<Proposal[]>();
  my_proposals$ = this.my_proposals.asObservable();


  private partner_proposals = new Subject<Proposal[]>();
  partner_proposals$ = this.partner_proposals.asObservable();

  private public_proposals = new Subject<Proposal[]>();
  public_proposals$ = this.public_proposals.asObservable();

  constructor(private http: Http,private userService: UserService) { }


   addProposal(proposal:Proposal) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .post('/co/api/proposal', proposal, {headers})
      .catch(this.handleError);
  }

  getMyProposals() : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .get('/co/api/proposal',  {headers})
      .catch(this.handleError);
  }

  publishMyProposals(proposals: Proposal[]) {
     this.my_proposals.next(proposals);
  }

  getPartnerProposals() : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .get('/co/api/proposal/partner',  {headers})
      .catch(this.handleError);
  }

   getPublicProposals() : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .get('/co/api/proposal/public',  {headers})
      .catch(this.handleError);
  }

  publishPublicProposals(proposals: Proposal[]) {
     this.public_proposals.next(proposals);
  }


  updateProposal(proposal:Proposal) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .put('/co/api/proposal', proposal,  {headers})
      .catch(this.handleError);
  }

  deleteProposal(id:number) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .delete('/co/api/proposal/'+ id,   {headers})
      .catch(this.handleError);
  }

  dismissPartnerProposal(id:number) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .delete('/co/api/proposal/partner/'+ id,   {headers})
      .catch(this.handleError);
  }

  publishPartnerProposals(proposals: Proposal[]) {
     this.partner_proposals.next(proposals);
  }



  private handleError(error: Response | any): Observable<any> {
    return Observable.throw(error);
  }
  

}
