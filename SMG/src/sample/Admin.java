package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Admin implements Initializable {
    @FXML
    ImageView AdminImage;
    @FXML
    Label LbAdminName;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("https://static.thenounproject.com/png/2303078-200.png");
        AdminImage.setImage(image);

    }
}
