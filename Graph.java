/* Graph.java
 * 	Simple graph class
 *		-- digraph is represented by adjacency list
 *		-- bigraph (i.e., edges are bi-directional or undirected)
 					is obtained by symmetrization of adjacency list
 *		-- node id's are [0..n) (and can have optional String names)
 *		-- node can have optional String names:
 *					we can dump the graph using id's or names.
 *		-- adjacency list is based on ArrayList from java.util.ArrayList
 *			MEMBERS:
 *					int n; 				// Node id's are [0..n)
 *					<ArrayList<ArrayList<Integer>> adjList;
 *					String[] names;
 *			CONSTRUCTORS:
 *					Graph(n)	-- generates a graph with no edges
 *					Graph(n,rg)	-- generates a graph with randomly edges
 *									assigned by random generator rg
 *					Graph(g)	-- deep copy of graph g
 *			GRAPH CONSTRUCTORS:
 *					standard graphs K(n), K(m,n), L(n), C(n), viz.,
 *						graphK(n), graphKmn(m,n), graphL(n), graphC(n)
 *					graph with Zipf distribution:
 *						graphZipf(n,rg)
 *			METHODS:
 *					numEdges() returns the number of edges in graph
 *					modifiers: addEdge(u,v), removeEdge(u,v), biGraph()
 *					biGraph(): converts into a bigraph
 *					checkBigraph(): verifies a bigraph
 *					graphUnion(g): disjoint union of this graph with g
 *					various graph dumps:
 *						dumpIds(), dumpNames() dumpBigraph(), cheapDump()
 *				All our methods goes to terminal! You can
 *				simply redirect the output to a file if you want.
 *		NOT IMPLEMENTED: HOW TO INPUT GRAPH from a file:
 *			Method 1: id's only 
 *				n; size of graph
 *				1 : 3, 2, 4, 5; // comments
 *				2 :	;	// this line COULD be omitted
 *				3 : 1, 9; //comment
 *				...
 *			Method 2: named id's
 *				n; size of graph
 *				apple : 3, 2, 4; // so id 1 has name "apple"
 *				orange : ;		// id 2 is "orange"; cannot be omitted!
 *				pears : 5, 1;	// id 3 has name "pear" 
 *				...
 *
 *  Chee Yap, Basic Algo, Fall2021
 *************************************************************/
//import java.util.Random;
//import java.util.ArrayList;
import java.util.*;

class Graph extends Util {
	//MEMBERS:==============================
		int n; //size of vertex set
		ArrayList<ArrayList<Integer>> adjList; //array of array of ints
		String[] names; 	// may be null (if n>26);
	//STATIC MEMBERS:=======================
		// useful to assign names to vertices:
		String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h",
						  "i", "j", "k", "l", "m", "n", "o", "p",
						  "q", "r", "s", "t", "u", "v", "w", "x",
						  "y", "z" };
		String[] ALPHA = {"A", "B", "C", "D", "E", "F", "G", "H",
						  "I", "J", "K", "L", "M", "N", "O", "P",
						  "Q", "R", "S", "T", "U", "V", "W", "X",
						  "Y", "Z" };
	//CONSTRUCTORS:==============================
		Graph (int n){ // Graph with no edges (with names if n<=26)
			this.n = n;
			adjList = new ArrayList<ArrayList<Integer>>(n);
			for (int i=0; i<n; i++)
				adjList.add(i, new ArrayList<Integer>());
			if (n <= 26) {// give default names if n<=26
				names = new String[n]; 
				for (int i=0; i<n; i++) names[i] = ALPHA[i];
			}//if
		}
		Graph (int n, Random rg){// 
			// Graph with randomly generated edges
			this(n);
			this.randomEdges(rg);
		}
