import { UserService } from './user.service';
import { Http , Headers } from '@angular/http';
import { UserProfile, UserProfileInterface } from './../models/user-profile';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';
import { Subject }    from 'rxjs/Subject';

@Injectable()
export class UserProfileService {

  //currentUserProfile: UserProfile;

  private userName = new Subject<string>();
  userName$ = this.userName.asObservable();
  private up = new Subject<UserProfile>();
  up$ = this.up.asObservable();

  public static readonly NOT_UNIQUE = "NOT-UNIQUE";

  constructor(private http: Http,private userService: UserService) { 
    
  }

  publishUserName(name:string) {
    this.userName.next(name);
  }
  
  publishUserProfile(up: UserProfile) {
    this.up.next(up);
  }

  getProfileData() : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .get('/co/api/profile', {headers})
      .catch(this.handleError);
  }
  
  updateLocation(locatation) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    let payload = {'location': locatation}
    
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .put('/co/api/profile/location', payload, {headers})
      .catch(this.handleError);
  }

  updatePassword(passwd) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    let payload = {'password': passwd}
    
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .put('/co/api/user', payload, {headers})
      .catch(this.handleError);
  }

  updateName(name) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    let payload = {'name': name}
    
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .put('/co/api/profile/name', payload, {headers})
      .catch(this.handleError);
  }

   updateEmail(email) : Observable<Response> {
    let bearerToken =  this.userService.getBearerToken();
    let headers = new Headers();
    let payload = {'email': email}
    
    headers.append('Authorization', bearerToken);
    headers.append('Content-Type', 'application/json');
    return this.http
      .put('/co/api/user/email', payload, {headers})
      .catch(this.handleError);
  }

  updateToken(token:string) {
    if (token) {
      this.userService.updateToken(token);
    }
  }

private handleError(error: Response | any): Observable<any> {

    return Observable.throw(error);
   
  }

  
}
