package com.example.gestioncocheshibernate.Util;

public class Validar {
    public static boolean validarMatriculaEuropea_Exp(String matricula) {

        return matricula.matches("^[0-9]{4}[A-Z]{3}$");

    }
}
