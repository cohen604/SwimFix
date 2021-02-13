package Domain.Streaming;


import Domain.SwimmingData.IDraw;

public interface IFactoryVideoHandler {

    IVideoHandler create(IDraw iDraw);

}
