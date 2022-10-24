/* UnionFind.java
 * 	This implements  
 *						UnionFindInterface.java
 *		which requires the 3 operations of
 *					link(u,v), find(u), and union(u,v).
 *		We give you the "naive" algorithm in this file.
 *		You are to replace them by the "clever" algorithms in your own
 *		myUnionFind class.
 *
 *		MEMBERS: 
 *			int U; 		-- the size of the universe (U=n for graph nodes)
 *			int[] pi;	-- this represents the "compressed tree"
 *							data structure (think of pi[u] as the parent
 *							of node u).
 *						-- CONVENTION: pi[u]=u iff u is a root of a
 *							compressed tree. Initially, pi[u]=u for all u.
 *		METHODS:
 *			int getpi(u);		-- returns the value of pi[u]
 *			void setpi(u,v); 	-- assigns pi[u] to value v
 *
 *		*************************************************************
 *		*  !!!! VERY IMPORTANT: YOUR METHODS CAN ONLY ACCESS THE    *
 *		*	 	ARRAY pi USING setpi AND getpi.  YOU GET ZERO FOR   *
 *		*		THIS PART IF YOU VIOLATE THIS RULE. !!!!            *
 *		*************************************************************
 *
 *	THERE are 3 statistics:
 *			numFinds	-- number of find operations
 *			numUnions	-- number of union operations
 *			numLinks	-- number of link operations
 *			count		-- number of times you access "pi[u]"
 *					(count is a good measure of overall of algorithm!)
 *
 *		You should be able to duplicate OUR values
 *		for numFinds and numUnions. 
 *			BUT IT IS UP TO YOU TO TRY TO MINIMIZE "count"
 *			by implementing any heuristic you know!!!
 *
 *		Food for thought: we show 3 simulations (mm=1,mm=2,mm=3).
 *			The naive implementation in UnionFind appears to be "faster"
 *			(based on "count") than the clever solution in myUnionFind
 *			when U is small.  Can you explain why?
 *
 *  Chee Yap, Basic Algo, Fall2021
 *************************************************************/

import java.util.Random;

public class UnionFind extends Util implements UnionFindInterface {
	//MEMBERS:==============================
		int U;
		int[] pi;		// this array is "protected" in a sense!
		String[] name;	// may arrays ALPHA and alpha as in Graphs.java
		int count=0;	// number of pointer following steps "u=p[u]"
		int numFinds=0;	// number of find operations
		int numLinks=0;	// number of link operations
		int numUnions=0;	// number of union operations
	//CONSTRUCTORS:==============================
		UnionFind (){ super(10); }
		UnionFind(int n){
			U = n;
			pi = new int[n];
			for (int i=0; i<n; i++) setpi(i,i);
		}
	//IMPLEMENTORS:==============================
	public int find (int u){
		numFinds++;
		int p=getpi(u);
		while (p != u){// invariant: p = parent of u
			u = p;
			p = getpi(u);
		}
		return u; }//find
	public int link (int u, int v){
		numLinks++;
		setpi(u,v); return v;}
	public int union (int u, int v){
		numUnions++;
		return link(find(u),find(v));}
	//MEMBERS:===================================
	/////////////////////////////////////////////
	int getpi(int u){ 
			count++; return pi[u];}
	void setpi(int u, int v){ 
			count++; pi[u]=v;}
	/////////////////////////////////////////////
	void dumpStats (){
		debug("UnionFind Statistics for U="+U);
		debug("Number of naive finds="+numFinds);
		debug("Number of naive unions="+numUnions);
		debug("Number of naive links="+numLinks);
		debug("Total count ="+count);
	}//dumpStats
	void dump (){
		debug("DUMP ============= U="+ U);
		dbug(" i  : ");
		for (int i=0; i<U; i++)
			System.out.printf(" %3d", i);
		dbug("\n pi  :");
		for (int i=0; i<U; i++)
			System.out.printf(" %3d", pi[i]); // we don't use getpi(i)
						// here because it is not part of the algorithm.
		dbug("\n pi* :");
		int[] root = new int[U];
		for (int i=0; i<U; i++){
			root[i] = find(i);
			System.out.printf(" %3d", root[i]);
		}
		debug("\n groups =");
		int[][] gp = groups(root);
		dump(gp);
		dumpStats();
		debug("\n=============  UNION FIND DUMP\n");
	}//dump
	void dump (int[][] gp){
		for (int i=0; i<U; i++){
			if (gp[i] != null) {
				dbug("i=" + i + ": ");
				for (int j=0; j<gp[i].length; j++){
					dbug("" + gp[i][j] + " ");
				}//for
				debug("");
			}
		}//for
	}//dump
	int[] sizes (int[] s){
			// s:[0,n)->[0,n) defines sets the s^-1[j] (j\in[0,n)).
			// siz[j] = size of s^-1[j].
		int n=s.length;
		int[] siz = new int[n];
		for (int i=0; i<n; i++)
			siz[s[i]]++;
		return siz;
	}
	int[][] groups (int[] s){
		int n=s.length;
		int[] siz = sizes(s);
		int[][] gp = new int[n][];
		int[] count = new int[n];
		for (int j=0; j<n; j++){
			if (siz[j]>0)
				gp[j] = new int[siz[j]];
			count[j]=siz[j];
		}
		for (int i=0; i<n; i++){
			int j=s[i];
			count[j]--;
			gp[j][count[j]] = i;
		}//for
		return gp;
	}
	//STATIC MEMBERS:==============================
	static void test (){
		test(10, RG);}
	static void test(int nn, Random rg){
		unitTest(nn, rg);}
	static void unitTest (int nn, Random rg){
		UnionFind uf = new UnionFind(nn);
		for (int i=0; i<2*nn; i++){ //
			boolean choose = rg.nextBoolean();
			if (choose){ //union
				int u = rg.nextInt(nn); int v = rg.nextInt(nn);
				uf.union(uf.find(u), uf.find(v));
			}else{ // find
				uf.find(rg.nextInt(nn));
			}
		}
		uf.dump();
	}//randomUF
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public static void main (String[] args){
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		int ss = (args.length>0)? Integer.valueOf(args[0]) : 11;
		int nn = (args.length>1)? Integer.valueOf(args[1]) : 10;
		int mm = (args.length>2)? Integer.valueOf(args[2]) : 0;
		Random rg = (ss==0)? new Random() : new Random(ss);
	
		switch(mm) {
			case 0: debug("mm = 0:");
					test(nn,rg);	
					break;
			case 1: debug("mm = 1:");
					unitTest(nn, new Random(111));	// debugged!
					break;
			case 2: debug("mm = 2:");
					unitTest(100, new Random(111));	
					break;
			case 3: debug("mm = 3:");
					unitTest(1000, new Random(111));	
					break;
			default: debug("default:");
					 break;
		}
	}//main
}//class UnionFind
