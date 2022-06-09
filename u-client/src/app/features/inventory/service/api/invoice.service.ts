import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'app/features/inventory/model/product';
import { Company } from '../../model/company';
import { Contact } from '../../model/contact';
import { Transaction } from '../../model/transaction';
import { PurchaseOrder } from '../../model/purchaseorder';
import { Invoice } from '../../model/invoice';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {
  private BASE_URL = 'http://localhost:8080/smulti/api/invoice';

  constructor(private http: HttpClient) {}

  list(): Observable<any> {
    const url = `${this.BASE_URL}/list.ws`;
    return this.http.post(url, {});
  }

  save(invoice: Invoice): Observable<any> {
    const url = `${this.BASE_URL}/save.ws`;
    return this.http.post(url, {invoice: invoice});
  }

  updatestatus(invid: number, date: number, status: string): Observable<any> {
    const url = `${this.BASE_URL}/updatestatus.ws`;
    return this.http.post(url, { invid, date, status });
  }
}
