package Domain.Streaming;


import Domain.SwimmingData.Drawing.IDraw;

public interface IFactoryVideoHandler {

    IVideoHandler create(IDraw iDraw);

}
