package alquilafacil.controller;

import java.net.URL;
import java.util.ResourceBundle;


import alquilafacil.application.Main;
import alquilafacil.exception.AlquilaException;
import alquilafacil.model.AlquilaFacil;

import alquilafacil.model.Vehiculo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;



public class RegistroVehiculosController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegistrarVehiculo;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> cbTipoCaja;

    @FXML
    private TextField txtKilometraje;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtPuertas;

    @FXML
    private TextField txtReferencia;

    @FXML
    private TextField txtUrlFoto;
    private Main main;
    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();


    @FXML
    void registrarVehiculoEvent(ActionEvent event) {
        registrarVehiculoAction();

    }

    private void registrarVehiculoAction() {

        String placa = txtPlaca.getText();
        String referencia = txtReferencia.getText();
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String foto =  txtUrlFoto.getText();
        String kilometraje = txtKilometraje.getText();
        String precio = txtPrecio.getText();
        String puertas =  txtPuertas.getText();
        boolean tipoCaja = cbTipoCaja.getValue().isEmpty();

        try {

            alquilaFacil.registrarVehiculo(placa, referencia, marca, modelo, foto, kilometraje, precio, tipoCaja, puertas);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha registrado correctamente el vehiculo de placa  " + placa.toLowerCase());
            alert.show();
            limpiarTexto();

            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.setHeaderText("");
                alert.show();
            }

        }


    @FXML
    void volverEvent(ActionEvent event) {
        this.main.mostrarInicio();

    }

    @FXML
    void initialize() {
        cbTipoCaja.getItems().addAll("Manual","Automatica");
    }

    public void setApplication(Main main) {
        this.main=main;
    }

    private void limpiarTexto() {
        txtPuertas.setText("");
        txtKilometraje.setText("");
        txtUrlFoto.setText("");
        txtMarca.setText("");
        txtReferencia.setText("");
        txtModelo.setText("");
        txtPrecio.setText("");
        txtPlaca.setText("");
        cbTipoCaja.commitValue();
    }
}
