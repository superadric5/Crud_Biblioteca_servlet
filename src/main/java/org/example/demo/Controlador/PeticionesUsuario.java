package org.example.demo.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.demo.Modelo.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

@WebServlet(name = "PeticionesUsuario", value = "/PeticionesUsuario")
public class PeticionesUsuario extends HttpServlet{
    private Scanner scanner;
    private DAOGenerico<Usuario, Integer> daoUsuario;
    private ControladorUsuarios controladorUsuarios;
    private ObjectMapper conversorJson;

    private String dni;
    private String nombre;
    private String email;
    private String password;
    private String tipo;
    private String accion;

    public void init() {
        scanner = new Scanner(System.in);
        this.daoUsuario = new DAOGenerico<>(Usuario.class, Integer.class);
        this.controladorUsuarios = new ControladorUsuarios();
        this.conversorJson = new ObjectMapper();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        dni = request.getParameter("dni");
        nombre = request.getParameter("nombre");
        email = request.getParameter("email");
        password = request.getParameter("password");
        tipo = request.getParameter("tipo");

        accion = request.getParameter("Accion");
        conversorJson.registerModule(new JavaTimeModule());

        if (accion.equals("Crear")){
            Usuario usuario = creacionUsuario(dni, nombre, email, password, tipo);
            if (!controladorUsuarios.verificarCreacionDeUsuario(usuario)){
                out.println("No se pudo crear el usuario");
            }
            else {
                daoUsuario.add(usuario);
                String jackson = conversorJson.writeValueAsString(usuario);
                out.println(jackson);
            }
        }
        else if (accion.equals("Actualizar")){
            Usuario usuario = modificarUsuario(dni);
            if (controladorUsuarios.comprobarUsuarioExiste(usuario)){
                if (controladorUsuarios.comprobarDNI(usuario) && controladorUsuarios.comprobarEmail(usuario) && controladorUsuarios.comprobarPassword(usuario)){
                    usuario.setEmail(email);
                    usuario.setNombre(nombre);
                    usuario.setTipo(tipo);
                    usuario.setPassword(password);
                    daoUsuario.update(usuario);
                    String jackson = conversorJson.writeValueAsString(usuario);
                    out.println(jackson);
                }
                else {
                    out.println("Usuario no se pudo actualizar. Datos introducidos incorrectamente");
                }
            }
            else {
                out.println("Usuario no existe");
            }
        }
        else if (accion.equals("Eliminar")){
            Usuario usuario = eliminarUsuario(dni);
            if (controladorUsuarios.comprobarUsuarioExiste(usuario)){
                daoUsuario.delete(usuario);
                out.println("Usuario eliminado");
            }
            else {
                out.println("Usuario no existe");
            }
        }
        else if (accion.equals("Listar")){
            String jackson = conversorJson.writeValueAsString(daoUsuario.getAll());
            out.println(jackson);
        }
    }

    public void destroy() {
    }

    public Usuario creacionUsuario(String dni, String nombre, String email, String password, String tipo) {
        Usuario usuario = new Usuario(dni, nombre, email, password, tipo);
        return usuario;
    }

    public Usuario modificarUsuario(String dni){
        Usuario usuario = daoUsuario.getByDni(dni);
        return  usuario;
    }

    public Usuario eliminarUsuario(String dni){
        Usuario usuario = daoUsuario.getByDni(dni);
        return  usuario;
    }
}
