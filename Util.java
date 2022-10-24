//class Util 
import java.util.Random;

public class Util{
	// MEMBERS:
	//////////////////////////////////////////
		static int COUNT=0;
		static Random RG = new Random(111);
		static int verbosity=1; //0=silence. Larger number is more verbose
			// verbosity is useful when debugging code...
	// CONSTRUCTORS:
	//////////////////////////////////////////
		Util (){ this(111); }
		Util(int ss){
			RG = (ss==0) ? new Random(0) : new Random(ss); }
	// METHODS:
	//////////////////////////////////////////
	static void dbug (String ss){ //no \n
		if (verbosity>0) System.out.print(ss); } 
	static void debug (String ss){//with \n
		if (verbosity>0) System.out.println(ss); } 
	static void debug(String ss, String ff){
		// E.g., debug("Testing", "\n =========== %s:\n");
		if (verbosity>0) System.out.printf(ff, ss); } 

	// THIS IS EXACTLY THE SAME AS "debug"
	// REASON for name change?
	//		In many places, we are NOT really debugging!
	static void see (String ss){  //no \n
		if (verbosity>0) System.out.print(ss); } 
	static void seee (String ss){ //with \n
		if (verbosity>0) System.out.println(ss); } 
	static void see(String ss, String ff){
		// E.g., debug("Testing", "\n =========== %s:\n");
		if (verbosity>0) System.out.printf(ff, ss); } 


	static String tab (int n, char c){
		// returns a string of length n filled with c
		char[] chars = new char[n];
		java.util.Arrays.fill(chars, c);
		return String.valueOf(chars); }//tab
}//Util
