import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import { User } from './../models/user';


@Injectable()
export class UserService {

  private token: string;

  private loggedIn = false;



  constructor(private http: Http) {
    this.loggedIn = !!localStorage.getItem('token');
    if (this.loggedIn) this.token = localStorage.getItem('token');
  }

  login(user: User) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    var creds = "email=" + user.email + "&password=" + user.password;
    this.loggedIn = false;
    return this.http
      .post('/co/api/auth/login', creds, { headers })
      .map((res) => {
        if (res.ok) {
          var jwt = res['_body'];
          this.token = jwt;
          localStorage.setItem('token', jwt);
          this.loggedIn = true;
        }
        return res.ok;
      }).catch(this.handleError);
  }

  updateToken(token:string) {
    this.token = token;
    localStorage.setItem('token', token);
  }

  logout() {
    return this.http
      .get('/co/logout')
      .map((res) => {
        if (res.ok) {
          this.loggedIn = false;
          localStorage.removeItem('token');
        }
        return res.ok;
      }).catch(this.handleError);
  }

  isLoggedIn() {
    return this.loggedIn;
  }

  
  /*
  *   Method used to both create a new account or 
      notify an existing user what their password is
  */
  registerAccount(user: User) : Observable<Response> {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
     return this.http
      .post('/co/api/user/register', user, {headers})
      .catch(this.handleError);
  }
 

  getBearerToken() : string {
    return 'Bearer ' + this.token;
  }

  private handleError(error: Response | any): Observable<any> {
    let errMsg: string;
    var t = typeof error;
    if (error instanceof Object) {
      errMsg = `${error.status} - ${error.statusText || ''}`;
    } else {
      errMsg = error.statusText ? error.statusText : error.toString();
    }

    //let d = new Observable(new Response());
    return Observable.throw(new Error(errMsg));
    //return [{'ok': false}];
  }
}
