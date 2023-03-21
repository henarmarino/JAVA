package Project;
/** Interface that manages the luminosity changes of the colors of tiles, regions and mosaics. It is implemented by Tile, RectangularRegion and Mosaic.
 */
public interface Luminosity {

/** Abstract method that changes the luminosity of a tile, a region or a mosaic.
@param value A integer which is the variation that will be added or subracted to the rgb color parameters.
 */
public abstract void changeLuminosity(int value);

}
