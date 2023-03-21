package Project;

/** Child class of Figure that creates the circles of the corresponding tiles.
*/
public class Circle extends Figure {
         //Attributes
    private int radius;   //percentage of the radius

    //static attributes
    private static final float PI = 3.1416f;
    private static final int MAX = 50;
    private static final int MIN = 0;


/** Constructor that creates a circle from its radius and color.
    @param radius The radius of the circle.
    @param color The color of the circle.
*/
    Circle(int radius,Color color) {
        this.color = color;
        if(radius > MAX)
        this.radius = 50;
        else if(radius < MIN)
        this.radius = 0;
        else
        this.radius = radius;
    }//end constrcutor



/** Method that calculates the area of the circle.
    @return The area of the circle.
*/
    public float area() {
        return  PI*(this.radius*this.radius)/100;//es un porcentage
    }


/**  Method that compares two circles, our circle and another that is passed by parameter.
    @param figure The figure that is compared.
    @return true (if they are equals) or false (if they are differents).
*/
    public boolean isEqualTo(Figure figure) { //compara nuestro circulo y de otro que nos han pasado por parametro
        if((this.radius == ((Circle)figure).radius)&&(this.color == figure.color))
            return true;
        else
         	return false;
    }


/** Method that displays the attributes of the circle, its radius and color.
    @return The string with its attributes.
*/
    public String toString() {
        return "Radius: " + radius + ", Color: " + color;
    }


    
    //Getters

    public int getRadius() {
        return this.radius;
    }
        
	//Setters	
	public void setRadius(int radius) {
	    if(radius > MAX)
         	this.radius = 50;
        else if(radius < MIN)
        	this.radius = 0;
        else
         	this.radius = radius;
    }
   
   	

}//end of the class
