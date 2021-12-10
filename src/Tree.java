package Testproject;

import java.util.ArrayList;

public class Tree {
	private Node root;
	Tree(){
		root=null;
		
	}
	Tree(Node root){
		this.root=root;
	}
	public Node getRoot() {
		return root;
	}
// node root ; root.setname(xml[0]);
	//static Index i=new Index(0);
	/*
static void fillTree_XML(Node node ,ArrayList<String>xmlarr,Index i) {
	//if (i.index==xmlarr.size())return;
	

	for (;i.index<xmlarr.size();i.index=i.index+1) {
		
		//System.out.println("index:"+i.index);
		if (xmlarr.get(i.index).length()>=2&&xmlarr.get(i.index).charAt(1)=='/') {   
			return;
		}
		else if(xmlarr.get(i.index).charAt(0)!='<'){
			node.setTagData(xmlarr.get(i.index));
			i.index=i.index+1;
			return;
		}
		else {
		Node child=new Node();	
		child.setDepth(node.getDepth()+1);
		child.setTagName(xmlarr.get(i.index));
		node.addChild(child);
		i.index=i.index+1;
		fillTree_XML(child,xmlarr,i);
			
		}
		
		
	
	}
	
*/
	public static boolean isNumeric(String str) {
		  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
		}
static void printLeaf(Node leaf) { // print value and name
	if (isNumeric(leaf.getTagData())) {
		System.out.print("\""+ leaf.getTagName().substring(1, leaf.getTagName().length()-1) +"\": "
				+ (leaf.getTagData())+",\n");
	}
	
	else {
System.out.print("\""+ leaf.getTagName().substring(1, leaf.getTagName().length()-1) +"\": "
		+ "\""+(leaf.getTagData())+"\""+","
		
		);
	}
	
}
static void printLeafV(Node leaf) { // print value only
	if (isNumeric(leaf.getTagData())) {
		System.out.print((leaf.getTagData())+",\n");
	}
	
	else {
System.out.print(  "\""+(leaf.getTagData())+"\""+","
		
		);
	}
	
}
	
void toJson(Node parent) {
	
	if (parent.getTagData()==null) { // take care == or .equals?
		//System.out.println(parent.getTagData());
		//System.out.println(parent.getTagName());
			if (parent.getDepth()==0) {
				System.out.println("{");
				System.out.println("\""+parent.getTagName().substring(1,parent.getTagName().length()-1)+"\": {");
				}
				for (int i=0;i<parent.getChildren().size();i++) {
					if(parent.getChildren().get(i).size()==1) {
						if(parent.getChildren().get(i).get(0).getTagData()==null) {
							System.out.println("");
							System.out.println("\""+
						parent.getChildren().get(i).get(0).getTagName().substring(1,parent.getChildren().get(i).get(0).getTagName().length()-1) +"\": {");
					}
							toJson(parent.getChildren().get(i).get(0));
							
						}
					else {
					for(int j=0;j<parent.getChildren().get(i).size();j++) {
						if (j==0) {
						System.out.println("\""+
						parent.getChildren().get(i).get(0).getTagName().substring(1,parent.getChildren().get(i).get(0).getTagName().length()-1)
						+"\": [");
						}
						//System.out.println("{");
						if (parent.getChildren().get(i).get(j).getTagData()!=null) {
							System.out.println("");
							printLeafV(parent.getChildren().get(i).get(j));
							
							if(j==parent.getChildren().get(i).size()-1) {
								System.out.println("");
								System.out.print("]");
								}
						}else {
							System.out.println("{");
						toJson(parent.getChildren().get(i).get(j));
						System.out.println("},");
						if(j==parent.getChildren().get(i).size()-1)System.out.println("]");
						 }
						
						}
					
					}
				}
				System.out.println(parent.getTagName());
				System.out.println("}");
	}
	
	else {
		
		printLeaf(parent);
		return;
		
	}
	
	
	if (parent.getDepth()==0) {
		System.out.println("}");
		
		}
	
	
	
}
	
	
	
	
	static void filltree2(Node node ,ArrayList<String>xmlarr,Index i) {
		for (;i.index<xmlarr.size();i.index=i.index+1) {
			
			//System.out.println("index:"+i.index);
			if (xmlarr.get(i.index).length()>=2&&xmlarr.get(i.index).charAt(1)=='/') {   
				return;
			}
			else if(xmlarr.get(i.index).charAt(0)!='<'){
				node.setTagData(xmlarr.get(i.index));
				i.index=i.index+1;
				return;
			}
			else {
			Node child=new Node();	
			child.setDepth(node.getDepth()+1);
			child.setTagName(xmlarr.get(i.index));
			Node.addDuplicateChild(node,child);
			i.index=i.index+1;
			filltree2(child,xmlarr,i);
				
			}
	
}
	
	
	
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
