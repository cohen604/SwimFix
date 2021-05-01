package IntegrationTests.ErrorsTests.Factories;

import Domain.Drawing.FactoryDraw;
import Domain.Drawing.IFactoryDraw;
import UnitTests.Erros.Factories.FactoryForearmErrorTests;

public class FactoryForearmErrorIntegrationTests extends FactoryForearmErrorTests {

    @Override
    protected IFactoryDraw setUpFactoryDraw() {
        return new FactoryDraw();
    }
}
