import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'app/features/inventory/model/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private BASE_URL = 'http://localhost:8080/smulti/api/product';

  constructor(private http: HttpClient) {}

  list(): Observable<any> {
    const url = `${this.BASE_URL}/list.ws`;
    return this.http.post(url, {});
  }

  save(product: Product): Observable<any> {
    console.log({product:{product}});

    const url = `${this.BASE_URL}/save.ws`;
    return this.http.post(url, {product: product});
  }
}
