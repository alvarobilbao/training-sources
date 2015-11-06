import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
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
	
	public String readLine() throws EOFException {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isEndChar(c));
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
	public boolean isEndChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return c == '\n';
	}

	public String next() throws EOFException {
		return readString();
	}

	public interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}

public class Main {
	public static void main(String[] args) throws EOFException{
		InputReader in = new InputReader(System.in);
		int t = in.nextInt();
		for(int i=0;i<t;i++){
			int n = in.nextInt();
			String correct = in.readString(),chef=in.readString();
			int[] money = new int[n+1];
			int correctAns=0,incorrect=0;
			for(int j=0;j<n;j++){
				money[j]=in.nextInt();
				if(correct.charAt(j)==chef.charAt(j)) 
					correctAns++;
				else
					incorrect++;
			}
			money[n]=in.nextInt();
			int max=-1;
			for(int j=correctAns;j>=0;j--){
				max = (max<=money[j])? money[j]:max;
			}
			if(incorrect!=0){
				System.out.println(max);
			}else{
				System.out.println(money[correctAns]);
			}
			
		}
	}
}
