/* GraphWt.java
 * 		GraphWt class 
 *			extends Graph class
 *			by giving integer weights to edges
 *			The weights are represented by
 *				ArrayList<ArrayList<Integer>> adjWt;
 *			which is "parallel" to the adjList in Graph.
 *				i.e., the edge
 *								i-adjList.get(i).get(j)
 *					has weight
 *								  adjWt.get(i).get(j).
 *		CONSTRUCTORS:
 *			GraphWt(int n, Random rg)
 							-- generates a random graph on n vertices
 *								with randomly assigned weights
 *			GraphWt(Graph g)-- assign random weights to g
 *		METHODS:
 *			edgeList()		-- return an ArrayList<Edge> of edges
 *			randomWt(rg)	-- assigns random weights to edges
 *			biGraphWt()		-- make the digraph into a bigraph
 *			isConnected()	-- checks that "this" bigraph is connected
 *			dump()			-- dump the graph using node names if possible
 *									else with node ids
 *			dumpIds()		-- dump the graph with node ids
 *			dumpNames()		-- dump the graph with node names
 *
 *
 *  Chee Yap, Basic Algo, Fall2021
 *************************************************************/

//import java.util.Random;
//import java.util.ArrayList;
import java.util.*;

class GraphWt extends Graph {
	//MEMBERS:==============================
		ArrayList<ArrayList<Integer>> adjWt; //array of array of wts
	//CONSTRUCTORS:==============================
		GraphWt (int n){ //THIS SHOULD HAVE NO EDGES!
			super(n);
			adjWt = new ArrayList<ArrayList<Integer>>(n);
			for (int i=0; i<n; i++)
				adjWt.set(i,null);
		}
		GraphWt (int n, Random rg){ // THIS HAS RANDOMLY ASSIGNED EDGES
			super(n,rg); 
			adjWt = new ArrayList<ArrayList<Integer>>(n);
			for (int i=0; i<n; i++){
				adjWt.add(null);
			}
			this.randomWt(rg); // random weights
		}
@SuppressWarnings("unchecked")
		GraphWt (GraphWt gw){ // copy from a graphWt
			super((Graph)gw);
			adjList = (ArrayList<ArrayList<Integer>>)gw.adjList.clone(); 
			adjWt = (ArrayList<ArrayList<Integer>>)gw.adjWt.clone(); 
		}
		GraphWt (Graph g){ // convert from a graph!
			this(g, RG); }
		GraphWt(Graph g, Random rg){// convert from a graph!
			super(g); // deepcopy of g
			adjWt = new ArrayList<ArrayList<Integer>>(n);
			for (int i=0; i<n; i++)
				adjWt.add(i,null);
			this.randomWt(rg);
		}
		//GraphWt(String fname){// read from file
		//}
		//GraphWt(GraphWt g){// deep copy of graph
		//}
	//METHODS:==============================
		boolean isConnected(){
			//assert("this" GraphWt is a bigraph)
			return true;	// dummy implementation because
							// you must implement it in myGraphWt.java
		}//isConnected
		void randomWt (){ randomWt (RG);} // RG from Util.java
		void randomWt(Random rg){
			// give random weight to this graph:
			// There are two cases:
			//		(A) this graph has no weights on any edges
			//		(B) this graph already has random weights on edges
			// NOTE: Case (B) is useful for generating related examples
			// We assume these 2 cases are distinguished by this flag:
				boolean caseA = (adjWt.get(0) == null) ? true : false;
			for (int i=0; i< adjList.size(); i++){
				if (caseA)
					adjWt.set(i, new ArrayList<Integer>());
				for (int j=0; j< adjList.get(i).size(); j++){
					int wt=rg.nextInt(2*n); // range [0,2n)
					if (caseA)
						adjWt.get(i).add(wt);
					else
						adjWt.get(i).set(j,wt);
				}//for j
			}//for al
		}//randomWt
		void biGraphWt (){
			// turn weighted digraph into weighted bigraph,
			// 		i.e., add edge j-i for each edge i-j
			// 		ALSO ensure that wt(j-i)=wt(i-j)
			// A bit tricky!
			for (int i=0; i<n; i++){
				int siz = adjList.get(i).size();
				for (int j=0; j<siz; j++){
					int v = adjList.get(i).get(j); // edge is i-v
					int w = adjWt.get(i).get(j);   // wt of i-v
					int k = adjList.get(v).indexOf(i); 
					if (k>=0) { // if v-i exists
						if(i<v)	// IMPORTANT!
							adjWt.get(v).set(k, w); // wt(v-i) <- wt(i-v)
					}else{
						addEdge(v,i);	// if v-i does not exist, add v-i
						adjWt.get(v).add(w); // wt of i-v given to v-i
					}//if
				}//for
			}//for
		}//biGraphWt
		void checkBigraphWt(){
			checkBigraph();
			for (int i=0; i<n; i++) {
				int siz = adjList.get(i).size(); 
				for (int j=0; j<siz; j++){
					int v=adjList.get(i).get(j);
					int w=adjWt.get(i).get(j);
					int ii=adjList.get(v).indexOf(i);
					assert (adjWt.get(v).get(ii)==w)
							: "ERROR: checkBigraphWt fails!";
				}//for
			}//for
			seee("checkBigraphWt() is OK");
		}//checkBigraphWt

