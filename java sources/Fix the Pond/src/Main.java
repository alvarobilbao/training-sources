import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

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

	static int[] p = new int[1000000];
	static int num_sets;
	
	static void init(int n){
		p = new int[1000000];
		for(int i=0;i<n;i++) p[i]=i;
		num_sets=n;
	}
	
	static int findSet(int i){
		return p[i]==i? i: findSet(p[i]);
	}
	
	static boolean isSameSet(int i, int j){
		return findSet(i)==findSet(j);
	}
	
	static void unionSet(int i,int j){
		if(!isSameSet(i, j)) num_sets--;
		p[findSet(i)] = findSet(j);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputReader in = new InputReader(System.in);
		
		while(true){
			try{
				int n = in.nextInt();
				init(2*n*(2*n+1));
				int mod = 2*n+1;
				int barrier =1;
				HashMap<Integer,Character> barriersDisp = new HashMap<>();
				for(int i=0;i<(2*n-1);i++){
					String s = in.next();
					if(barrier%mod==0) barrier+=2;
					for(int j=0;j<s.length();j++){
						char o = s.charAt(j);
						barriersDisp.put(barrier, o);
						barrier+=2;
					}
				}
				for(Integer aux : barriersDisp.keySet()){
					char o = barriersDisp.get(aux);
					
					if(aux%mod==2){
						unionSet(aux-2, 2*n+1+aux-2);
					}
					
					if(aux%mod == 2*n+1-2){
						unionSet(aux+1, 2*n+1+aux+1);
					}
					
					if(aux<2*n+1 ){
						unionSet(aux, aux+1);
					}
					
					if(aux<((2*n)*(2*n+1)-(2*n+1)) && aux>((2*n)*(2*n+1)-2*(2*n+1))){
						unionSet(aux+(2*n+1),aux+(2*n+2));
					}
					if(o=='H'){
						unionSet(aux, aux-1);
						unionSet(aux+2*n,aux+2*n+1);
						
						
					}else if(o=='V'){
						unionSet(aux, aux+2*n+1);
						unionSet(aux-1, aux+2*n);
					}
					
					
//					try{
//						if(aux-2*n>0) {
//							if(barriersDisp.get(aux-2*n)=='V'){
//								unionSet(aux, aux-2*n-1);
//							}else{
//								unionSet(aux, aux+1);
//							}
//						}
//					}catch(Exception e){
//						
//					}
//					
//					try{
//						if(aux-2*n-2>0) {
//							if(barriersDisp.get(aux-2*n-2)=='V'){
//								unionSet(aux-1, aux-2*n-2);
//							}else{
//								unionSet(aux-1, aux-2);
//							}
//						}
//					}catch(Exception e){
//						
//					}
//					
//					try{
//						if(aux+2*n < (2*n)*(2*n+1)-(2*n+1)) {
//							if(barriersDisp.get(aux+2*n)=='V'){
//								unionSet(aux+2*n, aux+2*n+2*n+1);
//							}else{
//								unionSet(aux+2*n, aux+2*n-1);
//							}
//						}
//					}catch(Exception e){
//						
//					}
//					
//					try{
//						if(aux+2*n+2 < (2*n)*(2*n+1)-(2*n+1)){
//							if(barriersDisp.get(aux+2*n+2)=='V'){
//								unionSet(aux+2*n+1, aux+2*n+1+2*n+1);
//							}else{
//								unionSet(aux+2*n+1, aux+2*n+2);
//							}
//						}else{
//							if((aux+2*n+2)%mod !=0)
//								unionSet(aux+2*n+2, aux+2*n+1);
//						}
//					}catch(Exception e){
//						
//					}
				}
				System.out.println(num_sets-1);
			}catch(IOException e){
				break;
			}
		}
	}

}
