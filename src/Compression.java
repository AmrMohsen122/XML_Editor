import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
public class Compression {
	final static int NUMBER_CHAR = 256;
	// where the magic happens
	public static void encode(String s) {
		int[] freqArray = new int[NUMBER_CHAR];
		ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode root;
		for(int i = 0 ; i < s.length() ; i++) {
			freqArray[s.charAt(i)]++;
		}
		for(int i = 0 ; i < NUMBER_CHAR ; i++) {
			if(freqArray[i] != 0) {
				nodes.add(new Leaf((char)i , freqArray[i]));
			}
		}
		//Sort the nodes in ascending order to begin constructing the tree
		Collections.sort(nodes);
		//Creating the HuffMan Tree
		
		//As long as we haven't created a root keep going
		while(nodes.size() != 1) {
			TreeNode combined  = new TreeNode(nodes.remove(0) , nodes.remove(0));
			//finds the position at which the node shall be inserted to keep the order
			int i = 0;
			while(i < nodes.size()) {
				if(nodes.get(i).compareTo(combined) != -1) {
					break;
				}
				i++;
			}
			nodes.add(i, combined);
		}
		root = nodes.get(0);
		System.out.print(root);
		
		
	}
	// used for testing
	public static void main(String[] args) {
		String a = "<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
		encode(a);
		System.out.println(a.length());
			
			
	}

}


class TreeNode implements Comparable<TreeNode>{
	protected int frequency;
	protected TreeNode leftChild;
	protected TreeNode rightChild;

	//Creates a node from the combined frequencies of its children
	public TreeNode(TreeNode leftChild , TreeNode rightChild){
		frequency = leftChild.frequency + rightChild.frequency;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	// will be used in the child class leaf
	public TreeNode(int freq) {
		frequency = freq;
		leftChild = null;
		rightChild = null;
	}	
	// this will be used later for sorting
	@Override
	public int compareTo(TreeNode nodeToCompare) {
		if(frequency == nodeToCompare.frequency) {
			return 0;
		}
		else if(frequency > nodeToCompare.frequency) {
			return 1;
		}
		else {
			return -1;
		}
	}
	@Override
	public String toString() {
		return "" + frequency;
	}
}

class Leaf extends TreeNode{
	private char character;
	
	/**
	 *  Constructor creates a HuffMan tree leaf node
	 * @param character the character to be added
	 * @param frequency the number of occurrences
	*/
	public Leaf(char character , int frequency){
		super(frequency);
		this.character = character;
	}
	
	@Override
	public String toString() {
		return character + " : " + frequency;
	}
}
