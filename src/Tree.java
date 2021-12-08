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
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
