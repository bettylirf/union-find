/* myUnionFind.java
 *	THIS IS A SKELETON CLASS FOR YOUR OWN IMPLEMENTATION OF
 *	THE METHODS OF THE UnionFindInterface:
 *
 * 	WHAT IS TO BE IMPLEMENTED:
 *		int find(int u); 			-- implement path compression 
 *		void union(int u, int v);	-- implement size heuristic
 *
 * 	NOTE: There is nothing to do for link(u,v).
 *
 *	REMEMBER THAT YOU MUST USE
 *			getpi(u)	to get the value of pi[u]
 *			setpi(u,v)	to change the value of pi[u] to v
 *	IF THIS RULE IS BROKEN, YOU GET ZERO FOR THIS PART.
 *	EACH TIME YOU CALL THESE METHODS, THE "count" VARIABLE IS
 *	INCREMENTED.  YOUR GOAL IS THE MINIMIZE THE FINAL "count" VALUE
 *	IN OUR RANDOM SIMULATION.  SEE THE OUTPUT FILES
 *
 *			OUT/testUnionFile* and OUT/testMyUnionFile*
 *
 *  Chee Yap, Basic Algo, Fall2021
 *************************************************************/


import java.util.*;

class myUnionFind extends UnionFind{
	// MEMBERS:==========================================
		int[] size;
	// CONSTRUCTORS: from super class:===================
		myUnionFind(){ super();}
		myUnionFind(int n){
			super(n);
			size = new int[U];
			for (int i=0; i<U; i++)
				size[i]=1;
		}
	// METHODS TO BE IMPLEMENTED: =======================
	public int find (int u){	// find with path compression
		numFinds++;
		int p = getpi(u);
		ArrayList<Integer> path = new ArrayList<>();
		while(p != u){
			path.add(u);
			u = p;
			p = getpi(u);
		}
		if(path.size() == 0) return p;
		for(Integer v : path)
			setpi(v, p);
		return p;
	}//find

	public int union (int u, int v){ // union with size heuristic
		numUnions++;
		u = find(u);
		v = find(v);

		if(size[u] <= size[v]){
			size[v] += size[u];
			return link(u, v);
		}
		else{
			size[u] += size[v];
			return link(v, u);
		}
	}//union
	void dumpStats (){ 
		debug("myUnionFind Statistics for U="+U); // "myUnionFind"
		debug("Number of improved finds="+numFinds);
		debug("Number of improved unions="+numUnions);
		debug("Number of naive links="+numLinks);
		debug("Total count ="+count);
	}//dumpStats
	//STATIC MEMBERS:==============================
	static void myTest (){
		myTest(10, RG);}
	static void myTest(int nn, Random rg){
		myUnitTest(nn, rg);}
	static void myUnitTest (int nn, Random rg){
		myUnionFind uf = new myUnionFind(nn);
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
		int mm = (args.length>2)? Integer.valueOf(args[2]) : 3;
		Random rg = (ss==0)? new Random() : new Random(ss);
	
		switch(mm) {
			case 0: debug("mm = 0:");
					myTest(nn,rg);	
					break;
			case 1: debug("mm = 1:");
					myUnitTest(nn, new Random(111));	// debugged!
					break;
			case 2: debug("mm = 2:");
					myUnitTest(100, new Random(111));	
					break;
			case 3: debug("mm = 3:");
					myUnitTest(1000, new Random(111));	
					break;
			default: debug("default:");
					 break;
		}
	}//main
}//class
