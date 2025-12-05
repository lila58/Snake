module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;

    exports niveau3;
    exports general;


    opens niveau3;
    opens general to javafx.fxml;
    opens org.example.demo5 to javafx.fxml;
    exports org.example.demo5;
}