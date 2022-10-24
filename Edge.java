/* Edge.java
 *		Although the adjacency list structure is best for graph search
 *		algorithms, some algorithms like Kruskal's need a list
 *		of all the edges.  This class provides some useful methods
 *		for such manipulation.  
 *
 * 	Edge class:
 *		MEMBERS: int u, v, wt;
 *				(representing an edge u-v with weight wt)
 *		METHODS:
 *			sort(ArrayList<Edge> eL):		sort eL in increasing order
 *			canonize(ArrayList<Edge> eL):	each bigraph edge appears
 *											twice in the list eL, as
 *											i-j and j-i.  But we keep the
 *											"canonical" edge i-j where i<j
 *
 *	-- Chee Yap, Basic Algo, Fall2021
 *******************************/
//import java.util.Random;
//import java.util.ArrayList;
import java.util.*;

class Edge extends Util {// used for Edge list as in Kruskal's algorithm
	//MEMBERS:==============================
		int u, v; int wt;
		static String[] names; // in case we want to output names
	//CONSTRUCTORS:==============================
		Edge (int i, int j, int w){
			u=i; v=j; wt=w; }
	//METHODS:==============================
		int compareTo (Edge e){
				return Integer.signum(wt-e.wt); }
		void dump (){ // dump Ids
			see(" "+u+"->"+v+"/"+wt);}
		void dumpNames (String[] names){ // use names if possible
			if (names==null) dump();
			else see(" "+names[u]+"->"+names[v]+"/"+wt);}
		static void dumpNames (ArrayList<Edge> eL, String[] names){
			// dump edge list using names
			if (names==null) dump(eL);
			else dumpNames(eL, 0, eL.size()-1, names); }
		static void dumpNames (ArrayList<Edge> eL,
								int i, int j, String[] names){
			for (; i<=j; i++)
				eL.get(i).dumpNames(names); }//dumpNames
		static void dump(ArrayList<Edge> eL){ // dump with iDs
			dump(eL, 0, eL.size()-1); seee(""); }
		static void dump (ArrayList<Edge> eL, int i, int j){
			for (; i<=j; i++)
				eL.get(i).dump(); }//dump
		static void sort(ArrayList<Edge> eL1){
			// sort edgeList
			ArrayList<Edge> eL2 = new ArrayList<Edge>();
			eL2.ensureCapacity( eL1.size());
			for (int i=0; i<eL1.size(); i++) // fill up arrayList!
				eL2.add(new Edge(i,i,i));
			sort(eL1, 0, eL1.size()-1, eL2);
		}//sort
		static void canonize (ArrayList<Edge> eL){
			// remove edge i-j iff i>j
			for (Iterator<Edge> it = eL.iterator(); it.hasNext();){
				Edge e = it.next();
				if (e.u>e.v)
					it.remove();
			}//for
		}//canonize
		static void sort (ArrayList<Edge> eL1,
						int i, int j, ArrayList<Edge> eL2){
			// sort eL1 into eL1, with eL2 as buffer
			assert (i<=j) : "ERROR: i>j";
			if (i==j) 
				return;
			int m= (i+j)/2;
			sort(eL1, i, m, eL2);	// eL1[i..m] sorted
			sort(eL1, m+1, j, eL2);	// eL1[m+1..j] sorted
			merge(eL1, i, j, eL2);	// merge into eL2, but copy back!
		}//sort
		static void merge (ArrayList<Edge> eL1,
						int i, int j, ArrayList<Edge> eL2){
			// merge eL1[i..j] into eL2, but copy back to eL1
			int m=(i+j)/2;
			int I=i; int J=m+1; int K=i;
			while (I<=m && J<= j){
				if (eL1.get(I).compareTo(eL1.get(J))<0)
					eL2.set(K, eL1.get(I++));
				else 
					eL2.set(K, eL1.get(J++));
				K++;
			}//while
			while (I<=m) {
				eL2.set(K, eL1.get(I++)); K++;}
			while (J<=j) {
				eL2.set(K, eL1.get(J++)); K++;}
			// COPY BACK into eL1:
			for (int k=i; k<=j; k++)
				eL1.set(k, eL2.get(k));
		}//merge
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    // Unit Tests 
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		static void test (int n, Random rg){ // generic
			testCanonize(n, rg);
			// testSort();
		}//test
		static void testCanonize (int nn, Random rg){ // bigraph edges
			GraphWt g = new GraphWt(Graph.graphZipf(nn));
			g.biGraphWt();	// convert into a bigraph
 			g.checkBigraph(); // check conversion
			ArrayList<Edge> eL = g.edgeList();
			seee("==========testCanonization of bigraph:");
			seee("\nBefore canonization: eL.size()=" + eL.size());
			Edge.dumpNames(eL, g.names);
			Edge.canonize(eL);
			seee("\n\nAfter canonization: eL.size()=" + eL.size());
			Edge.dumpNames(eL, g.names);
			seee("\n ==========END TEST CANONIZE");
		}//testBigraph
		static void testSort (int nn, Random rg){
			GraphWt g = new GraphWt(nn, rg);
			ArrayList<Edge> eL = g.edgeList();
			seee("==========testSort of edge list: list size="
							+ eL.size());
			seee("\nEdge list before sorting:");
			Edge.dumpNames(eL, g.names);
			sort(eL);
			seee("\n\nEdge list after sorting:");
			Edge.dumpNames(eL, g.names);
			seee("\n ==========END TEST SORT");
		}//testSort
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public static void main (String[] args){
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	int ss = (args.length>0)? Integer.valueOf(args[0]) : 111;
	int nn = (args.length>1)? Integer.valueOf(args[1]) : 10;
	int mm = (args.length>2)? Integer.valueOf(args[2]) : 0;
	Random rg = (ss==0)? new Random() : new Random(ss);

	switch(mm){
		case 0:
			test(nn,rg);
			break;
		case 1: // sorting
			seee("mm=1: testSort for sorting edge list");
			testSort(nn,rg);
			break;
		case 2: // sorting
			seee("mm=2: test canonization of edge list");
			testCanonize(nn,rg);
			break;
		case 9: // debugging
			break;
		default: //
			seee("Mode not implemented");
		}//switch
    }//main
}//class
