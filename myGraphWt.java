/* myGraphWt.java
 *	This is an extension of GraphWt class.
 *
 * 	WHAT IS TO BE IMPLEMENTED:
 *		boolean isConnected();
 *					-- returns true iff "this" graph is connected
 *					-- assumes "this" graph is a bigraph.
 *		void graphWtUnion(GraphWt g);
 *					-- transforms "this" graph into its disjoint
 *						union with g.  The graphs are general digraphs.
 *					-- renames the vertices of "this" graph to
 *						the letters A-Z, and the names of g to a-z.
 *						(assume n and g.n to be <=26)
 *
 *****************************************/

import java.util.*;

class myGraphWt extends GraphWt{
	// MEMBERS:==========================================
			int[] color; // may be helpful for graph search algorithms
			int counter=0;
	// CONSTRUCTORS: from super class:===================
		myGraphWt (int n){ super(n); }
		myGraphWt (int n, Random rg){ super(n,rg); }
		myGraphWt(Graph g){ super(g);}
		myGraphWt(Graph g, Random rg){ super(g,rg);}
	// METHODS TO BE IMPLEMENTED: =======================
		boolean isConnected (){
			ArrayList<Integer> fringe = new ArrayList<>();
			fringe.add(0);
			boolean[] connect = new boolean[n];
			for(int i = 0; i < n; i++)
				connect[i] = false;
			connect[0] = true;

			ArrayList<Integer> successors;
			int tmp;
			while(fringe.size() != 0){
				tmp = fringe.remove(0);
				successors = adjList.get(tmp);
				for(int i = 0; i < successors.size();i++){
					int suc = successors.get(i);
					if(connect[suc] == false) fringe.add(suc);
					connect[suc] = true;
				}
			}
			for(int i = 0; i < n; i++){
				if(connect[i] == false) 
					return false;
			}
			return true;
		}//isConnected
		void graphWtUnion(GraphWt g){
			String[] newNames = new String[n+g.n];
			for(int i = 0; i < n; i++)
				newNames[i] = ALPHA[i];
			for(int i = 0; i < g.n; i++){
				adjList.add(new ArrayList<Integer>());
				adjWt.add(new ArrayList<Integer>());
				newNames[n+i] = alpha[i];
				for(int j = 0; j < g.adjList.get(i).size(); j++){
					adjList.get(n+i).add(g.adjList.get(i).get(j)+n);
					adjWt.get(n+i).add(g.adjWt.get(i).get(j));
				}
			}
			n += g.n;
			names = newNames;
			// form the union of this graph with g
		}//graphWtUnion
	// ANY HELPER METHODS YOU WANT: =====================
		//....
	// UNIT TESTS: ======================================
		static void testConnected (){ testConnected(10, RG); }
		static void testConnected(int nn, Random rg){
			myGraphWt gw = new myGraphWt(graphZipf(nn,rg));
			gw.biGraphWt();
			gw.dump("Bigraph(a) with nn="+nn);
			if (gw.isConnected()) seee("Graph is connected!\n");
			else seee("Bigraph(a) is not connected!\n");
			////////////
			if (nn<4) nn=4; // ensure nn is large enough 
			gw = new myGraphWt(graphL(nn),rg);
			gw.removeEdge(2,3); //remove one edge
			gw.biGraphWt();
			gw.dump("Bigraph(b) with nn="+nn);
			if (gw.isConnected()) seee("Graph(b) is connected (ERROR)\n");
			else seee("Bigraph(b) is not connected (CORRECT)\n");
			////////////
			gw = new myGraphWt(graphK(3),rg);
			gw.graphWtUnion( new myGraphWt(graphC(4),rg));
			gw.biGraphWt();
			gw.dump("Bigraph(c) with nn="+gw.n);
			if (gw.isConnected()) seee("Graph is connected (ERROR)");
			else seee("Bigraph(c) is not connected (CORRECT)");
		}//testConnected
		static void testUnion (){ testUnion(10, RG); }
		static void testUnion(int nn, Random rg){
			seee("TEST GRAPH UNION");
			myGraphWt g1 = new myGraphWt(graphK(3));
				g1.dump("g1 = Graph K_3:");
			myGraphWt g2 = new myGraphWt(graphC(4));
				g2.dump("g2 = Graph C_4:");
			myGraphWt g3 = new myGraphWt(5, rg);
				g3.dump("g3 = GraphWt(5,rg):");
			////////////////
			g1.graphWtUnion( g2 );
			g1.dump("g1+g2:");
			g1.graphWtUnion( g3 );
			g1.dump("(g1+g2)+g3:");
		}//testUnion
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
			seee("mm=0:");
			testUnion(nn, rg);
			break;
		case 1: // 
			seee("mm=1: testUnion");
			testUnion(nn, rg);
			break;
		case 2: // 
			seee("mm=2: testConnected(nn,rg)");
			testConnected(nn, rg);
			break;
		case 9: // debugging
			break;
		default: //
			seee("Mode not implemented");
		}//switch
    }//main
}//class
