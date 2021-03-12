package DomainLogic.SwimmingErrorDetectors;

public interface IFactoryErrorDetectors {

    SwimmingErrorDetector createElbowErrorDetector(double minAngle, double maxAngle);
    SwimmingErrorDetector createForearmErrorDetector(double minAngle, double maxAngle);
    SwimmingErrorDetector createPalmCrossHeadErrorDetector();

}
