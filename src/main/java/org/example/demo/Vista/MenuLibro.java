package org.example.demo.Vista;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MenuLibro", value = "/MenuLibro")
public class MenuLibro extends HttpServlet {

    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Bienvenido al menu de libros</h1>");

        out.println("<form action=\"PeticionesLibros\" method=\"post\">");

        out.println("<label>ISBN:</label><br>");
        out.println("<input type=\"text\" name=\"isbn\" id=\"isbn\"/><br><br>");

        out.println("<label>Titulo:</label><br>");
        out.println("<input type=\"text\" name=\"titulo\" id=\"titulo\"/><br><br>");

        out.println("<label>Autor:</label><br>");
        out.println("<input type=\"text\" name=\"autor\" id=\"autor\"/><br><br>");

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
