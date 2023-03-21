package Project;

import java.util.Scanner;
import java.lang.String;
import java.io.*;
import java.util.*;

/**Class from which we receive the instructions and the required information to carry out the creation of our mosaic and all its components.
*/
public class MosaicProject { 

/** Main method of the project, it reads from a file the instructions to create the mosaic and its components.
@param args The arguments that are passed by the command line.
*/
	public static void main(String[] args) {
		Scanner keyboard = new Scanner (System.in);// call the scanner
		Mosaic mosaic = null; //to initialize because if not it doesnt exist
        //Mosaic.RectangularRegion region = mosaic.new RectangularRegion();
        Mosaic.RectangularRegion region = null; //to initialize because if not it doesnt exist
		//Tile tile=null;
		String line=null; //the string that we use to read line by line
		Scanner input=null; //file from which we will read
		try{
			input= new Scanner (new FileInputStream("Project/Instructions.txt"));
		} catch (FileNotFoundException e) {  //error with instructions file
			PrintWriter error=null; //file to write errors
			try{
                error= new PrintWriter(new FileOutputStream(new File("Project/error.txt")));
                //System.out.println("File open error");  
			} catch (FileNotFoundException e2) { //error with the error file
			    System.exit(-1); //exits the program
			  }
			error.print("FileNotFoundException");
			error.close();
			System.exit(-1); //exits the program
         } 
		
	    while (input.hasNextLine()) { // hasNextLine return false(no more lines)
			line = input.nextLine();  //line is the following line
			
            if (line.length() > 0){ //checks if it is an empty line
                //line = line.trim();
                String startsWith=line.substring(0, line.indexOf(" ")); //to read the instruction
                
                if (startsWith.equals("ReadMosaic")){ //ReadMosaic Mosaicfile
                    //System.out.println("a");
                    String mosaic_file = line.substring(line.indexOf(" ")+1, line.length());
                    //System.out.println("mosaicfile"+mosaic_file);
                    //System.out.println("FinalProject/"+mosaic_file);
                    mosaic = new Mosaic("Project/"+mosaic_file); //we call the mosaic contructor with the received file
                    //System.out.println("mosaic"+mosaic.toString());
                }


                if (startsWith.equals("CreateRegion")){ //CreateRegion name, r0, c0, h, w
                    //System.out.println ("Figure ");

                    String name = line.substring(line.indexOf(" ")+1,line.indexOf(","));
                    //System.out.println("name"+name);
                    int endName = line.indexOf(",")+1;
                
                    int r0 = Integer.parseInt(line.substring(endName, line.indexOf(",", endName)));
                    //System.out.println("c");
                    int endR0 = line.indexOf( ",", endName)+1;
                
                    int c0 = Integer.parseInt(line.substring(endR0, line.indexOf(",", endR0)));
                    // System.out.println("d");
                    int endC0 = line.indexOf( ",", endR0)+1;
                
                    int h = Integer.parseInt(line.substring(endC0 , line.indexOf(",", endC0)));
                    // System.out.println("e");
                    int endH = line.indexOf( ",", endC0)+1;
                
                    int w = Integer.parseInt(line.substring(endH , line.length()));
                    // System.out.println("f");

                    region = mosaic.new RectangularRegion(mosaic,name,r0,c0,h,w); //create the region
                    mosaic.addRegion(region); //add it to the array list of regions in mosaic
                    //  System.out.println("region"+mosaic.getRegion(name));
                    //  System.out.println("region"+region);

                }
                        //For Mosaic, only call the function
                        //For Regions, with the name we find the region and we do the changes
                        //For tile, we create the coordinate corresponding with its columns and rows, we get the tile associated to this coordinate and we do the changes.
                
                if (startsWith.equals("ChangeLuminosityMosaic")){ //ChangeLuminosityMosaic value
                    // System.out.println("g");
                    int value = Integer.parseInt(line.substring(line.indexOf(" ")+1, line.length()));
                    //System.out.println("value"+value);  
                    mosaic.changeLuminosity(value);
                }


                if (startsWith.equals("ChangeLuminosityRegion")){ //ChangeLuminosityRegion value,region_name
                    //System.out.println("h");
                    int value = Integer.parseInt(line.substring( line.indexOf(" ")+1, line.indexOf(",")));
                    // System.out.println("value"+value);
                    String regionName = line.substring(line.indexOf(",")+1, line.length());
                    //  System.out.println("regionName"+regionName+"value"+value);
                    //System.out.println("mosaic"+mosaic.toString());
                    //System.out.println("getregion"+mosaic.getRegion(regionName));
                    mosaic.getRegion(regionName).changeLuminosity(value);
                
                }


                if (startsWith.equals("ChangeLuminosityTile")){ //ChangeLuminosityTile value, row, column
                    //  System.out.println("i");
                    int value = Integer.parseInt(line.substring( line.indexOf(" ")+1, line.indexOf(",")));
                    int endValue = line.indexOf(",")+1;
                    // System.out.println("value"+value);
                    int row = Integer.parseInt(line.substring(endValue, line.indexOf(",", endValue))); 
                    int endRow = line.indexOf(",", endValue)+1;
                    int column = Integer.parseInt(line.substring(endRow, line.length()));
                    //  System.out.println("value"+value);
                    //  System.out.println("row"+row+"column"+column);
                    Coordinate c= new Coordinate(row, column);
                    //  System.out.println("coordinate"+c);    
                    mosaic.getTiles(c).changeLuminosity(value);
                }


                if (startsWith.equals("ChangeStatusMosaic")){ //ChangeStatusMosaic status
                    //  System.out.println("j");
                    int status = Integer.parseInt(line.substring( line.indexOf(" ")+1, line.length()));
                    //   System.out.println("status"+status);
                    mosaic.changeStatus(status);
                }


                if (startsWith.equals("ChangeStatusRegion")){ //ChangeStatusRegion status, region_name
                    //System.out.println("k");
                    int status = Integer.parseInt(line.substring( line.indexOf(" ")+1, line.indexOf(",")));
                    // System.out.println("status"+status);
                    String regionName = line.substring(line.indexOf(",")+1, line.length()); //Integer.toString(x)
                    mosaic.getRegion(regionName).changeStatus(status); 
                }


                if (startsWith.equals("ChangeStatusTile")){ //ChangeStatusTile status, row, column
                    // System.out.println("l");
                    int status = Integer.parseInt(line.substring( line.indexOf(" ")+1, line.indexOf(",")));
                    // System.out.println("status"+status);
                    int endStatus = line.indexOf(",")+1;
                    String row =  line.substring(endStatus, line.indexOf(",", endStatus));
                    int rowAsInt = Integer.parseInt(row);
                    int endRow = line.indexOf(",", endStatus)+1;
                    int column = Integer.parseInt(line.substring(endRow, line.length()));
                    // System.out.println("row"+row+"column"+column);
                    Coordinate c= new Coordinate(rowAsInt,column); 
                    // System.out.println("coordinate"+c); 
                    mosaic.getTiles(c).changeStatus(status); 
                }


                if (startsWith.equals("SortRegionsByArea")){ //SortRegionsByArea region_file
                    // System.out.println("m");
                    String region_file= line.substring(line.indexOf(" ")+1, line.length());
                    mosaic.sortRegionsByAreaAsc();
                    PrintWriter outputregion=null;
                    try{ //we create the file where we will print the sorted regions 
                        outputregion=new PrintWriter (new FileOutputStream(new File("Project/"+region_file)));
                        for (Mosaic.RectangularRegion r : mosaic.getRegions()){ 
                            outputregion.print(r.toString());
                        }	
                        outputregion.close();
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
                        //System.out.println("File not found");
                
                    }
		
                }


                if (startsWith.equals("SaveMosaic")){
                    String mosaic_file = line.substring(line.indexOf(" ")+1, line.length());
                    // System.out.println ("Figure " + mosaic_file);
                    mosaic.saveToFile("Project/"+ mosaic_file);
                }
            
            }//end if
        }//end while
    }//end main
}//end class
