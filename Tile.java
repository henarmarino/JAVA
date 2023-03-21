package Project;

/** Class that creates the tiles, the simplest structure of the mosaic.
*/
public class Tile implements Luminosity {

	private Color color;
	private Figure figure;
	private String position;
	private int status;
	private static int wTile; 
	private static int hTile;
	private int luminosityChange;

	public static String RIGHT="R";
	public static String LEFT="L";
	public static String UP="U";
	public static String DOWN="D";
	public static String CENTERED="C";
	
	public static int OFF=0;
	public static int ON=1;
	public static int FIGURE_OFF=2;

/** Constructor 1, which creates a tile with a figure from its color, type of figure, position and status.
	@param color The background color of the tile.
	@param figure The figure which its into the tile.
	@param position The position of the tile into the mosaic.
	@param status The status of the tile: 1 (Tile off), 2 (Tile on), 3 (Tile with figure in black).
*/	
  	public Tile(Color color, Figure figure, String position, int status) {
		this.color = color;
		this.figure = figure;
		this.position = position;
		this.status = status;
		this.luminosityChange=0;
    }


/**Constructor 2, which creates an empty tile, without figure, from its color and status.
	@param color The background color of the tile.
	@param status The status of the tile: 1 (Tile off), 2 (Tile on), 3 (Tile with figure in black).
*/
	public Tile(Color color, int status) { //Integer luminosityChange
        this.color = color;
        this.status = status;
        this.luminosityChange=0;
    }

/** Method that compares all the attributes of two tiles to check if they are equal.
	@return true (equals) or false (differents).
*/
	public boolean isEqualTo(Tile tile){
		if(!this.color.isEqualTo(tile.color))
			return false;

		if((this.status) != (tile.status))
			return false;		
		
		if((this.hasFigure()) != (tile.hasFigure()))
			return false;

		if((this.hasFigure() == false)&&(tile.hasFigure()==false))
			return true; //mismo color,status y sin figura

		if(((this.figure instanceof Circle) && (tile.figure instanceof Rectangle)) || ((this.figure instanceof Rectangle) && (tile.figure instanceof Circle))) 
			return false; //tienen diferentes figuras
		
		if((this.figure instanceof Rectangle) && (tile.figure instanceof Rectangle)){  //los dos son rectángulo
			if(!this.figure.isEqualTo(tile.getFigure())){
				return false; 
			}
			else if(!this.position.equals(tile.getPosition()))
				return false;
			else 
				return true;
		}

		if((this.figure instanceof Circle)&&(tile.figure instanceof Circle)){ //los dos son círculo
			if(!this.figure.isEqualTo(tile.getFigure())){
				return false; 
			}
			else if(!this.position.equals(tile.getPosition()))
				return false;
			else 
				return true;
		}

		return true;
	}	
	

/** Method that displays the attributes of the tile: position, color and figure.
 	@return The string with its attributes.
*/
	public String toString() {
		if ((this.position!=null) && (this.figure instanceof Rectangle))
	    	return "Position: " + position + ", Color: " + color + ", Rectangle: " + figure;
		if ((this.figure instanceof Circle) && (this.position!=null))
	    	return "Position: " + position + ", Color: " + color + ", Circle: " + figure;
		else
            return " Color: " + color;
	}


/** Method that attributes to the width and height parameters their values.
	@param w The width of the tile.
	@param h The height of the tile.
*/
	public static void setSize(int w, int h){
		wTile=w;
		hTile=h;
	}


/** Method that checks if the tile has figure.
 	@return true (has figure) or false (has not figure).
*/
	public boolean hasFigure(){
		if(this.figure==null)
			return false;
		else 
			return true;
	
	}


/** Method that changes the luminosity in a particular tile.
	@param value A integer which is the variation that will be added or subracted to the rgb color parameters.
*/
	public void changeLuminosity(int value) {
 		//System.out.println("value dentro del tile"+value);
		this.color.setR((this.color.getR() + value)%256); //We set the value of r color adding the value+r
			if(this.color.getR()<0) // If it is negative we add 256
				this.color.setR(this.color.getR()+256);
		this.color.setG((this.color.getG() + value)%256);
			if(this.color.getG()<0)
				this.color.setG(this.color.getG()+256);
		this.color.setB((this.color.getB() + value)%256);
			if(this.color.getB()<0)
				this.color.setB(this.color.getB()+256);
		if (this.hasFigure()){
			//System.out.println("antes Color figure"+getFigure().toString());
			getFigure().color.setR((getFigure().color.getR() + value)%256);
				if(getFigure().color.getR()<0)
					getFigure().color.setR(getFigure().color.getR()+256);
				getFigure().color.setG((getFigure().color.getG() + value)%256);

				if(getFigure().color.getG()<0)
					getFigure().color.setG(getFigure().color.getG()+256);
				getFigure().color.setB((getFigure().color.getB() + value)%256);
				
				if(getFigure().color.getB()<0)
					getFigure().color.setB(getFigure().color.getB()+256);
				//System.out.println(" despues Color figure"+getFigure().toString());
		}
	
	}


/** Method that changes the status of a particular tile.
	@param value A integer: 0 (tile off), 1 (Tile on) or 2 (Figure off).
*/	
	public void changeStatus(int value){
		this.status= value;
	}


//Getters
	
  	public static int getWTile() {
       return Tile.wTile; //static por eso lo llamamos con la class
	}

  	public static int getHTile() {
       return Tile.hTile; //static por eso lo llamamos con la class
	}
  
  	public Color getColor() {
      return this.color;
	}

  	public String getPosition() {
      return this.position;
	}

	public int getStatus() {
        return this.status;
	}

  	public Figure getFigure() {
  	    return this.figure;
	}

//Setters

  	public static void setWTile(int wTile) {
	   Tile.wTile=wTile; //static por eso lo llamamos con la class
	}  
	
	public static void setHTile(int hTile) {
	   Tile.hTile=hTile; //static por eso lo llamamos con la class
	}
  	
	public void setStatus(int status) {
	   this.status=status;
	}
   	
	public void setColor(Color color) {
	   this.color=color;
	}

  	public void setPosition(String position) {
	   this.position=position;
	}

}//end class
