import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		
		int t = in.nextInt();
		int n = in.nextInt();
		for(int c=0;c<t;c++){
			if(c!=0) System.out.println();
			in.nextLine();
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			boolean[] visited=new boolean[n+1];
			for(int i=0;i<=n;i++){
				g.add(i,new ArrayList<Integer>());
			}
			int success=0,unsuccess=0;
			String ch;
			while(((ch = in.next()).equals("c")|| ch.equals("q"))&& in.hasNextLine()){
				
					int c1=in.nextInt(), c2=in.nextInt();
					in.nextLine();
					if(ch.equals("c")){
						g.get(c1).add(c2);
						g.get(c2).add(c1);
					}
					if(ch.equals("q")){
						DFS(c1,visited,g);
						if(visited[c2]) success++;
						else unsuccess++;
						visited=new boolean[n+1];
					}
				
			}
			n=Integer.parseInt(ch);
			System.out.println(success+","+unsuccess);
			
			
		}
	}
	
	public static void DFS(int cand,boolean[] visited,ArrayList<ArrayList<Integer>> g){
		visited[cand]=true;
		for(int i=0;i<g.get(cand).size();i++){
			if(!visited[g.get(cand).get(i)]) DFS(g.get(cand).get(i),visited,g);
		}
	}
}
