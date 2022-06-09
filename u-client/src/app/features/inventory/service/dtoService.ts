import { Injectable } from '@angular/core';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { PurchaseSaleYearlyReport } from '../model/Report/purchaseSaleYearlyReport';
import { PurchaseOrder } from '../model/purchaseorder';

@Injectable()
export class OrderDtoService {
  
  
  private orderBehaviorSubject = new BehaviorSubject<PurchaseOrder>(new PurchaseOrder());
  order$ = this.orderBehaviorSubject.asObservable();


  constructor() {}

  public shareOrder(order: PurchaseOrder) {
    this.orderBehaviorSubject.next(order);
  }
}







/*  // notes

parent
-------
constructor(private dtoService: DtoService) { }

newMessage() {
    this.dtoService.shareData(purchaseOrder)
  }


child 
-------


  ngOnInit() {
    this.dtoService.sharedData.subscribe(dto => this.purchaseOrder = dto)
  }






https://medium.com/front-end-weekly/sharing-data-between-angular-components-f76fa680bf76


https://stackoverflow.com/questions/39494058/behaviorsubject-vs-observable
*/