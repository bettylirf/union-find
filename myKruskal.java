/* myKruskal.java
 *	This class extends the Kruskal Class.
 *
 *		THERE ARE TWO METHODS YOU MUST WRITE:
 *			(1) mySetupMST()	-- setting up the data structures 
 *									to run Kruskal's Algorithm
 *			(2) myRunMST()		-- implement Kruskal's Algorithm.
 *
 *		We provide a dummy implementation here.
 *
 *		REMEMBER to use 
 *				myUnionFind	(not "UnionFind")
 *				myGraphWt	(not "GraphWt")
 *
 *		Basically, we write the methods
 *				myTest()		-- like test()
 *				mySetupMST()	-- like setupMST()
 *				myRunMST()		-- like runMST()
 *				myShowMST()		-- like showMST()
 *		
 *		in order to use myUnionFind and myGraphWt.
 *
 *  Chee Yap, Basic Algo, Fall2021
 *************************************************************/

import java.util.*;

class myKruskal extends Kruskal{
	// MEMBERS:
		myUnionFind myUf;	// Just ignore the original UnionFind uf.
		myGraphWt myGw;		// Just ignore the original GraphWt gw.
	// CONSTRUCTORS:
		myKruskal (int n){
			this(n, RG);}
		myKruskal(int n, Random rg){
			super(n,rg);
		}//myKruskal
	// METHODS:
		static void myTest (){ myTest(7,RG); }
		static void myTest (int n, Random rg){
			myUnitTest(n,rg); }
		static void myUnitTest (int nn, Random rg){
			seee("===================== START myKRUSKAL");
			myKruskal k = new myKruskal(nn,rg);
			k.mySetupMST();
			k.myRunMST();
			k.myShowMST();
			seee("===================== END myKRUSKAL");
		}//myUnitTest
		void mySetupMST (){
			seee("===================== SETUP myKRUSKAL");
			myUf = new myUnionFind(n);
			myGw = new myGraphWt(n,rg);
			myGw.biGraphWt();		// turn into undirected graph
			assert (myGw.isConnected())
					: "ERROR: Kruskal needs a connected bigraph";
			seee("Connected bigraph for Kruskal: n="+myGw.n); 
			myGw.dump();
			// ===================================
			eL = myGw.edgeList();	// get edgeList
			Edge.canonize(eL);	// remove duplicates in edgeList
			Edge.sort(eL);
			seee("Sorted list of edges:  size=" + eL.size()*2);
			Edge.dumpNames(eL, myGw.names);
		}//mySetupMST
		void myRunMST (){
			seee("===================== RUNNING myKRUSKAL");
			int numEdges=0;
			for (int i=0; i<eL.size(); i++){
				Edge e = eL.get(i);
				int uu=myUf.find(e.u);
				int vv=myUf.find(e.v);
				if (uu != vv) {
					myUf.union(uu,vv);
					mst.add(e);
					mstCost += e.wt;
				}//if
			}//for
		}//myRunMST
		void myShowMST (){
			seee("===================== STATS and MST OF myKRUSKAL");
			seee("MST with cost="+mstCost);
			if (myGw.names == null)
				Edge.dump(mst);
			else
				Edge.dumpNames(mst, myGw.names);
			seee("\n\nStatistics from Union Find:");
			myUf.dumpStats();
		}//myShowMST
	//MAIN:==============================
    public static void main (String[] args){
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	int ss = (args.length>0)? Integer.valueOf(args[0]) : 111;
	int nn = (args.length>1)? Integer.valueOf(args[1]) : 10;
	int mm = (args.length>2)? Integer.valueOf(args[2]) : 0;
	Random rg = (ss==0)? new Random() : new Random(ss);

	switch(mm){
		case 0:
			seee("mm=0: myTest(nn,rg)");
			myTest(nn, rg);
			break;
		case 1: //
			seee("mm=1: myUnitTest(7,111)" );
			myUnitTest(7, new Random(111));
			break;
		case 2: //
			seee("mm=2: myUnitTest(26,111)" );
			myUnitTest(26, new Random(111));
			break;
		case 3: //
			seee("mm=3: myUnitTest(50,111)" );
			myUnitTest(50, new Random(111));
			break;
		case 9: // debugging
			seee("mm=9:");
			break;
		default: //
			seee("Mode not implemented");
		}//switch
    }//main
}
