package Project;

import java.lang.String;
import java.io.*;
import java.util.*;

/** Class that creates the Mosaic, the final structure which is made of rectangular regions.
*/
public class Mosaic implements Luminosity {

    // Attributes 
	private int rows;
	private int columns;
	private int hMosaic;
	private int wMosaic;
	private List<RectangularRegion> regions;
	
	//Tile[][] tiles;
	Map<Coordinate, Tile> mapTiles;
	

/** Constructor that checks if the file exits and reads it line by line, if it is possible, creating a tile from each line until we get to the end of the file.
 	@param file Text document where tiles are declared.
*/	 
    Mosaic(String file) {

		Scanner input = null; 
		try{
			input= new Scanner (new FileInputStream(file));
		} catch (FileNotFoundException e) {  
			PrintWriter error=null;
			try{
				error= new PrintWriter(new FileOutputStream(new File("Project/error.txt")));
				//System.out.println("File open error");  
			} catch (FileNotFoundException e2) {
				System.exit(-1);
			}
			error.print("FileNotFoundException");
			error.close();
			System.exit(-1);
        } //catch (TileOutOfBoundsException e2) {

		//read line by line
		String line=null;
		int counter=0;
		while (input.hasNextLine()) {
			line = input.nextLine(); 
			if (!line.startsWith("*")) { //we jump comment lines (*)
				if (counter==0)
					createMosaic(line);
				else 
					createTiles(line);
				counter++;
			}		
		}
		input.close(); 
		//System.out.println ("mapTiles: " + this.mapTiles);
	}//end constructor


/** Method that creates a mosaic using the information read from the initial file.
 	@param line String created at reading the initial file, where the attributes of the mosaic are specified.
*/
	private void createMosaic(String line) { //we only receive the first line of the file
	
        if (line.length() > 0){
        	line = line.trim();
              
            String mosaicSize = line.substring(0, line.indexOf(",")); //3x3
            String tileSize = line.substring(line.indexOf(",")+1, line.length()).trim(); //50x50

            int rowsIndex = mosaicSize.indexOf("x");

            String rows = mosaicSize.substring(0, rowsIndex);
            int rowsAsInt = Integer.parseInt(rows);

            if (rowsAsInt > 0 && rowsAsInt < 20){ //check that values are admited
                this.rows = rowsAsInt;
            }

            //valor desde donde esta la x+1 hasta donde esta la ,
            int columnIndex = mosaicSize.indexOf(",", rowsIndex);
            String columns = mosaicSize.substring(rowsIndex+1, mosaicSize.length());
            int columnsAsInt = Integer.parseInt(columns);

            if (columnsAsInt > 0 && columnsAsInt < 20){ //check that values are admited
                this.columns = columnsAsInt;
            }
            
            //System.out.println ("Row " + rowsAsInt + " and column " + columnsAsInt);

            this.hMosaic = Integer.parseInt(tileSize.substring(0, tileSize.indexOf("x")));
            this.wMosaic = Integer.parseInt(tileSize.substring(tileSize.indexOf("x")+1, tileSize.length()));
            
            //System.out.println ("hMosaic " + hMosaic + " wMosaic " + wMosaic);
            
            //this.tiles = new Tile[this.rows][this.columns];
			this.initialize();
        }
    }
    

/** Method that creates the tiles using the information read from the file.
 	 @param line String created at reading the initial file, where the attributes of the tile are specified.
*/
    private void createTiles(String line) { //we receive all the content of the file without the first line
    	if (this.mapTiles == null)
    		this.mapTiles = new TreeMap<Coordinate, Tile>();
    	
    	if (line.length() > 0){
    		line = line.trim();
    		int tilePositionEnd = line.indexOf(":");
    		String tilePosition = line.substring(0, tilePositionEnd);
    		
            int row = Integer.parseInt(tilePosition.substring(1, line.indexOf(",")));;
            int column = Integer.parseInt(tilePosition.substring(tilePosition.indexOf(",")+1, tilePosition.length()-1));
            	//System.out.println ("Row " + row + " and column " + column);
            	try{
            		if((row<1 || row>this.rows) || (column<1 || column>this.columns))
						throw new TileOutOfBoundsException();
				}catch(TileOutOfBoundsException e1){
					PrintWriter error=null;
					try{
						error= new PrintWriter(new FileOutputStream(new File("Project/error.txt")));
						//System.out.println("File open error");  
					} catch (FileNotFoundException e) {
						System.exit(-1);
					}
					error.print("TileOutOfBoundsException");
					error.close();
					System.exit(-1);
					//System.out.println("File not found");
                }
                
            int status = Integer.parseInt(line.substring(tilePositionEnd+1, tilePositionEnd+2));
           	//System.out.println ("Status " + status);
           	
            String tile = line.substring(line.indexOf("{"), line.length()); //{R0G0B255,CIR:{R255G0B0,R,35}}
            	//System.out.println ("Tile " + tile);
            
            int r = Integer.parseInt(tile.substring(tile.indexOf("R")+1,tile.indexOf("G")));
            int g = Integer.parseInt(tile.substring(tile.indexOf("G")+1,tile.indexOf("B")));

			if (tile.contains(",")){
				int b = Integer.parseInt(tile.substring(tile.indexOf("B")+1,tile.indexOf(",")));
					//System.out.println ("R " + r + " G " + g + " B " + b);
				
				String figure = tile.substring(tile.indexOf(",")+1, tile.indexOf(":"));
					//System.out.println ("Figure " + figure);
				
				if (figure.equals("CIR")) {//(1,1):1{R0G0B255,CIR:{R255G0B0,R,35}}
					int figurePositionEnd = tile.indexOf(",")+5;
	
					int circleR = Integer.parseInt(tile.substring(tile.indexOf("R", figurePositionEnd)+1,tile.indexOf("G",figurePositionEnd)));				
           			int circleG = Integer.parseInt(tile.substring(tile.indexOf("G", figurePositionEnd)+1,tile.indexOf("B",figurePositionEnd)));
           			int circleB = Integer.parseInt(tile.substring(tile.indexOf("B", figurePositionEnd)+1,tile.indexOf(",", figurePositionEnd)));
           			//System.out.println ("Circle: R " + circleR + " G " + circleG + " B " + circleB);
           			
           			String circlePosition = tile.substring(tile.indexOf(",", figurePositionEnd)+1, tile.indexOf(",", figurePositionEnd)+2);
           			//System.out.println ("Position: " + circlePosition);
           			           			
           			int radius = Integer.parseInt(tile.substring(tile.indexOf(",",figurePositionEnd)+3,tile.indexOf("}")));
           			//System.out.println ("Radius: " + radius);

           			this.mapTiles.put(new Coordinate (row, column), new Tile(new Color(r,g,b), new Circle(radius, new Color(circleR, circleG, circleB)), circlePosition, status));
				}	
				else if (figure.equals("REC")) { //(1,2):1{R236G250B5,REC:{R100G186B100,D,99,25}}
					int figurePositionEnd = tile.indexOf(",")+5;

					int rectangleR = Integer.parseInt(tile.substring(tile.indexOf("R", figurePositionEnd)+1,tile.indexOf("G",figurePositionEnd)));
           			int rectangleG = Integer.parseInt(tile.substring(tile.indexOf("G", figurePositionEnd)+1,tile.indexOf("B",figurePositionEnd)));
           			int rectangleB = Integer.parseInt(tile.substring(tile.indexOf("B", figurePositionEnd)+1,tile.indexOf(",", figurePositionEnd)));	
		       		//System.out.println ("Rectangle: R " + rectangleR + " G " + rectangleG + " B " + rectangleB);
		       		           			
		       		String rectanglePosition= tile.substring(tile.indexOf(",", figurePositionEnd)+1, tile.indexOf(",", figurePositionEnd)+2);
		      		int rectanglePositionEnd = tile.indexOf(rectanglePosition, figurePositionEnd)+2;
		       		//System.out.println ("Position: " + rectanglePosition);
		       		
		       		int rWidth = Integer.parseInt(tile.substring(tile.indexOf(",", figurePositionEnd)+3,tile.indexOf("}")-3));		       		
		       		int rHeight = Integer.parseInt(tile.substring(tile.indexOf(",", rectanglePositionEnd)+1,tile.indexOf("}")));
		       		//System.out.println ("rWidth: " + rWidth + " rHeight " + rHeight);

		       		this.mapTiles.put(new Coordinate (row, column), new Tile(new Color(r,g,b), new Rectangle(rHeight, rWidth, new Color(rectangleR, rectangleG, rectangleB)), rectanglePosition, status));
				} 

            } else {
            	int b = Integer.parseInt(tile.substring(tile.indexOf("B")+1,tile.length()-1)); 
            	this.mapTiles.put(new Coordinate (row, column), new Tile(new Color(r,g,b), status));
            }				
		}
	
	}

