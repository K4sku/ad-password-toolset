module pl.tbs {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires org.controlsfx.controls;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.commons.compress;

    opens pl.tbs to javafx.fxml;
    exports pl.tbs;
}
