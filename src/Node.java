package Testproject;

import java.util.ArrayList;



public class Node {
	private String tagName;
	private String tagData;
	private int depth;
	private ArrayList<ArrayList<Node>> children=new ArrayList<>();

	public Node() {
		
		tagName=null;
		tagData=null;
		depth=0;
	}
	public Node(String tagName, String tagData,int depth) {
		
		this.tagName = tagName;
		this.tagData = tagData;
		this.depth=depth;
	
		
	}
	
	/*                  ----getters----               */
//returns refrence of arraylist containing children of the node
	
public ArrayList<ArrayList<Node> >getChildren() {
	return children;
}
//returns data of the node if it has data
public String getTagData() {
	return tagData;
}
//returns name of the node
public String getTagName() {
	return tagName;
}
 public int getDepth() {
		return depth;
	}
/*                  ----Setters----               */
// set node data
	public void setTagData(String tagData) {
		this.tagData = tagData;
	}
	//set tag name of the node
	 public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	   public void setDepth(int depth) {
		this.depth = depth;
	} 
	 /*--->function that add child to the node calls it <---*/
	 void addChild(Node child,int i) {
		 this.children.get(i).add(child);
	 }
	 static public void addDuplicateChild(Node node,Node child) {
		 if (node.getChildren().size()==0) {
				 node.getChildren().add(new ArrayList<Node>());
				 node.addChild(child, 0);
				 return;
			 }
		 else {
		 
		 for (int i=0;i<node.getChildren().size();i++) {
			 //System.out.println(node.getChildren().get(i).get(0).getTagName());
			 //System.out.println(child.getTagName());
			 if (node.getChildren().get(i).get(0).getTagName().equals(child.getTagName()) ) {
			node.addChild(child, i);
			
			return;
			
			 }
		 }
			 node.getChildren().add(new ArrayList<Node>());
			 node.addChild(child, node.getChildren().size()-1);	 
		 }
	 }
	static String spaces(int n) {
		int i = 0;
		int j = 2 * n;
		String s = "";
		while (i < j) {
			s = s + " ";
			i++;
		}
		return s;
	}

	static void printValue(Node leaf, StringBuffer s, int j, int size) {
		if (Tree.isNumeric(leaf.getTagData())) {
			s.append(Node.spaces(leaf.getDepth() + 2) + (leaf.getTagData()));
		}
		else {
			s.append(Node.spaces(leaf.getDepth() + 2) + "\"" + (leaf.getTagData()) + "\"");
		}
		if (j != size - 1) {
			s.append(",");
		}
		s.append("\n");
	}
	static void printName_Value(Node leaf, StringBuffer s) {
		if (Tree.isNumeric(leaf.getTagData())) {

			s.append(Node.spaces(leaf.getDepth() + 1) + "\""
					+ leaf.getTagName().substring(1, leaf.getTagName().length() - 1) + "\": " + (leaf.getTagData())
					);
		}
		else {

			s.append(

					Node.spaces(leaf.getDepth() + 1) + "\""
							+ leaf.getTagName().substring(1, leaf.getTagName().length() - 1) + "\": " + "\""
							+ (leaf.getTagData()) + "\"" 
			);
		}
		s.append("");
	}

	static void conversion(Node parent, StringBuffer json) {
		
		if (parent.getTagData() == null) {
			if (parent.getDepth() == 0) {
				json.append("{\n");
				json.append(
				Node.spaces(parent.getDepth() + 1) + "\""
			    + parent.getTagName().substring(1, parent.getTagName().length() - 1)
			    + "\": {\n"
			    );
			}
			// iterating the children of the root
			for (int i = 0; i < parent.getChildren().size(); i++) {
				if (parent.getChildren().get(i).size() == 1) {
					if (parent.getChildren().get(i).get(0).getTagData() == null) {
						json.append(
					   Node.spaces(parent.getChildren().get(i).get(0).getDepth() + 1) + "\""
					 + parent.getChildren().get(i).get(0).getTagName().substring(1,parent.getChildren().get(i).get(0).getTagName().length() - 1)
					+ "\": { \n"
							      );
					}
					conversion(parent.getChildren().get(i).get(0), json);
					if (parent.getChildren().size()-1!=i) {
					if (parent.getTagData()==null&&parent.getChildren().size()==1&&parent.getChildren().get(0).get(0).getTagData()!=null) {
						//System.out.println(parent.getTagName());
					json.append("\n");
					}
					else if(parent.getTagData()==null&&parent.getChildren().get(0).get(0).getTagData()==null) {
						//System.out.println(parent.getTagName());
					}
					else {
						
						json.append(",\n");
					}
					}else {
						json.append("\n");
					}
					if (parent.getChildren().get(i).get(0).getTagData() == null) {
						if (json.toString().charAt(json.length()-2)==',') {
							
							json.deleteCharAt(json.length()-2);
						}
							
						if (i==parent.getChildren().size()-1) {
						json.append(  Node.spaces(parent.getChildren().get(i).get(0).getDepth() + 1) + "}\n");
						}
						else {
							json.append(  Node.spaces(parent.getChildren().get(i).get(0).getDepth() + 1) + "},\n");
						}
					}
				} else {
					for (int j = 0; j < parent.getChildren().get(i).size(); j++) {
						if (j == 0) {
							json.append(
									Node.spaces(parent.getChildren().get(i).get(0).getDepth() + 1) + "\""
											+ parent.getChildren().get(i).get(0).getTagName().substring(1,
													parent.getChildren().get(i).get(0).getTagName().length() - 1)
											+ "\": [ \n");
						}
						if (parent.getChildren().get(i).get(j).getTagData() != null) {
							printValue(parent.getChildren().get(i).get(j), json, j, parent.getChildren().get(i).size());
						} else {
							json.append(Node.spaces(parent.getDepth() + 2) + "{ \n");
							conversion(parent.getChildren().get(i).get(j), json);
			
							
							//json.append("\n");
							if (j != parent.getChildren().get(i).size() - 1) {
								
								json.append(Node.spaces(parent.getDepth() + 2) + "},\n");
							} else {
								json.append(Node.spaces(parent.getDepth() + 2) + "}\n");	
							}
						}
						if (j == parent.getChildren().get(i).size() - 1) {
							json.append(Node.spaces(parent.getChildren().get(i).get(0).getDepth() + 1) + "]\n");
						}
					}
				}
			}
		} else {
			printName_Value(parent, json);
			return;
		}
		// closing the root
		if (parent.getDepth() == 0) {

			json.append("\n" + Node.spaces(parent.getDepth() + 1) + "}\n");
			json.append("}");
		}
	}
}