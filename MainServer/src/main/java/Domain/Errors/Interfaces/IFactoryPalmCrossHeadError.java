package Domain.Errors.Interfaces;

import Domain.Errors.Interfaces.SwimmingError;

public interface IFactoryPalmCrossHeadError {

    SwimmingError createLeft(boolean inside);
    SwimmingError createRight(boolean inside);

}
