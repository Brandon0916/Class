/*Zimeng Ming V00844078
  Ronald Liu	V00838627
*/

/* The following code is using 2d Array which not figure out. 
import java.util.HashSet;
import java.util.Set;

public class ProblemWolfGoatCabbage extends Problem {

    boolean goal_test(Object state){
        StateWolfGoatCabbage wgc_state =(StateWolfGoatCabbage) state;
        
        boolean results=false;
        //set the goal test
        for(int i=1; i<4; i++){
        //if there is an animal are not go thorugh the river, it is false.
        	if(wgc_state.wgcArray[i][1] == 1){
                results=false;
        		
        	}else{
                results=true;
            }
        
        }
        return results;

    }

    Set<Object> getSuccessors(Object state){
        Set<Object> set = new HashSet<Object>();
        StateWolfGoatCabbage ss = (StateWolfGoatCabbage) state;

        //Successor state
      

        //from left to right
        
        
        if(goal_test(ss) == false){
            //check me is on the left side
            if(ss.wgcArray[0][1]==1){
                //I go to the le ft side
                ss.wgcArray[0][1] -=1;
                ss.wgcArray[0][0] +=1;

                //bring the animal to left side
                //System.out.print(ss.wgcArray[0][1]);
                leftside:
                for(int i=1; i<4;i++){
                    if(ss.wgcArray[i][1]==1){
                        //bring the animal to left 
                        ss.wgcArray[i][1]=0;
                        ss.wgcArray[i][0]=1;
        
                        if(checkSafty(ss)){
                            
                            set.add(ss);
                            break leftside;
                        }else{
                            ss.wgcArray[i][1]=1;
                            ss.wgcArray[i][0]=0;
                        }
                        
                    }
                }

                //System.out.print(set);
                
                

            }

        
            //right to left
            //System.out.print(ss.wgcArray[0][0]);
            if(ss.wgcArray[0][0]==1){
                //me back to left
                ss.wgcArray[0][1] +=1;
                ss.wgcArray[0][0] -=1;
                
                rightside:
                for(int k =1;k<4;k++){
                    if(ss.wgcArray[k][0]==1){
                        ss.wgcArray[k][0]=0;
                        ss.wgcArray[k][1]=1;
                        
                        if(checkSafty(ss)&& k != 2){
                            set.add(ss);
                            System.out.println(set);
                            break rightside;
                        }else{
                            ss.wgcArray[k][0]=1;
                            ss.wgcArray[k][1]=0;
                        }

                    }
                }
                    
            }
            
        
        }
      
        return set;
    
    }

    private boolean checkSafty(StateWolfGoatCabbage state){
        //check the right side
        if((state.wgcArray[1][1]==1 && state.wgcArray[2][1]==1 && state.wgcArray[0][1]==0)||(state.wgcArray[2][1]==1 && state.wgcArray[3][1]==1 && state.wgcArray[0][1]==0)){
            return false;
        }

        //check the left side
        if((state.wgcArray[1][0]==1 && state.wgcArray[2][0]==1 && state.wgcArray[0][0]==0)||(state.wgcArray[2][0]==1 && state.wgcArray[3][0]==1 && state.wgcArray[0][0]==0)){
            return false;
        }

        return true;
    }



    double step_cost(Object fromState, Object toState) { return 1; }

    public double h(Object state) { return 0; }
    
    public static void main(String[] args) throws Exception {
		ProblemWolfGoatCabbage problem = new ProblemWolfGoatCabbage();
		int[][] wgcArray = {{0,1},{0,1},{0,1},{0,1}};
		
		problem.initialState = new StateWolfGoatCabbage(wgcArray); 
		
		Search search  = new Search(problem);
		
		System.out.println("TreeSearch------------------------");
		//System.out.print(wcgArray[0][1]);
		//System.out.println("  :\t\t" + search.BreadthFirstTreeSearch());
		//System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
		//System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
		//System.out.println("GreedyBestFirstTreeSearch:\t" + search.GreedyBestFirstTreeSearch());
		//System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());
		
		System.out.println("\n\nGraphSearch----------------------");
		System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
		//System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
		//System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
		//System.out.println("GreedyBestGraphSearch:\t\t" + search.GreedyBestFirstGraphSearch());
		//System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());
		
		System.out.println("\n\nIterativeDeepening----------------------");
		//System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
		//System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());
	}


}

*/



