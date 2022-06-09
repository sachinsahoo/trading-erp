import { Stock } from './stock';
import { Contact } from './contact';

export class Person {
    id: number;
    name: string;
    phone: string;
    email: string;
    address: Address;

}


export class Address {
    id: number;
    name: string;
    address1: string;
    address2: string;
    city: string;
    state: string;
}


