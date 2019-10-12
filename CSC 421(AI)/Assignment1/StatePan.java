/*Zimeng Ming V00844078
  Ronald Liu	V00838627
*/
public class StatePan{
    //create the array 
    int[] panArray;
    //as here in order to we can determined the length by the question giver, we set a length.
    int N;

    public StatePan(int[] panArray){
        this.panArray=panArray;
        N = panArray.length;
    }

    public StatePan(StatePan state){
        N=state.N;
        panArray= new int[N];

        for(int i=0;i<N;i++){
            //need to copy an array to do the oprateion
            this.panArray[i]=state.panArray[i];
        }
    }

    public boolean equals(Object o) {
        return java.util.Arrays.equals( panArray, ((StatePan) o).panArray );
    }
    
    public int hashCode() {
        return java.util.Arrays.hashCode( panArray );
    }    
    
    public String toString() {
    	return java.util.Arrays.toString( panArray );
    }



}