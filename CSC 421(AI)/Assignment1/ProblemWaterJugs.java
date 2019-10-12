/*Zimeng Ming V00844078
  Ronald Liu	V00838627
*/
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;


public class ProblemWaterJugs extends Problem{

    //first we creat the node indicator 
    static final int tw= 0;//represent the 12 gallons
    static final int eight =1;//represent the 8 gallons
    static final int three=2;//represent the 3 gallons
    

    //set the goal test,
    //0 represent not cross the river, 1 represent cross the river;
    boolean goal_test(Object state){
        StateWaterJugs wj_state = (StateWaterJugs) state;
        
        //we only need excatly one gallon
        if(wj_state.wjArray[tw]==1 || wj_state.wjArray[eight] == 1 || wj_state.wjArray[three] == 1){
            return true;
        }else{
            return false;
        }
    
        
    }

    Set<Object> getSuccessors(Object state){

        Set<Object> set=new HashSet<Object>();
        StateWaterJugs wj_state =(StateWaterJugs) state;

        //then we need a succssor_state that use to do the opration
        StateWaterJugs ss;

        //as for the initial, all the jugs are empty

        //first get 12 gallon full
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[tw] += 12;
        if(checkValid(ss)){
            set.add(ss);
        }
       
        //first out 12 gallon and make empty
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[tw] -= 12;
        if(checkValid(ss)){
            set.add(ss);
        }
       
        //first get 8 gallon full
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[eight] += 8;
        if(checkValid(ss)){
            set.add(ss);
        }
       
        //first out 8 gallon and make empty
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[eight] -= 8;
        if(checkValid(ss)){
            set.add(ss);
        }
       
        //first get 3 gallon full
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[three] += 3;
        if(checkValid(ss)){
            set.add(ss);
        }
       
        //first out 3 gallon and make empty
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[three] -= 3;
        if(checkValid(ss)){
            set.add(ss);
        }
       
        //then we can doing put the 12 gallon full to 8 gallon empty jugs
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[tw] -=8;
        ss.wjArray[eight] +=8;
        if(checkValid(ss)){
            set.add(ss);
        }
       
        //then we can doing put the 8 gallon full to 12 gallon empty jugs
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[tw] +=8;
        ss.wjArray[eight] -=8;
        if(checkValid(ss)){
            set.add(ss);
        }

        //then we can doing put the 12 gallon full to 3 gallon empty jugs
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[tw] -=3;
        ss.wjArray[three] +=3;
        if(checkValid(ss)){
            set.add(ss);
        }
       
        //then we can doing put the 3 gallon full to 12 gallon empty jugs
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[tw] +=3;
        ss.wjArray[three] -=3;
        if(checkValid(ss)){
            set.add(ss);
        }

        //then we can doing put the 8 gallon full to 3 gallon empty jugs
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[eight] -=3;
        ss.wjArray[three] +=3;
        if(checkValid(ss)){
            set.add(ss);
        }
       
        //then we can doing put the 3 gallon full to 8 gallon empty jugs
        ss=new StateWaterJugs(wj_state);
        ss.wjArray[eight] +=3;
        ss.wjArray[three] -=3;
        if(checkValid(ss)){
            set.add(ss);
        }



        return set;
    }

    private boolean checkValid(StateWaterJugs state){
        //Checking to see if any element of the array is negative  or  out of bound
        for (int i = 0; i < 3; i++) {
            if (state.wjArray[i] < 0) {
                return false;
            }
        }

        //checking the 12 gallon not over
        if(state.wjArray[tw] >12){
            return false;
        }

        //checking the 8 gallon not over
        if(state.wjArray[eight]>8){
            return false;
        }
        
        //checking the 3 gallon not over
        if(state.wjArray[three] >3){
            return false;
        }

        return true;
    }

    //step_cost
    double step_cost(Object fromState, Object toState) {
        StateWaterJugs from =(StateWaterJugs) fromState;
        StateWaterJugs to =(StateWaterJugs) toState;

        int step_cost = 0;
       

        // first we decided which jugs is move, so we first find the changes of each jugs
        for(int i =0; i<3;i++){
            if(from.wjArray[i] != to.wjArray[i]){
                //then we just calculate how much it is change
                step_cost=Math.abs(from.wjArray[i]-to.wjArray[i]);
                
            }
        }

        return step_cost; 
    }

    public double h(Object state) { 
        // this question do not need heristuric
        return 0;
    }


    public static void main(String[] args) throws Exception {
        ProblemWaterJugs problem = new ProblemWaterJugs();
        int[] wjArray = {0,0,0};
        problem.initialState = new StateWaterJugs(wjArray);

        Search search = new Search(problem);

        PrintStream out = new PrintStream(new FileOutputStream("output_Water_Jugs.txt"));
        System.setOut(out);
        System.out.println("This is the result for Question 4 Water Jugs\n");

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
        
        System.out.println("The Heuristic function is not valid , the step_cost is show on the code.");
        System.out.println("------------------------------------------------------ \n");

    }

}