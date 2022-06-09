import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject
} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { AppState } from 'app/core/core.state';
import { Store } from '@ngrx/store';
import { ProductYearlyReport } from 'app/features/inventory/model/Report/productYearlyReport';

@Component({
  selector: 'product-monthly-modal',
  templateUrl: './product-monthly-modal.component.html',
  styleUrls: ['./product-monthly-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductMonthlyModalComponent implements OnInit {
  //transactions$: Observable<any> = this.store.pipe(select(selectSupplierList));
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  custDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  supDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  displayedCols: string[] = [
    'date',
    'purchaseqty',
    'saleqty',
    'avgCostPrice',
    'avgSalePrice',
    'purchaseamt',
    'saleamt',
    'profit'
  ];

  custDisplayedCols: string[] = [
    'name',
    'saleqty',
    'avgSalePrice',
    'saleamt',
    'profit'
  ];
  
  supDisplayedCols: string[] = [
    'name',
    'purchaseqty',
    'avgCostPrice',
    'purchaseamt',
  ];


 

  constructor(
    public dialogRef: MatDialogRef<ProductMonthlyModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductYearlyReport,

    private store: Store<AppState>
  ) {}

  ngOnInit() {
    
    this.dataSource = new MatTableDataSource<any>(this.data.monthlyReport);
    this.custDataSource = new MatTableDataSource<any>(this.data.customerReport);
    this.supDataSource = new MatTableDataSource<any>(this.data.supplierReport);

    this.dialogRef.keydownEvents().subscribe(event => {
      if (event.key === 'Escape') {
        this.onClose();
      }
    });
  }

  onClose() {
    this.dialogRef.close();
  }
}
