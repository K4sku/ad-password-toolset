module pl.tbs {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires org.controlsfx.controls;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.commons.compress;
    requires java.net.http;
    requires org.apache.commons.lang3;

    opens pl.tbs to javafx.fxml;
    opens pl.tbs.controller to javafx.fxml;
    opens pl.tbs.model to javafx.fxml;
    exports pl.tbs;
}