   /** Method that initializes the mosaic map with its coordinates and tiles.
   */
    public void initialize() { 
    	this.mapTiles = new TreeMap<Coordinate, Tile>(); //create the map, in each column and row we will have a coordinate and a, white and status 0, tile
		for (int i=1; i<=this.rows; i++){
			for (int y=1; y<=this.columns; y++){
				Coordinate c = new Coordinate(i, y);
				this.mapTiles.put(c, new Tile(Color.WHITE(), 0));
			}
		}
    }

  /** Method that displays the attributes of the mosaic (rows, columns, height and width) and the attributes of the tiles that 
  it contains (rows, columns, status, color and, if it has, the figure and its specific attributes (color, position, radius or height and width).
  @return A String with its attributes.
  */
	public String toString() {			
	String output = new String("");
		output=output.concat(this.rows + "x" + this.columns + "," + this.hMosaic + "x" + this.wMosaic+"\n");

		for (Map.Entry<Coordinate, Tile> entry: this.mapTiles.entrySet()){ //run the map
			Coordinate c = entry.getKey();
			Tile t = entry.getValue();
			
			if(t.getStatus()==0){ //All in black
				t.getColor().setR(0);
				t.getColor().setG(0);
				t.getColor().setB(0);
				if(t.hasFigure()){
					t.getFigure().getColor().setR(0);
					t.getFigure().getColor().setG(0);
					t.getFigure().getColor().setB(0);
				}
			}
			if(t.getStatus()==2){ //Figure black
				if(t.hasFigure()){
					t.getFigure().getColor().setR(0);
					t.getFigure().getColor().setG(0);
					t.getFigure().getColor().setB(0);
				}
			}
			
			
			if (t != null && t.getFigure() != null && t.getFigure() != null){
				if (t.getFigure() instanceof Circle){
					output=output.concat("(" + (c.getRow()) + "," + (c.getColumn()) + "):" + t.getStatus() + "{" + t.getColor() + ",CIR:{" + ((Circle) t.getFigure()).getColor() + "," + t.getPosition() + "," + ((Circle)t.getFigure()).getRadius() + "}}\n");
				}
				
				if (t.getFigure() instanceof Rectangle){
					output=output.concat( "(" + (c.getRow()) + "," + (c.getColumn()) + "):" + t.getStatus() + "{" + t.getColor() + ",REC:{" + ((Rectangle) t.getFigure()).getColor() + "," + t.getPosition() + "," + ((Rectangle) t.getFigure()).getHeight() + "," + ((Rectangle) t.getFigure()).getWidth() + "}}\n");
				}
			}
			
			else if (t != null && t.getFigure() == null){ //empty
				output=output.concat( "(" + (c.getRow()) + "," + (c.getColumn()) + "):" + t.getStatus() + "{" + t.getColor() + "}\n");
					
			}	
		}
		
 		return output.trim();
	}


/** Method that saves into a new file the attributes of our mosaic, printing in each line the attributes of each tile that compones the whole mosaic.
	@param file The new file where we will print the attributes of our mosaic.
*/
	public void saveToFile(String file){
		PrintWriter output=null;
	
		try{
			output=new PrintWriter (new FileOutputStream(file));
			output.println(toString()); //we print in the file the toString content
			output.close();
		}
		catch(FileNotFoundException e){
			PrintWriter error=null;
			try{
				error= new PrintWriter(new FileOutputStream(new File("Project/error.txt")));
				//System.out.println("File open error");  
			} catch (FileNotFoundException e2) {
				System.exit(-1);
			}
			error.print("FileNotFoundException");
			error.close();
			System.exit(-1);
		}
		
	}

/** Method that adds a new region, passed by argument, to the regions attribute (a list).
  	@param r The region that we will add.
*/
	public void addRegion(RectangularRegion r){
		if (this.regions == null) {
			this.regions =new ArrayList<RectangularRegion>();
		}
		this.regions.add(r);
	}
    
/** Method that sorts the regions by area in ascending order.
*/
	public void sortRegionsByAreaAsc(){
		if (this.regions != null) {
			Collections.sort(this.regions, new CompareArea());
		}
	}

/** Method that receiving the name of a region can return it to us.
	@param name String with the name of the region. 
  	@return r The selected region.
*/
	public RectangularRegion getRegion(String name){
		Iterator<RectangularRegion> collectRegions = this.regions.iterator();//We save all our collection of regions in an Iterator
		RectangularRegion r = null;
		while(collectRegions.hasNext()){ //we run the collection of regions checking if some name coincides with the region that we are looking for.
			r = collectRegions.next();
			if (name.equals(r.getName())){
				return r;
			}
		}
	    return null;

	}

/** Method that displays the attributes of the regions that compones the mosaic.
*/
	public void toStringRegions(){
		for (RectangularRegion r : this.regions){
			System.out.println(r.toString());
		}
	}
	
/** Method that receives a coordinate by argument, checks if it exist in our mosaic and if it is we get the data of the tile that it is in this coordinate.
 	@param c The coordinate, number of row and column, from which we want to get a tile.
  	@return null if the coordinate does not exist or the data of the map tile at the given coordinate.
*/
	public Tile getTiles(Coordinate c){//getTile
		if (c != null){
			return this.mapTiles.get(c);//gettiles(c)
		}
		return null;
	}
	
/** Method that returns a list with the names of the differents figures that appear in the mosaic.
	@return figureClass A list which contains the name of all the differents figures that appear in the mosaic.
*/
	public List<String> listFigureClasses(){ //tiene que retornar una collection con las figuras que existen (circle y rectangle)
		List<String> figureClasses = new ArrayList<>();
		boolean yesfigure;
		//de la los tiles hay que saber el nombre de las figuras 
		for (Tile t: this.mapTiles.values()){
			yesfigure=false;
			if (t.hasFigure()) {
				for (String whatfigure : figureClasses) {
					if((whatfigure.equals(t.getFigure().getClass().getSimpleName()))) yesfigure=true;
				}
				if(!yesfigure)figureClasses.add(t.getFigure().getClass().getSimpleName());
			}
		}

		return figureClasses;
	}
	
/** Method that returns the number of tiles that contains a certain figure type into a mosaic.
	@param figureClass The type of figure of which we want to know how many times it appears in the mosaic.
  	@return totalNumber The value that represents how many times a certain figure appears in a mosaic.
*/
	public int totalNumberFiguresClass(String figureClass){
		int totalNumber=0;

		for (Map.Entry<Coordinate, Tile> tile: this.mapTiles.entrySet()){
			Tile t = tile.getValue();
			System.out.println ("Coordinate " + tile.getKey());
			System.out.println ("Has figure " + t.hasFigure());
		
			if (t.hasFigure()){
				System.out.println ("Figure " + t.getFigure().getClass().getSimpleName());
			}
		
			if(t.hasFigure()&&(figureClass.equals(t.getFigure().getClass().getSimpleName()))) {
				totalNumber++;
				//System.out.println ("Figure " + t.getFigure().getClass().getSimpleName());
			}
		}

		return totalNumber;
	}
	

/** Method that changes the luminosity in all the tiles of a mosaic.
	@param value A integer which is the variation that will be added or subracted to the rgb color parameters.
*/	
	public void changeLuminosity(int value){
		for (Map.Entry<Coordinate, Tile> tile: this.mapTiles.entrySet()){
			Tile t = tile.getValue();
			t.changeLuminosity(value); 
		}
	}	


/** Method that changes the status of all the mosaic tiles.
	@param value A integer: 0 (tile off), 1 (Tile on) or 2 (Figure off).
*/
	public void changeStatus(int value){
		for (Map.Entry<Coordinate, Tile> tile: this.mapTiles.entrySet()){
			Tile t = tile.getValue();
			t.changeStatus(value); 
		}
	}	


