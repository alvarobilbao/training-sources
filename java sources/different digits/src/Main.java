import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		
		while(in.hasNextInt()){
			int n=in.nextInt(),m=in.nextInt();
			int ans=0;
			for(int i=n;i<=m;i++){
				boolean[] arr = new boolean[10];
				String aux = Integer.toString(i);
				boolean flag=true;
				for(int j=0;j<aux.length();j++){
					int k = Integer.parseInt(aux.charAt(j)+"");
					if(arr[k]){
						
						flag = false;
						break;
					}
					arr[k]=true;
				}
				if(flag) ans++;
			}
			System.out.println(ans);
		}
	}

}
