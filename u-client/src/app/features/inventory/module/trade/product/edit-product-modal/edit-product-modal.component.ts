import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject
} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { Product } from 'app/features/inventory/model/product';
import { Observable } from 'rxjs';
import { selectActiveProduct } from 'app/features/inventory/selectors/order.selectors';
import { FormBuilder, Validators } from '@angular/forms';
import { saveProduct } from 'app/features/inventory/action/product.actions';

@Component({
  selector: 'edit-product-modal',
  templateUrl: './edit-product-modal.component.html',
  styleUrls: ['./edit-product-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EditProductModalComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<EditProductModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Product,
    public dialog: MatDialog,
    private fb: FormBuilder,
    private store: Store<AppState>
  ) {}

  // Form related
  productFormGroup = this.fb.group(
    EditProductModalComponent.createProductForm()
  );

  static createProductForm(): any {
    return {
      name: ['', [Validators.required]],
      description: ['', Validators.required],
      costPrice: [
        '',
        [Validators.required, Validators.min(1), Validators.max(9199254749)]
      ],
      salesPrice: [
        '',
        [Validators.required, Validators.min(1), Validators.max(9199254749)]
      ],
      quantity: [
        '',
        [Validators.required, Validators.min(1), Validators.max(9199254749)]
      ],
      unit: ['', Validators.required],
      barcode: [''],
      internalReference: [''],
      group: [''],
      category: [''],
      uom: [''],
      hsnCode: [''],
      gstRate: ['']
    };
  }

  ngOnInit() {
    this.populateForm();

    this.dialogRef.keydownEvents().subscribe(event => {
      if (event.key === 'Escape') {
        this.onClose();
      }
    });
  }

  submitMain() {
    this.data.name = this.productFormGroup.controls.name.value;
    this.data.internalReference = this.productFormGroup.controls.internalReference.value;
    this.data.barcode = this.productFormGroup.controls.barcode.value;
    this.data.description = this.productFormGroup.controls.description.value;
    this.data.costPrice = this.productFormGroup.controls.costPrice.value;
    this.data.salesPrice = this.productFormGroup.controls.salesPrice.value;
    this.data.cgstRate = this.productFormGroup.controls.gstRate.value;
    this.data.quantity = this.productFormGroup.controls.quantity.value;
    this.data.unit = this.productFormGroup.controls.unit.value;
    this.data.group = this.productFormGroup.controls.group.value;
    this.data.category = this.productFormGroup.controls.category.value;
    this.data.hsnCode = this.productFormGroup.controls.hsnCode.value;
    this.data.uom = this.productFormGroup.controls.uom.value;
    console.log(this.data);

    this.store.dispatch(saveProduct({ product: this.data }));
  }

  populateForm() {
    if (this.data.id) {
      this.productFormGroup.controls.name.setValue(this.data.name);
      this.productFormGroup.controls.internalReference.setValue(
        this.data.internalReference
      );
      this.productFormGroup.controls.barcode.setValue(this.data.barcode);
      this.productFormGroup.controls.description.setValue(
        this.data.description
      );
      this.productFormGroup.controls.costPrice.setValue(this.data.costPrice);
      this.productFormGroup.controls.salesPrice.setValue(this.data.salesPrice);
      this.productFormGroup.controls.gstRate.setValue(this.data.cgstRate);
      this.productFormGroup.controls.quantity.setValue(this.data.quantity);
      this.productFormGroup.controls.unit.setValue(this.data.unit);
      this.productFormGroup.controls.group.setValue(this.data.group);
      this.productFormGroup.controls.category.setValue(this.data.category);
      this.productFormGroup.controls.hsnCode.setValue(this.data.hsnCode);
      this.productFormGroup.controls.uom.setValue(this.data.uom);
    }
  }

  onClose() {
    //this.store.dispatch(approveTransModalClose());
    this.dialogRef.close();
  }
}
