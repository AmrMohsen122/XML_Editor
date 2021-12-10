import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
//TODO CREATE A FILE AND ADD THE BINARY CODE TO IT

public abstract class Compression {
	private final static int NUMBER_CHAR = 256;
	private static int next = -1;
	private static TreeNode huffmanRoot;

	/*********************** HELPER METHODS ******************/

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

	private static String binarfyHuffmanTree(TreeNode huffmanRoot) {
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
				temp.append(String.format("%8s", Integer.toBinaryString(sb.charAt(++i))).replace(' ', '0'));
			}
		}
		return temp.toString();
	}

	private static void writeEncodedHuffman(String encodedHuffman, String encodedHuffmanOutputPath) throws IOException {
		FileOutputStream fileOS = new FileOutputStream(encodedHuffmanOutputPath);
		DataOutputStream dataOS = new DataOutputStream(fileOS);
		byte flag = 0;
		String toInt = "";
		byte remainder = (byte) (8 - encodedHuffman.length() % 8);
		if (remainder != 0) {
			flag = remainder;
		}
		dataOS.writeByte(flag);
		for (int i = 0; i < encodedHuffman.length(); i++) {
			toInt += encodedHuffman.charAt(i);
			// everytime you concat. 8 bits write them as a byte to the file
			if ((i + 1) % 8 == 0) {
				dataOS.writeByte(Integer.parseInt(toInt, 2));
				toInt = "";
			}
		}
		for (; remainder != 0; remainder--) {
			toInt += '0';
			if (remainder == 1) {
				dataOS.writeByte(Integer.parseInt(toInt, 2));
			}
		}
		dataOS.close();
	}

	private static TreeNode decodeHuffmanTree(String encoded) {
		// next should start from -1;
		TreeNode decodedRoot;
		next += 1;
		// a leaf
		if (next < encoded.length()) {

		}
		if (encoded.charAt(next) == '0') {
			decodedRoot = new Leaf(' ', 3);
			String toChar = "";
			for (int i = next + 1; i < next + 9; i++) {
				toChar += encoded.charAt(i);
			}
			((Leaf) decodedRoot).setCharacter((char) Integer.parseInt(toChar, 2));
			next = next + 8;
		} else {
			decodedRoot = new TreeNode(10);
			decodedRoot.leftChild = decodeHuffmanTree(encoded);
			decodedRoot.rightChild = decodeHuffmanTree(encoded);
		}

		return decodedRoot;
	}

	private static TreeNode readEncodedHuffman(String encodedHuffmanOutputPath) throws IOException {
		FileInputStream fileIS = new FileInputStream(encodedHuffmanOutputPath);
		DataInputStream dataIS = new DataInputStream(fileIS);
		StringBuffer encoded = new StringBuffer();
		char[] read = new char[dataIS.available()];
		int count = 0;
		while (dataIS.available() > 0) {
			read[count++] = (char) dataIS.readByte();
		}
		dataIS.close();
		char flag = read[0];
		for (int i = 1; i < count; i++) {
			encoded.append(String.format("%8s", Integer.toBinaryString(read[i] & 0xFF)).replace(' ', '0'));
		}
		encoded.delete(encoded.length() - flag, encoded.length());
		return decodeHuffmanTree(encoded.toString());
	}

	/******************** ENCODING AND DECODING *******************/

	// where the magic happens
	private static String encode(String s) {
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
		huffmanRoot = nodes.get(0);
		fillReplacementTable(huffmanRoot, replacementTable, "");

		for (int i = 0; i < s.length(); i++) {
			encoded += replacementTable[s.charAt(i)];
		}
		return encoded;
	}

	private static void writeEncoded(String encoded, String outputFilePath) throws IOException {
		FileOutputStream fileOS = new FileOutputStream(outputFilePath);
		DataOutputStream os = new DataOutputStream(fileOS);
		String toInt = "";
		byte flag = 0;
		byte remainder = (byte) (8 - (encoded.length() % 8));
		if (remainder != 0) {
			flag = remainder;
		}
		os.writeByte(flag);
		for (int i = 0; i < encoded.length(); i++) {
			toInt += encoded.charAt(i);
			// everytime you concat. 8 bits write them as a byte to the file
			if ((i + 1) % 8 == 0) {
				os.writeByte(Integer.parseInt(toInt, 2));
				toInt = "";
			}

		}
		// if the string length is not divisible by 8 the previous loop will terminate
		// with some bits not added
		// so pad and add the rest of the bits in that case
		for (; remainder != 0; remainder--) {
			toInt += '0';
			if (remainder == 1) {
				os.writeByte(Integer.parseInt(toInt, 2));
			}
		}
		os.close();
	}

	private static String readAndDecode(String outputFilePath, TreeNode huffmanRoot) throws IOException {
		// create a buffered reader to read the encoded line
		StringBuffer encoded = new StringBuffer();
		FileInputStream fileIS = new FileInputStream(outputFilePath);
		DataInputStream dataIS = new DataInputStream(fileIS);
		char[] read = new char[dataIS.available()];
		int count = 0;
		while (dataIS.available() > 0) {
			read[count++] = (char) dataIS.readByte();
		}
		dataIS.close();
		// the header byte which contains remainder
		char flag = read[0];
		// create the econded StringBuffer
		for (int i = 1; i < count; i++) {
			encoded.append(String.format("%8s", Integer.toBinaryString(read[i] & 0xFF)).replace(' ', '0'));
		}
		encoded.delete(encoded.length() - flag, encoded.length());
		return decode(encoded.toString(), huffmanRoot);
	}

	// where the magic is removed
	private static String decode(String encoded, TreeNode huffmanRoot) {
		String decoded = "";
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
				decoded += ((Leaf) start).getCharacter();
				start = huffmanRoot;
			}
		}
		return decoded;
	}

	/********************************* FINAL METHODS *************************/
	public static void compress(String xmlContent, String compressedOutputPath, String encodedHuffmanOutputPath)
			throws IOException {
		huffmanRoot = null;
		String encodedXML = encode(xmlContent);
		writeEncoded(encodedXML, compressedOutputPath);
		String encodedHuffman = binarfyHuffmanTree(huffmanRoot);
		writeEncodedHuffman(encodedHuffman, encodedHuffmanOutputPath);
	}

	public static String decompress(String compressedOutputPath, String encodedHuffmanOutputPath) throws IOException {
		next = -1;
		TreeNode reconstructedHuffmanTree = readEncodedHuffman(encodedHuffmanOutputPath);
		return readAndDecode(compressedOutputPath, reconstructedHuffmanTree);
	}

	// used for testing
	public static void main(String[] args) throws IOException {
		String compressedFilePath = "D:\\Compression\\outputCompressed.txt";
		String huffmanFilePath = "D:\\Compression\\outputHuffman.txt";
		
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

	public void setCharacter(char c) {
		character = c;
	}

	@Override
	public String toString() {
		return character + " : " + frequency;
	}
}
