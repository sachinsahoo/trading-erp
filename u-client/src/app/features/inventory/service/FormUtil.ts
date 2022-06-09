import { Dropdown } from '../model/dropdown';


export class FormUtil {


    static getDatePresets () :Dropdown[] {

        let datePresets: Array<Dropdown> = new Array<Dropdown>()
        datePresets.push({"value": "all", "display": "All Time" });
        datePresets.push({"value": "last1", "display": "Last 1 Month" });
        datePresets.push({"value": "last3", "display": "Last 3 Months" });
        datePresets.push({"value": "custom", "display": "Custom Range" });
        return datePresets
    }

    static getClientPresets () :Dropdown[] {
        let clientPresets: Array<Dropdown> = new Array<Dropdown>()
        clientPresets.push({"value": "", "display": "All" });
        clientPresets.push({"value": "customer", "display": "Customers" });
        clientPresets.push({"value": "supplier", "display": "Suppliers" });
        clientPresets.push({"value": "agent", "display": "Agents" });
        return clientPresets
    }

    static getTransTypePresets () :Dropdown[] {
        let transPresets: Array<Dropdown> = new Array<Dropdown>()
        transPresets.push({"value": "all", "display": "All Transactions" });
        transPresets.push({"value": "Payment", "display": "Payment" });
        transPresets.push({"value": "Receipt", "display": "Receipt" });
        transPresets.push({"value": "Purchase", "display": "Purchase" });
        transPresets.push({"value": "Sale", "display": "Sale" });
        transPresets.push({"value": "Commpayable", "display": "Commission Payable" });
        transPresets.push({"value": "Commpaid", "display": "Commision Paid" });
        transPresets.push({"value": "Commreceivable", "display": "Commission Receivable" });
        transPresets.push({"value": "Commreceived", "display": "Commission Received" });
        transPresets.push({"value": "Journal", "display": "Journal" });
        transPresets.push({"value": "PurchaseAdvance", "display": "Purchase Advance" });
        transPresets.push({"value": "SaleAdvance", "display": "Sale Advance" });
        return transPresets
    }

}