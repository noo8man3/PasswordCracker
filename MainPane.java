package PasswordCracker;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class MainPane {
    private BorderPane root;
    private InfoPane infoPane;
    private ButtonPane btnPane;
    private MenuBar menuBar;
    private Menu hashMenu, optionsMenu;
    private RadioMenuItem md2, md5, sha1, sha256, sha384, sha512;
    private MenuItem consoleItem;
    private ToggleGroup hashGroup;
    
    public MainPane() {
        hashGroup = new ToggleGroup();
        md2 = new RadioMenuItem("MD2");
            md2.setToggleGroup(hashGroup);
        md5 = new RadioMenuItem("MD5");
            md5.setToggleGroup(hashGroup);
        sha1 = new RadioMenuItem("SHA-1");
            sha1.setToggleGroup(hashGroup);
        sha256 = new RadioMenuItem("SHA-256");
            sha256.setToggleGroup(hashGroup);
        sha384 = new RadioMenuItem("SHA-384");
            sha384.setToggleGroup(hashGroup);
        sha512 = new RadioMenuItem("SHA-512");
            sha512.setToggleGroup(hashGroup);
        hashMenu = new Menu("Hashes");
            hashMenu.getItems().addAll(md2, md5, sha1, sha256, sha384, sha512);
        consoleItem = new MenuItem("Console");
        optionsMenu = new Menu("Options");
            optionsMenu.getItems().add(consoleItem);
        menuBar = new MenuBar();
            menuBar.getMenus().addAll(hashMenu, optionsMenu);
            
        infoPane = new InfoPane();
        btnPane = new ButtonPane();
        root = new BorderPane();
            root.setTop(menuBar);
            root.setCenter(infoPane.getGridPane());
            root.setBottom(btnPane.gethBox());
    }

    public BorderPane getRoot() {
        return root;
    }

    public ButtonPane getBtnPane() { return btnPane; }

    public InfoPane getInfoPane() { return infoPane; }

    public RadioMenuItem getMd2() { return md2; }

    public MenuItem getConsoleItem() { return consoleItem; }

    public String getSelectedItem() { return ((RadioMenuItem)hashGroup.getSelectedToggle()).getText(); }
    
}
