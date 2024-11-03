package com.example.gestioncocheshibernate.Controller;

import com.example.gestioncocheshibernate.DAO.CocheDAOImpl;
import com.example.gestioncocheshibernate.Modelo.Coche;
import com.example.gestioncocheshibernate.Util.Alerta;
import com.example.gestioncocheshibernate.Util.HibernateUtil;
import com.example.gestioncocheshibernate.Util.Validar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GestionCochesController implements Initializable {

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private TableColumn<?, ?> columnMarca;

    @FXML
    private TableColumn<?, ?> columnMatricula;

    @FXML
    private TableColumn<?, ?> columnModelo;

    @FXML
    private TableColumn<?, ?> columnTipo;

    @FXML
    private TableView<Coche> TableView;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtModelo;
    SessionFactory sessionFactory;
    org.hibernate.Session session;
    Coche cocheseleccionado;



    CocheDAOImpl cocheDao= new CocheDAOImpl();


    private final ArrayList<String> listaTipos = new ArrayList<>(Arrays.asList("Gasolina", "Diesel", "Hibrido", "Electrico"));

    private ObservableList<Coche> listacoches;

    @FXML
    void clicFilaTableView(MouseEvent event) {
        if (TableView.getSelectionModel().getSelectedItem()!=null){
            cocheseleccionado = TableView.getSelectionModel().getSelectedItem();
            txtMatricula.setText(cocheseleccionado.getMatricula());
            txtModelo.setText(cocheseleccionado.getModelo());
            txtMarca.setText(cocheseleccionado.getMarca());
            cbTipo.setValue(cocheseleccionado.getTipo());
        }
    }

    @FXML
    void onClicCrear(ActionEvent event) {
        String matricula = txtMatricula.getText();
        if (!Validar.validarMatriculaEuropea_Exp(matricula)){
            Alerta.mostrarAlerta("Debes escribir una matricula con formato europeo");
        }else{
            String marca = txtMarca.getText();
            String modelo=txtModelo.getText();
            String tipo = cbTipo.getValue();
            Coche nuevoCoche = new Coche(matricula, marca, modelo, tipo);
            if (cocheDao.crearCoche(session, nuevoCoche)){
                Alerta.mostrarAlerta("Se ha creado el coche");
                listacoches.addAll(nuevoCoche);
            }else {
                Alerta.mostrarAlerta("No se ha podido crear el coche");
            }
        }
    }

    @FXML
    void onClicEliminar(ActionEvent event) {
        if (cocheDao.eliminarCoche(session, cocheseleccionado)){
            Alerta.mostrarAlerta("Coche eliminado correctamente");
            listacoches.remove(cocheseleccionado);
        }else {
            Alerta.mostrarAlerta("No se ha eliminado el coche");
        }
    }

    @FXML
    void onClicLimpiar(ActionEvent event) {
        txtMatricula.clear();
        txtMarca.clear();
        txtModelo.clear();
        cbTipo.setValue(null);
    }

    @FXML
    void onClicModificar(ActionEvent event) {
        int index = listacoches.indexOf(cocheseleccionado);
        if (!Validar.validarMatriculaEuropea_Exp(txtMatricula.getText())){
            Alerta.mostrarAlerta("Introduce una matricula correcta");
        }else {
            cocheseleccionado.setMatricula(txtMatricula.getText());
            cocheseleccionado.setMarca(txtMarca.getText());
            cocheseleccionado.setModelo(txtModelo.getText());
            cocheseleccionado.setTipo(cbTipo.getValue());

            if (cocheDao.actualizarCoche(session, cocheseleccionado)){
                Alerta.mostrarAlerta("Coche modificado");
                listacoches.set(index, cocheseleccionado);
            }else {
                Alerta.mostrarAlerta("No se ha actualizado el coche");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();

        cbTipo.getItems().addAll(listaTipos);

        columnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        listacoches = FXCollections.observableArrayList(cocheDao.listarCoches(session));
        TableView.setItems(listacoches);

    }

}
