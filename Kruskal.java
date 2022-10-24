/* Kruskal.java
 *
 * 	This implements a
 *					"NAIVE KRUSKAL ALGORITHM",
 *		not Kruskal's Algorithm, using the "naive Union Find" 
 *		data structure from UnionFind.java.
 *		But it has all the infra-structure for your implementation of
 *		the real algorithm (that is to be done in myKruskal.java)
 *
 *		The class Kruskal uses 3 other classes
 *				GraphWt		-- to created weighted bigraphs
 *				UnionFind	-- to detect connected components
 *				Edge		-- for sorting edge lists
 *
 *		The MEMBERS of Kruskal class are
 *				GraphWt gw;			-- the input bigraph
 *										(your constructor must check that
 *										this graph is connected)
 *				UnionFind uf;		-- a UnionFind datastructure
 *				ArrayList<Edge> eL;	-- an list of edges (which we will
 *										ensure is made canonical)
 *				ArrayList<Edge> mst; -- a list of edges representing the
 *										computed MST
 *				int mstCost;		 -- the cost of the MST
 *
 *		The Kruskal class three METHODs:
 *				void setupMST();	-- set up the graph and data structures
 *										NEED TO CHECK THAT BIGRAPH IS
 *										CONNECTED, else abort.
 *				void runMST();		-- implement an MST algorithm; we do
 *										not actually implement Kruskal.
 *				void showMST();		-- show the MST and statistics from
 *										UnionFind.
 *				static unitTest();	-- packaging of the previous 3
 *										methods.
 *
 *
 *  Chee Yap, Basic Algo, Fall2021
 *************************************************************/
import java.util.*;

class Kruskal extends Util {
	//MEMBERS:==============================
		int n;
		Random rg;
		GraphWt gw;
		UnionFind uf;
		ArrayList<Edge> eL;
		ArrayList<Edge> mst = new ArrayList<Edge>();
		int mstCost=0;
	//CONSTRUCTORS:==============================
		Kruskal (int n){
			this(n, RG);}
		Kruskal(int n, Random rg){
			this.n = n;
			this.rg = rg;
			// REMARK: we defer setting up the members:
			// 		gw, uf, eL
			// to allow Kruskal to be easily extended
			// to myKruskal later...
		}//Kruskal
	//METHODS:==============================
		static void test (){ test(7,RG); }
		static void test (int n, Random rg){
			unitTest(n,rg); }
		static void unitTest (int nn, Random rg){
			seee("===================== START KRUSKAL");
			Kruskal k = new Kruskal(nn,rg);
			k.setupMST();
			k.runMST();
			k.showMST();
			seee("===================== END KRUSKAL");
		}//unitTest
		void setupMST (){
			seee("===================== SETUP KRUSKAL");
			uf = new UnionFind(n);
			gw = new GraphWt(n,rg);
			gw.biGraphWt();		// turn into undirected graph
			assert (gw.isConnected())
					: "ERROR: Kruskal needs a connected bigraph";
			seee("Connected bigraph for Kruskal: n="+gw.n); 
			gw.dump();
			// ===================================
			eL = gw.edgeList();	// get edgeList
			Edge.canonize(eL);	// remove duplicates in edgeList
			Edge.dumpNames(eL, gw.names);
		}//setupMST
		void runMST (){
			seee("\n===================== RUNNING NAIVE KRUSKAL");
			int numEdges=0;
			for (int i=0; i<eL.size(); i++){
				Edge e = eL.get(i);
				int uu=uf.find(e.u);
				int vv=uf.find(e.v);
				if (uu != vv) {
					uf.union(uu,vv);
					mst.add(e);
					mstCost += e.wt;
				}//if
			}//for
		}//runMST
		void showMST (){
			seee("===================== STATS and MST OF NAIVE KRUSKAL");
			seee("MST with cost="+mstCost);
			if (gw.names == null)
				Edge.dump(mst);
			else
				Edge.dumpNames(mst, gw.names);
			seee("\n\nStatistics from Union Find:");
			uf.dumpStats();
		}//showMST
	//MAIN:==============================
    public static void main (String[] args){
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	int ss = (args.length>0)? Integer.valueOf(args[0]) : 111;
	int nn = (args.length>1)? Integer.valueOf(args[1]) : 10;
	int mm = (args.length>2)? Integer.valueOf(args[2]) : 0;
	Random rg = (ss==0)? new Random() : new Random(ss);

	switch(mm){
		case 0:
			seee("mm=0: test(nn,rg)");
			test(nn, rg);
			break;
		case 1: //
			seee("mm=1: unitTest(7,111)" );
			unitTest(7, new Random(111));
			break;
		case 2: //
			seee("mm=2: unitTest(26,111)" );
			unitTest(26, new Random(111));
			break;
		case 3: //
			seee("mm=3: unitTest(50,111)" );
			unitTest(50, new Random(111));
			break;
		case 9: // for debugging
			seee("mm=9: debugging");
			break;
		default: //
			seee("Mode not implemented");
		}//switch
    }//main
}//class Kruskal
