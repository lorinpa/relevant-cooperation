import { UserService } from './user.service';
import { KeywordInterface, Keyword } from './../models/keyword';
import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class KeywordServiceService {

 // private key_seed = 6;

  //list: Keyword[];
  constructor(private http: Http, private userService: UserService) {
   // this.list = [];
  }

  getKeywords() : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .get('/co/api/keyword', {headers})
      .catch(this.handleError);
  }

  /**
   * PROBABLY NO LONGER USED
   * @param keyword 
   */

/*
    addKeyword(keyword) : Keyword {
      var found = this.list.find(rec => rec.keyword === keyword);
      var kw:Keyword;
      if (!found) {
        kw = new Keyword(++this.key_seed, keyword);
        this.list.push(kw);
      }
      return kw;
  }*/



  /* Adds both a new keyword and a relationship of the new to keyword
    to the user profile (as provided service)
  */
  addProvidedService(value: String) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .post('/co/api/keyword/provided/new', value, {headers})
      .catch(this.handleError);
  }

   /* Adds a relationship of an existing keyword
    to the user profile (as provided service)
  */
  addProvidedServiceRelationship(id: number) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .post('/co/api/keyword/provided', id, {headers})
      .catch(this.handleError);
  }

  deleteProvidedService(id: number) {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .delete('/co/api/keyword/provided/'+ id, {headers})
      .catch(this.handleError);
  }

  getUserProvidedServices() {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .get('/co/api/keyword/provided', {headers})
      .catch(this.handleError);
  }

  /* BUSINESS CONCEPT METHODS */


  /* Adds both a new keyword and a relationship of the new to keyword
    to the user profile (as business concept)
  */
  addBusinessConcept(value: String) : Observable<Response> {
    let headers = this.authHeaders();
    return this.http
      .post('/co/api/keyword/bus/new', value, {headers})
      .catch(this.handleError);
  }

   /* Adds a relationship of an existing keyword
    to the user profile (as business concept)
  */
  addUserBusinessConcept(id: number) : Observable<Response> {
    let headers = this.authHeaders();
    return this.http
      .post('/co/api/keyword/bus', id, {headers})
      .catch(this.handleError);
  }

  deleteUserBusinessConcept(id: number) {
    let headers = this.authHeaders();
    return this.http
      .delete('/co/api/keyword/bus/'+ id, {headers})
      .catch(this.handleError);
  }
 
  getUserBusinessConcepts() {
    let headers = this.authHeaders();
    return this.http
      .get('/co/api/keyword/bus', {headers})
      .catch(this.handleError);
  }


/* PARTNER SERVICE METHODS */


  /* Adds both a new keyword and a relationship of the new to keyword
    to the user profile (as partner service)
  */
  addPartnerService(value: String) : Observable<Response> {
    let headers = this.authHeaders();
    return this.http
      .post('/co/api/keyword/partner/new', value, {headers})
      .catch(this.handleError);
  }

   /* Adds a relationship of an existing keyword
    to the user profile (as partner service)
  */
  addUserPartnerService(id: number) : Observable<Response> {
    let headers = this.authHeaders();
    return this.http
      .post('/co/api/keyword/partner', id, {headers})
      .catch(this.handleError);
  }

  deleteUserPartnerService(id: number) {
    let headers = this.authHeaders();
    return this.http
      .delete('/co/api/keyword/partner/'+ id, {headers})
      .catch(this.handleError);
  }
 
  getUserPartnerServices() {
    let headers = this.authHeaders();
    return this.http
      .get('/co/api/keyword/partner', {headers})
      .catch(this.handleError);
  }


  /*** Search Methods */

  searchByTerm() {
   let headers = this.authHeaders();
  /// var srch = new Keyword(0);
    return this.http
      .get('/co/api/keyword/search',  {headers})
      .catch(this.handleError);
  }

  searchByBusConcepts() {
   let headers = this.authHeaders();
  /// var srch = new Keyword(0);
    return this.http
      .get('/co/api/keyword/search/bus',  {headers})
      .catch(this.handleError);
  }

  /**
   *      ******  COMMON METHODS   *****
   */

  authHeaders() {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return headers;
  }


private handleError(error: Response | any): Observable<any> {
    let errMsg: string;
    if (error instanceof Object) {
      console.log("got error status of: "+ error.status);
      errMsg = `${error.status} - ${error.statusText || ''}`;
    } else {
      errMsg = error.statusText ? error.statusText : error.toString();
    }
    //return Observable.throw(new Error(errMsg));
    return Observable.throw(error);
   
  }
}
