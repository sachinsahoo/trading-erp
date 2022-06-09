import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'app/features/inventory/model/product';
import { Company } from '../../model/company';
import { Contact } from '../../model/contact';
import { Transaction } from '../../model/transaction';
import { PurchaseOrder } from '../../model/purchaseorder';
import { JournalSearchRequest } from '../../model/rs/journalSearchRequest';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private BASE_URL = 'http://localhost:8080/smulti/api/account';

  constructor(private http: HttpClient) {}

  list(): Observable<any> {
    const url = `${this.BASE_URL}/aclist.ws`;
    return this.http.post(url, {});
  }

  balancesheet(id: Number): Observable<any> {
    const url = `${this.BASE_URL}/balancesheet.ws`;
    return this.http.post(url, {id});
  }

  ledger(searchRequest: JournalSearchRequest): Observable<any> {
    const url = `${this.BASE_URL}/ledger.ws`;
    return this.http.post(url,  searchRequest);
  }
}
