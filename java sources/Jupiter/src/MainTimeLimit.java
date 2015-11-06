import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;

/*class InputReader {
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
}*/

public class MainTimeLimit {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IOException{
		InputReader in = new InputReader(System.in);
		long start = new Date().getTime();
		StringBuilder str = new StringBuilder("");
		OutputStream out = new BufferedOutputStream ( System.out );
		while(true){
			long b=in.nextLong(),p=in.nextLong(),l=in.nextLong(),n=in.nextLong();
			if(b==0 && p==0 && l==0 && n==0){
				System.out.println("time before print="+(new Date().getTime()-start));
				System.out.print(str);
				
				//out.write(str.toString().getBytes());
				System.out.println("time ="+(new Date().getTime()-start));
				return;
			}
			long[] f = new long[(int)(l+1)];
			HashMap<Integer, Integer> modIndexes = new HashMap<>();
			HashMap<Integer, Long>pows = new HashMap<>();
			long aux2=1%p;
			for(int aux=0;aux<=l+1;aux++){
				pows.put(aux, aux2%p);
				aux2 *= b%p;
				aux2 %=p;
			}
			for(long i=0;i<n;i++){
				String o=in.next();
				int index = in.nextInt(),v = in.nextInt();
				if(o.equals("E")){
					f[index]=v;
					modIndexes.put(index, v);
				}else{
					//BigInteger auxP =new BigInteger(p+"");
					//BigInteger auxB= new BigInteger(b+"").mod(auxP);
					
//					Esto funciona pero es lento
//					BigInteger sum = new BigInteger("0");
//					for(Integer ind : modIndexes.keySet()){
//						if(ind>=index && ind<=v){
//							BigInteger helper =BigInteger.ONE;
//							for(int aux=0;aux<v-ind;aux++) 
//								helper=helper.multiply(auxB.mod(auxP)).mod(auxP);
//							
//							sum=sum.add(helper.mod(auxP).multiply(new BigInteger(f[ind]%p+"").mod(auxP)));
//						}
//					}
					
					long suma =0;
					for(Integer ind : modIndexes.keySet()){
						if(ind>=index && ind<=v){
							long helper =1;
							//for(int aux=0;aux<v-ind;aux++) 
								//helper=(helper*(b%p))%p; 
								//helper=helper.multiply(auxB.mod(auxP)).mod(auxP);
							suma=(suma+pows.get(v-ind)*(f[ind]%p))%p;
							//sum=sum.add(helper.mod(auxP).multiply(new BigInteger(f[ind]%p+"").mod(auxP)));
						}
					}
					
					str.append(suma+"\n");
				//	out.write((suma+"\n").getBytes());
//					System.out.print(suma+"\n");
				}
			}
			str.append("-\n");
			//out.write(("-\n").getBytes());
//			System.out.print("-\n");
		}
		
	}
}
