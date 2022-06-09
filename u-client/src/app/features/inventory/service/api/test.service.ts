import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'app/features/inventory/model/product';

@Injectable({
  providedIn: 'root'
})
export class TestService {
  private BASE_URL = '/assets/test_data';

  constructor(private http: HttpClient) {}

  productList(): Observable<any> {
    const url = `${this.BASE_URL}/productListResponse.json`;
    return this.http.get(url);
  }

  companyList(): Observable<any> {
    const url = `${this.BASE_URL}/companyList.json`;
    return this.http.get(url);
  }
  purchaseList(): Observable<any> {
    const url = `${this.BASE_URL}/purchaseListResponse.json`;
    return this.http.get(url);
  }
  transList(): Observable<any> {
    const url = `${this.BASE_URL}/transactionListResponse.json`;
    return this.http.get(url);
  }
  report(): Observable<any> {
    const url = `${this.BASE_URL}/yearlyReportResponse.json`;
    return this.http.get(url);
  }

  balancesheet(): Observable<any> {
    const url = `${this.BASE_URL}/balancesheet.json`;
    return this.http.get(url);
  }

}
