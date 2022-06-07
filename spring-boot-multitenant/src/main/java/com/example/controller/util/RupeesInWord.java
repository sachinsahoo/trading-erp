package com.example.controller.util;

import java.text.DecimalFormat;

public class RupeesInWord {

    private static final String[] tensNames = {
            "",
            " Ten",
            " Twenty",
            " Thirty",
            " Forty",
            " Fifty",
            " Sixty",
            " Seventy",
            " Eighty",
            " Ninety"
    };

    private static final String[] numNames = {
            "",
            " One",
            " Two",
            " Three",
            " Four",
            " Five",
            " Six",
            " Seven",
            " Eight",
            " Nine",
            " Ten",
            " Eleven",
            " Twelve",
            " Thirteen",
            " Fourteen",
            " Fifteen",
            " Sixteen",
            " Seventeen",
            " Eighteen",
            " Nineteen"
    };

    private RupeesInWord() {





    }

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20){
            soFar = numNames[number % 100];
            number /= 100;
        }
        else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " Hundred" + soFar;
    }


    public static String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) { return "zero"; }

        if(number > 9999999999L) {
            return "";
        }

        String snumber = Long.toString(number);
        // pad with "0"
        String mask1 = "0000000000";
        DecimalFormat df1 = new DecimalFormat(mask1);
        snumber = df1.format(number);
        // XXX nn,nn,nn,nnn
        int crores = Integer.parseInt(snumber.substring(0,3));
        int lakhs =  Integer.parseInt(snumber.substring(3,5));
        int thousands =  Integer.parseInt(snumber.substring(5,7));
        int hundreds =  Integer.parseInt(snumber.substring(7,10));

        String wCrores;
        switch (crores) {
            case 0:
                wCrores = "";
                break;
            case 1 :
                wCrores = convertLessThanOneThousand(crores)
                        + " Crore ";
                break;
            default :
                wCrores = convertLessThanOneThousand(crores)
                        + " Crore ";
        }
        String result =  wCrores;

        String wLakhs;
        switch (lakhs) {
            case 0:
                wLakhs = "";
                break;
            case 1 :
                wLakhs = convertLessThanOneThousand(lakhs)
                        + " Lakh ";
                break;
            default :
                wLakhs = convertLessThanOneThousand(lakhs)
                        + " Lakh ";
        }
        result =  result + wLakhs;

        String wThousands;
        switch (thousands) {
            case 0:
                wThousands = "";
                break;
            case 1 :
                wThousands = convertLessThanOneThousand(thousands)
                        + " Thousand ";
                break;
            default :
                wThousands = convertLessThanOneThousand(thousands)
                        + " Thousand ";
        }
        result =  result + wThousands;



        String wHundreds;
        wHundreds = convertLessThanOneThousand(hundreds);
        result =  result + wHundreds;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    /**
     * testing
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("*** " + RupeesInWord.convert(0));
        System.out.println("*** " + RupeesInWord.convert(1));
        System.out.println("*** " + RupeesInWord.convert(16));
        System.out.println("*** " + RupeesInWord.convert(100));
        System.out.println("*** " + RupeesInWord.convert(118));
        System.out.println("*** " + RupeesInWord.convert(200));
        System.out.println("*** " + RupeesInWord.convert(219));
        System.out.println("*** " + RupeesInWord.convert(800));
        System.out.println("*** " + RupeesInWord.convert(801));
        System.out.println("*** " + RupeesInWord.convert(1316));
        System.out.println("*** " + RupeesInWord.convert(1000000));
        System.out.println("*** " + RupeesInWord.convert(2000000));
        System.out.println("*** " + RupeesInWord.convert(3000200));
        System.out.println("*** " + RupeesInWord.convert(700000));
        System.out.println("*** " + RupeesInWord.convert(9000000));
        System.out.println("*** " + RupeesInWord.convert(9001000));
        System.out.println("*** " + RupeesInWord.convert(123456789));
        System.out.println("*** " + RupeesInWord.convert(2147483647));
        System.out.println("*** " + RupeesInWord.convert(3000000010L));

        /*
         *** zero
         *** one
         *** sixteen
         *** one hundred
         *** one hundred eighteen
         *** two hundred
         *** two hundred nineteen
         *** eight hundred
         *** eight hundred one
         *** one thousand three hundred sixteen
         *** Ten Lakh
         *** Twenty Lakh
         *** Thirty Lakh Two Hundred
         *** Seven Lakh
         *** Ninety Lakh
         *** Ninety Lakh One Thousand
         *** Twelve Crore Thirty Four Lakh Fifty Six Thousand Seven Hundred Eighty Nine
         **
         *** Two Hundred Fourteen Crore Seventy Four Lakh Eighty Three Thousand Six Hundred Forty Seven
         **      four hundred eighty three thousand six hundred forty seven
         *** Three Hundred Crore Ten
         **/
    }






}
