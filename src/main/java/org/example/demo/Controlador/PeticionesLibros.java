package org.example.demo.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.Modelo.DAOGenerico;
import org.example.demo.Modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@WebServlet(name = "PeticionesLibros", value = "/PeticionesLibros")
public class PeticionesLibros  extends HttpServlet {
    private Scanner scanner;
    private DAOGenerico<Libro, String> daoLibro;
    private ControladorLibros controladorLibros;
    private ObjectMapper conversorJson = new ObjectMapper();

    private String isbn;
    private String titulo;
    private String autor;
    private String accion;

    public void init() {
        controladorLibros = new ControladorLibros();
        daoLibro = new DAOGenerico<>(Libro.class, String.class);
        Scanner scanner = new Scanner(System.in);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");

        String accion = request.getParameter("Accion");

        if (accion.equals("Crear")){
            Libro libro = creacionLibro(isbn, titulo, autor);
            if (controladorLibros.comprobarLibroExiste(libro)){
                out.println("Libro Ya Existe");
            } else if (controladorLibros.comprobarISBN(libro)) {
                daoLibro.add(libro);
                String jackson = conversorJson.writeValueAsString(libro);
                out.println(jackson);
            }
            else {
                out.println("ISBN introducido incorrectamente");
            }
        }
        else if (accion.equals("Actualizar")){
            Libro libro = modificarLibro(isbn);
            if (controladorLibros.comprobarLibroExiste(libro)){
                Libro libro2 = new Libro(isbn, titulo, autor);
                daoLibro.update(libro2);
                String jackson = conversorJson.writeValueAsString(libro2);
                out.println(jackson);
            }
            else {
                out.println("Libro no existe");
            }
        }
        else if (accion.equals("Eliminar")){
            Libro libro = eliminarLibro(isbn);
            if (controladorLibros.comprobarLibroExiste(libro)){
                daoLibro.delete(libro);
                out.println("Libro eliminado");
            }
            else {
                out.println("Libro no existe");
            }
        }
        else if (accion.equals("Listar")){
            String jackson = conversorJson.writeValueAsString(daoLibro.getAll());
            out.println(jackson);
        }
    }

    public void destroy() {
    }

    public PeticionesLibros() {
        scanner = new Scanner(System.in);
        daoLibro = new DAOGenerico<>(Libro.class, String.class);
    }

    public Libro creacionLibro(String isbn, String titulo, String autor) {
        Libro libro = new Libro(isbn, titulo, autor);
        return libro;
    }

    public Libro modificarLibro(String isbn) {
        Libro libro = daoLibro.getById(isbn);
        return libro;
    }

    public Libro eliminarLibro(String isbn){
        Libro libro = daoLibro.getById(isbn);
        return libro;
    }
}

