package application;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.event.*;
import javafx.fxml.*;
import java.util.*;

import Testproject.Minifying;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SampleController implements Initializable {
	@FXML
	private Label label;
	@FXML
	private Button file;
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

		XmlTextArea.setText(xml);
	}

	@FXML
	private void correct(ActionEvent event) {
		StringBuffer str = new StringBuffer(content);
		ErrorDetect.error(ErrorDetect.removeSpace(str));
		XmlTextArea1.setText(ErrorDetect.newXML.toString());
	}

	@FXML
	private void Compress(ActionEvent event) {
		String mohsen = "";
		try {
			Compression.compress(content, "C:\\Users\\Samra\\Desktop\\fady1.txt",
					"C:\\Users\\Samra\\Desktop\\fady2.txt");

			mohsen = new String(Files.readAllBytes(Paths.get("C:\\Users\\Samra\\Desktop\\fady1.txt")));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlTextArea1.setText(mohsen);
	}

	@FXML
	private void DeCompress(ActionEvent event) {
		String mohsen = "";
		try {

			mohsen = Compression.decompress("C:\\Users\\Samra\\Desktop\\fady1.txt",
					"C:\\Users\\Samra\\Desktop\\fady2.txt");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlTextArea1.setText(mohsen);
	}

	@FXML
	private void Xml_Json(ActionEvent event) {
		ArrayList<String> arrayListXml = new ArrayList<>();
		content=Minifying.removeLines(content);
		content=Minifying.minify(content);
		Tree.parsing_xml(content, arrayListXml);
		for (int i = 0; i < arrayListXml.size(); i++) {
			 arrayListXml.set(i, Minifying.stringTrim(arrayListXml.get(i), '<', '>'));
		}
		Node root = new Node();

		root.setTagName(arrayListXml.get(0));
		Index i = new Index(1);
		Tree.filltree2(root, arrayListXml, i);

		StringBuffer n = new StringBuffer();
		Tree.toJson(root, n);
		n = Tree.removeJsonEmptyLines(n);
		n = Tree.formattingJson(n);
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
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
