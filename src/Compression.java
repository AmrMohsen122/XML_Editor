import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.PriorityQueue;

public abstract class Compression {
	private final static int NUMBER_CHAR = 256;
	private static int next = -1;
	private static TreeNode huffmanRoot;

	/*********************** HELPER METHODS ******************/

	private static void fillReplacementTable(TreeNode root, String[] replacementTable, String code) {
		// fill the hashtable which contains each character and its replacement
		if (root instanceof Leaf) {
			replacementTable[((Leaf) root).getCharacter()] = code;
			return;
		}
		// left = 0 , right = 1
		fillReplacementTable(root.leftChild, replacementTable, code + "0");
		fillReplacementTable(root.rightChild, replacementTable, code + "1");
	}

	private static StringBuilder charToBinaryString(char c) {
		StringBuilder result = new StringBuilder();
		String binary = Integer.toBinaryString(c & 0xFF);
		for(int i = 0 ; i < (8 - binary.length()) ; i++) {
			result.append('0');
		}
		result.append(binary);
		return result;
	}
	
	/************************ File Handling ******************/

	private static void writeBinaryToFile(StringBuilder binary, String filePath) throws IOException {
		FileOutputStream fileOS = new FileOutputStream(filePath);
		DataOutputStream dataOS = new DataOutputStream(fileOS);
		byte flag = 0;
		byte toByte = 0;
		byte count = 7;
		byte remainder = (byte) (binary.length() % 8);
		if (remainder != 0) {
			flag = (byte) (8 - remainder);
		}
		dataOS.writeByte(flag);
		for (int i = 0; i < binary.length(); i++) {
			if (binary.charAt(i) == '1') {
				toByte |= (1 << count);
			}
			// everytime you form 8 bits write them as a byte to the file
			if (count == 0) {
				dataOS.writeByte(toByte);
				toByte = 0;
				count = 8;
			}
			count--;
		}
		// the last byte will be padded by zeros equal to the flag value
		dataOS.writeByte(toByte);
		dataOS.close();
	}

	private static StringBuilder readBinaryFromFile(String filePath) throws IOException {
		FileInputStream fileIS = new FileInputStream(filePath);
		DataInputStream dataIS = new DataInputStream(fileIS);
		StringBuilder encoded = new StringBuilder();
		char[] read = new char[dataIS.available()];
		int count = 0;
		while (dataIS.available() > 0) {
			read[count++] = (char) dataIS.readByte();
		}
		dataIS.close();
		char flag = read[0];
		for (int i = 1; i < count; i++) {
			encoded.append(charToBinaryString(read[i]));
		}
		if (flag != 0) {
			encoded.delete(encoded.length() - flag, encoded.length());
		}
		return encoded;
	}

	/******************** TREE STROING AND READING ******************/

	private static void encodeHuffmanTree(TreeNode root, StringBuilder s) {
		if (root instanceof Leaf) {
			s.append('0');
			s.append(((Leaf) root).getCharacter());
			return;
		} else {
			s.append('1');
			encodeHuffmanTree(root.leftChild, s);
			encodeHuffmanTree(root.rightChild, s);
		}
	}

	private static StringBuilder binarfyHuffmanTree(TreeNode huffmanRoot) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		encodeHuffmanTree(huffmanRoot, sb);
		char current;
		for (int i = 0; i < sb.length() - 1; i++) {
			current = sb.charAt(i);
			if (current == '1') {
				temp.append(current);

			} else {
				temp.append(current);
				temp.append(charToBinaryString(sb.charAt(++i)));
			}
		}
		return temp;
	}

	private static TreeNode decodeHuffmanTree(String encoded) {
		// next should start from -1;
		TreeNode decodedRoot;
		next += 1;
		// a leaf
		if (next < encoded.length()) {

		}
		if (encoded.charAt(next) == '0') {
			decodedRoot = new Leaf();
			char currentChar = 0;
			char count = 7;
			for (int i = next + 1; i < next + 9; i++) {
				if (encoded.charAt(i) == '1') {
					currentChar |= (1 << count);
				}
				count--;
			}
			((Leaf) decodedRoot).setCharacter(currentChar);
			next = next + 8;
		} else {
			decodedRoot = new TreeNode();
			decodedRoot.leftChild = decodeHuffmanTree(encoded);
			decodedRoot.rightChild = decodeHuffmanTree(encoded);
		}

		return decodedRoot;
	}

	/******************** ENCODING AND DECODING *******************/
	
	private static StringBuilder encode(String s) {
		int[] freqArray = new int[NUMBER_CHAR];
		String[] replacementTable = new String[NUMBER_CHAR];
		PriorityQueue<TreeNode> nodes = new PriorityQueue<TreeNode>();
		StringBuilder encoded = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			freqArray[s.charAt(i)]++;
		}
		// fill the heap
		for (int i = 0; i < NUMBER_CHAR; i++) {
			if (freqArray[i] != 0) {
				nodes.add(new Leaf((char) i, freqArray[i]));
			}
		}
		while (nodes.size() != 1) {
			// remove the two smallest elements in the heap, combine them into a parent node and re-insert it in the heap
			TreeNode combined = new TreeNode(nodes.poll(), nodes.poll());
			nodes.add(combined);
		}
		huffmanRoot = nodes.poll();
		fillReplacementTable(huffmanRoot, replacementTable, "");

		for (int i = 0; i < s.length(); i++) {
			encoded.append(replacementTable[s.charAt(i)]);
		}
		return encoded;
	}

	private static StringBuilder decode(StringBuilder encoded, TreeNode huffmanRoot) {
		StringBuilder decoded = new StringBuilder();
		TreeNode start = huffmanRoot;
		for (int i = 0; i < encoded.length(); i++) {
			// rules for traversing the tree
			if (encoded.charAt(i) == '0') {
				start = start.leftChild;
			} else if (encoded.charAt(i) == '1') {
				start = start.rightChild;
			}
			// if you hit a leaf node just add its value and restart the tree
			if (start instanceof Leaf) {
				decoded.append(((Leaf) start).getCharacter());
				start = huffmanRoot;
			}
		}
		return decoded;
	}

	/********************************* FINAL METHODS *************************/

	public static void compress(String xmlContent, String compressedOutputPath, String encodedHuffmanOutputPath)
			throws IOException {
		huffmanRoot = null;
		StringBuilder encodedXML = encode(xmlContent);
		writeBinaryToFile(encodedXML, compressedOutputPath); 
		StringBuilder encodedHuffman = binarfyHuffmanTree(huffmanRoot);
		writeBinaryToFile(encodedHuffman, encodedHuffmanOutputPath);
	}

	public static String decompress(String compressedOutputPath, String encodedHuffmanOutputPath) throws IOException {
		next = -1;
		TreeNode reconstructedHuffmanTree = decodeHuffmanTree(readBinaryFromFile(encodedHuffmanOutputPath).toString());
		return decode(readBinaryFromFile(compressedOutputPath) , reconstructedHuffmanTree).toString();
	}
}

/************************** TREE IMPLEMENTATION ************************/
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

	public TreeNode() {
		leftChild = null;
		rightChild = null;
		frequency = 0;
	}

	// in-order tree traversal
	private void print(TreeNode root) {
		if (root instanceof Leaf) {
			System.out.println(root);
			return;
		}
		print(root.leftChild);
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

	public Leaf(char character, int frequency) {
		super(frequency);
		this.character = character;
	}

	public Leaf() {
		super();
		character = ' ';
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char c) {
		character = c;
	}

	@Override
	public String toString() {
		return character + " : " + frequency;
	}
}