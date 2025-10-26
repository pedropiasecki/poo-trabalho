module br.unicentro.appjogador {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens br.unicentro.appjogador to javafx.fxml;
    opens br.unicentro.appjogador.controller to javafx.fxml;
    opens br.unicentro.appjogador.model to javafx.base, javafx.fxml;

    exports br.unicentro.appjogador;
}
