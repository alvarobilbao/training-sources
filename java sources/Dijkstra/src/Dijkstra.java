import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.ArrayList;

public class Dijkstra {

	public static final int INF = 1000000000;
	static ArrayList<ArrayList<IntegerPair>> AdjList = new ArrayList<ArrayList<IntegerPair>>();
	static ArrayList<Integer> dist;
	
	public static void dijkstra(int start) {
		dist = new ArrayList<Integer>();
		dist.addAll(Collections.nCopies(AdjList.size(), INF));
		dist.set(start, 0); // INF = 1*10^9 not MAX_INT to avoid overflow
		//Create a priority queue, overriding the comparator method compare, to make dijkstra greedy
		//pq stores a pair <dist from source,vertex>
		PriorityQueue<IntegerPair> pq = new PriorityQueue<IntegerPair>(1, new Comparator<IntegerPair>() { 
			public int compare(IntegerPair i, IntegerPair j) {
				return i.first() - j.first();
			}
		});
		pq.offer(new IntegerPair(0, start)); // sort based on increasing
												// distance

		while (!pq.isEmpty()) { // main loop
			IntegerPair top = pq.poll(); // greedy: pick shortest unvisited
											// vertex
			int d = top.first(), u = top.second();
			if (d > dist.get(u))
				continue; // This check is important! We want to process vertex
							// u only once but we can
			Iterator it = AdjList.get(u).iterator();
			while (it.hasNext()) { // all outgoing edges from u
				IntegerPair p = (IntegerPair) it.next();
				int v = p.first();
				int weight_u_v = p.second();
				if (dist.get(u) + weight_u_v < dist.get(v)) { // if can relax
																// (note: Record
																// SP spanning
																// tree)
					dist.set(v, dist.get(u) + weight_u_v); // relax (here if
															// needed. This is
															// similar)
					pq.offer(new IntegerPair(dist.get(v), v)); // (as printpath
																// in BFS)
					// enqueue this neighbor regardless whether it is already in
					// pq or not
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		int V, E, s, u, v, w;

		
		Scanner sc = new Scanner(System.in);
		V = sc.nextInt();
		E = sc.nextInt();
		s = sc.nextInt(); // start node

		AdjList.clear();
		for (int i = 0; i < V; i++) {
			ArrayList<IntegerPair> Neighbor = new ArrayList<IntegerPair>();
			AdjList.add(Neighbor); // add neighbor list to Adjacency List
		}

		for (int i = 0; i < E; i++) {
			u = sc.nextInt();
			v = sc.nextInt();
			w = sc.nextInt();
			AdjList.get(u).add(new IntegerPair(v, w)); // first time using
														// weight
		}

		dijkstra(s);
		
		for (int i = 0; i < V; i++) // index + 1 for final answer
			System.out.printf("SSSP(%d, %d) = %d\n", s , i , dist.get(i));
	}
}

class IntegerPair implements Comparable {
	Integer _first, _second;

	public IntegerPair(Integer f, Integer s) {
		_first = f;
		_second = s;
	}

	Integer first() {
		return _first;
	}

	Integer second() {
		return _second;
	}

	@Override

	public int compareTo(Object o) {
		IntegerPair p = (IntegerPair) o;
		if (!this.first().equals(p.first()))
			return this.first() - p.first();
		else
			return this.second() - p.second();
	}

}
