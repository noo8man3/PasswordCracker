package PasswordCracker;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InfoPane {
    private Label hashesLbl, wordLbl;
    private TextField hashesField, wordField;
    private Button hashesBtn, wordBtn;
    private GridPane gridPane;

    public InfoPane() {
        hashesLbl = new Label("Hashes");
        wordLbl = new Label("Wordlist");
        hashesField = new TextField();
        wordField = new TextField();
        hashesBtn = new Button("Choose...");
        wordBtn = new Button("Choose...");
        
        gridPane = new GridPane();
            gridPane.add(hashesLbl, 0, 0);
            gridPane.addRow(1, hashesField, hashesBtn);
            gridPane.add(wordLbl, 0, 2);
            gridPane.addRow(3, wordField, wordBtn);
            
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(8.0);
            gridPane.setVgap(2.5);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Button getHashesBtn() {
        return hashesBtn;
    }

    public Button getWordBtn() {
        return wordBtn;
    }

    public void setHashesField(String path) {
        hashesField.setText(path);
    }
    
    public void setWordField(String path) {
        wordField.setText(path);
    }
    
}
