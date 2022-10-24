public interface UnionFindInterface {
	int U=10; // capacity
	int find (int u); // return root
	int union (int u, int v); // return v
	int link (int u, int v); // return root
}
