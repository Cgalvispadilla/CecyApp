package com.example.cecyapp.clases;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


//la clase cliente hereda de la clase padre persona
public class Validaciones {
    public boolean ValidarCorreo(String correo){
        Pattern pattern  = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        Matcher  mather = pattern.matcher(correo);
        if(mather.find() == true){
            return true;
        }else{
            return false;
        }
    }

    public boolean validarNumeros(String campo){
        if (campo.matches("[0-9]*")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarTexto(String cadena){

            //Recorremos cada caracter de la cadena y comprobamos si son letras.
            //Para comprobarlo, lo pasamos a mayuscula y consultamos su numero ASCII.
            //Si está fuera del rango 65 - 90, es que NO son letras.
            //Para ser más exactos al tratarse del idioma español, tambien comprobamos
            //el valor 165 equivalente a la Ñ

            for (int i = 0; i < cadena.length(); i++)
            {
                char caracter = cadena.toUpperCase().charAt(i);
                int valorASCII = (int)caracter;
                if (valorASCII != 165 && (valorASCII < 65 || valorASCII > 90))
                    return false; //Se ha encontrado un caracter que no es letra
            }

            //Terminado el bucle sin que se haya retornado false, es que todos los caracteres son letras
            return true;

    }
}
