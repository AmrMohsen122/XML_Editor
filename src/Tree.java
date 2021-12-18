package Testproject;

import java.util.ArrayList;
import java.lang.StringBuffer;
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
		/*
static void printLeaf(Node leaf , StringBuffer s) { // print value and name
	if (isNumeric(leaf.getTagData())) {
		s.append(Node.spaces(leaf.getDepth())+"\""+ leaf.getTagName().substring(1, leaf.getTagName().length()-1) +"\": "
				+ (leaf.getTagData())+",\n \n");
	}
	
	else {
s.append(Node.spaces(leaf.getDepth())+"\""+ leaf.getTagName().substring(1, leaf.getTagName().length()-1) +"\": "
		+ "\""+(leaf.getTagData())+"\""+", \n"
		
		);
	}
	
}
static void printLeafV(Node leaf, StringBuffer s) { // print value only
	if (isNumeric(leaf.getTagData())) {
		s.append(Node.spaces(leaf.getDepth())+(leaf.getTagData())+",\n \n ");
	}
	
	else {
s.append( Node.spaces(leaf.getDepth()) + "\""+(leaf.getTagData())+"\""+", \n");
	}
	
}
	
static void toJson(Node parent , StringBuffer s) {
	
	
	if (parent.getTagData()==null) { // take care == or .equals?
		//System.out.println(parent.getTagData());
		//System.out.println(parent.getTagName());
			if (parent.getDepth()==0) {
				s.append(Node.spaces(parent.getDepth())+"{ \n");
				s.append(Node.spaces(parent.getDepth())+"\""+parent.getTagName().substring(1,parent.getTagName().length()-1)+"\": { \n");
				}
				for (int i=0;i<parent.getChildren().size();i++) {
					if(parent.getChildren().get(i).size()==1) {
						if(parent.getChildren().get(i).get(0).getTagData()==null) {
							s.append("\n");
							s.append(Node.spaces(parent.getChildren().get(i).get(0).getDepth()) + "\""+
						parent.getChildren().get(i).get(0).getTagName().substring(1,parent.getChildren().get(i).get(0).getTagName().length()-1) +"\": { \n");
					}
							toJson(parent.getChildren().get(i).get(0) , s);
							
						}
					else {
					for(int j=0;j<parent.getChildren().get(i).size();j++) {
						if (j==0) {
						s.append(Node.spaces(parent.getChildren().get(i).get(0).getDepth())+"\""+
						parent.getChildren().get(i).get(0).getTagName().substring(1,parent.getChildren().get(i).get(0).getTagName().length()-1)
						+"\": [ \n");
						}
						//System.out.println("{");
						if (parent.getChildren().get(i).get(j).getTagData()!=null) {
							s.append("\n");
							printLeafV(parent.getChildren().get(i).get(j),s);
							
							if(j==parent.getChildren().get(i).size()-1) {
								s.append("\n");
								s.append(Node.spaces(parent.getDepth())+ "] \n");
								}
						}else {
							s.append(Node.spaces(parent.getDepth())+"{ \n");
						toJson(parent.getChildren().get(i).get(j), s);
						
						s.append(Node.spaces(parent.getDepth())+ "}, \n");
						if(j==parent.getChildren().get(i).size()-1)
							s.append(Node.spaces(parent.getDepth())+"] \n");
						 }
						
						}
					
					}
				}
				
				s.append(Node.spaces(parent.getDepth())+"\n"+"} \n");
	}
	
	else {
		
		printLeaf(parent ,s);
		return;
		
	}
	
	
	if (parent.getDepth()==0) {
		s.append(Node.spaces(parent.getDepth())+"} \n");
		
		}
	
	
	
}*/
	
	
	
	
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

	static StringBuffer removeJsonEmptyLines (StringBuffer s) 
	{
		
		String string;
		string=s.toString();
		string=string.replaceAll("(?m)^[ ]*\r?\n", "");
		StringBuffer adjusted = new StringBuffer (string);
		return adjusted;
	}
	
	static StringBuffer formattingJson(StringBuffer s) 
	{
		
		int spacesCounter=0;
		for (int i=0 ; i<s.length() ; i++) 
		{
			if (s.charAt(i)=='{' )
			{
				while(s.charAt(i)!='\n') 
				{
					i++;
				}
				//i='\n'
				i++; //lama ba3ml insert by7ot f makan el index w el makan el adeem ygeebo ba3do
				s.insert(i, Node.spaces(spacesCounter+2));
				spacesCounter+=2;
			}
			else if(s.charAt(i)=='\"') 
			{
				boolean found=false;
				while (s.charAt(i)!='\n') 
				{
					if(s.charAt(i)=='{' || s.charAt(i)=='[') 
					{
						found=true; 
						
					}
				i++;	
				}
				//i=' \n '
				i++;
				if (found) 
				{
					
					s.insert(i, Node.spaces(spacesCounter+2));
					spacesCounter+=2;	
				}
				else {
					s.insert(i, Node.spaces(spacesCounter));
				}
			}
			else if (s.charAt(i)==']' || s.charAt(i)=='}' ) 
			{
				boolean found=false;
				while (s.charAt(i)!='\n') 
				{
					if(s.charAt(i)==',' ) {
						found=true;
					} 
					i++;
				}//i='\n'
				i++;
				if(!found) {
					
					s.insert(i, Node.spaces(spacesCounter-2));
					spacesCounter-=2;	
				}
				else 
				{
					s.insert(i, Node.spaces(spacesCounter));
				}
			}
			
		}
		
		return s;
	}
	static void parsing_XML(String sa,ArrayList<String> string) {
	char space=32;
	for (int i = 0; i < sa.length(); i++) {
		if (sa.charAt(i)=='<'&&sa.charAt(i+1)=='!') {
			while(sa.charAt(i)!='>') {
				i++;
				}
			i++;
		}
		if (sa.charAt(i)=='<'&&sa.charAt(i+1)=='?') {
			while(sa.charAt(i)!='>') {
				i++;
				}
			i++;
			
		}
		if (sa.charAt(i)==space||sa.charAt(i)=='\r'||sa.charAt(i)=='\n')continue;
		else if (sa.charAt(i)=='<') {
			StringBuffer temp=new StringBuffer();
			while ((sa.charAt(i)!='>')) {
				temp.append(sa.charAt(i));
				i++;
				}
			temp.append(sa.charAt(i));
			string.add(temp.toString());
			}
		else {
			StringBuffer temp=new StringBuffer();
			while((sa.charAt(i)!='<')) {
				if (sa.charAt(i)=='\r'||sa.charAt(i)=='\n')break;
				temp.append(sa.charAt(i));
			i++;
			}
			string.add(temp.toString());
			--i;
		}
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}