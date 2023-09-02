module com.example.currencyconverter {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires com.google.gson;

    opens com.example.currencyconverter to javafx.fxml;
    exports com.example.currencyconverter;
}