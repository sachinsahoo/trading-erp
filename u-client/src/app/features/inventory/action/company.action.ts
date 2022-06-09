import { createAction, props } from '@ngrx/store';
import { Company } from '../model/company';
import { Contact } from '../model/contact';




// --------------------------
// Switch Company
// --------------------------

export const switchCompany = createAction(
  '[Company] Set Active Self Company id',
  props<{ id: number}>()
);




// Load Company
export const loadCompanies = createAction('[Company] Load Companies');

export const loadCompaniesSuccess = createAction(
  '[Company] Load Companies Success',
  props<{ companies: Company[]}>()
);

export const loadCompaniesFailure = createAction(
  '[Company] Load Companies Failure',
  props<{ errorMessage: string }>()
);

//Add Company
export const addCompany = createAction(
  '[Company] Add Company',
  props<{ company: Company }>()
);

export const addCompanySuccess = createAction(
  '[Company] Add Company Success',
  props<{ companies: Array<Company> }>()
);

export const addCompanyFailure = createAction(
  '[Company] Add Company Fail',
  props<{ message: any }>()
);


//contact


export const addContact = createAction(
  '[Contact] Add Company Contact',
  props<{ contact:Contact, companyId:any }>()
);


// export const save = createAction(
//   '[Company] Add Company Failure',
//   props<{ errorMessage: string }>()
// );
