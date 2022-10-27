package com.example.system_dev_lab;

import org.junit.Test;

import Activities.MyCalculator;

import static org.junit.Assert.assertEquals;

public class MyCalculatorTest {

    @Test
     public void calculatorTest(){
        MyCalculator myCalculator = new MyCalculator();

        assertEquals(10,myCalculator.calculateSum(4, 6));
    }

}