package org.example.demo.Vista;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MenuUsuario", value = "/MenuUsuario")
public class MenuUsuario extends HttpServlet{

    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Bienvenido al menu de Usuarios</h1>");

        out.println("<form action=\"PeticionesUsuario\" method=\"post\">");

        out.println("<label>DNI:</label><br>");
        out.println("<input type=\"text\" name=\"dni\" id=\"dni\"/><br><br>");

        out.println("<label>Nombre:</label><br>");
        out.println("<input type=\"text\" name=\"nombre\" id=\"nombre\"/><br><br>");

        out.println("<label>Email:</label><br>");
        out.println("<input type=\"text\" name=\"email\" id=\"email\"/><br><br>");

        out.println("<label>Contraseña:</label><br>");
        out.println("<input type=\"password\" name=\"password\" id=\"password\"/><br><br>");

        out.println("<label>Tipo de Usuario:</label><br>");
        out.println("<input type=\"text\" name=\"tipo\" id=\"tipo\"/><br><br>");

        out.println("<input type=\"submit\" value=\"Crear\" name=\"Accion\" /><br>");
        out.println("<input type=\"submit\" value=\"Actualizar\" name=\"Accion\" /><br>");
        out.println("<input type=\"submit\" value=\"Eliminar\" name=\"Accion\" /><br>");
        out.println("<input type=\"submit\" value=\"Listar\" name=\"Accion\" /><br>");

        out.println("</form>");

        out.println("</body></html>");
    }

    public void destroy() {
    }
}
