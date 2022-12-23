module de.nuernberger.kniffel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens de.nuernberger.kniffel to javafx.fxml;
    exports de.nuernberger.kniffel;
}