@SuppressWarnings("unchecked")
		Graph (Graph g){// deep copy of g
			n = g.n;
			// deep copy:
			adjList = (ArrayList<ArrayList<Integer>>)g.adjList.clone(); 
			// shallow copy:		adjList = g.adjList; 
			if (g.names!=null)
				names = g.names.clone();
		}
		//Graph(String fname){// read from file
		// 		not implemented }
	//METHODS:==============================
		void randomEdges(){ randomEdges(RG);}
		void randomEdges(Random rg){
			for (int i=0; i<n; i++){
				int m = rg.nextInt(n); // number of edges to be added
				for (int j=0; j<m; j++)
					addEdge(i, rg.nextInt(n)); // randomly generated edge
			}
		}//randomEdges
		int numEdges (){
			// total number of edges in graph
			int x=0;
			for (int i=0; i<n; i++)
				x = x+ adjList.get(i).size();
			return x;
		}//numEdges
		boolean addEdge (int u, int v){
			// add u->v to graph; return true if added
			// ASSERT (u != v)
			if (u<0 || u>=n || v<0 || v>=n) return false;
			if ((u != v) && (! adjList.get(u).contains(v))) {
				adjList.get(u).add(v); 
				return true; }//if
			return false;
		}//addEdge
		boolean removeEdge (int u, int v){
			// remove u->v to graph; return true if removed
			if (u<0 || u>=n || v<0 || v>=n) return false;
			int j = adjList.get(u).indexOf(v);
			if (j>-1){
				adjList.get(u).remove(j); return true;}
			return false;
		}//removeEdge
		void biGraph (){
			// turn digraph into bigraph by symmetrization
			// i.e., add edge j-i for each edge i-j
			for (int i=0; i<n; i++) {
				int siz = adjList.get(i).size(); 
				for (int j=0; j<siz; j++){
					int v = adjList.get(i).get(j);
					addEdge(v,i);
				}//for
			}//for
		}//biGraph
		void checkBigraph (){
			// nothing happens if a valid bigraph
			for (int i=0; i<n; i++) {
				int siz = adjList.get(i).size(); 
				for (int j=0; j<siz; j++){
					int v=adjList.get(i).get(j);
					assert (adjList.get(v).contains(i))
							: "ERROR: checkBigraph fails!";
				}//for
			}//for
			//seee("checkBigraph() is OK");
		}//checkBigraph
	//METHODS FOR GRAPH GENERATION:====================
		void graphUnion(Graph g){
			// form the union of this graph with g
			// NOT IMPLEMENTED
		}//graphUnion
		static Graph graphC (int n){
			// generate the cyclic graph on [0..n)
			Graph g=graphL(n);
			g.adjList.get(n-1).add(0);
			return g;
		}//graphC
		static Graph graphL (int n){
			// generate the linear graph on [0..n)
			Graph g=new Graph(n);
			for (int i=0; i<n-1; i++)
				g.adjList.get(i).add(i+1);
			return g;
		}//graphL
		static Graph graphK (int n){
			// generate the complete graph on [0..n)
			Graph g=new Graph(n);
			for (int i=0; i<n; i++)
				for (int j=0; j<n; j++)
					if (j!=i) g.adjList.get(i).add(j);
			return g;
		}//graphK
		static Graph graphKmn (int m, int n){
			// generate the complete bipartite graph on [0..m)x[0..n)
			Graph g=new Graph(m+n);
			for (int i=0; i<m; i++)
				for (int j=0; j<n; j++)
					g.adjList.get(i).add(m+j);
			return g;
		}//graphKmn
		static Graph graphZipf (int n){ return graphZipf(n, RG);}
		static Graph graphZipf (int n, Random rg){
			// generate a random graph where outdegree follows zipf's law
			Graph g = new Graph(n);
			for (int i=0; i<n; i++){
				int len = zipf(n, rg);
				for (int j=0; j<len; j++){
					int r= simpleRand(n,i); //r must not be equal to i!
					while (g.adjList.get(i).contains(r)){
						// if r is a duplicate, try again:
						r=simpleRand(n,i);
					}//while
					g.adjList.get(i).add(j, r);
				}// for j
			}//for i
			return g;
		}//graphZipf
		static int simpleRand (int n, int i){
			return simpleRand(n,i,RG);}
		static int simpleRand (int n, int i, Random rg){
			// generate a random int x in [0..n)\ {i}
			// Here, "simple" refers to "simple graph"
			//		that has no edge of the form (i-i)
			int r = (int)(rg.nextDouble()*(double)(n-1));
			if (r>=i) return r+1;
			return r;
		}//simpleRand
		static int zipf (int n){ return zipf(n, RG); }// using Util.RG
		static int zipf(int n, Random rg){
			// Method: generate a value x in [1,n] with zipf probability,
			// 		i.e., Pr(x=i)=1/i.
			// Return (x mod n);  i.e., if x=n, we return 0.
			// NOTE: empirically, we never reach x=n (or barely x=n/2)
			double m = (n*Math.log(n))+0.577; // m=n*ln(n)+g(n)
									// where g(n)~0.577 (Euler's constant)
			double r = (rg.nextDouble())*m;
		// SELF TEST: ====
		// debug("Zipf Selftest: n="+n);
		// double sumx = m;
		// for (int i=1; i<n; i++){
		//	double decx = n/(double)i;
		//	sumx = sumx - decx;
		//}//for
		//debug("How close to 0 is sumx="+sumx);
		//Empirically: sumx(10)=-4.7, sumx(20)=-10.5, sumx(30)=-16.2
			double sum = m;
			for (int i=1; i<n; i++){
				double dec = n/(double)i;
				if (r< dec) return i;
				sum = sum - dec;
				r = r - dec;
			}		
			return 0;
		}//zipf
	//METHODS FOR GRAPH DUMPS :====================
		void cheapdump (){ cheapdump("");}
		void cheapdump (String msg){
			seee(msg); seee(""+adjList);}
		void dump (){ dump("");}
		void dump(String msg){
			if (names==null) dumpIds(msg);
			else dumpNames(msg);
		}
		void dumpIds (){ dumpIds(""); }
		void dumpIds(String msg){
			see(msg);
			seee(" n="+n);
			for (int i=0; i<n; i++)
				seee(""+i+": "+adjList.get(i));
			seee("");
		}//dumpIds
		void dumpNames (){ dumpNames(""); }
		void dumpNames(String msg){
			assert (names!=null) : "ERROR: names array is null";
			see(msg);
			seee(" n="+n);
			for (int i=0; i<n; i++){
				see(names[i] + ": ");
				for (int j=0; j< adjList.get(i).size(); j++)
					see("" + names[adjList.get(i).get(j)] + " ");
				seee("");
			}//for
			seee("");
		}//dumpNames
		void dumpBigraph (){ dumpBigraph("");}
		void dumpBigraph (String msg){
			see(msg);
			seee(" n="+n);
			for (int i=0; i<n; i++){
				seee("" + i + ": " + adjList.get(i));
			}//for
			seee("");
		}//dumpBigraph
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    // METHODS for Unit Tests 
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		static void test (){ test(10, RG);}
		static void test (int nn, Random rg){ // generic
			unitGraphGen(nn,rg);
			//unitZipf(nn,rg);
			//unitGraphDump();
		}//test
		static void unitGraphDump (){ unitGraphDump(RG);}
		static void unitGraphDump (Random rg){ 
			seee("UNIT TEST: graph dumps");
			Graph g = graphZipf(8,rg);
			g.dumpIds("dump graph id's -- ");
			String[] nnn =
				{"ant", "bat", "cat", "dog",
				 "elk", "fox", "goat", "hog"};
			g.names = nnn;
			g.dumpNames("dump graph names -- ");
		}//unitGraphDump
		static void unitGraphGen (){ unitGraphGen(10, RG);}
		static void unitGraphGen (int nn, Random rg){
			seee("UNIT TEST: tests all graph generators");
			Graph g = graphZipf(nn,rg);
			g.dumpIds("graphZipf("+nn+", rg) dumpIds\n");
			g.cheapdump("Cheap dump of same graph:");
			g = new Graph(7,rg);
			g.dump("\nGraph(7,rg) dump\n");
			g = graphK(6);
			g.dump("complete graph K_n, n=6");
			g = graphKmn(3,4);
			g.dump("complete bipartite graph K_mn where m=3, n=4");
			g = graphL(6);
			g.dumpNames("line graph L_n, n=6");
			g = graphC(6);
			g.dump("cyclic graph C_n, n=6");
			seee("===============unitGraphGen");
		}//unitGraphGen
		static void unitSimpleRand (){ 
			seee("UNIT TEST: simpleRand(10,2)");
			for (int i=0; i<10; i++){
				int j = simpleRand(10, 2);
				dbug(" j="+j);
			}
			seee("\n===============unitSimpleRand");
		}//unitSimpleRand
		static void unitZipf (){ unitZipf(10, RG);}
		static void unitZipf (int nn, Random rg){
			seee("UNIT TEST: zipf("+nn+", rg)");
			for (int i=0; i<nn; i++)
				see("" + zipf(nn,rg) + " ");
			seee("\n===============unitZipf");
		}//unitZipf
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public static void main (String[] args){
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	int ss = (args.length>0)? Integer.valueOf(args[0]) : 111;
	int nn = (args.length>1)? Integer.valueOf(args[1]) : 10;
	int mm = (args.length>2)? Integer.valueOf(args[2]) : 0;
	Random rg = (ss==0)? new Random() : new Random(ss);

	Graph g;
	switch(mm){
		case 0:
			seee("mm=0: test(nn, ss)\n\n");
			test(nn, rg);
			break;
		case 1: // testing addEdge and removeEdge (use ss=111, nn=15)
			seee("mm=1: graphZipf(10, rg), testing add/remove edges");
			g = graphZipf(10, rg);
			g.dumpIds("graphZipf(10, rg)\n");
			// to see these edges added/removed, use ss=111, nn=15:
			g.addEdge(4,100); g.addEdge(5,6);
			g.removeEdge(0,100); g.removeEdge(0,4);
			g.dumpIds("After adding (100-4), (5-6)"
					+ " and removing (0-100) and (0-4)\n");
			break;
		case 2: // bigraph
			seee("mm=2: graphZipf(nn, rg), convert into bigraph");
			g = graphZipf(nn, rg);
			g.dumpIds("graphZipf(" + nn+ ", rg):  ");
			g.biGraph();
			g.dumpBigraph("after symmetrization:  ");
			g.checkBigraph(); seee("checkBigraph() is OK");
			break;
		case 3: // 
			seee("mm=3: unitGraphGen");
			unitGraphGen(8, rg);
			break;
		case 8: // various tests
			seee("mm=8: Miscellaneous tests");
			unitZipf();
			unitSimpleRand();
			unitGraphDump();
			break;
		case 9: // debugging
			seee("mm=9: zipf(nn)");
			for (int i=0; i<nn; i++)
				see("" + zipf(nn) + " ");
			seee("");
			break;
		default: //
			seee("Mode not implemented");
		}//switch
    }//main
}//class
