package Testproject;

import java.util.ArrayList;

public class Testing {
	
static StringBuffer asd=new StringBuffer("");
	public static void main(String[] args) {
		
String s="<!--Your comment-->"
+"<?xml version = \"1.0\" encoding = \"UTF-8\" ?>"
+"<users>\r\n"
		+ "    <user>\r\n"
		+ "        <id>1</id>\r\n"
		+ "        <name>Ahmed Ali</name>\r\n"
		+ "        <posts>\r\n"
		+ "            <post>\r\n"
		+ "                <body>\r\n"
		+ "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n"
		+ "                </body>\r\n"
		+ "                <topics>\r\n"
		+ "                    <topic>\r\n"
		+ "                        economy\r\n"
		+ "                    </topic>\r\n"
		+ "                    <topic>\r\n"
		+ "                        finance\r\n"
		+ "                    </topic>\r\n"
		+ "                </topics>\r\n"
		+ "            </post>\r\n"
		+ "            <post>\r\n"
		+ "                <body>\r\n"
		+ "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n"
		+ "                </body>\r\n"
		+ "                <topics>\r\n"
		+ "                    <topic>\r\n"
		+ "                        solar_energy\r\n"
		+ "                    </topic>\r\n"
		+ "                </topics>\r\n"
		+ "            </post>\r\n"
		+ "        </posts>\r\n"
		+ "        <followers>\r\n"
		+ "            <follower>\r\n"
		+ "                <id>2</id>\r\n"
		+ "            </follower>\r\n"
		+ "            <follower>\r\n"
		+ "                <id>3</id>\r\n"
		+ "            </follower>\r\n"
		+ "        </followers>\r\n"
		+ "    </user>\r\n"
		+ "    <user>\r\n"
		+ "        <id>2</id>\r\n"
		+ "        <name>Yasser Ahmed</name>\r\n"
		+ "        <posts>\r\n"
		+ "            <post>\r\n"
		+ "                <body>\r\n"
		+ "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n"
		+ "                </body>\r\n"
		+ "                <topics>\r\n"
		+ "                    <topic>\r\n"
		+ "                        education\r\n"
		+ "                    </topic>\r\n"
		+ "                </topics>\r\n"
		+ "            </post>\r\n"
		+ "        </posts>\r\n"
		+ "        <followers>\r\n"
		+ "            <follower>\r\n"
		+ "                <id>1</id>\r\n"
		+ "            </follower>\r\n"
		+ "        </followers>\r\n"
		+ "    </user>\r\n"
		+ "    <user2>\r\n"
		+ "        <id>3</id>\r\n"
		+ "        <name>Ashraf my nigga</name>\r\n"
		+ "        <posts>\r\n"
		+ "            <post>\r\n"
		+ "                <body>\r\n"
		+ "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n"
		+ "                </body>\r\n"
		+ "                <topics>\r\n"
		+ "                    <topic>\r\n"
		+ "                        sports\r\n"
		+ "                    </topic>\r\n"
		+ "                </topics>\r\n"
		+ "            </post>\r\n"
		+ "        </posts>\r\n"
		+ "        <followers>\r\n"
		+ "            <follower>\r\n"
		+ "                <id>1</id>\r\n"
		+ "            </follower>\r\n"
		+ "        </followers>\r\n"
		+ "    </user2>\r\n"

		
		+ "</users>";
String b="<users>\r\n"
		+ "    <user>\r\n"
		+ "        <posts>\r\n"
		+ "            <post>\r\n"
		+ "                <body>\r\n"
		+ "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n"
		+ "                </body>\r\n"
		+ "                <topics>\r\n"
		+ "                    <topic>\r\n"
		+ "                        economy\r\n"
		+ "                    </topic>\r\n"
		+ "                    <topic>\r\n"
		+ "                        finance\r\n"
		+ "                    </topic>\r\n"
		+ "                </topics>\r\n"
		+ "            </post>\r\n"
		+ "            <post>\r\n"
		+ "                <body>\r\n"
		+ "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n"
		+ "                </body>\r\n"
		+ "                <topics>\r\n"
		+ "                    <topic>\r\n"
		+ "                        solar_energy\r\n"
		+ "                    </topic>\r\n"
		+ "                </topics>\r\n"
		+ "            </post>\r\n"
		+ "        </posts>\r\n"

		+ "    </user>";



		ArrayList<String> xmlparsed = new ArrayList<String>();
			parsing_xml(b,xmlparsed);
			//for (int i=0;i<xmlparsed.size();i++)
			//System.out.println(xmlparsed.get(i));
				Node parent =new Node ();
					parent.setDepth(0);
					parent.setTagName(xmlparsed.get(0));
							Index cc=new Index(1);
								Tree.filltree2(parent ,xmlparsed,cc);
									Tree tr=new Tree(parent);
									tr.toJson(tr.getRoot());
	}



static void parsing_xml(String sa,ArrayList<String> string) {
	char space=32;
	for (int i = 0; i < sa.length(); i++) {
		if (sa.charAt(i)=='<'&&sa.charAt(i+1)=='!') {
			while(sa.charAt(i)!='>') {
				i++;}
			i++;
			
		}
		if (sa.charAt(i)=='<'&&sa.charAt(i+1)=='?') {
			while(sa.charAt(i)!='>') {
				i++;}
			i++;
			
		}
		if (sa.charAt(i)==space||sa.charAt(i)=='\r'||sa.charAt(i)=='\n')continue;
		
		
		else if (sa.charAt(i)=='<') {
			
			int j=i;
			
			while ((sa.charAt(i)!='>')) {
				i++;
			}
			//if (sa.charAt(j+1)=='/')continue;
			string.add(sa.substring(j,i+1));
			
			
		}
		else {
			int k=i;
			while((sa.charAt(i)!='<')) {
				
				if (sa.charAt(i)=='\r'||sa.charAt(i)=='\n')break;
			i++;}
			string.add(sa.substring(k,i));
			--i;
		}
	}
}
}