package UnitTests.SwimmingErrorDetectorsTests;

import Domain.Drawing.FactoryDraw;
import Domain.Drawing.IFactoryDraw;
import Domain.Errors.Factories.FactoryElbowError;
import Domain.Errors.Interfaces.IFactoryElbowError;
import DomainLogic.SwimmingErrorDetectors.MaxDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ElbowErrorDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ForearmErrorDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ISkeletonErrorDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.PalmCrossHeadDetector;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import java.util.List;


// TODO
public class MaxDetectorTests extends TestCase {

    private MaxDetector maxDetector;
    private List<ISkeletonErrorDetector> detectors;
    private ElbowErrorDetector elbowErrorDetector;
    private ForearmErrorDetector forearmErrorDetector;
    private PalmCrossHeadDetector palmCrossHeadDetector;

    @BeforeClass
    public void setUp() {
        IFactoryDraw iFactoryDraw = new FactoryDraw();
        IFactoryElbowError iFactoryElbowError = new FactoryElbowError(iFactoryDraw);
        elbowErrorDetector = new ElbowErrorDetector(iFactoryElbowError, 90, 175);
        maxDetector = new MaxDetector(detectors);
    }

}
