import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
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

	static long[] tree;
	static long[] v = new long[1000000];
	static HashMap<Integer, Long>pows;
	static long mod;
	public static void main(String[] args) throws EOFException {
		// TODO Auto-generated method stub

		InputReader in = new InputReader(System.in);
		while(true){
			long b=in.nextLong(),p=in.nextLong(),l=in.nextLong(),n=in.nextLong();
			if(b==0 && p==0 && l==0 && n==0){
				return;
			}
			
			mod=p;
			pows = new HashMap<>();
			long aux2=1%p;
			tree = new long[(int) (l*4)];
			for(int aux=0;aux<=l;aux++){
				pows.put(aux, aux2%p);
				aux2 *= b%p;
				aux2 %=p;
			}
			//initTree(0,0,(int)(l=1));
			for(int i=0;i<n;i++){
				String c =in.next();
				if(c.equals("E")){
					long index = in.nextLong()-1,value = in.nextLong();
					update(0,0,(int) (l-1),(int)index,value);
				}else{
					int from = in.nextInt()-1,to = in.nextInt()-1;
					System.out.println(query(0,0,(int)(l-1),from,to));
				}
			}
			System.out.println("-");
		}
	}
	
	public static void initTree(int node,int a,int b){
		if(a==b){
			if(a==b){
				tree[node] = v[a];
				return;
			}
			initTree(2*node+1,a,(a+b)/2);
			initTree(2*node+2, ((a+b)/2)+1, b);
			tree[node] = (tree[2*node+2]+(pows.get(b-a+1)*tree[2*node+1]))%mod;
		}
	}
	
	public static void update(int node,int a,int b,int index,long v){
		if(index < a || index > b) return;
		if(a==b){
			tree[node] = v%mod;
			return;
		}
		update(2*node+1,a,(a+b)/2,index,v);
		update(2*node+2,(a+b)/2+1,b,index,v);
		tree[node] = (tree[2*node+2]+(pows.get((b-a+1)/2)*tree[2*node+1]))%mod;
	}
	
	public static long query(int node,int a,int b,int init,int end){
		if( end < a || b < init ) return 0;
		if( init <=a &&  b <= end){
			return (tree[node]*pows.get(end-b))%mod;
		}
		return (query(2*node+1, a, (a+b)/2, init, end) + query(2*node+2, (a+b)/2+1, b, init, end))%mod;
	}

}
