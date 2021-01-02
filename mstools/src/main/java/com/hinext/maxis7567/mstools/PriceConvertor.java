package com.hinext.maxis7567.mstools;

import java.text.DecimalFormat;

import static com.hinext.maxis7567.mstools.FaNumToEnNum.convertTofa;


public class PriceConvertor {
    public static String Convert(float price){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        decimalFormat.setMaximumFractionDigits(1);
        return (decimalFormat.format(price));
    }

}
