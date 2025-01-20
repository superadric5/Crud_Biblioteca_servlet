package org.example.demo.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.Modelo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

@WebServlet(name = "PeticionesPrestamo", value = "/PeticionesPrestamo")
public class PeticionesPrestamo extends HttpServlet {
    private Scanner scanner;
    private DAOGenerico<Prestamo, Integer> daoPrestamo;
    private DAOGenerico<Usuario, Integer> daoUsuario;
    private DAOGenerico<Ejemplar, Integer> daoEjemplar;
    private ControladorPrestamos controladorPrestamos;
    private ObjectMapper conversorJson;

    private Integer id;
    private Integer usuarioId;
    private Integer ejemplarId;
    private String accion;

    public void init() {
        scanner = new Scanner(System.in);
        this.daoPrestamo = new DAOGenerico<>(Prestamo.class, Integer.class);
        this.daoUsuario = new DAOGenerico<>(Usuario.class, Integer.class);
        this.daoEjemplar = new DAOGenerico<>(Ejemplar.class, Integer.class);
        this.controladorPrestamos = new ControladorPrestamos();
        conversorJson = new ObjectMapper();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        conversorJson.registerModule(new JavaTimeModule());

        if (request.getParameter("Id") != null) {
            id = Integer.valueOf(request.getParameter("Id"));
        }
        if (request.getParameter("usId") != null) {
            usuarioId = Integer.valueOf(request.getParameter("usId"));
        }
        if (request.getParameter("ejId") != null) {
            ejemplarId = Integer.valueOf(request.getParameter("ejId"));
        }

        accion = request.getParameter("Accion");

        if (accion.equals("Crear")){
            Prestamo prestamo = creacionPrestamo(usuarioId, ejemplarId);
            if (!controladorPrestamos.verificarCreacionPrestamo(prestamo)){
                out.println("Prestamo No se pudo crear");
            }
            else {
                daoPrestamo.add(prestamo);
                String jackson = conversorJson.writeValueAsString(prestamo);
                out.println(jackson);
            }
        }
        else if (accion.equals("Actualizar")){
            Prestamo prestamo = modificarPrestamo(id);
            if (controladorPrestamos.comprobarPrestamoExiste(prestamo)){
                Usuario usuario = daoUsuario.getById(usuarioId);
                Ejemplar ejemplar = daoEjemplar.getById(ejemplarId);
                prestamo.setUsuario(usuario);
                prestamo.setEjemplar(ejemplar);
                prestamo.setFechaInicio(LocalDate.now());
                prestamo.setFechaDevolucion(LocalDate.now().plusDays(15));
                daoPrestamo.update(prestamo);
                String jackson = conversorJson.writeValueAsString(prestamo);
                out.println(jackson);
            }
            else {
                out.println("Prestamo no existe");
            }
        }
        else if (accion.equals("Eliminar")){
            Prestamo prestamo = eliminarPrestamo(id);
            if (controladorPrestamos.comprobarPrestamoExiste(prestamo)){
                daoPrestamo.delete(prestamo);
                out.println("Prestamo eliminado");
            }
            else {
                out.println("Prestamo no existe");
            }
        }
        else if (accion.equals("Listar")){
            String jackson = conversorJson.writeValueAsString(daoPrestamo.getAll());
            out.println(jackson);
        }
    }

    public void destroy() {
    }

    public Prestamo creacionPrestamo(Integer usuarioId, Integer ejemplarId) {
        Usuario us = daoUsuario.getById(usuarioId);
        Ejemplar ejemplar = daoEjemplar.getById(ejemplarId);

        Prestamo prestamo = new Prestamo(null ,us, ejemplar, LocalDate.now(), LocalDate.now().plusDays(15));
        return prestamo;
    }

    public Prestamo modificarPrestamo(Integer id) {
        Prestamo prestamo = daoPrestamo.getById(id);
        return  prestamo;
    }

    public Prestamo eliminarPrestamo(Integer id){
        Prestamo prestamo = daoPrestamo.getById(id);
        return  prestamo;
    }
}
