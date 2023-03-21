package Project;

/** Class used to define colors of figures and the backgrounds of tiles.
*/
public class Color {

    // Attributes
    private int r;
    private int g;
    private int b;

    // Static Attributes
    private static final int MAX = 255;


/** Constructor that creates a color from its values r, g and b.
    @param r The value of the r.
    @param g The value of the g.
    @param b The value of the b.
*/
    Color(int r, int g, int b) {
        if ((r > MAX) || (g > MAX) || (b > MAX) ) {
            this.r= MAX;
            this.g= MAX;
            this.b= MAX;
        }
        else {
            this.r= r;
            this.g= g;
            this.b= b;
        }
    }


/** Method that creates the color white.
    @return The color white with its values r, g and b.
*/
    public static final Color WHITE() {
        return new Color(255,255,255);
    }


/** Method that creates the color black.
    @return The color black with its values r, g and b.
*/
	public static final Color BLACK() {
       return new Color(0,0,0);
    }


/** Method that creates the color red.
    @return The color red with its values r, g and b.
*/
    public static final Color RED() {
        return new Color(255,0,0);
    }

/** Method that creates the color green.
    @return The color green with its values r, g and b.
*/
    public static final Color GREEN() {
        return new Color(0,255,0);
    }


/** Method that creates the color blue.
    @return The color blue with its values r, g and b.
*/
    public static final Color BLUE() {
        return new Color(0,0,255);
    }


/** Method that creates the color yellow.
    @return The color yellow with its values r, g and b.
*/
    public static final Color YELLOW() {
        return new Color(255,255,0);
    }


/** Method that creates the color magenta.
    @return The color magenta with its values r, g and b.
*/
    public static final Color MAGENTA() {
        return new Color(255,0,255);
    }


/**  Method that creates the color cyan.
    @return The color cyan with its values r, g and b.
*/
    public static final Color CYAN() {
        return new Color(0,255,255);
    }
    

/**  Method that compares two colors, our color and one that is passed by parameter.
    @param color The color taht is compared.
    @return true (if they are equals) or false (if they are differents).
*/
    public boolean isEqualTo(Color color) { // Color clase, color el parametro      boolean: returns true or flse
        if ( (color.r ==this.r) && (color.g == this.g) && (color.b == this.b) ) {
            return true;
        } else {
        	return false;
        }
    }


/** Method that displays the attributes of the color.
    @return The string with the values r, g and b of the color.
*/
    public String toString() { 
        return "R"+r+"G"+g+"B"+b;
    }



    // Getters And Setters
    
    public int getR() { 
        return this.r;
    }

    public int getG() {
        return this.g;
    }

    public int getB() {
        return this.b;
    }
    
    public void setR(int r) {
	   this.r=r;
    }

   public void setG(int g) {
	   this.g=g;
    }   
   public void setB(int b) {
	   this.b=b;
    }
 

}//end of class
