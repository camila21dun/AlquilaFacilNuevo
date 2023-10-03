package alquilafacil.controller;

import java.net.URL;
import java.util.ResourceBundle;

import alquilafacil.application.Main;
import alquilafacil.exception.AlquilaException;
import alquilafacil.model.AlquilaFacil;
import alquilafacil.model.Cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



public class RegistroClientesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegistroCliente;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField txtCedulaCliente;

    @FXML
    private TextField txtCiudadCliente;

    @FXML
    private TextField txtCorreoCliente;

    @FXML
    private TextField txtDireccionCliente;

    @FXML
    private TextField txtNombreCliente;

    @FXML
    private TextField txtTelefonoCliente;
    private Main main;

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();



    @FXML
    void RegistroClienteEvent(ActionEvent event) {
        registroClienteAction();

    }

    private void registroClienteAction() {

        try {
            alquilaFacil.registrarCliente(

                    txtNombreCliente.getText(),
                    txtCedulaCliente.getText(),
                    txtCorreoCliente.getText(),
                    txtDireccionCliente.getText(),
                    txtCiudadCliente.getText(),
                    txtTelefonoCliente.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha registrado correctamente el cliente "+txtNombreCliente.getText());
            alert.show();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();

        }

    }

    private boolean verificarCampos(String nombre, String cedula, String correo, String direccion, String ciudad, String teleono) {

        if (nombre.equals("")){
            return false;
        }
        if (cedula.equals("")){
            return false;
        }
        if (correo.equals("")){
            return false;
        }if (direccion.equals("")){
            return false;
        }
        if (ciudad.equals("")){
            return false;
        }
        if (teleono.equals("")){
            return false;
        }
        return true;
    }





    @FXML
    void volverEvent(ActionEvent event) {
        this.main.mostrarInicio();

    }

    @FXML
    void initialize() {

    }
    public void setApplication(Main main) {
        this.main=main;
    }

    private void limpiarTexto() {
        txtNombreCliente.setText("");
        txtCedulaCliente.setText("");
        txtCorreoCliente.setText("");
        txtDireccionCliente.setText("");
        txtCiudadCliente.setText("");
        txtTelefonoCliente.setText("");
    }
}
