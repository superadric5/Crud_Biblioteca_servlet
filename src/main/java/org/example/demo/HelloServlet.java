package org.example.demo;

import java.io.*;
import java.util.regex.Pattern;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String username;
    private String password;

    public void init() { }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Bienvenido " + username + "</h1>");
        out.println("<p>Tu contraseña: " + password + "</p>");
        if (Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,20}$",password)) {
            out.println("<p style=\"color: green;\">Contraseña Correcta</p>");
        }
        else{
            out.println("<p style=\"color: red;\">Contraseña Incorrecta:</p>");
            if (password.length() < 8) {
                out.println("<p style=\"color: red;\">Longitud Menor de 8</p>");
            } else if (password.length() > 20) {
                out.println("<p style=\"color: red;\">Longitud Mayor de 20</p>");
            } else if (!Pattern.matches(".*[A-Z].*", password)) {
                out.println("<p style=\"color: red;\">No contiene una letra mayuscula</p>");
            }
            else if (!Pattern.matches(".*[a-z].*", password)) {
                out.println("<p style=\"color: red;\">No contiene una letra minuscula</p>");
            }
            else if (!Pattern.matches(".*\\d.*", password)) {
                out.println("<p style=\"color: red;\">No contiene un numero</p>");
            }
        }
        System.out.println(username);
        System.out.println(password);
        out.println("</body></html>");
    }

    public void destroy() {
    }
}