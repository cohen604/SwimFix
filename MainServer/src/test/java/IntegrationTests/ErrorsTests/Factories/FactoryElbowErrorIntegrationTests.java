package IntegrationTests.ErrorsTests.Factories;

import Domain.Drawing.FactoryDraw;
import Domain.Drawing.IDraw;
import Domain.Drawing.IFactoryDraw;
import Domain.Errors.Factories.FactoryElbowError;
import Domain.Errors.Interfaces.IFactoryElbowError;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Errors.LeftElbowError;
import Domain.Errors.RightElbowError;
import UnitTests.Erros.Factories.FactoryElbowErrorTests;
import UnitTests.Erros.Factories.FactoryForearmErrorTests;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.Iterator;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FactoryElbowErrorIntegrationTests extends FactoryElbowErrorTests {

    @Override
    protected IFactoryDraw setUpIFactoryDraw() {
        return new FactoryDraw();
    }
}