		ArrayList<Edge> edgeList (){
			// return a list of all edges with weights
			ArrayList<Edge> el = new ArrayList<Edge>();
				for (int i=0; i<n; i++)
					for (int j=0; j<adjList.get(i).size(); j++)
						el.add( new Edge(i,
							adjList.get(i).get(j), adjWt.get(i).get(j)));
			return el;
		}//edgeList
		void dump (){ dump(""); }
		void dump(String msg){
			if (names==null) dumpIds(msg);
			else dumpNames(msg);}
		void dumpIds (){ dumpIds(""); }
		void dumpIds(String msg){
			see(msg);
			seee(" n="+n);
			for (int i=0; i<n; i++){
				see(""+i+": ");
				for (int j=0; j< adjList.get(i).size(); j++){
					see(" " + adjList.get(i).get(j) + "/"
							+ adjWt.get(i).get(j) );
				}//for
				seee("");
			}
			seee("");
		}//dump
		void dumpNames (){ dumpNames(""); }
		void dumpNames(String msg){
			assert (names!=null) : "ERR in dumpNames: names==null!";
			see(msg);
			seee(" n="+n);
			for (int i=0; i<n; i++){
				see(names[i] + ": ");
				for (int j=0; j< adjList.get(i).size(); j++)
					see(" " + names[adjList.get(i).get(j)] + "/"
							+ adjWt.get(i).get(j) );
				seee("");
			}
			seee("");
		}//dumpNames
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    // Unit Tests 
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		static void test (int n, Random rg){ // generic
			testCanonize(n, rg);
			//testBigraphWt(n, rg);
			//testGraphWt();
		}//test
		static void testBigraphWt (int nn, Random rg){ 
			GraphWt g = new GraphWt(graphZipf(nn, rg));
			debug("BiGRAPH! Dump before symmetrize: numEdges="+
						g.numEdges());
			g.dump();
			g.biGraphWt(); //symmetrize
			debug("\n\nBiGRAPH! Dump after symmetrize: numEdges="+
						g.numEdges());
			g.dump();
			g.checkBigraphWt();
		}//testBigraphWt
		static void testCanonize (int nn, Random rg){ 
			seee("==============testCanonize:");
			GraphWt g = new GraphWt(graphZipf(nn,rg));
			g.dump("graphZipfWt:");
			ArrayList<Edge> eL = g.edgeList();
			seee("Edge list before symmetrization:"
						+ "  size="+eL.size());
			Edge.dumpNames(eL, g.names);
			// symmetrize:
			g.biGraphWt();
			g.dump("\ngraphZipfWt after symmetrization:");
			eL = g.edgeList();
			seee("\n\nEdge list after symmetrization:"
						+ "  size="+eL.size());
			Edge.dumpNames(eL, g.names);
			Edge.canonize(eL);
			seee("\n\nCanonized edge list:  size="+eL.size());
			Edge.dumpNames(eL,g.names);
			seee("\n"); g.checkBigraphWt();
			seee("\n============END testCanonize");
		}//testCanonize
		static void testGraphWt(int nn, Random rg){ 
			seee("UNIT TEST: GraphWt dumps");
			GraphWt g = new GraphWt(graphZipf(nn,rg));
			g.randomWt(rg);
			g.dumpIds("dump GraphWt id's -- ");
			g.dumpNames("dump GraphWt names -- ");
			ArrayList<Edge> el = g.edgeList();
			debug("Unsorted edgeList");
			Edge.dump(el);
			Edge.sort(el);
			debug("\n\nSorted edgeList");
			Edge.dump(el);
			debug("\n -----------------testGraphWt");

			debug("Constructor GraphWt(nn,rg) gives denser graphs");
			g = new GraphWt(nn,rg);
			g.dumpIds("dump GraphWt(nn,rg) --");
		}//testGraphWt
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public static void main (String[] args){
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	int ss = (args.length>0)? Integer.valueOf(args[0]) : 111;
	int nn = (args.length>1)? Integer.valueOf(args[1]) : 10;
	int mm = (args.length>2)? Integer.valueOf(args[2]) : 0;
	Random rg = (ss==0)? new Random() : new Random(ss);

	// Generate a zipf Graph
	GraphWt g = new GraphWt(graphZipf(nn));

	switch(mm){
		case 0:
			seee("mm=0: test(nn,rg)");
			//test(nn, rg);
			testCanonize(nn, rg);
			break;
		case 1: // 
			seee("mm=1: testGraphWt(nn,rg)");
			testGraphWt(nn, rg);
			break;
		case 2: // 
			seee("mm=2: testBigraphWt and testCanonize");
			testBigraphWt(nn, rg); // good
			testCanonize(nn, rg); // good
			break;
		case 8:
			seee("mm=8: misc");
			break;
		case 9: // debugging
			break;
		default: //
			seee("Mode not implemented");
		}//switch
    }//main
}//class
