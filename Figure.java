package Project;
/** Abstract class that represents the figure of the tile, that is a circle or a rectangle, which are its children classes.
*/
public abstract class Figure{

public Color color;

/** Constructor that creates a figure without arguments.
*/
     Figure(){ //constructor of figure
     }



/**  Abstract method that calculates the area of the figure, it depends on the type of figure, a circle or a rectangle.
     @return The area of the figure.
*/
     public abstract float area();

/**  Abstract method that compares two figures, our figure and another that is passed by parameter. It depends on the type of figure, a circle or a rectangle.
     @param figure The figure that is compared.
     @return true (if they are equals) or false (if they are differents).
*/
     public abstract boolean isEqualTo(Figure figure);

/**  Abstract method that displays the attributes of the figure, it depends on the type of figure, a circle or a rectangle.
     @return The string with its attributes.
*/
     public abstract String toString();





//Getters 
     public Color getColor() {
           return this.color;
}

//Setters
     public void setColor(Color color) {
	   this.color=color;
   }



}//end of class
