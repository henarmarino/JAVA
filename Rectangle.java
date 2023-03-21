package Project;
/** Child class of Figure that creates the rectangles of the corresponding tiles.
*/
public class Rectangle extends Figure {

         //Attributes

    private int width; 
	private int height;

         //static attributes
    private static final int MAX = 100;
    private static final int MIN = 0;

/** Constructor that creates a rectangle from its width, height and color.
    @param width The width of the rectangle.
    @param height The height of the rectangle.
    @param color The color of the rectangle.
*/
    Rectangle(int width,int height, Color color) {
        this.color = color; //it can access it because color is public in fugure and it extends from Figure
        if(width > MAX)
        this.width = 100;
        else if(width < MIN)
        this.width = 0;
        else
        this.width = width;
        if(height > MAX)
        this.height = 100;
        else if(height < MIN)
        this.height = 0;
        else
        this.height = height;
    }//end constrcutor

    

         
/** Method that calculates the area of the rectangle.
    @return The area of the rectangle.
*/
    public float area() {
        return  ((this.width*this.height)/100); //it is a percentage
    }//end area


/** Method that compares two figures, our figure and another that is passed by parameter. In this case the figures are rectangles.
    @param figure The figure that is compared.
    @return true (if they are equals) or false (if they are differents).
*/
    public boolean isEqualTo(Figure figure) {
        if((this.width == ((Rectangle)figure).width)&&(this.height == ((Rectangle)figure).height) && (this.color.isEqualTo(figure.color)))
         	return true;
        else
        	return false;
    }//end equal


/** Method that displays the attributes of the rectangle, its width, height and color.
    @return The string with its attributes.
*/
    public String toString() {
        return "Width: " + width + ", Height: " + height + ", Color: " + color;
    }//end toString



        //Getters

    public int getWidth() {
        return this.width;
    }
	public int getHeight() {
        return this.height;
    }


	//Setters
	public void setWidth(int width) {
	    if(width > MAX)
            this.width = 100;
        else if(width < MIN)
        	this.width = 0;
        else
        	this.width = width;
    }
   
	public void setHeight(int height) {
	    if(height > MAX)
       		this.height = 100;
        else if(height < MIN)
        	this.height = 0;
        else
       		this.height = height;
    }

  

}//end of the class
