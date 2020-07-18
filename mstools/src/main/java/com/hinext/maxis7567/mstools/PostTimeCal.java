package com.hinext.maxis7567.mstools;

public class PostTimeCal {

    public static String Calculator(long unix){
        if (unix<60){
            return (unix +" second");
        }else if(unix < 3600){
            return (unix / 60 +"minute");
        }else if(unix < 86400){
            return (unix / 3600 +"hour");
        }else if(unix < 604800){
            return (unix / 86400 +"days");
        }else if (unix < 2592000){
            return (unix / 604800 +"weak");
        }else if (unix < 31104000){
            return (unix / 2592000 +"month");
        }else {
            return (unix / 31104000 +"year");
        }
    }
}
