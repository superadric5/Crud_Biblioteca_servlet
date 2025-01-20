package org.example.demo.Controlador;

import org.example.demo.Modelo.DAOGenerico;
import org.example.demo.Modelo.Ejemplar;
import org.example.demo.Modelo.Prestamo;
import org.example.demo.Modelo.Usuario;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ControladorPrestamos {
    private final DAOGenerico<Prestamo, Integer> daoPrestamo;
    private final DAOGenerico<Usuario, Integer> daoUsuario;
    private final DAOGenerico<Ejemplar, Integer> daoEjemplar;

    public ControladorPrestamos() {
        this.daoPrestamo = new DAOGenerico<>(Prestamo.class, Integer.class);
        this.daoUsuario = new DAOGenerico<>(Usuario.class, Integer.class);
        this.daoEjemplar = new DAOGenerico<>(Ejemplar.class, Integer.class);
    }

    public boolean verificarCreacionPrestamo(Prestamo prestamo) {

        boolean ejemplarCorrecto = false;
        boolean usuarioCorrecto = false;

        ejemplarCorrecto = comprobarEjemplar(prestamo);
        usuarioCorrecto = comprobarUsuario(prestamo.getUsuario());

        if (ejemplarCorrecto == true && usuarioCorrecto == true) {
            return true;
        }
        return false;
    }

    public boolean comprobarPrestamoExiste(Prestamo prestamo) {
        List<Prestamo> prestamos = daoPrestamo.getAll();
        for (Prestamo p : prestamos) {
            if (p.getId().equals(prestamo.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean comprobarEjemplar(Prestamo prestamo) {
        Ejemplar ej = prestamo.getEjemplar();
        ControladorEjemplares cEj = new ControladorEjemplares();
        if (cEj.comprobarEjemplarExiste(ej) == true && cEj.comprobarEstadoDisponible(ej) == true) {
            return true;
        }
        return false;
    }

    public boolean comprobarEjemplar(Ejemplar ej) {
        ControladorEjemplares cEj = new ControladorEjemplares();
        if (cEj.comprobarEjemplarExiste(ej) == true) {
            return true;
        }
        return false;
    }

    public boolean comprobarUsuario(Usuario usuario) {
        boolean usuarioPenalizado = false;
        boolean usuarioExiste = false;
        boolean usuarioMaxPrestamos = false;

        ControladorUsuarios cUs = new ControladorUsuarios();
        usuarioPenalizado = cUs.comprobarUsuarioPenalizado(usuario);
        usuarioExiste = cUs.comprobarUsuarioExiste(usuario);
        usuarioMaxPrestamos = cUs.comprobarMaxPrestamosUsuario(usuario);
        if (usuarioExiste == true && usuarioMaxPrestamos == false && usuarioPenalizado == false) {
            return true;
        }
        return false;
    }


    public boolean comprobarPrestamoDeXusuarioXejemplarExiste(Usuario us, Ejemplar ej) {
        if (daoPrestamo.getPrestamoDeXUsuarioDeXEjemplar(us, ej) != null) {
            return true;
        }
        return false;
    }

    public void comprobarDevolucionCorrecta(Prestamo prestamo) {
        if (LocalDate.now().isAfter(prestamo.getFechaDevolucion())) {
            //Para sacar los dias he utilizado mi mejor amigo chatGPT
            long diasPasados = ChronoUnit.DAYS.between(prestamo.getFechaDevolucion(), LocalDate.now());
            prestamo.getUsuario().setPenalizacionHasta(LocalDate.now().plusDays(diasPasados*15));
            daoUsuario.update(prestamo.getUsuario());
        }
    }
}
