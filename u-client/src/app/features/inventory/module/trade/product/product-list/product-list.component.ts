import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  OnDestroy
} from '@angular/core';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { loadProducts } from 'app/features/inventory/action/product.actions';
import { Observable, Subscription } from 'rxjs';
import {
  selectProductList,
  selectCompanyList
} from 'app/features/inventory/selectors/order.selectors';
import { MatTableDataSource } from '@angular/material/table';
import { Product } from 'app/features/inventory/model/product';
import { routeAnimations } from 'app/core/core.module';

import * as product_list from 'app/core/test_db/productListResponse.json';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { EditProductModalComponent } from '../edit-product-modal/edit-product-modal.component';
import { ViewProductModalComponent } from '../view-product-modal/view-product-modal.component';

@Component({
  selector: 'anms-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [routeAnimations]
})
export class ProductListComponent implements OnInit, OnDestroy {
  //routeAnimationsElement = ROUTE_ANIMATIONS_ELEMENTS;

  products$: Observable<any> = this.store.pipe(select(selectProductList));
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  displayedCols: string[] = [
    'name',
    'costPrice',
    'salesPrice',
    'pendingarrival',
    'pendingdispatch',
    'quantity'
  ];

  subscriptions: Array<Subscription> = [];

  constructor(private store: Store<AppState>, public dialog: MatDialog) {}

  // Init
  ngOnInit(): void {
    // Products
    this.subscriptions.push(
      this.products$.subscribe(products => {
        if (products != null) {
          this.dataSource = new MatTableDataSource<any>(products);
        }
      })
    );
  }

  // Edit Product Modal
  createProduct() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'edit-product';
    dialogConfig.disableClose = true;
    dialogConfig.width = '1000px';
    dialogConfig.data = new Product();

    // Open Dialog
    const dialogRef = this.dialog.open(EditProductModalComponent, dialogConfig);
  }

  // view Product Modal
  viewProduct(data: Product) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'view-product';
    dialogConfig.disableClose = true;
    dialogConfig.width = '1000px';
    dialogConfig.data = data;

    // Open Dialog
    const dialogRef = this.dialog.open(ViewProductModalComponent, dialogConfig);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  printPreview() {}
  import() {}
}
