import java.util.ArrayList;
import java.util.Stack;

public class DFS {

	static boolean[] visited;
	static ArrayList<ArrayList<Integer>> g;
	
	//with recursion
	public static void dfs(int start){
		visited[start] = true;
		for(int i=0;i<g.get(start).size();i++){
			int adj = g.get(start).get(i);
			if(!visited[adj]){
				dfs(adj);
			}
		}
	}
	
	//with stack
	public static void dfs2(int start){
		Stack<Integer> s = new Stack<>();
		s.push(start);
		while(!s.isEmpty()){
			int node = s.pop();
			if(!visited[node]){
				visited[node] = true;
				for(Integer i:g.get(node)){
					if(!visited[g.get(node).get(i)])
						s.push(g.get(node).get(i));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int n =5; // nodes 1,2,3,4,5
		g= new ArrayList<>();
		for(int i=0;i<n+1;i++){
			g.add(new ArrayList<Integer>());
		}
		
		//Add edges according to the problem statement
		
		//
		visited = new boolean[n+1];
		dfs(1);

		
	}

}
