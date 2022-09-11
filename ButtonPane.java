package PasswordCracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ButtonPane {
    private HBox hBox;
    private Button startBtn;
    
    public ButtonPane() {
        startBtn = new Button("Start");
        hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().add(startBtn);
            hBox.setPadding(new Insets(20));
    }

    public Button getStartBtn() {
        return startBtn;
    }

    public HBox gethBox() {
        return hBox;
    }
    
}
