module pl.tbs {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.tbs to javafx.fxml;
    exports pl.tbs;
}
