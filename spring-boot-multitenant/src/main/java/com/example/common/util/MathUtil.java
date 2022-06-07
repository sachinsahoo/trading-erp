package com.example.common.util;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.Format;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.regex.Pattern;

public class MathUtil {

    private static Format rupeeformat = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));


    public static void main (String args[]) {

        String tax = "GST   @    3.4 ,  CGST@4.44  " ;
        System.out.println(getTax(tax));


    }


    public static String rupeesFormat(BigDecimal amount) {

        amount = amount != null ? amount : BigDecimal.ZERO;
        amount = roundUp(amount);
        String rupees =  rupeeformat.format(amount);
        rupees = rupees.split("\\.")[0];
        return rupees;

    }

    public static String rupeesInWords(BigDecimal amount) {

        NumberFormat formatter =
                new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT);

        amount = amount != null ? amount : BigDecimal.ZERO;
        amount = roundUp(amount);
        String rupees =  formatter.format(amount);

        return rupees;

    }

    public static BigDecimal roundUp(BigDecimal amount) {
        amount = amount != null ? amount : BigDecimal.ZERO;
        BigDecimal rounded = amount.round(new MathContext(0, RoundingMode.HALF_UP));
        return rounded;
    }



    public static  BigDecimal getTax(String taxes) {
        BigDecimal totalTax = BigDecimal.ZERO;
        taxes = taxes.trim();

        if(StringUtils.isEmpty(taxes)){
            return BigDecimal.ZERO;
        }
        try {
            String[] taxStrList = taxes.split(",");
            for (String taxitem : taxStrList) {
                taxitem = taxitem.trim();
                if (taxitem.contains("@")) {
                    BigDecimal tax = new BigDecimal(taxitem.split("@")[1].trim());
                    totalTax = totalTax.add(tax);
                }
            }
        }catch (Exception e) {

        }
        return totalTax;
    }

    public static  Map<String,BigDecimal> getTaxMap(String taxes) {

        Map<String, BigDecimal> taxMap = new HashMap<>();
        BigDecimal totalTax = BigDecimal.ZERO;
        taxes = taxes.trim();

        if(StringUtils.isEmpty(taxes)){
            return taxMap;
        }
        try {
            String[] taxStrList = taxes.split(",");
            for (String taxitem : taxStrList) {
                taxitem = taxitem.trim();
                if (taxitem.contains("@")) {
                    BigDecimal tax = new BigDecimal(taxitem.split("@")[1].trim());
                    taxMap.put(taxitem, tax);
                }
            }
        }catch (Exception e) {

        }
        return taxMap;
    }


}
