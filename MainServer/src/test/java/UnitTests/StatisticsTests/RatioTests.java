package UnitTests.StatisticsTests;

import Domain.StatisticsData.Ratios;
import junit.framework.TestCase;
import org.junit.BeforeClass;

public class RatioTests extends TestCase {

    private Ratios ratios;

    @BeforeClass
    public void setUp() {
        int frames = 100;
        ratios = new Ratios(frames);
    }
    
    public void testGetModelTP() {
        assertEquals(0, ratios.getModelTP());
    }

    public void testGetModelTN() {
        assertEquals(0, ratios.getModelTN());
    }

    public void testGetModelFP() {
        assertEquals(0, ratios.getModelFP());
    }

    public void testGetModelNF() {
        assertEquals(0, ratios.getModelFN());
    }

    public void testGetModelAndInterTP() {
        assertEquals(0, ratios.getModelAndInterTP());
    }

    public void testGetModelAndInterTN() {
        assertEquals(0, ratios.getModelAndInterTN());
    }

    public void testGetModelAndInterFP() {
        assertEquals(0, ratios.getModelAndInterFP());
    }

    public void testGetModelAndInterFN() {
        assertEquals(0, ratios.getModelAndInterFN());
    }

    public void testGetActualCount() {
        assertEquals(0, ratios.getActualCount());
    }

    public void testGetModelCount() {
        assertEquals(0, ratios.getModelCount());
    }

    public void testGetModelAndInterpolationCount() {
        assertEquals(0, ratios.getModelAndInterpolationCount());
    }

    public void testAddActual() {
        ratios.addActual();
        assertEquals(1, ratios.getActualCount());
        ratios.addActual();
        assertEquals(2, ratios.getActualCount());
    }

    public void testAddModel() {
        ratios.addModel();
        assertEquals(1, ratios.getModelCount());
        ratios.addModel();
        assertEquals(2, ratios.getModelCount());
    }

    public void testAddModelAndInterpolation() {
        ratios.addModelAndInterpolation();
        assertEquals(1, ratios.getModelAndInterpolationCount());
        ratios.addModelAndInterpolation();
        assertEquals(2, ratios.getModelAndInterpolationCount());
    }

    public void testAddModelTP() {
        ratios.addModelTP();
        assertEquals(1, ratios.getModelTP());
        ratios.addModelTP();
        assertEquals(2, ratios.getModelTP());
    }

    public void testAddModelTN() {
        ratios.addModelTN();
        assertEquals(1, ratios.getModelTN());
        ratios.addModelTN();
        assertEquals(2, ratios.getModelTN());
    }

    public void testAddModelFP() {
        ratios.addModelFP();
        assertEquals(1, ratios.getModelFP());
        ratios.addModelFP();
        assertEquals(2, ratios.getModelFP());
    }

    public void testAddModelFN() {
        ratios.addModelFN();
        assertEquals(1, ratios.getModelFN());
        ratios.addModelFN();
        assertEquals(2, ratios.getModelFN());
    }

    public void testModelAndInterFN() {
        ratios.addModelAndInterFN();
        assertEquals(1, ratios.getModelAndInterFN());
        ratios.addModelAndInterFN();
        assertEquals(2, ratios.getModelAndInterFN());
    }

    public void testModelAndInterTP() {
        ratios.addModelAndInterTP();
        assertEquals(1, ratios.getModelAndInterTP());
        ratios.addModelAndInterTP();
        assertEquals(2, ratios.getModelAndInterTP());
    }

    public void testModelAndInterTN() {
        ratios.addModelAndInterTN();
        assertEquals(1, ratios.getModelAndInterTN());
        ratios.addModelAndInterTN();
        assertEquals(2, ratios.getModelAndInterTN());
    }

    public void testModelAndInterFP() {
        ratios.addModelAndInterFP();
        assertEquals(1, ratios.getModelAndInterFP());
        ratios.addModelAndInterFP();
        assertEquals(2, ratios.getModelAndInterFP());
    }

    public void testGetRatioModelTPSecondElementIsZero() {
        assertEquals(0.0, ratios.getRatioModelTP());
    }

    public void testGetRatioModelTNSecondElementIsZero() {
        assertEquals(0.0, ratios.getRatioModelTN());
    }

    public void testGetRatioModelFPSecondElementIsZero() {
        assertEquals(0.0, ratios.getRatioModelFP());
    }

    public void testGetRatioModelFNSecondElementIsZero() {
        assertEquals(0.0, ratios.getRatioModelFN());
    }

    public void testGetRatioModelAndInterTPSecondElementIsZero() {
        assertEquals(0.0, ratios.getRatioModelAndInterTP());
    }

    public void testGetRatioModelAndInterTNSecondElementIsZero() {
        assertEquals(0.0, ratios.getRatioModelAndInterTN());
    }

    public void testGetRatioModelAndInterFPSecondElementIsZero() {
        assertEquals(0.0, ratios.getRatioModelAndInterFP());
    }

    public void testGetRatioModelAndInterFNSecondElementIsZero() {
        assertEquals(0.0, ratios.getRatioModelAndInterFN());
    }

    //------------
    public void testGetRatioModelTP() {
        for (int i = 0; i < 100; i++) {
            ratios.addActual();
            if(i % 2 == 0)
                ratios.addModelTP();
        }
        assertEquals(50.0, ratios.getRatioModelTP());
    }

    public void testGetRatioModelTN() {
        for (int i = 0; i < 50; i++) {
            ratios.addActual();
            if(i % 2 == 0)
                ratios.addModelTN();
        }
        assertEquals(50.0, ratios.getRatioModelTN());
    }

    public void testGetRatioModelFP() {
        for (int i = 0; i < 50; i++) {
            ratios.addActual();
            if(i % 2 == 0)
                ratios.addModelFP();
        }
        assertEquals(50.0, ratios.getRatioModelFP());
    }
}