    	// Getters
	public Map<Coordinate, Tile> getMapTiles(){
		return this.mapTiles;
	}

	public int getRows() {
    	return this.rows;
	}

	public int getColumns() {
    	return this.columns;
	}

    public int getHMosaic() {
    	return this.hMosaic;
	}

	public int getWMosaic() {
    	return this.wMosaic;
	}

	public List<RectangularRegion> getRegions(){
		return this.regions;
	}
	
    //Setters
	public void setTiles(Map<Coordinate, Tile> mapTiles){
		this.mapTiles = mapTiles;
	}

	public void setHMosaic(int hMosaic) {
	   this.hMosaic=hMosaic;
	}  

	public void setWMosaic(int wMosaic) {
	   this.wMosaic=wMosaic;
	}

   	public void setColumns(int columns) {
	   this.columns=columns;
	}

	public void setRows(int rows) {
	   this.rows=rows;
	}

	public void setRegions(List<RectangularRegion> regions){
	   this.regions=regions;
	}


/**Inner class of Mosaic where we create the rectangular regions, the regions that compose a mosaic.
*/
public class RectangularRegion implements Luminosity {

//Atributtes

	private String name;
	private Coordinate origin; //The coordinate of the origin point.
	private int h; //The height of the region in number of tiles.
	private int w; //The width of the region in number of tiles.
	private List<Coordinate> coordinates;
	Mosaic myMosaic;
	


/** Constructor, it creates the rectangular region. For this, it checks if its name has the correct length, declares its origin and creates a list with all the coordinates that the region occupies. 
	@param mosaic The mosaic that constains the region.
	@param name The name of our region.
	@param r0 The origin row, the row where the region starts.
	@param c0 The origin column, the column where the region starts.
	@param h The height of the region.
	@param w The width of the region.
*/
	RectangularRegion(Mosaic mosaic, String name, int r0, int c0, int h, int w){
		
		if (name.length() > 0 && name.length() <= 30){
			this.name= name;
		}

		if ((r0 + h -1) > mosaic.getRows()) { //hay que tener en cuenta las coordenadas de origen para saber si "entra"
			this.h = mosaic.getRows() - r0 + 1;
			//System.out.println("Rows "+ mosaic.getRows() + "current size " + this.h);

		} else {
			this.h = h;
		  }

		if ((c0 + w -1) > mosaic.getColumns()) {
			//System.out.println("Column "+ mosaic.getColumns()+ "current size" + this.w);
			this.w = mosaic.getColumns() - c0 + 1;
		} else {
			this.w = w;
		  }

		
		this.origin = new Coordinate(r0,c0);

		
		this.coordinates = new LinkedList<>();
		//this.coordinates.add(origin);

		createCoordinates(this.h, this.w, origin, this.coordinates);

		myMosaic= mosaic; //correct way of inform to what mosaic it belongs
	}


/** Method that knowing the height, width and the origin coordinates of a region, is able to calculate all its coordinates.
	@param h The height of the region.
	@param w The width of the region.
	@param origin The origin coordinates, row and column, where the region starts.
	@param coordinates The list of all the coordinates of the region.
*/
	public void createCoordinates(int h, int w, Coordinate origin, List<Coordinate> coordinates){
		for (int i = origin.getRow(); i<=(origin.getRow()+h-1); i++){ //hay que tener en cuenta que la h empieza desde 0, por eso le sumamos el origen y le restamos uno para que no lo cuente dos veces
			//System.out.println("i:" + i + "h:"+ h);
			for (int y= origin.getColumn(); y<=(origin.getColumn()+w-1); y++){
				//System.out.println("y:" + y + "w:"+ w);
				//System.out.println(new Coordinate(i,y).toString());
				coordinates.add(new Coordinate(i,y));
			}
		}
	}


/** Method that sorts the coordinates of a region in a random order.
*/
	public void shuffle(){
		if (this.coordinates != null)
		Collections.shuffle(this.coordinates);
	}


/** Method that sorts the coordinates of a region in ascending order.								
*/
	public void sortByCoordinateAsc(){
		if (this.coordinates != null)
		Collections.sort(this.coordinates);
	}


/** Method that calculates the area of a certain region.
	@return The value of the area of the region.							
*/
	public int getArea(){
		if (this.coordinates == null)
			return 0;
		return this.coordinates.size();
	}


/** Method that displays the attributes of the rectangular region (name, origin row, origin column, height, width and its coordinates).
	@return A String with its attributes.
*/
	public String toString(){ //<Name>:(<r0>,<c0>),<h>-<w>:[<coordinates>]
		return this.name + ":(" + this.origin.getRow() + "," + this.origin.getColumn() + ")," + this.h + "-" + this.w + ":" + this.coordinates.toString() + "\n"; 
	}


/** Method that changes the luminosity in all the tiles of a region.
	@param value A integer which is the variation that will be added or subracted to the rgb color parameters.
*/
	public void changeLuminosity(int value){
		for(Coordinate c: this.coordinates){
			this.myMosaic.getTiles(c).changeLuminosity(value);
		}
	}


/** Method that changes the status of all the tiles of a region.
	@param value A integer: 0 (tile off), 1 (Tile on) or 2 (Figure off).
*/
	public void changeStatus(int value){
		for(Coordinate c: this.coordinates){
			this.myMosaic.getTiles(c).changeStatus(value);
		}
	}


//Getters
	public String getName(){
		return this.name;
	}
	
