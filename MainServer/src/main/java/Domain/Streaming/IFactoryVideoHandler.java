package Domain.Streaming;


import Domain.Drawing.IDraw;

public interface IFactoryVideoHandler {

    IVideoHandler create(IDraw iDraw);

}
