module com.esalaff.esalaff {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.projet.badr to javafx.fxml;
    exports com.projet.badr;
}