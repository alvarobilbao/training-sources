import java.util.Scanner;

public class Kadane {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] nums=new int[n];
		int max=0,sum=0,maxIndex=0;
		for(int i=0;i<n;i++){
			nums[i]=in.nextInt();
			sum+=nums[i];
			if(sum>max){
				max=sum;
				maxIndex = i;
			}
			if(sum<0) sum=0;
		}
		
		System.out.println(max+" at index "+ maxIndex);
	}

}