//The following code using 1d array

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class ProblemWolfGoatCabbage extends Problem{

    //first we creat the node indicator 
    static final int meR = 0;
    static final int meL =1;
    static final int wolfR=2;
    static final int wolfL=3;
    static final int goatR =4;
    static final int goatL =5;
    static final int cabbageR=6;
    static final int cabbageL =7;

    //set the goal test,
    //0 represent not cross the river, 1 represent cross the river;
    boolean goal_test(Object state){
        StateWolfGoatCabbage wgc_state = (StateWolfGoatCabbage) state;
        
        if(wgc_state.wgcArray[meL]==1 && wgc_state.wgcArray[wolfL]==1 &&wgc_state.wgcArray[goatL]==1 && wgc_state.wgcArray[cabbageL]==1){
            return true;
        }else{
            return false;
        }
    
        
    }

    Set<Object> getSuccessors(Object state){

        Set<Object> set=new HashSet<Object>();
        StateWolfGoatCabbage wgc_state =(StateWolfGoatCabbage) state;

        //then we need a succssor_state that use to do the opration
        StateWolfGoatCabbage successoor_state;

        //bring the wolf to from right to left
        successoor_state = new StateWolfGoatCabbage(wgc_state);
        successoor_state.wgcArray[meL] +=1; 
        successoor_state.wgcArray[meR] -=1;
        successoor_state.wgcArray[wolfL] +=1;
        successoor_state.wgcArray[wolfR] -=1; 
        if(checkSafty(successoor_state)){
           
            set.add(successoor_state);
        }

        //bring the goat to from right to left
        successoor_state = new StateWolfGoatCabbage(wgc_state);
        successoor_state.wgcArray[meL] +=1; 
        successoor_state.wgcArray[meR] -=1;
        successoor_state.wgcArray[goatL] +=1;
        successoor_state.wgcArray[goatR] -=1; 
        if(checkSafty(successoor_state)){
             //System.out.print(set);
            set.add(successoor_state);
        }

        //bring the cabbage to from right to left
        successoor_state = new StateWolfGoatCabbage(wgc_state);
        successoor_state.wgcArray[meL] +=1; 
        successoor_state.wgcArray[meR] -=1;
        successoor_state.wgcArray[cabbageL] +=1;
        successoor_state.wgcArray[cabbageR] -=1; 
        if(checkSafty(successoor_state)){
            set.add(successoor_state);
        }

        //bring the Cabbage from left to right
        successoor_state = new StateWolfGoatCabbage(wgc_state);
        successoor_state.wgcArray[meL] -=1; 
        successoor_state.wgcArray[meR] +=1;
        successoor_state.wgcArray[cabbageL] -=1;
        successoor_state.wgcArray[cabbageR] +=1; 
        if(checkSafty(successoor_state)){
            set.add(successoor_state);
        }
        

        //bring the wolf from left to right
        successoor_state = new StateWolfGoatCabbage(wgc_state);
        successoor_state.wgcArray[meL] -=1; 
        successoor_state.wgcArray[meR] +=1;
        successoor_state.wgcArray[wolfL] -=1;
        successoor_state.wgcArray[wolfR] +=1; 
        if(checkSafty(successoor_state)){
            set.add(successoor_state);
        }

        //bring the goat from left to right
        successoor_state = new StateWolfGoatCabbage(wgc_state);
        successoor_state.wgcArray[meL] -=1; 
        successoor_state.wgcArray[meR] +=1;
        successoor_state.wgcArray[goatL] -=1;
        successoor_state.wgcArray[goatR] +=1; 
        if(checkSafty(successoor_state)){
            set.add(successoor_state);
        }

        //bring the ship from left to right
        successoor_state = new StateWolfGoatCabbage(wgc_state);
        successoor_state.wgcArray[meL] -=1; 
        successoor_state.wgcArray[meR] +=1;
        if(checkSafty(successoor_state)){
            set.add(successoor_state);
        }

         //bring the goat from right to left
         successoor_state = new StateWolfGoatCabbage(wgc_state);
         successoor_state.wgcArray[meL] +=1; 
         successoor_state.wgcArray[meR] -=1;
         if(checkSafty(successoor_state)){
             set.add(successoor_state);
         }


       

        

        //

       


        return set;
    }

    private boolean checkSafty(StateWolfGoatCabbage state){
          //Checking to see if any element of the array is negative  or  out of bound
          for (int i = 0; i < 8; i++) {
            if (state.wgcArray[i] < 0 || state.wgcArray[i]>1) {
                return false;
            }
        }

        //checking the safty for left band river 
        if((state.wgcArray[meL]==0 && state.wgcArray[wolfL]==1 && state.wgcArray[goatL]==1)||(state.wgcArray[meL]==0 && state.wgcArray[cabbageL]==1 && state.wgcArray[goatL]==1)){
            return false;
        }

        //checking the safty for right band river 
        if((state.wgcArray[meR]==0 && state.wgcArray[wolfR]==1 && state.wgcArray[goatR]==1)||(state.wgcArray[meR]==0&& state.wgcArray[cabbageR]==1 && state.wgcArray[goatR]==1)){
            return false;
        }

        return true;
    }

    //step_cost
    double step_cost(Object fromState, Object toState) { return 1; }

    public double h(Object state) { 
        StateWolfGoatCabbage states = (StateWolfGoatCabbage) state;

        double results= 2*(states.wgcArray[wolfR]+states.wgcArray[goatR]+states.wgcArray[cabbageR])-1;
        
        return results; 
        //return 0;
    }


    public static void main(String[] args) throws Exception {
        ProblemWolfGoatCabbage problem = new ProblemWolfGoatCabbage();
        int[] wgcArray = {1,0,1,0,1,0,1,0};
        problem.initialState = new StateWolfGoatCabbage(wgcArray);

        Search search = new Search(problem);


        PrintStream out = new PrintStream(new FileOutputStream("output_Wolf-Goat-Cabbage.txt"));
        System.setOut(out);
        System.out.println("This is the result for Question 2 Wolf-Goat-Cabbage \n");
        
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