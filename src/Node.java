package Testproject;

import java.util.ArrayList;



public class Node {
	private String tagName;
	private String tagData;
	private int depth;
	//private ArrayList<Node> children=new ArrayList<>(); // to store children of a node
	private ArrayList<ArrayList<Node>> children=new ArrayList<>();
	//empty constructor 
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
	 static void addDuplicateChild(Node node,Node child) {
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
		 int i=0;
		 String s="";
		 while (i<n) {
			 s=s+" ";
			 i++;
		 }
		 
		 return s; 
		 
	 }
	 /*
	static void print (Node node) {
		
	
		if (node.getTagData()==null) {
			System.out.println(spaces(2)+"\""+node.getTagName().substring(1,node.getTagName().length()-1)+"\""+":"+" {");
			if (node.getChildren().size()==0)return;
			for (int i=0;i<node.getChildren().size();i++) {
				print(node.getChildren().get(i));
			}
			System.out.println(spaces(2)+"}");
		}
		else{
			
			
		System.out.println(spaces(4)+"\""+node.getTagName().substring(1,node.getTagName().length()-1)+"\""+": "+node.getTagData()+",");
			if (node.getChildren().size()==0) {
				
				return;}
			for (int i=0;i<node.getChildren().size();i++) {
				print(node.getChildren().get(i));
			}
			
		}
		
		
	}
	*/
	
	 
	
	
	

}