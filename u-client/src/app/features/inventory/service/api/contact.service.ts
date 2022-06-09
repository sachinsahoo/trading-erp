import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'app/features/inventory/model/product';
import { Contact } from '../../model/contact';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private BASE_URL = 'https://trading.urvaru.com/contactapi/';

  constructor(private http: HttpClient) {}

  list(): Observable<any> {
    const url = `${this.BASE_URL}`;
    return this.http.get(url);
  }

  save(contact: Contact): Observable<any> {
   // console.log(company);

    const url = `${this.BASE_URL}`;
    return this.http.post(url, {company:contact});
  }

  saveContact(contact: Contact, contactId: number): Observable<any> {
    const url = `${this.BASE_URL}/savecontact.ws`;
    console.log(contact);

    return this.http.post(url, { companyId: contactId, contact });
  }
}
