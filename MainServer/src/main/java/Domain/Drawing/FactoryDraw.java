package Domain.Drawing;

public class FactoryDraw implements IFactoryDraw {

    @Override
    public IDraw create() {
        return new Draw();
    }
}
