/*Zimeng Ming V00844078
  Ronald Liu	V00838627
*/
public class StateMCan{
    int mcanArray[];

    public StateMCan(int[] mcanArray){
        this.mcanArray=mcanArray;
    }

    public StateMCan(StateMCan state){
        mcanArray = new int[6];
        for (int i=0; i<6; i++){
            this.mcanArray[i]=state.mcanArray[i];
        }
    }

    public boolean equals(Object o) {
        return java.util.Arrays.equals( mcanArray, ((StateMCan) o).mcanArray );
    }
    
    public int hashCode() {
        return java.util.Arrays.hashCode( mcanArray );
    }    
    
    public String toString() {
    	return java.util.Arrays.toString( mcanArray );
    }
}