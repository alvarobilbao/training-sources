import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;


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

	static int[][] rows,cols;
	static char[][] board;
	static ArrayList<ArrayList<Integer>> g;
	static ArrayList<Integer> match, visited;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in =  new Scanner(System.in);
		while(in.hasNextInt()){
			int n = in.nextInt();
			in.nextLine();
			board = new char[n][n];
			rows = new int[n][n];
			cols = new int [n][n];
			int helper = 1;
			for(int i=0;i<n;i++){
				String s = in.nextLine();
				board[i] = s.toCharArray();
				for(int j=0;j<s.length();j++){
					if((s.charAt(j)+"").equals("X")){
						rows[i][j]=-1;
						helper++;
					}else{
						rows[i][j]=helper;
					}
				}
				helper++;
				
			}
			
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(board[j][i]=="X".charAt(0)){
						cols[j][i]=-1;
						helper++;
					}else{
						cols[j][i]=helper;
					}
				}
				helper++;
			}
			g = new ArrayList<>();
			//parejas = new int[helper];
			for(int i=0;i<helper+1;i++){
				g.add(i,new ArrayList<Integer>());
			}
			int edges=0;
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(cols[i][j]!=-1 && rows[i][j]!=-1){
						//Solo el segundo if xq sera un grafo dirigido
						//if(!g.get(cols[i][j]).contains(rows[i][j])) g.get(cols[i][j]).add(rows[i][j]);
						if(!g.get(rows[i][j]).contains(cols[i][j])){
							g.get(rows[i][j]).add(cols[i][j]);
							edges++;
						}
					}
				}
			}
			
			
//			for(int k=0;k<helper;k++){
//				ArrayList<Integer> aux = g.get(k);
//				if(parejas[k]!=0) continue;
//				for(Integer p:aux){
//					if(parejas[p]==0){
//						parejas[k] =p;
//						parejas[p]=k;
//						break;
//					}
//				}
//			}
			
			//La magia del MCBM (augmenting path)
			int MCBM =0;
			match=new ArrayList<>();
			match.addAll(Collections.nCopies(helper+1	, -1)); //helper = cantidad de vertices
			for(int aux=0;aux<g.size();aux++){
				visited = new ArrayList<>();
				visited.addAll(Collections.nCopies(helper+1, 0));
				MCBM +=Aug(aux);
			}
			System.out.println(MCBM);
		}
		

	}
	
	//Esto funciona cuando las aristas direccionadas van de uno solo lado del grafo bipartito,
//	si hay aristas de ambos lados esto ya no funciona.
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
