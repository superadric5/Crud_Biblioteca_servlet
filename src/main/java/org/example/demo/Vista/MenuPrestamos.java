package org.example.demo.Vista;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MenuPrestamo", value = "/MenuPrestamo")
public class MenuPrestamos extends HttpServlet {

    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Bienvenido al menu de prestamos</h1>");

        out.println("<form action=\"PeticionesPrestamo\" method=\"post\">");

        out.println("<label>ID:</label><br>");
        out.println("<input type=\"text\" name=\"Id\" id=\"Id\"/><br><br>");

        out.println("<label>Usuario_ID:</label><br>");
        out.println("<input type=\"text\" name=\"usId\" id=\"usId\"/><br><br>");

        out.println("<label>Ejemplar_ID:</label><br>");
        out.println("<input type=\"text\" name=\"ejId\" id=\"ejId\"/><br><br>");

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
