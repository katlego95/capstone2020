package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PaymentFrontController implements Initializable {
    DatabaseConnection connectionNow = new DatabaseConnection();
    Connection connectDB = connectionNow.getConnection();


    ArrayList<String> SN = new ArrayList<String>();
    ArrayList<String> PN = new ArrayList<String>();
    ArrayList<Integer> P = new ArrayList<Integer>();

    ArrayList<String> noDuplicates = new ArrayList<>();



    String[] spl;

    ArrayList<Label> LabelList = new ArrayList<>();
    @FXML
    AnchorPane APANE;

    @FXML
    private Label LBDisplay, AmountLabel,  txtName, txtEmail, txtPhone, txtRole;
    @FXML
    Pane pPane, PaymentPane;
    String emailName ="", roleName="", name="", phone="";
    @FXML
    private TableView<Products> TBview;

    @FXML
    private TableColumn<Products, String> StoreName;

    @FXML
    private TableColumn<Products, String> ProductName;
    @FXML
    private TableColumn<Products, Integer> ProductPrice;
    ObservableList<Products> observableList = FXCollections.observableArrayList();
    @FXML
    VBox boxV = new VBox();
    @FXML
    ImageView iv;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("https://cdn.onlinewebfonts.com/svg/img_544644.png");
        iv.setImage(image);
        boxV.styleProperty().set("-fx-background-color: #add8e6");
        APANE.styleProperty().set("-fx-background-color: #DCAE96");
        pPane.styleProperty().set("-fx-background-color: #add8e6");
    }

    public void ListOfLabels(){
        for (int i = 0; i < 21; i++){
            Label lb = new Label("");
            LabelList.add(lb);
        }

    }

    public void populateTBView(){
        for (int i =0; i < SN.size(); i++){
            observableList.add(new Products(SN.get(i), PN.get(i), P.get(i)));
        }
        TBview.setItems(observableList);
    }

    public void getSubTotals() {
        int subtotal;
        for (int i = 0; i < SN.size(); i++) {
            for (int j = i + 1; j < SN.size(); j++) {
                if (SN.get(i).equals(SN.get(j))) {
                    subtotal = P.get(i) + P.get(j);
                    System.out.println(SN.get(i) + "subtotal = " + subtotal);
                } else {
                    subtotal = P.get(i);
                    System.out.println(SN.get(i) + "subtotal = " + subtotal);
                }

            }

        }
    }

    public void getAccountDetails() throws SQLException {
        ListOfLabels();
        ArrayList<String> namesofStore = new ArrayList<>();
        for (int x = 0; x < SN.size(); x++){
            namesofStore.add(SN.get(x));

        }
        List<String> unique = namesofStore.stream().distinct().collect(Collectors.toList());
        for (int i =0 ; i < unique.size(); i++) {
            PreparedStatement p = null;
            ResultSet s = null;
            String query = "SELECT * FROM sellerinfo WHERE seller_store = ?";
            p = connectDB.prepareStatement(query);
            p.setString(1, unique.get(i));
            s = p.executeQuery();
            while (s.next()) {
                LabelList.get(i).setText("Store Name: " + unique.get(i) + "\n Sellers Name: " + s.getString("seller_name") + "\n Store Location: " + s.getString("seller_location") + "\n Sellers email: " + s.getString("seller_email") + "\n Account Name: " + s.getString("account_name") + "\n Account Number: " + s.getString("account_no") + "\n Account Type: " + s.getString("account_type") + "\n Branch Name: " + s.getString("branch") + "\n Branch Code: " + s.getString("branch_code"));
                LabelList.get(i).prefWidthProperty().bind((boxV.widthProperty()));
                boxV.setSpacing(15);
                boxV.getChildren().add(LabelList.get(i));
            }


        }


        System.out.println(unique);



        }




    public void myFunction2(int total, String em, String r, ArrayList<String> l, ArrayList<String> o, ArrayList<Integer> m)  {
        AmountLabel.setText("R " + total);
        txtEmail.setText(em);
        txtRole.setText(r);
        String sname = "";
        String pname = "";
        int pr = 0;
        StoreName.setCellValueFactory(new PropertyValueFactory<>("StoreName"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        for(int i =0; i < l.size(); i++){
            SN.add(l.get(i));
            PN.add(o.get(i));
            P.add(m.get(i));
        }

        populateTBView();
       // getSubTotals();
        try {
            getAccountDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        String query2 = "SELECT name, phone FROM userdbinfo WHERE email= ?";
        try {
            ps2 = connectDB.prepareStatement(query2);
            ps2.setString(1, em);
            rs2 = ps2.executeQuery();
            while (rs2.next()) {
               txtName.setText(rs2.getString("name"));
               txtPhone.setText(rs2.getString("phone"));
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ReturnHome(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreFront.fxml"));
            Parent viewParent =  (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(viewParent));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}










