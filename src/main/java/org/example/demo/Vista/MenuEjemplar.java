package org.example.demo.Vista;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MenuEjemplar", value = "/MenuEjemplar")
public class MenuEjemplar extends HttpServlet{

    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Bienvenido al menu de ejemplares</h1>");

        out.println("<form action=\"PeticionesEjemplares\" method=\"post\">");

        out.println("<label>Id:</label><br>");
        out.println("<input type=\"text\" name=\"id\" id=\"id\"/><br><br>");

        out.println("<label>ISBN:</label><br>");
        out.println("<input type=\"text\" name=\"isbn\" id=\"isbn\"/><br><br>");

        out.println("<label>Estado:</label><br>");
        out.println("<input type=\"text\" name=\"estado\" id=\"estado\"/><br><br>");

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
