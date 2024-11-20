module com.example.lbycpa2_finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    opens com.example.lbycpa2_finalproject to javafx.fxml;
    exports com.example.lbycpa2_finalproject;
}