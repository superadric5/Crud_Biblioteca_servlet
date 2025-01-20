package org.example.demo.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.Modelo.*;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@WebServlet(name = "PeticionesEjemplares", value = "/PeticionesEjemplares")
public class PeticionesEjemplar extends HttpServlet{

    private Scanner scanner;
    private DAOGenerico<Ejemplar, Integer> daoEjemplar;
    private DAOGenerico<Libro, String> daoLibro;
    private ControladorEjemplares controladorEjemplares;
    private ControladorLibros controladorLibros;
    private ObjectMapper conversorJson;

    private Integer id;
    private String isbn;
    private String estado;
    private String accion;

    public void init() {
        scanner = new Scanner(System.in);
        daoEjemplar = new DAOGenerico<>(Ejemplar.class, Integer.class);
        daoLibro = new DAOGenerico<>(Libro.class, String.class);
        controladorEjemplares = new ControladorEjemplares();
        controladorLibros = new ControladorLibros();
        conversorJson = new ObjectMapper();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (request.getParameter("id") != null) {
            id = Integer.valueOf(request.getParameter("id"));
        }
        isbn = request.getParameter("isbn");
        estado = request.getParameter("estado");

        accion = request.getParameter("Accion");

        if (accion.equals("Crear")){
            Ejemplar ejemplar = creacionEjemplar(isbn, estado);
            if (!controladorEjemplares.verificarCreacionDeEjemplar(ejemplar)){
                out.println("No se pudo crear el ejemplar");
            }
            else {
                daoEjemplar.add(ejemplar);
                String jackson = conversorJson.writeValueAsString(ejemplar);
                out.println(jackson);
            }
        }
        else if (accion.equals("Actualizar")){
            Ejemplar ejemplar = modificarEjemplar(id);
            if (controladorEjemplares.comprobarEjemplarExiste(ejemplar)){
                Libro libro = daoLibro.getById(isbn);
                ejemplar.setIsbn(libro);
                ejemplar.setEstado(estado);
                daoEjemplar.update(ejemplar);
                String jackson = conversorJson.writeValueAsString(ejemplar);
                out.println(jackson);
            }
            else {
                out.println("Ejemplar no existe");
            }
        }
        else if (accion.equals("Eliminar")){
            Ejemplar ejemplar = eliminarEjemplar(id);
            if (controladorEjemplares.comprobarEjemplarExiste(ejemplar)){
                daoEjemplar.delete(ejemplar);
                out.println("Ejemplar eliminado");
            }
            else {
                out.println("Ejemplar no existe");
            }
        }
        else if (accion.equals("Listar")){
            String jackson = conversorJson.writeValueAsString(daoEjemplar.getAll());
            out.println(jackson);
        }
    }

    public void destroy() {
    }

    public Ejemplar creacionEjemplar(String isbn, String estado) {
        Libro libro = daoLibro.getById(isbn);
        Ejemplar ej = new Ejemplar(libro, estado);
        return ej;
    }

    public Ejemplar modificarEjemplar(Integer id) {
        Ejemplar ej = daoEjemplar.getById(id);
        return  ej;
    }

    public Ejemplar eliminarEjemplar(Integer id) {
        Ejemplar ej = daoEjemplar.getById(id);
        return  ej;
    }


}
