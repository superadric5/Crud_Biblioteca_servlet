package org.example.demo.Modelo;

import jakarta.persistence.*;
import java.util.List;

public class DAOGenerico<T, ID> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidad-biblioteca");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    Class<T> clase;
    Class<ID> claseID;

    public DAOGenerico(Class<T> clase, Class<ID> claseID){
        this.clase=clase;
        this.claseID=claseID;
    }

    //INSERT
    public boolean add(T objeto){
        tx.begin();
        em.persist(objeto);
        tx.commit();
        return false;
    }

    //SELECT WHERE ID
    public T getById(ID id){
        return em.find(clase, id);
    }

    //SELECT *
    public List<T> getAll(){
        return em.createQuery("SELECT c from "+ clase.getName()+" c").getResultList();
    }


    //SELECT COUNT(*) de los ejemplares disponibles
//    public int getStockEjemplaresDisponibles(){
//        String sql ="select count(e) from "+ clase.getName()+" e WHERE e.estado = :estado";
//        Query query = em.createQuery(sql);
//        query.setParameter("estado", Estado.Disponible);
//        return ((Long)query.getSingleResult()).intValue();
//    }

    //SELECT p.uduario_id de los prestamos vencidos
    public List<Integer> getUsuariosConPrestamosVencidos(){
        String sql = "Select p.usuario FROM "+ clase.getName()+" p Where p.fechaDevolucion < CURRENT_DATE";
        Query query = em.createQuery(sql);
        return query.getResultList();
    }

    //Select p de los prestamos con un id de usuario especificoç
//    public List<Prestamo> getPrestamosDeXUsuario(Usuario usuario){
//        String sql = "Select p From "+ clase.getName()+" p Where p.usuario = :usuario";
//        Query query = em.createQuery(sql);
//        query.setParameter("usuario", usuario);
//        return query.getResultList();
//    }

    //Select p de un prestamo con un ususario y un ejemplar especifico
//    public T getPrestamoDeXUsuarioDeXEjemplar(Usuario usuario, Ejemplar ejemplar){
//        String sql = "Select p From "+ clase.getName()+" p Where p.usuario = :usuario AND p.ejemplar = :ejemplar";
//        Query query = em.createQuery(sql);
//        query.setParameter("usuario", usuario);
//        query.setParameter("ejemplar", ejemplar);
//        return (T)query.getSingleResult();
//    }

    //UPDATE
    public T update(T objeto){
        tx.begin();
        objeto = em.merge(objeto);
        tx.commit();
        return objeto;
    }
    //DELETE WHERE objeto.id
    public boolean delete(T objeto){
        tx.begin();
        em.remove(objeto);
        tx.commit();
        return true;
    }

    @Override
    public String toString() {
        return "DAOGenerico{" +
                "clase=" + clase.getSimpleName() +
                "clase=" + clase.getName() +
                ", claseID=" + claseID +
                '}';
    }
}

