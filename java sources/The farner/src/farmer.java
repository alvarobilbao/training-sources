import java.util.Scanner;

public class farmer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner in = new Scanner(System.in);
		while(in.hasNext()){
			int n = in.nextInt();
			int[] xarr=new int[n];
			int[] yarr=new int[n];
			int[] warr=new int[n];
			double xws=0,yws=0,ws=0;
			
			for(int i=0;i<n;i++){
				int xi=  in.nextInt();
				xarr[i]=xi;
				int yi = in.nextInt();
				yarr[i]=yi;
				int wi = in.nextInt();
				warr[i]=wi;
				
				ws+=wi;
				xws+=wi*xi;
				yws+=wi*yi;
				
				
			}
			double x=xws/ws,y=yws/ws;
			double w=0;
			for(int i=0;i<n;i++){
				w+=warr[i]*((xarr[i]-x)*(xarr[i]-x)+(yarr[i]-y)*(yarr[i]-y));
			}
			System.out.printf("%.3f",w);
			System.out.println();
		}
		
	}

}
