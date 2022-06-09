

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'app/features/inventory/model/product';
import { Company } from '../../model/company';
import { Contact } from '../../model/contact';
import { Transaction } from '../../model/transaction';
import { TransactionSearchRequest } from '../../model/rs/transactionSearchRequest';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private BASE_URL = 'http://localhost:8080/smulti/api/transaction';

  constructor(private http: HttpClient) {}

  search(request: TransactionSearchRequest, selfId: number): Observable<any> {
    request.myCompanyId = selfId;
    const url = `${this.BASE_URL}/search.ws`;
    return this.http.post(url, request);
  }


  list(): Observable<any> {
    const url = `${this.BASE_URL}/list.ws`;
    return this.http.post(url, {});
  }


  get(id: number): Observable<any> {
    const url = `${this.BASE_URL}/get.ws`;
    return this.http.post(url, {id: id});
  }

  save(transaction: Transaction): Observable<any> {
    const url = `${this.BASE_URL}/save.ws`;
    return this.http.post(url, {transaction: transaction});
  }


}
