package pl.wroc.edu.nadolny.firstlab;

import org.junit.Test;

import pl.wroc.edu.nadolny.firstlab.BMICounter.CountBMIKGM;
import pl.wroc.edu.nadolny.firstlab.BMICounter.ICountBMI;

import static org.junit.Assert.*;

public class CountBMITest {
    @Test
    public void massUnderZeroIsInvalid(){
        //GIVEN
        float mass = -10f;
        //WHEN
        ICountBMI countBMI = new CountBMIKGM();
        //THEN
        assertFalse(countBMI.isValidMass(mass));
    }
    @Test
    public void massOverLimitIsInvalid(){
        //GIVEN
        float mass = 251f;
        //WHEN
        ICountBMI countBMI = new CountBMIKGM();
        //THEN
        assertFalse(countBMI.isValidMass(mass));
    }


    @Test
    public void heightUnderZeroIsInvalid(){
        //GIVEN
        float height = -1f;
        //WHEN
        ICountBMI countBMI = new CountBMIKGM();
        //THEN
        assertFalse(countBMI.isValidHeight(height));
    }
    @Test
    public void heightOverLimitIsInvalid(){
        //GIVEN
        float height = 3f;
        //WHEN
        ICountBMI countBMI = new CountBMIKGM();
        //THEN
        assertFalse(countBMI.isValidHeight(height));
    }
    @Test
    public void bmiCalculatedGood(){
        //GIVEN
        float height = 2f;
        float mass = 40f;
        //WHEN
        ICountBMI countBMI = new CountBMIKGM();
        //THEN
        try {
            assertEquals(countBMI.countBMI(mass, height), 10f, 0.1f);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