	public Coordinate getOrigin(){
		return this.origin;
	}

	public List<Coordinate> getCoordinates(){
		return this.coordinates;
	}
	
	public int getH(){
		return this.h;
	}

	public int getW(){
		return this.w;
	}


//Setters

	public void setName(String name){
		this.name= name;
	}

	public void setOrigin(Coordinate origin){
		this.origin = origin;
	}
	
	public void setH(int h){
		this.h = h;
	}

	public void setW(int w){
		this.w = w;
	}

	public void setCoordinates(List<Coordinate> coordinates){
		this.coordinates = coordinates;
	}

} //end of Rectangular region



/**Class used to compare rectangular regions by its area and name.
*/
	class CompareArea implements Comparator<RectangularRegion> { 
 
/** Method that receives two regions and compare them, by ascending area, and if they are equals, by their name following alphabetic order.
  @param r1 The first region to be compared.
  @param r2 The second region to be compared.
  @return 0 if they are equals, 1 if the first region is bigger and -1 if the second one is bigger.
*/
    	public int compare(RectangularRegion r1, RectangularRegion r2) {
 			if (r1.getArea() < r2.getArea()) return -1;
			else if (r1.getArea() == r2.getArea())
			return r1.getName().compareTo(r2.getName());
			else return 1;
		}


	} //end of CompareArea



	
}//end of mosaic
