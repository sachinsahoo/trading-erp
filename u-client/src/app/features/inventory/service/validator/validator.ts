import { AbstractControl, ValidatorFn } from '@angular/forms';
import { Company } from '../../model/company';






// Validators 
export function companyNameValidator(company: Company): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
        if (control.value !== undefined && (company != null && company.name === control.value)) {
            return { 'company': true };
        }
        return null;
    };
  }