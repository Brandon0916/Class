/*Zimeng Ming V00844078
  Ronald Liu	V00838627
*/
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class ProblemMcan extends Problem{
    static final int cannR=0;
    static final int cannL=1;
    static final int missR=2;
    static final int missL=3;
    static final int boatR=4;
    static final int boatL=5;

    boolean goal_test(Object state) {
        StateMCan mcan_state = (StateMCan) state;

        if(mcan_state.mcanArray[cannR]==3 && mcan_state.mcanArray[missR]==3 && mcan_state.mcanArray[boatR]==1){
            return true;
        }else{
            return false;
        }

    }
    
    Set<Object> getSuccessors(Object state){
        Set<Object> set=new HashSet<Object>();
        StateMCan mcan_state=(StateMCan) state;

        //create the succssor without any constraint
        StateMCan ss;

        //for the array , the initial is 3,3,1 
        //which indicate the  3 on left and boat is on left

        //we total have 10 situations, the boat can bring the 1 or 2 object
        
        //one c from left to right 
        ss = new StateMCan(mcan_state);
        ss.mcanArray[cannL] -=1;
        ss.mcanArray[cannR] +=1;
        ss.mcanArray[boatL] -=1;
        ss.mcanArray[boatR] +=1;
        if(isValid(ss)){
            set.add(ss);
        }

        //two c from left to right
        ss = new StateMCan(mcan_state);
        ss.mcanArray[cannL] -=2;
        ss.mcanArray[cannR] +=2;
        ss.mcanArray[boatL] -=1;
        ss.mcanArray[boatR] +=1;
        if(isValid(ss)){
            set.add(ss);
        }

        //one m from left to right
        ss = new StateMCan(mcan_state);
        ss.mcanArray[missL] -=1;
        ss.mcanArray[missR] +=1;
        ss.mcanArray[boatL] -=1;
        ss.mcanArray[boatR] +=1;
        if(isValid(ss)){
            set.add(ss);
        }

        //two m from left to right
        ss = new StateMCan(mcan_state);
        ss.mcanArray[missL] -=2;
        ss.mcanArray[missR] +=2;
        ss.mcanArray[boatL] -=1;
        ss.mcanArray[boatR] +=1;
        if(isValid(ss)){
            set.add(ss);
        }

        //one m and one c from left to right
        ss = new StateMCan(mcan_state);
        ss.mcanArray[missL] -=1;
        ss.mcanArray[missR] +=1;
        ss.mcanArray[cannL] -=1;
        ss.mcanArray[cannR] +=1;
        ss.mcanArray[boatL] -=1;
        ss.mcanArray[boatR] +=1;
        if(isValid(ss)){
            set.add(ss);
        }

        //inverse the direction
        //one c from right to left 
        ss = new StateMCan(mcan_state);
        ss.mcanArray[cannL] +=1;
        ss.mcanArray[cannR] -=1;
        ss.mcanArray[boatL] +=1;
        ss.mcanArray[boatR] -=1;
        if(isValid(ss)){
            set.add(ss);
        }

        //two c from right to left
        ss = new StateMCan(mcan_state);
        ss.mcanArray[cannL] +=2;
        ss.mcanArray[cannR] -=2;
        ss.mcanArray[boatL] +=1;
        ss.mcanArray[boatR] -=1;
        if(isValid(ss)){
            set.add(ss);
        }

        //one m from right to left
        ss = new StateMCan(mcan_state);
        ss.mcanArray[missL] +=1;
        ss.mcanArray[missR] -=1;
        ss.mcanArray[boatL] +=1;
        ss.mcanArray[boatR] -=1;
        if(isValid(ss)){
            set.add(ss);
        }

        //two m from right to left
        ss = new StateMCan(mcan_state);
        ss.mcanArray[missL] +=2;
        ss.mcanArray[missR] -=2;
        ss.mcanArray[boatL] +=1;
        ss.mcanArray[boatR] -=1;
        if(isValid(ss)){
            set.add(ss);
        }

        //one m and one c from right to left
        ss = new StateMCan(mcan_state);
        ss.mcanArray[missL] +=1;
        ss.mcanArray[missR] -=1;
        ss.mcanArray[cannL] +=1;
        ss.mcanArray[cannR] -=1;
        ss.mcanArray[boatL] +=1;
        ss.mcanArray[boatR] -=1;
        if(isValid(ss)){
            set.add(ss);
        }


        return set;

    }   



    private boolean isValid(StateMCan state) {
        //Checking to see if any element of the array is negative 
        for (int i = 0; i < 6; i++) {
            if (state.mcanArray[i] < 0) {
                return false;
            }
        }

        //check boat valid
        if(state.mcanArray[boatL]+state.mcanArray[boatR]>2){
            return false;
        }

        //check the number of m and c is greater then 
        if((state.mcanArray[missL]+state.mcanArray[missR]>3) || (state.mcanArray[cannL]+state.mcanArray[cannR]>3)){
            return false;
        }

        //check the number if boat go right side,  left band could not have c bigger then b
        if((state.mcanArray[missL]<state.mcanArray[cannL] && state.mcanArray[missL]>0) || (state.mcanArray[missR]<state.mcanArray[cannR] && state.mcanArray[missR]>0)){
            return false;
        }

        return true;
    }

    //the cost is just one
    double step_cost(Object fromState, Object toState) {
        return 1;
    }
    
    public double h(Object state) {
        StateMCan States = (StateMCan) state;
        
        //return (States.mcanArray[cannL] + States.mcanArray[missL]-1);
        return (2*(States.mcanArray[cannL] + States.mcanArray[missL])-1);
    }


    //test function
    public static void main(String[] args) throws Exception {
        ProblemMcan problem = new ProblemMcan();
        int[] canArray = {0,3,0,3,0,1};
        problem.initialState = new StateMCan(canArray);

        Search search = new Search(problem);

        PrintStream out = new PrintStream(new FileOutputStream("output_Missionaries_Cannibals.txt"));
        System.setOut(out);
        System.out.println("This is the result for Question 3 Missionaries and Cannibals \n");

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

        System.out.println("The Heuristic function is using the '2n-1'");
        System.out.println("------------------------------------------------------ \n");

    }

}