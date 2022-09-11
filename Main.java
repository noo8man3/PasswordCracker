package PasswordCracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main extends Application {

    private FileChooser hashCh = new FileChooser(),
            wordCh = new FileChooser();
    private FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files",
            "*.txt");
    private File hashFile, wordFile;
    private transient Scanner hashSc, wordSc;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private MainPane mp;
    private TextArea console;
    private Stage consoleStage = new Stage();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        initConsole();

        mp = new MainPane();
        mp.getMd2().setSelected(true); //sets MD2 as default on startup
        Scene mainScene = new Scene(mp.getRoot(), 500, 250);
        mainScene.getStylesheets().add(Main.class.getResource("ViewStyle.css").toExternalForm());

        primaryStage.setTitle("Shitty Password Cracker v.2.0");
        primaryStage.getIcons().add(new Image("file:programicon.png"));
        primaryStage.setScene(mainScene);
        primaryStage.show();

        hashCh.setSelectedExtensionFilter(extFilter);
        wordCh.setSelectedExtensionFilter(extFilter);

        log("Initialization complete.");

        mp.getBtnPane().getStartBtn().setOnAction(e -> {
            String hash, word, hashedWord;
            StringBuilder passwords = new StringBuilder();

            log("=========================\n" + "PASSWORD CRACKING IN PROGRESS\n" +
                    "=========================");

            if(filesExist()) {
                initScanners();
                hashSc.reset();

                while (hashSc.hasNextLine()) {
                    hash = hashSc.nextLine();
                    log("\nPassword Hash: " + hash);
                    resetWordScanner();

                    while (wordSc.hasNextLine()) {
                        word = wordSc.nextLine();
                        hashedWord = hash(mp.getSelectedItem(), word);
                        log(word + " -> " + hashedWord.toUpperCase());

                        if (hashedWord.equalsIgnoreCase(hash)) {
                            log("MATCH FOUND (" + word + ")");
                            passwords.append("Password: ").append(word).append("\n");
                            break; //once password is found, loop is broken
                        }
                    }
                }
                if (passwords.toString().trim().equals(""))
                    displayAlert(true, "Hashing Complete", "No passwords cracked.");
                else
                    displayAlert(true, "Hashing Complete", passwords.toString());

                log(passwords.toString());

            } else {
                displayAlert(false, "File Error", "Hash and/or word file(s) do not exist.");
            }
        });

        mp.getInfoPane().getHashesBtn().setOnAction(e -> {
            hashFile = hashCh.showOpenDialog(primaryStage);

            if(hashFile != null) {
                mp.getInfoPane().setHashesField(hashFile.getPath());
                log("Hash File Path: " + hashFile.getPath());
            } else {
                displayAlert(false, "File Error", "Please select a valid file.");
                mp.getInfoPane().setHashesField("");
            }
        });

        mp.getInfoPane().getWordBtn().setOnAction(e -> {
            wordFile = wordCh.showOpenDialog(primaryStage);

            if(wordFile != null) {
                mp.getInfoPane().setWordField(wordFile.getPath());
                log("Word File Path: " + hashFile.getPath());
            } else {
                displayAlert(false, "File Error","Please select a valid file.");
                mp.getInfoPane().setWordField("");
            }
        });

        mp.getConsoleItem().setOnAction(e -> {
            Scene consoleScene = new Scene(console, 550, 250);
            consoleStage.setScene(consoleScene);
            consoleStage.show();
        });
    }

    private String hash(String hashType, String text) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance(hashType);
        } catch (NoSuchAlgorithmException ex) {
            displayAlert(false, "Hashing Failed", "Invalid algorithm selected.");
            return "";
        }

        md.update(text.getBytes(StandardCharsets.UTF_8));

        byte[] hash = md.digest();
        return convertToHex(hash);
    }

    private String convertToHex(byte[] byteData) {
        StringBuilder hexString = new StringBuilder();

        for (byte byteElem : byteData) {
            String hex = Integer.toHexString(0xff & byteElem);

            if (hex.length() == 1)
                hexString.append('0');

            hexString.append(hex);
        }

        return hexString.toString();
    }

    private void displayAlert(boolean signal, String head, String content) {
        if(signal) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText(head);
            alert.setContentText(content);
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText(head);
            alert.setContentText(content);
        }

        alert.showAndWait();
    }

    private void initScanners() {
        try {
            hashSc = new Scanner(hashFile);
            wordSc = new Scanner(wordFile);
        } catch (FileNotFoundException ex) {
            displayAlert(false, "File Error", "File(s) not found.");
        }
    }

    private void initConsole() {
        console = new TextArea();
        console.setEditable(false);
        consoleStage.setTitle("Console");
        consoleStage.getIcons().add(new Image("file:programicon.png"));
    }

    private void resetWordScanner() {
        wordSc.close();

        try {
            wordSc = new Scanner(wordFile);
        } catch (FileNotFoundException ex) {
            displayAlert(false, "File Error", "File(s) not found.");
        }
    }

    private void log(String content) {
        console.appendText(content + "\n");
    }

    private boolean filesExist() {
        return hashFile != null && wordFile != null;
    }

}
