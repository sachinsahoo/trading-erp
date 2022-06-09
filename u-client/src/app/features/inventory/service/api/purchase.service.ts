import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'app/features/inventory/model/product';
import { Company } from '../../model/company';
import { Contact } from '../../model/contact';
import { Transaction } from '../../model/transaction';
import { PurchaseOrder } from '../../model/purchaseorder';
import { RequestOptions, ResponseContentType } from '@angular/http';
import { OrderSearchRequest } from '../../model/rs/orderSearchRequest';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {
  private BASE_URL = 'http://localhost:8080/smulti/api/purchase';


  constructor(private http: HttpClient) {}

  list(): Observable<any> {
    const url = `${this.BASE_URL}/list.ws`;
    return this.http.post(url, {});
  }

  search(searchRequest: OrderSearchRequest, selfid: number): Observable<any> {
    searchRequest.myCompanyId = selfid;
    const url = `${this.BASE_URL}/search.ws`;
    return this.http.post(url, searchRequest);
    // const url = `/assets/test_data/purchaseListResponse.json`;
    // return this.http.get(url);


  }

  save(order: PurchaseOrder): Observable<any> {
    const url = `${this.BASE_URL}/save.ws`;
    return this.http.post(url, { purchaseOrder: order });
  }

  updatestatus(oid: number, date: number, status: string): Observable<any> {
    const url = `${this.BASE_URL}/updatestatus.ws`;
    return this.http.post(url, { oid, date, status });
  }

  downloadPDF(id): Observable<Blob> {
    const url = `${this.BASE_URL}/getpdf.ws`;
    return this.http.post(url, { id: id }, { responseType: 'blob' });
  }
}
