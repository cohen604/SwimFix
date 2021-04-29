package UnitTests.SwimmingErrorDetectorsTests;

import Domain.Drawing.FactoryDraw;
import Domain.Drawing.IFactoryDraw;
import Domain.Errors.Factories.FactoryElbowError;
import Domain.Errors.Factories.FactoryForearmError;
import Domain.Errors.Factories.FactoryPalmCrossHeadError;
import Domain.Errors.Interfaces.IFactoryElbowError;
import Domain.Errors.Interfaces.IFactoryPalmCrossHeadError;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SwimmingErrorDetectors.RegularDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ElbowErrorDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ForearmErrorDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ISkeletonErrorDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.PalmCrossHeadDetector;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RegularDetectorTests extends TestCase {

    private RegularDetector regularDetector;
    private List<ISkeletonErrorDetector> detectors;
    private ElbowErrorDetector elbowErrorDetector;
    private ForearmErrorDetector forearmErrorDetector;
    private PalmCrossHeadDetector palmCrossHeadDetector;
    private SkeletonsForTests skeletonsForTests;



    @BeforeClass
    public void setUp() {
        setUpErrors();
        setUpErrorsList();
        regularDetector = new RegularDetector(detectors);
        skeletonsForTests = new SkeletonsForTests();
    }

    private void setUpErrors() {
        IFactoryDraw iFactoryDraw = new FactoryDraw();
        // elbow
        IFactoryElbowError iFactoryElbowError = new FactoryElbowError(iFactoryDraw);
        this.elbowErrorDetector = new ElbowErrorDetector(iFactoryElbowError, 90, 175);
        // forearm
        FactoryForearmError iFactoryForearmError = new FactoryForearmError(iFactoryDraw);
        this.forearmErrorDetector = new ForearmErrorDetector(iFactoryForearmError, -10, 45);
        // palm
        IFactoryPalmCrossHeadError iFactoryPalmCrossHeadError = new FactoryPalmCrossHeadError(iFactoryDraw);
        this.palmCrossHeadDetector = new PalmCrossHeadDetector(iFactoryPalmCrossHeadError);
    }

    private void setUpErrorsList() {
        detectors = new LinkedList<>();
        this.detectors.add(this.elbowErrorDetector);
        this.detectors.add(this.forearmErrorDetector);
        this.detectors.add(this.palmCrossHeadDetector);
    }

    private List<ISwimmingSkeleton> getSkeletonsListNoErrors() {
        List<ISwimmingSkeleton> skeletonList = new LinkedList<>();
        int size = 25;
        for (int i = 0; i < size; i++) {
            ISwimmingSkeleton skeleton = skeletonsForTests.getSkeletonNoError();
            skeletonList.add(skeleton);
        }
        return skeletonList;
    }


    public void testDetectNoErrors() {
        List<ISwimmingSkeleton> skeletonList = getSkeletonsListNoErrors();
        Map<Integer, List<SwimmingError>> errorMap = regularDetector.detect(skeletonList);
        for (List<SwimmingError> listOfErrors: errorMap.values()) {
            assertEquals(0,listOfErrors.size());
        }
    }

    // TODO - add more test for other errors



}
