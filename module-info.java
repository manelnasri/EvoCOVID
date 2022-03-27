module fr.ul.miage.evocovid {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires commons.cli;
	requires commons.csv;
	requires javafx.base;

    opens fr.ul.miage.evocovid to javafx.fxml;
    exports fr.ul.miage.evocovid;

}