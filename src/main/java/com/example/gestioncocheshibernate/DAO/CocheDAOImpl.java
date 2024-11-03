package com.example.gestioncocheshibernate.DAO;

import com.example.gestioncocheshibernate.Modelo.Coche;
import org.hibernate.Session;

import java.util.List;

public class CocheDAOImpl implements CocheDAO{

    @Override
    public boolean crearCoche(Session session, Coche coche) {
        boolean crearCoche=true;
        try {
            session.beginTransaction();
            session.save(coche);
            session.getTransaction().commit();
        }catch (Exception e) {
            crearCoche=false;
            if (session.getTransaction()!=null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
        return crearCoche;
    }

    @Override
    public boolean eliminarCoche(Session session, Coche coche) {
        boolean eliminarCoche=true;
        try {
            session.beginTransaction();
            session.delete(coche);
            session.getTransaction().commit();
        }catch (Exception e) {
            eliminarCoche=false;
            if (session.getTransaction()!=null) {
                session.getTransaction().rollback();
            }
        }
        return eliminarCoche;
    }

    @Override
    public boolean actualizarCoche(Session session, Coche coche) {
        boolean actualizarCoche=true;
        try {
            session.beginTransaction();
            session.update(coche);
            session.getTransaction().commit();
        }catch (Exception e) {
            if (session.getTransaction()!=null) {
                session.getTransaction().rollback();
            }
        }
        return actualizarCoche;
    }

    @Override
    public List<Coche> listarCoches(Session session) {
        session.beginTransaction();
        List<Coche> listaCoches;
        listaCoches = session.createQuery("from Coche", Coche.class).list();
        session.getTransaction().commit();
        return listaCoches;
    }
}
