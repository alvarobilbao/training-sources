import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;

class InputReader {
	private InputStream stream;
	private byte[] buf = new byte[1024];
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;

	public InputReader(InputStream stream) {
		this.stream = stream;
	}

	public int read() throws EOFException {
		if (numChars == -1)
			throw new EOFException();
		if (curChar >= numChars) {
			curChar = 0;
			try {
				numChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (numChars <= 0)
				return -1;
		}
		return buf[curChar++];
	}

	public int nextInt() throws EOFException {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		int res = 0;
		do {
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}

	public String readString() throws EOFException {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isSpaceChar(c));
		return res.toString();
	}

	public double nextDouble() throws EOFException {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		double res = 0;
		while (!isSpaceChar(c) && c != '.') {
			if (c == 'e' || c == 'E')
				return res * Math.pow(10, nextInt());
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		}
		if (c == '.') {
			c = read();
			double m = 1;
			while (!isSpaceChar(c)) {
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				m /= 10;
				res += (c - '0') * m;
				c = read();
			}
		}
		return res * sgn;
	}

	public long nextLong() throws EOFException {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		long res = 0;
		do {
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}

	public boolean isSpaceChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	public String next() throws EOFException {
		return readString();
	}

	public interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}

public class Main {

	static ArrayList<ArrayList<Integer>> g ;
	static int[] distances;
	static int[] parents;
	
	public static void main(String[] args) throws EOFException {
		// TODO Auto-generated method stub

		InputReader in = new InputReader(System.in);
		long c =1;
		while(true){
			int nc = in.nextInt();
			if(nc ==0) break;
			g=new ArrayList<>();
			
			
			HashMap<Integer, Integer> equivalencies = new HashMap<>();
			for(int i=0,aux=0; i<nc;i++){
				int node1 = in.nextInt(), node2=in.nextInt();
				if(!equivalencies.containsKey(node1)){
					equivalencies.put(node1, aux);
					aux++;
				}
				if(!equivalencies.containsKey(node2)){
					equivalencies.put(node2,aux);
					aux++;
				}
				try{
					g.get(equivalencies.get(node1)).add(node2);
				}catch(Exception e){
					g.add(equivalencies.get(node1),new ArrayList<Integer>());
					g.get(equivalencies.get(node1)).add(node2);
				}
				
				try{
					g.get(equivalencies.get(node2)).add(node1);
				}catch(Exception e){
					g.add(equivalencies.get(node2),new ArrayList<Integer>());
					g.get(equivalencies.get(node2)).add(node1);
				}
			}
			
			while(true){
				int initialNode=in.nextInt(),ttl=in.nextInt();
				if(initialNode==0 && ttl==0) break;
				
				distances =  new int[g.size()];
				parents = new int[g.size()];
				Arrays.fill(parents, -2);
				Arrays.fill(distances, -1);
				parents[equivalencies.get(initialNode)]=-1;
				distances[equivalencies.get(initialNode)]=0;
				LinkedList<Integer> queue = new LinkedList<>();
				queue.offer(equivalencies.get(initialNode));
				bfs(ttl,queue,equivalencies);
				int notReacheable =0;
				
				for(int i=0;i<parents.length;i++){
					if(parents[i]==-2) notReacheable++;
				}
				System.out.println("Case "+c+": "+notReacheable+" nodes not reachable from node "+initialNode+" with TTL = "+ttl+".");
				c++;
			}

		}
				
		
	}
	
	static void bfs(int maxHops,LinkedList<Integer> queue,HashMap<Integer, Integer> eq){
		if(maxHops==0) return;
		maxHops--;
		LinkedList<Integer> next = new LinkedList<>();
		while(!queue.isEmpty()){
			int node = queue.poll();
			ArrayList<Integer> nextNodes = g.get(node);
			for(Integer i : nextNodes){
				int aux = eq.get(i);
				if(distances[aux]!=-1) continue;
				parents[aux]=node;
				distances[aux]=distances[node]+1;
				next.offer(aux);
			}
		}
		bfs(maxHops,next,eq);
	}

}
