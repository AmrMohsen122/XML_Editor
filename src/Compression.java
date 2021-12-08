import java.util.HashMap;
public class Compression {
	
	public static void main(String[] args) {
			
			
			
			
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
}

class Leaf extends TreeNode{
	private char character;
	
	//Creates a leaf node from a character and its frequency
	public Leaf(char character , int frequency){
		super(frequency);
		this.character = character;
	}
}
