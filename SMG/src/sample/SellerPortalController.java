package sample;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SellerPortalController implements Initializable {
    DatabaseConnection connectionNow = new DatabaseConnection();
    Connection connectDB = connectionNow.getConnection();
    @FXML
    Button btnAdd;
    @FXML
    Label lbLabel,  txtStoreName,  txtName, txtEmail, txtPhone, txtRole, txtOwner, txtStore,txtStroreLocation,txtOwnerEmail,txtAccountName,txtAccountNumber,txtAccountType,txtBranch,txtBranchCode;
    @FXML
    AnchorPane aPane;
    @FXML
    Pane UserPane, sPane;
    String StoreName;
    @FXML
    private TableView<SellerModel> table;
    @FXML
    private TableColumn<SellerModel, String> col_ProductName;
    @FXML
    private TableColumn<SellerModel, Integer> col_Price;
    @FXML
    private TableColumn<SellerModel, String> col_Category;
    @FXML
    private TableColumn<SellerModel, Integer> col_Quantity;
    @FXML
    private TableColumn<SellerModel, String> col_Store;
    @FXML
    private TableColumn<SellerModel, String> col_ImaageLink;
    @FXML
    private TableColumn<SellerModel, String> col_Owner;
    @FXML
    private TableColumn<SellerModel, String> col_OwnerContact;
    @FXML
    private TableColumn<SellerModel, String> col_Location;
    @FXML
    TextField txfProductName, txfProductPrice,  txfQuantity, txfStoreName, txfImageLink, txfOwnersName, txfContact, txfLocation;
    @FXML
    RadioButton rbtKH, rbtA, rbtS, rbtCE;
    @FXML
    ImageView sellerImage;

    String name1, storename1, doesntexist;
    String GetNameOfStore;
    String RadioButtonCategory;
    ObservableList<SellerModel> observableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("https://static.thenounproject.com/png/94050-200.png");
        sellerImage.setImage(image);
        aPane.styleProperty().set("-fx-background-color: #add8e6");
        UserPane.styleProperty().set("-fx-background-color: #DCAE96");
        sPane.styleProperty().set("-fx-background-color: #DCAE96");
        lbLabel.setText("**Note** \n To remove product select product \n from table then click the below button");


    }

    public void RecieveFromStoreController(String storename, String password) throws SQLException {
        GetNameOfStore = storename;
        String txfname = "";
        String txfphone = "";
        String txflocation = "";
        txtStoreName.setText(storename);
        StoreName = storename;
        PreparedStatement pst  = null;
        ResultSet rst = null;
        String query = "SELECT name, email, phone, role FROM userdbinfo WHERE password = ?";
        pst = connectDB.prepareStatement(query);
        pst.setString(1, password);
        rst = pst.executeQuery();
        while (rst.next()){
            txfname = rst.getString("name");
            txfphone = rst.getString("phone");
            txtName.setText(rst.getString("name"));
            txtEmail.setText(rst.getString("email"));
            txtRole.setText(rst.getString("role"));
            txtPhone.setText(rst.getString("phone"));
        }
        PreparedStatement statement = null;
        ResultSet set = null;
        String q = "SELECT seller_name, seller_location,  seller_store, seller_email, account_name, account_no, account_type, branch, branch_code FROM sellerinfo WHERE seller_store = ?";
        statement = connectDB.prepareStatement(q);
        statement.setString(1, StoreName);
        set = statement.executeQuery();
        while(set.next()){
            txtOwner.setText(set.getString("seller_name"));
            txtStroreLocation.setText(set.getString("seller_location"));
            txtStoreName.setText(set.getString("seller_store"));
            txtOwnerEmail.setText(set.getString("seller_email"));
            txtAccountName.setText(set.getString("account_name"));
            txtAccountNumber.setText(set.getString("account_no"));
            txtAccountType.setText(set.getString("account_type"));
            txtBranch.setText(set.getString("branch"));
            txtBranchCode.setText(set.getString("branch_code"));

            txflocation = set.getString("seller_location");
        }

        col_ProductName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        col_Price.setCellValueFactory(new PropertyValueFactory<>("ProductPrice"));
        col_Category.setCellValueFactory(new PropertyValueFactory<>("ProdcutCategory"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("ProductQuantity"));
        col_Store.setCellValueFactory(new PropertyValueFactory<>("ProductStore"));
        col_ImaageLink.setCellValueFactory(new PropertyValueFactory<>("ProductLinkImage"));
        col_Owner.setCellValueFactory(new PropertyValueFactory<>("ProductOwner"));
        col_OwnerContact.setCellValueFactory(new PropertyValueFactory<>("ProductOwnerDetails"));
        col_Location.setCellValueFactory(new PropertyValueFactory<>("ProductLocation"));

        PreparedStatement p =null;
        ResultSet s =null;
        String qt = "SELECT * FROM productinfo WHERE product_store = ?";
        p =  connectDB.prepareStatement(qt);
        p.setString(1,  txtStoreName.getText());
        s = p.executeQuery();
        while (s.next()){
            observableList.add(new SellerModel(s.getString("prduct_name"), s.getInt("product_price"),s.getString("product_category"),s.getInt("product_quantity"),s.getString("product_store"),s.getString("product_linkimage"),s.getString("product_owner"),s.getString("owner_details"),s.getString("product_location") ));

        }

        table.setItems(observableList );
        txfStoreName.setText(txtStoreName.getText());
        txfContact.setText(txfphone);
        txfOwnersName.setText(txfname);
        txfLocation.setText(txflocation);





    }

    public void AddProduct(ActionEvent event) throws SQLException {
        PreparedStatement p = null;
        ResultSet s = null;
        String query = "SELECT * FROM productinfo WHERE product_store = ?";
        p = connectDB.prepareStatement(query);
        p.setString(1, txfStoreName.getText());
        s = p.executeQuery();
        if (!s.next()){
            doesntexist = txfStoreName.getText();
        }

        if (rbtA.isSelected()){
            RadioButtonCategory = "Apparel";
        } else if (rbtCE.isSelected()){
            RadioButtonCategory = "Computer and Electronics";
        } else if (rbtKH.isSelected()){
            RadioButtonCategory = "Kitchen and Home";
        } else if (rbtS.isSelected()){
            RadioButtonCategory = "Sneakers";
        }

        observableList.add(new SellerModel(txfProductName.getText(),Integer.parseInt(txfProductPrice.getText()),RadioButtonCategory,Integer.parseInt(txfQuantity.getText()),txfStoreName.getText(),txfImageLink.getText(),txfOwnersName.getText(),txfContact.getText(),txfLocation.getText()));

        PreparedStatement steez = null;
        String q = "INSERT INTO productinfo (prduct_name, product_price, product_category, product_quantity, product_store, product_linkimage, product_owner, owner_details,product_location)  VALUES (?,?,?,?,?,?,?,?,?)";
        steez = connectDB.prepareStatement(q);
        steez.setString(1,txfProductName.getText());
        steez.setString(2, String.valueOf(Integer.parseInt(txfProductPrice.getText())));
        steez.setString(3,RadioButtonCategory);
        steez.setString(4,String.valueOf(Integer.parseInt(txfQuantity.getText())));
        steez.setString(5,txfStoreName.getText());
        steez.setString(6,txfImageLink.getText());
        steez.setString(7,txfOwnersName.getText());
        steez.setString(8,txfContact.getText());
        steez.setString(9,txfLocation.getText());
        steez.executeUpdate();


        txfProductName.setText("");
        txfProductPrice.setText("");
        txfImageLink.setText("");
        txfQuantity.setText("");
    }

    public void Home(ActionEvent event) {

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreFront.fxml"));
            Parent viewParent =  (Parent) loader.load();
            StoreFrontController sc = loader.getController();
            sc.getUserFromSellerPortal(txtEmail.getText(), txtRole.getText(), txtStoreName.getText());
            sc.Recieve(RadioButtonCategory, doesntexist);
            Stage stage = new Stage();
            stage.setScene(new Scene(viewParent));
            stage.show();

        }catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }


    public void Remove(ActionEvent event) throws SQLException {
        observableList = table.getSelectionModel().getSelectedItems();
        name1 = observableList.get(0).getProductName();
        storename1 = observableList.get(0).getProductStore();
        PreparedStatement ptsd = null;
        String qt = "DELETE FROM productinfo  WHERE prduct_name = ? and product_store = ? ";
        ptsd = connectDB.prepareStatement(qt);
        ptsd.setString(1, name1);
        ptsd.setString(2, storename1);
        ptsd.executeUpdate();

        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());






    }

    public void DeleteStore(ActionEvent event) throws SQLException {
        PreparedStatement p1 = null;
        PreparedStatement p2 = null;
        ResultSet s1 = null;
        ResultSet s2 = null;
        String query1 = "DELETE FROM sellerinfo WHERE seller_store = ?";
        String query2 = "DELETE FROM productinfo WHERE product_store = ?";
        p1 = connectDB.prepareStatement(query1);
        p2 = connectDB.prepareStatement(query2);

        p1.setString(1, txtStoreName.getText());
        p2.setString(1, txtStoreName.getText());

        p1.executeUpdate();
        p2.executeUpdate();

        observableList.clear();

        Stage dmessage = new Stage();
        VBox b = new VBox();
        Label success = new Label("Store deleted succesfully");
        success.setFont((new Font("Arial Bold", 30)));
        b.getChildren().add(success);
        Scene ns = new Scene(b, 400, 400);
        dmessage.setScene(ns);
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> dmessage.hide());
        dmessage.initStyle(StageStyle.UNDECORATED);
        dmessage.show();
        delay.play();




    }



    public void UpdateAccountInfo(ActionEvent event) {
        Stage popup = new Stage();
        VBox box = new VBox();
        box.setSpacing(30);
        box.setPadding(new Insets(10,10,10,10));

        Label UpdateAccountName = new Label("Update account name");
        TextField txfUpdateAccountName = new TextField(txtAccountName.getText());

        Label UpdateAccountNumber = new Label("Update account number");
        TextField txfUpdateAccountNumber= new TextField(txtAccountNumber.getText());

        Label UpdateAccountType = new Label("Update account type");
        TextField txfUpdateAccountType = new TextField(txtAccountType.getText());

        Label UpdateBranch = new Label("Update branch");
        TextField txfUpdateBranch = new TextField(txtBranch.getText());

        Label UpdateBranchNo = new Label("Update branch");
        TextField txfUpdateBranchNo = new TextField(txtBranchCode.getText());


        Button UpdateButton = new Button("Update Info");

        UpdateAccountName.setFont((new Font("Arial Bold", 20)));
        UpdateAccountNumber.setFont((new Font("Arial Bold", 20)));
        UpdateAccountType.setFont((new Font("Arial Bold", 20)));
        UpdateBranch.setFont((new Font("Arial Bold", 20)));
        UpdateBranchNo.setFont((new Font("Arial Bold", 20)));

        txfUpdateAccountName.setFont((new Font("Arial", 15)));
        txfUpdateAccountNumber.setFont((new Font("Arial", 15)));
        txfUpdateAccountType.setFont((new Font("Arial", 15)));
        txfUpdateBranch.setFont((new Font("Arial", 15)));
        txfUpdateBranchNo.setFont((new Font("Arial", 15)));

        UpdateButton.setMaxSize(500, 500);

        box.getChildren().add(UpdateAccountName);
        box.getChildren().add(txfUpdateAccountName);
        box.getChildren().add(UpdateAccountNumber);
        box.getChildren().add(txfUpdateAccountNumber);
        box.getChildren().add(UpdateAccountType);
        box.getChildren().add(txfUpdateAccountType);
        box.getChildren().add(UpdateBranch);
        box.getChildren().add(txfUpdateBranch);
        box.getChildren().add(UpdateBranchNo);
        box.getChildren().add(txfUpdateBranchNo);

        box.getChildren().add(UpdateButton);
        Scene stageScene = new Scene(box, 500,  600);
        String title = "Update Personal Information";
        popup.setTitle(title);
        popup.setScene(stageScene);
        popup.show();

        UpdateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PreparedStatement p = null;
                String query = "UPDATE sellerinfo SET account_name = ?, account_no = ?, account_type = ?, branch = ?, branch_code = ? WHERE seller_name = ?";
                try {
                    p = connectDB.prepareStatement(query);
                    p.setString(1, txfUpdateAccountName.getText());
                    p.setString(2, txfUpdateAccountNumber.getText());
                    p.setString(3, txfUpdateAccountType.getText());
                    p.setString(4, txfUpdateBranch.getText());
                    p.setString(5, txfUpdateBranchNo.getText());
                    p.setString(6,txtName.getText() );
                    p.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Stage dmessage = new Stage();
                VBox b = new VBox();
                Label success = new Label("Changes made succesfully \n log out then in \n to view changes");
                success.setFont((new Font("Arial Bold", 30)));
                b.getChildren().add(success);
                Scene ns = new Scene(b, 400, 400);
                dmessage.setScene(ns);
                PauseTransition delay = new PauseTransition(Duration.seconds(5));
                delay.setOnFinished(e -> dmessage.hide());
                dmessage.initStyle(StageStyle.UNDECORATED);
                dmessage.show();
                delay.play();
                popup.close();



            }
        });




    }
}
