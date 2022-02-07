package com.example.demo3;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SampleController implements Initializable {
	@FXML
	private Button GraphBtn;
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
		str = ErrorDetect.removeLine(ErrorDetect.removeSpace(str));
		ErrorDetect.error(str);
		XmlTextArea1.setText(ErrorDetect.newXML.toString());
	}

	@FXML
	private void Compress(ActionEvent event) {
		AlertBox.display("Alert!!", "Please select the location where you want to export your compressed files!");
		DirectoryChooser choose = new DirectoryChooser();
		File selected = choose.showDialog(null);
		try {

			File f1 = new File(selected.getAbsolutePath() , "output_" + currentFileName);
			f1.createNewFile();
			File f2 = new File(selected.getAbsolutePath() , "huffman_" + currentFileName);
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
		String str;
		str = Minifying.removeLines(content);
		str = Minifying.minify(str);
		Tree.parsing_XML(str, arrayListXml);
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
		String str = new String(content);
		XmlTextArea1.setText(Minifying.minify(str));
	}

	@FXML
	private void validate(ActionEvent event) {
		ErrorDetect.errorIndecies.clear();
		ErrorDetect.errorCodes.clear();
		StringBuffer str = ErrorDetect.removeSpace(new StringBuffer(content));
		str = ErrorDetect.removeLine(str);
		ErrorDetect.error(str);
		StringBuffer val = new StringBuffer(str);
		if (ErrorDetect.errorIndecies.size() != 0) {
			int count = 0;
			int j = 0;
			int i = 0;
			for (; i < val.length(); i++) {

				if (i < val.length() && val.charAt(i) == 10)
					count++;

				if (j < ErrorDetect.errorIndecies.size() && count == ErrorDetect.errorIndecies.get(j)) {

					val.insert(i, "    <-------- " + ErrorDetect.errorCodes.get(j) );
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

			File f1 = new File(selected.getAbsolutePath() , "SavedFile.txt");
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
	@FXML
	private void graph(ActionEvent event) {

		ArrayList<user> users = new ArrayList();

		user.new_parse(content,  users);

		System.setProperty("org.graphstream.ui", "javafx");

		Graph graph = new MultiGraph("Network");

		graph.setAutoCreate(true);
		graph.setStrict(false);

		graph.display();


		FxViewer view = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		view.enableAutoLayout();
		FxViewPanel panel = (FxViewPanel)view.addDefaultView(false, new FxGraphRenderer());

		for (int i = 0; i < users.size(); i++) {

			for (int j = 0; j < users.get(i).followers.size(); j++) {
				graph.addEdge(users.get(i).id+users.get(i).followers.get(j).toString(), String.valueOf(users.get(i).id), users.get(i).followers.get(j).toString(), true);
			}

		}
		for (org.graphstream.graph.Node node : graph) {

			node.setAttribute("ui.label", node.getId());



			node.setAttribute("ui.style", "fill-color:black;");
			node.setAttribute("ui.style", "text-mode:normal;");
			node.setAttribute("ui.style", "text-background-mode:rounded-box;");
			node.setAttribute("ui.style", "text-background-color:white;");
			node.setAttribute("ui.style", "text-size:30px;");
			node.setAttribute("ui.style", "size:15px, 15px;");

			node.setAttribute("ui.style", "text-alignment:at-left;");
			node.setAttribute("ui.style", "text-offset:-10px,20px;");


		}
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Graph");
		window.setMinWidth(250);



		Scene scene = new Scene(panel,800,600);
		window.setScene(scene);
		window.show();

	}
	
		

	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
