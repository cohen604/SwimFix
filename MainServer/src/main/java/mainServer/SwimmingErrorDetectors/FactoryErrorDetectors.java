package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.Errors.PalmCrossHeadError;

public class FactoryErrorDetectors implements IFactoryErrorDetectors {

    @Override
    public SwimmingErrorDetector createElbowErrorDetector(double minAngle, double maxAngle) {
        IFactoryDraw iFactoryDraw = new FactoryDraw();
        IFactoryElbowError iFactoryElbowError = new FactoryElbowError(iFactoryDraw);
        return new ElbowErrorDetector(iFactoryElbowError,minAngle, maxAngle);
    }

    @Override
    public SwimmingErrorDetector createForearmErrorDetector(double minAngle, double maxAngle) {
        IFactoryDraw iFactoryDraw = new FactoryDraw();
        IFactoryForearmError iFactoryForearmError = new FactoryForearmError(iFactoryDraw);
        return new ForearmErrorDetector(iFactoryForearmError,minAngle,maxAngle);
    }

    @Override
    public SwimmingErrorDetector createPalmCrossHeadErrorDetector() {
        IFactoryDraw iFactoryDraw = new FactoryDraw();
        IFactoryPalmCrossHeadError iFactoryPalmCrossHeadError = new FactoryPalmCrossHeadError(iFactoryDraw);
        return new PalmCrossHeadDetector(iFactoryPalmCrossHeadError);
    }
}
