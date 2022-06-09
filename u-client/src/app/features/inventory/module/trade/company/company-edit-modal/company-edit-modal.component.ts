import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { selectCompanyList } from 'app/features/inventory/selectors/order.selectors';
import { FormBuilder, Validators, AbstractControl, ValidatorFn, ControlContainer } from '@angular/forms';
import { MatDialog, MatDialogConfig, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { AppState } from 'app/core/core.state';
import { Store } from '@ngrx/store';
import { addCompany } from 'app/features/inventory/action/company.action';

@Component({
  selector: 'company-edit-modal',
  templateUrl: './company-edit-modal.component.html',
  styleUrls: ['./company-edit-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CompanyEditModalComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<any>,
    private store: Store<AppState>,

    private formBuilder: FormBuilder,
  ) { }

  ngOnInit() {

      console.log(this.data);
      this.populateForm();

  }

  companyForm = this.formBuilder.group(
    CompanyEditModalComponent.createCompanyForm()
  );

  static createCompanyForm():any{

return{
  name: ['',
    [Validators.required]
  ],

  email: ['',
    [Validators.required]
  ],
  phone: ['',
    [Validators.required]
  ],
  gstin1: [''],
  tan: [''],
  pan: [''],

  //bank details

  bankaccno: [''],
  bankname: [''],
  accountType: [''],
  ifsccode: [''],
  id: [],
  type: [],
}

  }


  public errorMessages = {
    name: [
      { type: 'required', message: 'company name is required.' },
      { type: 'invalid', message: 'Invalid company name .' },

    ],
    phone: [
      { type: 'required', message: 'phone number is required' },
      { type: 'mismatch', message: 'Invalid phone number.' },

    ],
     email: [
      { type: 'required', message: 'email id is required' },
      { type: 'mismatch', message: 'Invalid email id.' },

    ],


  }

  get name() {
    return this.companyForm.get('name');
  }
  get phone() {
    return this.companyForm.get('phone');
  }
  get email() {
    return this.companyForm.get('email');
  }



  submitMain() {



      this.data.name = this.companyForm.controls.name.value;
      this.data.email = this.companyForm.controls.email.value;
      this.data.phone = this.companyForm.controls.phone.value;
      this.data.gstin1 = this.companyForm.controls.gstin1.value;
      this.data.tan = this.companyForm.controls.tan.value;
      this.data.pan = this.companyForm.controls.pan.value;
      this.data.bankaccno = this.companyForm.controls.bankaccno.value;
      this.data.bankname = this.companyForm.controls.bankname.value;
      this.data.accountType = this.companyForm.controls.accountType.value;
      this.data.ifsccode = this.companyForm.controls.ifsccode.value;
      //this.data.id = this.companyForm.controls.id.value;
      //this.data.type = this.companyForm.controls.type.value;

      console.log(this.data);




       this.store.dispatch(addCompany({ company: this.data }));

  }

  populateForm(){
    if (this.data.id) {
    this.companyForm.controls.name.setValue(this.data.name);
    this.companyForm.controls.email.setValue(this.data.email);
    this.companyForm.controls.phone.setValue(this.data.phone);
    this.companyForm.controls.gstin1.setValue(this.data.gstin1);
    this.companyForm.controls.tan.setValue(this.data.tan);
    this.companyForm.controls.pan.setValue(this.data.pan);
    this.companyForm.controls.bankaccno.setValue(this.data.bankaccno);
    this.companyForm.controls.bankname.setValue(this.data.bankname);
    this.companyForm.controls.accountType.setValue(this.data.accountType);
    this.companyForm.controls.ifsccode.setValue(this.data.ifsccode);
    this.companyForm.controls.id.setValue(this.data.id);
    this.companyForm.controls.type.setValue(this.data.type);
    }
  }

}
