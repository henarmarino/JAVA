package Project;

import java.util.List;
import java.lang.Comparable;


/** Class that creates the coordinates that each region of the mosaic has.
*/
public class Coordinate implements Comparable<Coordinate>{

	private int row;
	private int column;


/** Constructor that creates the coordinates using the number of columns and rows.
@param row Number of rows.
@param column Number of columns.
*/
	Coordinate(int row, int column){
		this.row = row;
		this.column = column;
	}


//Getters
	public int getRow() {
    	return this.row;
	}


	public int getColumn() {
    	return this.column;
	}


//Setters

	public void setColumn(int column) {
	   this.column=column;
	}


	public void setRow(int row) {
	   this.row=row;
	}
	
/** Method that compares our coordinate and one that is passed by parameter and it sorts them in ascending order.
 @return 0 if they are equals, 1 if the first coordinate is bigger and -1 if the second one is bigger.
 */	
	public int compareTo(Coordinate c){
		if (this.row < c.getRow()) return -1; //return -1 and exits because is in the right order (lower to bigger)
			else if (this.row == c.getRow()){
				if (this.column < c.getColumn()) return -1; //return -1 and exits because is in the right order (lower to bigger)
				else if (this.column == c.getColumn()) return 0; //retun 0 because they are equal
				}
		return 1;
	}


 /** Method that displays the attributes of the coordinate: row, column.
 @return The string with its attributes.
 */	
	public String toString(){
	   return "("+this.row+","+this.column+")";
	}
	
	
 /** Method that compares the columns and rows of two coordinates to check if they are equals.
 @param c A collection of two attributes: row and column.
 @return true (if they are equals) or false (if they are differents).
 */ 
 	public boolean equals(Coordinate c){
		if(this.row != c.getRow())
			return false;
		if(this.column != c.getColumn())
			return false;	
		return true;	
	}


}//end of class
