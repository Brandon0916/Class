/*Zimeng Ming V00844078
  Ronald Liu	V00838627
*/
public class StateWaterJugs{
    //create the array 
    int wjArray[];

    public StateWaterJugs(int[] wjArray){
        this.wjArray=wjArray;
    }

    public StateWaterJugs(StateWaterJugs state){
        wjArray= new int[3];

        for(int i=0;i<3;i++){
            //need to copy an array to do the oprateion
            this.wjArray[i]=state.wjArray[i];
        }
    }

    public boolean equals(Object o) {
        return java.util.Arrays.equals( wjArray, ((StateWaterJugs) o).wjArray );
    }
    
    public int hashCode() {
        return java.util.Arrays.hashCode( wjArray );
    }    
    
    public String toString() {
    	return java.util.Arrays.toString( wjArray );
    }



}