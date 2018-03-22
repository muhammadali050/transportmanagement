package com.climesoftt.transportmanagement.model;

import java.util.Random;

/**
 * Created by AtoZ on 3/21/2018.
 */

public class GenerateRandomNumber {

    public static int randomNum() {
        Random r = new Random( System.currentTimeMillis() );
        int firstNum = 1000 + r.nextInt(2000);
        int secondNum = 100 + r.nextInt(500) ;
        int thirdNum =  501 + r.nextInt(999);
        int fourthNum =  1 + r.nextInt(98);
        int randomNum = firstNum+secondNum+thirdNum+fourthNum;

        return randomNum;
    }
}
