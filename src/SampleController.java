package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.event.*;
import javafx.fxml.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SampleController implements Initializable {
	@FXML
	private Label label;
	@FXML
	private Button openBtn;
	@FXML
	private Button saveBtn;
	@FXML
	private Button Correct;
	@FXML
	private TextArea XmlTextArea = new TextArea();
	@FXML
	private TextArea XmlTextArea1 = new TextArea();
	@FXML
	static String content;
	@FXML
	private Button CompressBtn;
	@FXML
	private Button DeCompressbtn;
	@FXML
	private Button convert;
	@FXML
	private Button formatBtn;
	@FXML
	private Button minifyBtn;
	@FXML
	private static String currentFileName;
	@FXML
	private Button validateBtn;

	@FXML
	private void hello(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();

		File selectedFile = fileChooser.showOpenDialog(null);
		String s = selectedFile.getAbsolutePath();
		String xml = "";

		try {

			xml = new String(Files.readString(Paths.get(s)));
			content = xml;
		} catch (IOException fg) {
			fg.printStackTrace();
		}
		currentFileName = selectedFile.getName();
		XmlTextArea.setText(xml);
	}

	@FXML
	private void correct(ActionEvent event) {
		StringBuffer str = new StringBuffer(content);
		ErrorDetect.error(ErrorDetect.removeSpace(str));
		content = Format.Format(ErrorDetect.newXML.toString());
		XmlTextArea1.setText(content);
	}

	@FXML
	private void Compress(ActionEvent event) {
		AlertBox.display("Alert!!", "Please select the location where you want to export your compressed files!");
		DirectoryChooser choose = new DirectoryChooser();
		File selected = choose.showDialog(null);
		try {

			File f1 = new File(selected.getAbsolutePath() + "output_" + currentFileName);
			f1.createNewFile();
			File f2 = new File(selected.getAbsolutePath() + "huffman_" + currentFileName);
			f2.createNewFile();
			if (!content.equals("")) {
				Compression.compress(content, f1.getAbsolutePath(), f2.getAbsolutePath());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlTextArea1.setText("The compressed files is in: " + selected.getAbsolutePath());
	}

	@FXML
	private void DeCompress(ActionEvent event) {
		AlertBox.display("Alert!!", "Please select the corrected 2 compressed files");
		String deCompressedStr = "";
		try {
			FileChooser fileChooser = new FileChooser();

			File selectedFile1 = fileChooser.showOpenDialog(null);
			File selectedFile2 = fileChooser.showOpenDialog(null);
			if (selectedFile1.getName().substring(0, 7).equals("output_")) {
				deCompressedStr = Compression.decompress(selectedFile1.getAbsolutePath(), selectedFile2.getAbsolutePath());
			} else if (selectedFile1.getName().substring(0, 8).equals("huffman_")) {
				deCompressedStr = Compression.decompress(selectedFile2.getAbsolutePath(), selectedFile1.getAbsolutePath());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlTextArea1.setText(deCompressedStr);
	}

	@FXML
	private void Xml_Json(ActionEvent event) {
		ArrayList<String> arrayListXml = new ArrayList<>();
		content = Minifying.removeLines(content);
		content = Minifying.minify(content);
		Tree.parsing_XML(content, arrayListXml);
		for (int i = 0; i < arrayListXml.size(); i++) {
			arrayListXml.set(i, Minifying.stringTrim(arrayListXml.get(i), '<', '>'));
		}
		Node root = new Node();

		root.setTagName(arrayListXml.get(0));
		Index i = new Index(1);
		Tree.filltree2(root, arrayListXml, i);

		StringBuffer n = new StringBuffer();
		Node.conversion(root, n);
		n = Tree.removeJsonEmptyLines(n);
		
		XmlTextArea1.setText(n.toString());
	}

	@FXML
	private void format(ActionEvent event) {
		XmlTextArea1.setText(Format.Format(content));
	}

	@FXML
	private void minify(ActionEvent event) {
		XmlTextArea1.setText(Minifying.minify(content));
	}

	@FXML
	private void validate(ActionEvent event) {

		StringBuffer val = new StringBuffer(ErrorDetect.removeSpace(new StringBuffer(content)));
		ErrorDetect.error(val);
		if (ErrorDetect.errorIndecies.size() != 0) {
			int count = 0;
			int j = 0;
			int i = 0;
			for (; i < val.length(); i++) {

				if (i < val.length() && val.charAt(i) == 10)
					count++;

				if (j < ErrorDetect.errorIndecies.size() && count == ErrorDetect.errorIndecies.get(j)) {

					val.insert(i, "    <-------- Error Here!!!!");
					for (; i < val.length() && val.charAt(i) != 10; i++)
						;

					j++;

				}

			}
			XmlTextArea1.setText(val.toString());
		} else {
			XmlTextArea1.setText("The XML file is correct!");
		}
	}

	@FXML
	private void save(ActionEvent event) {
		DirectoryChooser choose = new DirectoryChooser();
		File selected = choose.showDialog(null);
		try {

			File f1 = new File(selected.getAbsolutePath()+"SavedFile.txt");
			f1.createNewFile();
			try{
				  // Create file 
				  FileWriter fstream = new FileWriter(f1.getAbsolutePath());
				  BufferedWriter out = new BufferedWriter(fstream);
				  out.write(XmlTextArea1.getText());
				  //Close the output stream
				  out.close();
				  }catch (Exception e){//Catch exception if any
				  System.err.println("Error: " + e.getMessage());
				  }
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//XmlTextArea1.setText("The compressed files is in: " + selected.getAbsolutePath());
	}
	
		

	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
