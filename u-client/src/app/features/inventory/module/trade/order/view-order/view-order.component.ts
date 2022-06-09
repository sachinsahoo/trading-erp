import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  OnDestroy
} from '@angular/core';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { flatMap, mergeMap } from 'rxjs/operators';
import { combineLatest, Observable, Subscription } from 'rxjs';
import {
  selectActiveOrder,
  selectOrderById,
  selectCompanyList,
  selectSaleOrdersRelatedToPOID
} from 'app/features/inventory/selectors/order.selectors';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Company } from 'app/features/inventory/model/company';
import {
  updateSelectedOrderInStore,
  updateSelectedInvoiceIdInStore
} from 'app/features/inventory/action/order.action';
import { routeAnimations } from 'app/core/core.module';
import { PrintOrderModalComponent } from '../print-order-modal/print-order-modal.component';
import { saveAs } from 'file-saver';
import { PurchaseService } from 'app/features/inventory/service/api/purchase.service';
@Component({
  selector: 'view-order',
  templateUrl: './view-order.component.html',
  styleUrls: ['./view-order.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [routeAnimations]
})
export class ViewOrderComponent implements OnInit, OnDestroy {
  // Observables
  selectActiveOrder$: Observable<any> = this.store.pipe(
    select(selectActiveOrder)
  );
  companies$: Observable<any> = this.store.pipe(select(selectCompanyList));
  //relatedSaleOrders$: Observable<any> = this.store.pipe(select(selectSaleOrdersRelatedToPOID(this.oid)));
  subscriptions: Array<Subscription> = [];

  // Local Vars
  orderType: String = '';
  order: PurchaseOrder;
  allCompanies: Company[];
  //datasource for PO Product

  // Constructor ---------------
  constructor(
    private store: Store<AppState>,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private orderService: PurchaseService
  ) {}

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  // Init ----------------------
  ngOnInit() {
    console.log('oierueowru-----------Init');

    this.route.params
      .pipe(
        flatMap(params => {
          return combineLatest(
            this.store.pipe(select(selectOrderById(params['id']))),
            this.companies$
          );
        })
      )
      .subscribe(data => {
        console.log('oierueowru-----------Order' + data[0]);
        this.order = data[0];
        this.allCompanies = data[1];
        this.populateOrder();
      });
  } // End Init --------

  populateOrder() {
    if (this.order != null) {
      this.orderType = this.order.type;
      this.order.customer = this.getCompnayById(this.order.customerid);
      this.order.supplier = this.getCompnayById(this.order.supplierid);
    } else {
    }
  }

  // ---------------------------
  // Navigation Functions
  // ---------------------------

  printPreview() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'print-purchase';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = this.order;

    // Open Dialog
    const dialogRef = this.dialog.open(PrintOrderModalComponent, dialogConfig);
  }

  downloadPDF() {
    this.orderService.downloadPDF(this.order.id).subscribe(blob => {
      saveAs(blob, 'Order ' + this.order.referenceno);
    });
  }

  getOrderURL() {
    return this.orderType == 'purchase'
      ? 'trade/purchase'
      : this.orderType == 'sale'
      ? 'trade/sale'
      : 'trade/comm';
  }

  getOrderLabel() {
    console.log(this.orderType);
    return this.orderType == 'purchase'
      ? 'Purchases'
      : this.orderType == 'sale'
      ? 'Sales'
      : 'Commissions';
  }

  viewOrderList() {
    let navStr: string = this.getOrderURL().concat('/list');

    console.log(navStr);
    this.router.navigate([navStr]);
  }

  editOrder() {
    let navStr = 'trade/order/edit';
    this.router.navigate([navStr, this.order.type, this.order.id]);
  }

  createInvoice() {
    let navStr = 'trade/invoice/edit';

    this.router.navigate([navStr, this.order.id, 0]);
  }

  // Get Company By ID
  getCompnayById(cid: number): Company {
    let company: Company;
    if (this.allCompanies != null) {
      company = this.allCompanies.filter(function(comp) {
        return comp.id == cid;
      })[0];
    }

    return company;
  }
  deleteOrder(){

  }
  updateStatus(){

  }
  viewOrder(){

  }
  cloneOrder(){}
}
