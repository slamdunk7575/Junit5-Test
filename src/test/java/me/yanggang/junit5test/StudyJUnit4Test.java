package me.yanggang.junit5test;

import org.junit.Before;
import org.junit.jupiter.api.Test;

public class StudyJUnit4Test {

    @Before
    public void before() {
        System.out.println("Before");
    }

    @Test
    public void CreateTest1() {
        System.out.println("CreateTest1");
    } 

    @Test
    public void CreateTest2() {
        System.out.println("CreateTest1");
    }

}
