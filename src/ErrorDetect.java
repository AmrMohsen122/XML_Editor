import java.util.Stack;
import java.util.*;
public class ErrorDetect {
    
    static Stack<String> s = new Stack<String>();
    static StringBuffer newXML;
    static Vector<Integer> errorIndecies = new Vector<Integer>(1);
    static Vector<String> errorCodes = new Vector<String>();
    
    
    
    static int findPreviousLine(int index, StringBuffer str) {
        int j = index;
        for (; str.charAt(j) != 10; j--);

        return j;
    }

    static int incrementIndex(int index, String str) {

        for (int i = 0; i < str.length(); i++, index++);

        return index;
    }

    static StringBuffer removeSpace(StringBuffer str) {
        StringBuffer formattedXML = new StringBuffer(str);

        for (int i = 1; i < formattedXML.length() - 1; i++) {
            //32 is ascii code for blank space and 10 is ascii code for new lines
            if (formattedXML.charAt(i) == 32 && formattedXML.charAt(i - 1) == 10 || formattedXML.charAt(i) == 32 && formattedXML.charAt(i - 1) == 32) {

                formattedXML.deleteCharAt(i);
                i--;

            }

        }

        return formattedXML;
    }

    static void insertTag(StringBuffer str, int index, String ins) {

        newXML = new StringBuffer(str.insert(index, ins));

    }

    static String getTag(StringBuffer str, int index) {
        int j = 0;
        char tag[] = new char[15];                                      //3ayz at2ked mn length el tag
        for (; !Character.isWhitespace(str.charAt(index)) && index != str.length() && str.charAt(index) != '>'; index++) {
            tag[j] = str.charAt(index);
            j++;
        }

        String tagStr = new String(tag);
        return tagStr;

    }

    static void error(StringBuffer str) {
       
        newXML = new StringBuffer(str);

        Boolean tagValue = false;
        int index = 0;
        int newXMLindex = 0;
        int count = 0;

        for (; index < str.length(); index++, newXMLindex++) {
            if (str.charAt(index) == 10) {
                count++;
            }
            if (str.charAt(index) == '<') {

                newXMLindex++;
                index++;                   

                if (str.charAt(index) == '?') {

                    for (; str.charAt(index) != '\n'; index++, newXMLindex++);

                    continue;
                } else if (str.charAt(index) == '!' && str.charAt(index + 1) == '-' && str.charAt(index + 2) == '-') {
                    int occurence = 0;
                    for (int i = 0; str.charAt(index) != '>'; index++, newXMLindex++, i++) {
                        if (i > 2) {
                            if (str.charAt(index) == '-' && str.charAt(index - 1) == '-') {
                                occurence++;
                                if (occurence > 1) {
                                    errorCodes.add("Comment format is Incorrect at line: " + count + 1);
                                    errorIndecies.add(count + 1);
                                }
                            }

                        }

                    }
                    if (str.charAt(index - 1) != '-' || str.charAt(index - 2) != '-') {
                        errorCodes.add("Comment format is Incorrect at line: " + count + 1);
                        errorIndecies.add(count + 1);
                    }

                } else if (str.charAt(index) != '/' ) {

                    String tagStr = getTag(str, index);
                    
                    
                    if (!s.empty() && tagStr.equals(s.peek())) {
                        
                        errorIndecies.add(count);
                        
                        String unmatchingTag = new String("</" + s.peek() + ">");
                        errorCodes.add("Closing tag not found: " + unmatchingTag );
                        
                        int j = findPreviousLine(newXMLindex, newXML);
                        

                        insertTag(newXML, j, unmatchingTag);
                        s.pop();
                        newXMLindex = incrementIndex(newXMLindex, unmatchingTag);
                        
                    }
                    s.push(tagStr);
                    
                } else if (str.charAt(index) == '/') {
                    index++;
                    newXMLindex++;
                    String closingTag = getTag(str, index);

                    //code for correcting the error in case of a missing openning tag
                    if (!s.empty() && !s.peek().equals(closingTag) && tagValue) {
                        errorIndecies.add(count + 1);
                        errorCodes.add("Missing openning tag: " + '<'+closingTag+'>');
                        int j = findPreviousLine(newXMLindex, newXML);
                        j++;

                        String unmacthedTag = new String("<" + closingTag + ">");
                        insertTag(newXML, j, unmacthedTag);

                        for (int i = 0; i < unmacthedTag.length(); i++, newXMLindex++, j++);

                        if (newXML.charAt(j) == '<') {
                            j++;
                            j++;
                            if (getTag(newXML, j).equals(closingTag)) {
                                
                                continue;
                            }
                            //We always assume that in case of mismatching tags that the closing tag is the correct one   
                            else {
                                j--;

                                newXML.deleteCharAt(--j);

                                newXMLindex--;

                                //String tagToRemove = s.peek();
                                for (int k = 0; newXML.charAt(j) != '>'; k++, newXMLindex--) {

                                    newXML.deleteCharAt(j);

                                    //newXMLindex++;
                                }
                                newXML.deleteCharAt(j);
                                newXMLindex--;
                                s.pop();

                            }

                        }

                        tagValue = false;
                        continue;
                        
                    } 
                    //case for correcting in case the closing tag of a parent node is missing after the closing tag of its child
                    else if (!s.empty() && !s.peek().equals(closingTag) && !tagValue) {
                        int j = findPreviousLine(newXMLindex, newXML);

                        String missingParentClosing = new String("</" + s.peek() + ">");
                        insertTag(newXML, j, missingParentClosing);
                        newXMLindex = incrementIndex(newXMLindex, missingParentClosing);

                        s.pop();
                        errorCodes.add("Missing closing parent tag: " + missingParentClosing );
                        errorIndecies.add(count);
                    }

                    if (!s.empty()) {
                        s.pop();
                        
                        tagValue = false;
                        
                    }
                }
            } else if ((Character.isLetterOrDigit(str.charAt(index)) && (str.charAt(index - 1) == '>' || str.charAt(index - 2) == '>')) || (Character.isLetterOrDigit(str.charAt(index)) && str.charAt(index + 1) == '<')) {

                tagValue = true;
               
            }
            //function for correcting in case there is no closing tag for a certain node
            if (str.charAt(index) == 10 && tagValue) {

                errorIndecies.add(count);
                String unmatchingTag = new String("</" + s.peek() + ">");
                errorCodes.add("Closing tag not found: " + unmatchingTag);
                
                insertTag(newXML, newXMLindex, unmatchingTag);

                s.pop();
                tagValue = false;
                newXMLindex = incrementIndex(newXMLindex, unmatchingTag);
                

            }
            continue;

        }

        if (errorIndecies.isEmpty()) {
            
            errorCodes.add("XML file is without Errors");
        } else if (!s.empty() && newXMLindex == newXML.length() - 1) {
            insertTag(newXML, newXMLindex, "</" + s.peek() + ">");
            errorIndecies.add(count + 1);
            s.pop();
            
        }
        
    }

}
