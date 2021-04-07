package net.foxdenstudio.foxsuite.foxcore;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {

//    @Test
    public void someTest(){
        String[] parts = "/awoo".split("/+");
        for (int i = 0; i < parts.length; i++) {
            System.out.println(i + ": \"" + parts[i] + "\"");
        }
    }

}
