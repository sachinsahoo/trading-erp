import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { selectCompanyList } from 'app/features/inventory/selectors/order.selectors';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { select, Store } from '@ngrx/store';
import { Company } from 'app/features/inventory/model/company';
import { AppState } from 'app/core/core.state';
import { ActivatedRoute } from '@angular/router';
import { CompanyViewModalComponent } from '../company-view-modal/company-view-modal.component';
import { TransactionsModalComponent } from '../../transaction/transactions-modal/transactions-modal.component';
import { CompanyEditModalComponent } from '../company-edit-modal/company-edit-modal.component';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
@Component({
  selector: 'company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CompanyListComponent implements OnInit {
  // Observables
  companies$: Observable<any> = this.store.pipe(select(selectCompanyList));
  subscriptions: Array<Subscription> = [];


  // Mat Table Datasource
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();



  columnDefinitions = [
    { def: 'name', show: true },
    { def: 'phone', show: true },
    { def: 'email', show: true },
    { def: 'balance', show: true },
    { def: 'receivable', show: true },
    { def: 'payable', show: true },
    { def: 'bank', show: true }
  ];

  // Local variables
  companyType: string = 'self';
  allCompanies: Company[] = [];
  suppliers: Company[] = [];
  customers: Company[] = [];
  agents: Company[] = [];
  self: Company[] = [];


  getDisplayedCols(): string[] {
    let showBank = this.companyType == 'self';
    this.columnDefinitions[3].show = !showBank;
    this.columnDefinitions[4].show = showBank;
    this.columnDefinitions[5].show = showBank;
    this.columnDefinitions[6].show = showBank;

    return this.columnDefinitions.filter(cd => cd.show).map(cd => cd.def);
  }

  constructor(
    private store: Store<AppState>,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private changeDetection: ChangeDetectorRef,
  ) { }

  ngOnInit() {

    // Companies
    this.subscriptions.push(
      this.companies$.subscribe(companyList => {
        if (companyList != null) {
          this.populateCompanies(companyList);
        
         // console.log(companyList);

        }
      })
    );

    // Company Type from Router Data
    this.subscriptions.push(
      this.route.data.subscribe(data => {
        if (data != null) {
          this.changeCompanyType(data.type);

        }
      })
    );
  }




  changeCompanyType(type: string) {
    this.companyType = type;
    if (type == 'self') {
      this.dataSource = new MatTableDataSource<any>(this.self);
    }
   else if (type == 'supplier') {
      this.dataSource = new MatTableDataSource<any>(this.suppliers);
    }
   else if (type == 'customer') {
      this.dataSource = new MatTableDataSource<any>(this.customers);
    }
    else if (type == 'agent') {
      this.dataSource = new MatTableDataSource<any>(this.agents);
    }
  }

  populateCompanies(companyList: Company[]) {
    this.allCompanies = companyList;
    this.self = companyList.filter(function (comp) {
      return comp.type == 'self';
    });
    this.suppliers = companyList.filter(function (comp) {
      return comp.type == 'supplier';
    });
    this.customers = companyList.filter(function (comp) {
      return comp.type == 'customer';
    });
    this.agents = companyList.filter(function(comp){
      return comp.type == 'agent';
    })

    this.changeCompanyType(this.companyType);
  }

  // view Product Modal
  viewCompany(data: Company) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'view-product';
    dialogConfig.disableClose = true;
    dialogConfig.width = '1000px';
    dialogConfig.data = data;

    // Open Dialog
    const dialogRef = this.dialog.open(CompanyViewModalComponent, dialogConfig);
  }

  openTrans(element: Company) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'company-transactions';
    dialogConfig.disableClose = true;
    dialogConfig.width = '1000px';
    dialogConfig.data = { cid: element.id };

    // Open Dialog
    const dialogRef = this.dialog.open(
      TransactionsModalComponent,
      dialogConfig
    );
  }

  createCompany_modal(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'company-transactions';
    dialogConfig.disableClose = true;
    dialogConfig.width = '1000px';
    let company = new Company();
    company.type = this.companyType;
    dialogConfig.data = company;

    // Open Dialog
    const dialogRef = this.dialog.open(
      CompanyEditModalComponent,
      dialogConfig
    );
  }

  printPreview()
{

}
import(){

}

}
