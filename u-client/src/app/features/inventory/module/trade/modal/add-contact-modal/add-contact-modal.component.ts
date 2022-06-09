import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { FormBuilder, Validators, AbstractControl, ValidatorFn, ControlContainer } from '@angular/forms';
import { MatDialog, MatDialogConfig, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { AppState } from 'app/core/core.state';
import { Store } from '@ngrx/store';
import { addContact } from 'app/features/inventory/action/company.action';

@Component({
  selector: 'add-contact-modal',
  templateUrl: './add-contact-modal.component.html',
  styleUrls: ['./add-contact-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AddContactModalComponent implements OnInit {

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

  contactForm = this.formBuilder.group(AddContactModalComponent.createContactForm())

  static createContactForm(): any {
    return {
      name: ['',
        [Validators.required]
      ],

      email: ['',
        [Validators.required]
      ],

      phone1: ['',
        [Validators.required]
      ],
      phone2: [''],
      city: ['',
        [Validators.required]
      ],


      state: [''],
      pincode: [''],
      type: [''],
      address1: ['',
        [Validators.required]
      ],
      address2: [''],

    }
  }

  submitMain() {
    this.data.contact.name = this.contactForm.controls.name.value;
    this.data.contact.phone1 = this.contactForm.controls.phone1.value;
    this.data.contact.phone2 = this.contactForm.controls.phone2.value;
    this.data.contact.city = this.contactForm.controls.city.value;
    this.data.contact.state = this.contactForm.controls.state.value;
    this.data.contact.pincode = this.contactForm.controls.pincode.value;
    this.data.contact.type = this.contactForm.controls.type.value;
    this.data.contact.address1 = this.contactForm.controls.address1.value;
    this.data.contact.address2 = this.contactForm.controls.address2.value;
    this.data.contact.email = this.contactForm.controls.email.value;
    // this.data.contact.name = this.contactForm.controls.name.value;
    this.store.dispatch(addContact({ contact: this.data.contact, companyId: this.data.companyId }));

    console.log(this.data);
  }

  populateForm() {

    if (this.data.contact.id) {
      this.contactForm.controls.name.setValue(this.data.contact.name);
      this.contactForm.controls.phone1.setValue(this.data.contact.phone1);
      this.contactForm.controls.phone2.setValue(this.data.contact.phone2);
      this.contactForm.controls.city.setValue(this.data.contact.city);
      this.contactForm.controls.state.setValue(this.data.contact.state);
      this.contactForm.controls.pincode.setValue(this.data.contact.pincode);
      this.contactForm.controls.type.setValue(this.data.contact.type);
      this.contactForm.controls.address1.setValue(this.data.contact.address1);
      this.contactForm.controls.address2.setValue(this.data.contact.address2);
      this.contactForm.controls.email.setValue(this.data.contact.email);
      //this.contactForm.controls.name.setValue(this.data.contact.name);

    }


  }

}
