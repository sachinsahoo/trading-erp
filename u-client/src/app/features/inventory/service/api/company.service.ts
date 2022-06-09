import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'app/features/inventory/model/product';
import { Company } from '../../model/company';
import { Contact } from '../../model/contact';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private BASE_URL = 'http://localhost:8080/smulti/api/company';

  constructor(private http: HttpClient) {}

  list(): Observable<any> {
    const url = `${this.BASE_URL}/list.ws`;
    return this.http.post(url, {});
  }

  save(company: Company): Observable<any> {
   // console.log(company);

    const url = `${this.BASE_URL}/save.ws`;
    return this.http.post(url, {company:company});
  }

  saveContact(contact: Contact, companyId: number): Observable<any> {
    const url = `${this.BASE_URL}/savecontact.ws`;
    console.log(contact);

    return this.http.post(url, { companyId, contact });
  }
}
