module com.mycompany.ps {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.mycompany.ps to javafx.fxml;
    exports com.mycompany.ps;
    requires org.java_websocket;
    requires com.fasterxml.jackson.databind;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
}
