/*Zimeng Ming V00844078
  Ronald Liu	V00838627
*/

/* the following code is not valid , try the code that is base on 1d Array.
import sun.security.util.Length;
import java.util.Arrays;

/* Problem 2
    To solve this problem, we could use the depth search. 
    
    first create the two dimensional array, then we get initial state as [0,0,0,0][1,1,1,1]. 
    
    we need to be [1,1,1,1][0,0,0,0] and need to check the safe. if [0,1,1,0] or [0,0,1,1] shows 
    in the either side of the array, the safe check is fail. 

public class StateWolfGoatCabbage{
    //create the first array 
    int[][] wgcArray;
    int N;
    
    //here for the array: 
    //first row is me
    //second row is wolf
    //thrid row is goat
    //last row is Cabbage
    
    

    //constructor 
    public StateWolfGoatCabbage(int[][] wgcArray){
        
        this.wgcArray=wgcArray;
        N=wgcArray.length;
        
    }

    public StateWolfGoatCabbage(StateWolfGoatCabbage state) {
        
        this.wgcArray = new int[4][2];
        
        for(int i=0; i<4; i++){
        	for(int j=0; j<2; j++){
                this.wgcArray[i][j] = state.wgcArray[i][j];
                //System.out.println(state.wgcArray[i][j]);
            }
        }
        
    }


    public boolean equals(Object o) {
        return java.util.Arrays.deepEquals( wgcArray, ((StateWolfGoatCabbage) o).wgcArray );
    }
    
    public int hashCode() {
        return java.util.Arrays.deepHashCode( wgcArray );
    }    
    
    public String toString() {
    	return java.util.Arrays.deepToString( wgcArray );
    }
}
*/


public class StateWolfGoatCabbage{
    //create the 1d Array
    int wgcArray[];

    //constructor
    public StateWolfGoatCabbage(int[] wgcArray){
        this.wgcArray=wgcArray;
    }

    public StateWolfGoatCabbage(StateWolfGoatCabbage state){
        wgcArray=new int[8];
        for(int i=0;i<8;i++){
            //need to copy an array to do the oprateion
            this.wgcArray[i]=state.wgcArray[i];
        }
    }

    public boolean equals(Object o) {
        return java.util.Arrays.equals( wgcArray, ((StateWolfGoatCabbage) o).wgcArray );
    }
    
    public int hashCode() {
        return java.util.Arrays.hashCode( wgcArray );
    }    
    
    public String toString() {
    	return java.util.Arrays.toString( wgcArray );
    }
}

