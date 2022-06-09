import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginDetail } from '../../model/login';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private BASE_URL = 'http://localhost:8080/smulti/api/user';

  constructor(private http: HttpClient) {}

  logIn(user: LoginDetail): Observable<any> {
    const url = `${this.BASE_URL}/login.ws`;
    return this.http.post(url, { user: user });
  }
}
