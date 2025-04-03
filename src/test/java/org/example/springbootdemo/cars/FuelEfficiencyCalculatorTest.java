package org.example.springbootdemo.cars;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FuelEfficiencyCalculatorTest {
    private final FuelEfficiencyCalculator calculator=new FuelEfficiencyCalculator();
    @Test
    public void fuelEfficiencyCalculatorTest() {
        double horsePower=400;
        double capacity=4.4;
        double weightKg=1800;

        double expectedResult=0.8;
        double actualResult=calculator.calculateFuelEfficiency(horsePower, capacity, weightKg);
        Assertions.assertEquals(expectedResult, actualResult);


    }

    @Test
    public void shouldThrowExceptionIfInvalidHorsePower() {
        double horsePower=-1;
        double capacity=4.4;
        double weightKg=1800;

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> calculator.calculateFuelEfficiency(horsePower, capacity, weightKg));

    }

    @Test
    public void shouldThrowExceptionIfInvalidCapacity() {
        double horsePower=400;
        double capacity=-1;
        double weightKg=1800;

        Assertions.assertThrowsExactly(IllegalArgumentException.class,() -> calculator.calculateFuelEfficiency(horsePower, capacity, weightKg));
    }
}
