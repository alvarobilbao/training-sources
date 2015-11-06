import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;

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
	static ArrayList<ArrayList<Integer>> g;
	static ArrayList<Integer> visited, match;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputReader in = new InputReader(System.in);
		while(true){
			try{
				g= new ArrayList<>();
				int r =in.nextInt(),c=in.nextInt();
				for(int i=0;i<r*c+r-1;i++){
					g.add(i,new ArrayList<Integer>());
				}
				int count =-1;
				int nodes=0;
				int[][] board = new int[r][c];
				for(int i=0;i<r;i++){
					String s = in.readString();
					count++;
					for(int j=0;j<c;j++){
						if(s.charAt(j)=='.'){
							nodes++;
							board[i][j]=count;
							if(j>0 && board[i][j-1]!=-1){
								g.get(count).add(board[i][j-1]);
								g.get(board[i][j-1]).add(count);
							}
							if(i>0 && board[i-1][j]!=-1){
								g.get(count).add(board[i-1][j]);
								g.get(board[i-1][j]).add(count);
							}
						}
						else board[i][j]=-1;
						count++;
					}
				}
//				int[] x = {0,1},y={1,0};
//				for(int i=0;i<r;i++){
//					for(int j=0;j<c;j++){
//						int aux =board[i][j];
//						if(aux !=-1){
//							for(int k=0;k<2;k++){
//								try{
//									int aux2 = board[x[k]+i][y[k]+j];
//									if(aux2!=-1){
//										g.get(aux).add(aux2);
//										g.get(aux2).add(aux);
//									}
//								}catch(IndexOutOfBoundsException e){
//									continue;
//								}
//							}
//						}
//					}
//				}
				
				
				match=new ArrayList<>();
				match.addAll(Collections.nCopies(g.size(), -1)); //helper = cantidad de vertices
				int MCBM =0;
				//make thinks greedy
				for(int aux=0;aux<g.size();aux++){
					for(Integer aux2:g.get(aux)){
						if(match.get(aux)==-1 && match.get(aux2)==0){
							match.set(aux, aux2);
							match.set(aux2, aux);
							MCBM++;
							break;
						}
					}
				}
				
				
				for(int aux=0;aux<g.size();aux++){
					visited = new ArrayList<>();
					visited.addAll(Collections.nCopies(g.size(), 0));
					MCBM +=Aug(aux);
				}
				int ans;
				if(MCBM == nodes)	System.out.println(2);
				else System.out.println(1);
			}catch(EOFException e){
				break;
			}
		}
	}
	
	private static int Aug(int l) {
	    if (visited.get(l) == 1) return 0;
	    visited.set(l, 1);
	    
	    Iterator it = g.get(l).iterator();
	    while (it.hasNext()) { // either greedy assignment or recurse
	      Integer right = (Integer)it.next();
	      if (match.get(right) == -1 || Aug(match.get(right)) == 1) {
	        match.set(right, l);
	        return 1; // we found one matching
	      }
	    }

	    return 0; // no matching
	}

}
