/*Zimeng Ming V00844078
  Ronald Liu	V00838627
*/
import java.util.*;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;


public class ProblemPan extends Problem{

    /* for this question, we denote the weight of the Pan to 0,1,....,n 
    so it is same indicator and the array value will be same as weight.

    */

    //set the goal test,
    //0 represent not cross the river, 1 represent cross the river;
    boolean goal_test(Object state) {

        StatePan pan_state = (StatePan) state;

        for(int i=0; i<pan_state.N; i++){
        		if(pan_state.panArray[i] != i ){
        			return false;
        	}
		}
			
        return true;
	}

    Set<Object> getSuccessors(Object state){

        Set<Object> set=new HashSet<Object>();
        StatePan pan_state =(StatePan) state;

        //then we need a succssor_state that use to do the opration
        StatePan ss; 
    
        //so we begin with filp two Pan at a time
        /* the algorithm in here is 
           1. build a temp array , length is equal to the number we need flip
           2. copy the state array to temp.  
           3. reverse the array useing collection.reverse()
           4. back to the state array. 
        */

      
        //flip using for loop for the flip number from 2 to 6
      
		for (int j = 2; j < 7 ; j++){
			ss = new StatePan(pan_state);
			int[] temp=new int[j];
			for(int i =0 ; i<j ; i++){
				temp[i]=ss.panArray[i];
			}
			for(int i =0 ; i<j ; i++){
				ss.panArray[i]=temp[j-i-1];
			}
			set.add(ss);  
		}
        
        

        return set;
    }


    /*for this question, there is no constraint.
    private boolean checkValid(StatePan state){
       

        return true;
    }
    */


    //step_cost, 
    double step_cost(Object fromState, Object toState) {
        
        return 1; 
    }

    public double h(Object state) { 
        
		StatePan s = (StatePan) state;
	
		int gap_count = 0;
		int N = s.N;
	
		for ( int i = 0 ; i < (N-1) ; i++ ){
			
			int diff_value = s.panArray[i] - s.panArray[i+1];
			if ( Math.abs( diff_value ) != 1 )
				gap_count++;
		}
		return gap_count; 
    }


    public static void main(String[] args) throws Exception {
        ProblemPan problem = new ProblemPan();
        int[] panArray = {1,0,3,5,2,4};
        problem.initialState = new StatePan(panArray);

        Search search = new Search(problem);

        PrintStream out = new PrintStream(new FileOutputStream("output_Pancake.txt"));
        System.setOut(out);
        System.out.println("This is the result for Question 5 Pancake \n");
        System.out.println("Tree search: \n");
        System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
        System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
        System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
        System.out.println("GreedyBestFirstTreeSearch:\t\t" + search.GreedyBestFirstTreeSearch());
        System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());
        System.out.println("IterativeDeepeningTreeSearch:\t\t" + search.IterativeDeepeningTreeSearch());

        System.out.println("Graph search: \n");
        System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstTreeSearch());
        System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
        System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
        System.out.println("GreedyBestGraphTreeSearch:\t\t" + search.GreedyBestFirstGraphSearch());
        System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());
        System.out.println("IterativeDeepeningGraphSearch:\t\t" + search.IterativeDeepeningGraphSearch());
        
        System.out.println("The Heuristic function is using the 'Gap Heuristic'");
        System.out.println("------------------------------------------------------ \n");

        
    }

}