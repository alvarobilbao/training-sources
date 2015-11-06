
public class Main {

	static long[] tree = new long[4000000];
	static long[] lazy = new long[4000000];
	static long[] input = new long[1000000];
	
	static void init(int node,int s, int e){
		if(s==e){
			tree[node]=input[s];
			return;
		}
		int mid = (s+e)/2;
		init(node*2+1,s,mid);
		init(node*2+2,mid+1,e);
		tree[node] = tree[node*2+1]+tree[node*2+2];
	}
	
	//Para actualizar uno solo de los valores
	static void update(int node,int s,int e, int i, long val){
		// index out of range
		if(i < s || i > e) return;
		
		//update the node
		if(s==e){
			tree[node] = val;
			return;
		}
		int mid = (s+e)/2;
		update(node*2+1,s,mid,i,val);
		update(node*2+2,mid+1,e,i,val);
		tree[node] = tree[node*2+1]+tree[node*2+2];
	}
	
	//Para hacer queries sin usar lazy prop
	static  long query(int node,int s, int e, int qs, int qe){
		if(qe < s || e < qs) return 0; //suma 0 producto 1
		
		if(qs <= s && e <= qe){
			return tree[node];
		}
		int mid=(s+e)/2;
		return query(node*2+1,s,mid,qs,qe)+query(node*2+2,mid+1,e,qs,qe);
	}
	
	//update range hace uso de lazy prop,
//	en este caso suma una valor val a todos los elementos del rango [us,ue]
	static void update_range(int node,int s, int e, int us, int ue, long val){
		//Hay actualizaciones lazy q deben hacerse antes 
		if(lazy[node]!=0){
			tree[node] += lazy[node];
			
			//marcamos los hijos como lazy
			if(s!=e){
				lazy[node*2+1] += val;
				lazy[node*2+2] +=val;
			}
			
			lazy[node] =0; //reseteado el nodo pues ya no es lazy
		}
		//fuera de rango
		if(s> ue || e < us) return;
		
		//segmento completamente en rango
		if(s >=us && e <= ue){
			tree[node] += (e-s+1)*val;
			
			if(s!=e){ // no es nodo hoja, entonces marcamos los hijos como lazy
				lazy[node*2+1] +=val;
				lazy[node*2+2] +=val;
			}
			
			return;
		}
		
		int mid = (s+e)/2;
		
		update_range(node*2+1, s, mid, us, ue, val);
		update_range(node*2+2, mid+1, e, us, ue, val);
		
		tree[node] = tree[node*2+1]+tree[node*2+2];
	}
	
	static long query_lazy(int node,int s,int e, int qs, int qe){
		
		// si hay llamadas lazy pendientes las terminamos
		if(lazy[node] != 0){
			tree[node] += (e-s+1)*lazy[node];
			if(s != e){
				lazy[node*2+1] += lazy[node];
				lazy[node*2+2] += lazy[node];
			}	
			lazy[node] =0;
		}

		if( qe < s || e < qs) return 0;
		
		if(s >=qs && e<=qe){
			return tree[node];
		}
		
		int mid = (s+e)/2;
		return query_lazy(node*2+1, s, mid, qs, qe)+query_lazy(node*2+2, mid+1, e, qs, qe);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		input[0] = 1;
		input[1] = 0;
		input[2] = 1;
		input[3] = 0;
		input[4] = 1;
		input[5] = 0;
		input[6] = 1;
		input[7] = 0;
		input[8] = 1;
		input[9] = 0;
		input[10] = 1;
		input[11] = 0;
		input[12] = 0;
		input[13] = 0;
		input[14] = 1;
		input[15] = 0;
		input[16] = 0;
		input[17] = 0;
		
		
		init(0,0,17);
		
		//System.out.println(query(0,0,17,1,5));
//		update(0,0,7,3,8);
//		System.out.println(query(0,0,7,1,5));
//		
//		System.out.println(query_lazy(0, 0, 7, 1, 5));
		update_range(0, 0, 17, 1, 10, 4);
		System.out.println(query_lazy(0, 0, 7, 1, 5));
		System.out.println(query_lazy(0, 0, 7, 2, 2));
	}

}
