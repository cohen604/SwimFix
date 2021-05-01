package IntegrationTests.ErrorsTests.Factories;

import Domain.Drawing.FactoryDraw;
import Domain.Drawing.IFactoryDraw;
import UnitTests.Erros.Factories.FactoryPalmCrossHeadErrorTests;

public class FactoryPalmCrossHeadErrorIntegrationTests extends FactoryPalmCrossHeadErrorTests {

    @Override
    protected IFactoryDraw setUpFactoryDraw() {
        return new FactoryDraw();
    }
}
