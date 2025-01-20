package org.example.demo.Controlador;

import org.example.demo.Modelo.*;

import java.util.List;

public class ControladorEjemplares {
    private final DAOGenerico<Ejemplar, Integer> daoEjemplar;
    private final DAOGenerico<Libro, String> daoLibro;
    public ControladorEjemplares() {
        daoEjemplar = new DAOGenerico<>(Ejemplar.class, Integer.class);
        daoLibro = new DAOGenerico<>(Libro.class, String.class);
    }

    public boolean verificarCreacionDeEjemplar(Ejemplar ej) {

        boolean isbnCorrecto = false;
        boolean estadoCorrecto = false;

        isbnCorrecto = comprobarISBN(ej);
        estadoCorrecto = comprobarEstado(ej);

        if (isbnCorrecto == true && estadoCorrecto == true) {
            return true;
        }
        return false;
    }

    public boolean comprobarEjemplarExiste(Ejemplar ej) {
        List<Ejemplar> ejemplares = daoEjemplar.getAll();
       for (Ejemplar e : ejemplares) {
           if (e.getId().equals(ej.getId())) {
               return true;
           }
       }
        return false;
    }

    public boolean comprobarISBN(Ejemplar ej) {
        Libro libro = ej.getIsbn();
        List<Libro> libros = daoLibro.getAll();
        for (Libro l : libros) {
            if (l.getIsbn().equals(libro.getIsbn())) {
                return true;
            }
        }
        return false;
    }

    public boolean comprobarISBN(String isbn) {
        Libro libro = daoLibro.getById(isbn);
        List<Libro> libros = daoLibro.getAll();
        for (Libro l : libros) {
            if (l.getIsbn().equals(libro.getIsbn())) {
                return true;
            }
        }
        return false;
    }

    public boolean comprobarEstado(Ejemplar ej) {
        if (ej.getEstado().equals("Dañado") || ej.getEstado().equals("Prestado") || ej.getEstado().equals("Disponible")) {
            return true;
        }
        return false;
    }

    public boolean comprobarEstado(String estado) {
        if (estado.equals("Dañado") || estado.equals("Prestado") || estado.equals("Disponible")) {
            return true;
        }
        return false;
    }

    public boolean comprobarEstadoDisponible(Ejemplar ej) {
        if (ej.getEstado().equals("Disponible")) {
            return true;
        }
        return false;
    }

}
