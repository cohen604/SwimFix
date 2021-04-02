package DomainLogic.SwimmingErrorDetectors;

public interface IFactoryErrorDetectors {

    ISwimmingTimeErrorDetector createTimeErrorDetector();
    ISwimmingErrorDetector createErrorDetector();

}
