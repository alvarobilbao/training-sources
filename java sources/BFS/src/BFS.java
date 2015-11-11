import java.util.ArrayList;
import java.util.LinkedList;

public class BFS {

	static ArrayList<ArrayList<Integer>> g ;
	static int[] distances;
	static int[] parents;
	
	public static void bfs(int init){
		LinkedList<Integer> q = new LinkedList<>(); // the queue for bfs algorithm
		parents[init] = -1; //marking that the initial node has no parent
		distances[init] =0; //The minimun distance from the first node to itself is 0
		
		q.offer(init);
		while(!q.isEmpty()){
			int node = q.poll();
			ArrayList<Integer> adj = g.get(node);
			for(Integer i : adj){
				if(distances[i]!=-1) continue;
				parents[i]=node;
				distances[i] = distances[node]+1;
				q.offer(i);
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//read the graph
		
		//if first node in graph is 1 we create an array list of size n+1, if it start in 0 an arraylist 
		//of size n (n quantity of nodes
		int n =5; // nodes 1,2,3,4,5
		g= new ArrayList<>();
		for(int i=0;i<n+1;i++){
			g.add(new ArrayList<Integer>());
		}
		
		//Add edges according to the problem statement
		
		// initialize the distances and fathers arrays
		distances = new int[n+1];
		parents = new int[n+1];
		
		bfs(1);
	}

}
