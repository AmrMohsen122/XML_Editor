import java.util.ArrayList;
import java.util.Collections;

public class Compression {
	private final static int NUMBER_CHAR = 256;
	private static TreeNode root;

	// fill the hashtable which contains each character and its replacement
	private static void fillReplacementTable(TreeNode root, String[] replacementTable, String code) {
		if (root instanceof Leaf) {
			replacementTable[((Leaf) root).getCharacter()] = code;
			return;
		}
		// left = 0 , right = 1
		fillReplacementTable(root.leftChild, replacementTable, code + "0");
		fillReplacementTable(root.rightChild, replacementTable, code + "1");
	}

	// where the magic happens
	public static String encode(String s) {
		int[] freqArray = new int[NUMBER_CHAR];
		String[] replacementTable = new String[NUMBER_CHAR];
		ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
		String encoded = "";
		for (int i = 0; i < s.length(); i++) {
			freqArray[s.charAt(i)]++;
		}
		for (int i = 0; i < NUMBER_CHAR; i++) {
			if (freqArray[i] != 0) {
				nodes.add(new Leaf((char) i, freqArray[i]));
			}
		}
		// Sort the nodes in ascending order to begin constructing the tree
		Collections.sort(nodes);

		// Creating the HuffMan Tree
		// As long as we haven't created a root keep going
		while (nodes.size() != 1) {
			TreeNode combined = new TreeNode(nodes.remove(0), nodes.remove(0));
			// finds the position at which the node shall be inserted to keep the order
			int i = 0;
			while (i < nodes.size()) {
				if (nodes.get(i).compareTo(combined) != -1) {
					break;
				}
				i++;
			}
			nodes.add(i, combined);
		}
		root = nodes.get(0);
		fillReplacementTable(root, replacementTable, "");

		for (int i = 0; i < s.length(); i++) {
			encoded += replacementTable[s.charAt(i)];
		}
//		// testing
//		for (int i = 0; i < NUMBER_CHAR; i++) {
//			if (replacementTable[i] != null)
//				System.out.println((char) i + " : " + replacementTable[i]);
//		}
//		System.out.println(encoded);
		return encoded;
	}

	// where the magic is removed
	public static String decode(String encoded) {
		String decoded = "";
		TreeNode start = root;
		for (int i = 0; i < encoded.length(); i++) {
			//rules for traversing the tree
			if (encoded.charAt(i) == '0') {
				start = start.leftChild;
			} else if (encoded.charAt(i) == '1') {
				start = start.rightChild;
			}
			// if you hit a leaf node just add its value and restart the tree
			if (start instanceof Leaf) {
				decoded += ((Leaf) start).getCharacter();
				start = root;
			}
		}
		return decoded;
	}

	// used for testing
	public static void main(String[] args) {
//		String a = "<breakfast_menu><food><name>Belgian Waffles</name><price>$5.95</price><description>Two of our famous Belgian Waffles with plenty of real maple syrup</description><calories>650</calories></food><food><name>Strawberry Belgian Waffles</name><price>$7.95</price><description>Light Belgian waffles covered with strawberries and whipped cream</description><calories>900</calories></food><food><name>Berry-Berry Belgian Waffles</name><price>$8.95</price><description>Belgian waffles covered with assorted fresh berries and whipped cream</description><calories>900</calories></food><food><name>French Toast</name><price>$4.50</price><description>Thick slices made from our homemade sourdough bread</description><calories>600</calories></food><food><name>Homestyle Breakfast</name><price>$6.95</price><description>Two eggs, bacon or sausage, toast, and our ever-popular hash browns</description><calories>950</calories></food></breakfast_menu>";
//		String a = "AAAABBBBBCD";
		String a = "<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
		String b = encode(a);
		String c = decode(b);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}

}

class TreeNode implements Comparable<TreeNode> {
	protected int frequency;
	protected TreeNode leftChild;
	protected TreeNode rightChild;

	// Creates a node from the combined frequencies of its children
	public TreeNode(TreeNode leftChild, TreeNode rightChild) {
		frequency = leftChild.frequency + rightChild.frequency;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	// this will be used in the child class leaf
	public TreeNode(int freq) {
		frequency = freq;
		leftChild = null;
		rightChild = null;
	}

	// in-order tree traversal
	private void print(TreeNode root) {
		if (root == null) {
			return;
		}
		print(root.leftChild);
		System.out.println(root);
		print(root.rightChild);
	}

	public void print() {
		print(this);
	}

	// this will be used later for sorting
	@Override
	public int compareTo(TreeNode nodeToCompare) {
		if (frequency == nodeToCompare.frequency) {
			return 0;
		} else if (frequency > nodeToCompare.frequency) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public String toString() {
		return "" + frequency;
	}
}

class Leaf extends TreeNode {
	private char character;

	/**
	 * Constructor creates a HuffMan tree leaf node
	 * 
	 * @param character the character to be added
	 * @param frequency the number of occurrences
	 */
	public Leaf(char character, int frequency) {
		super(frequency);
		this.character = character;
	}

	public char getCharacter() {
		return character;
	}

	@Override
	public String toString() {
		return character + " : " + frequency;
	}
}
