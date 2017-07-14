/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miApp.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import miApp.dao.UsuarioDAO;
import miApp.models.Usuario;

/**
 *
 * @author hramirez
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private UsuarioDAO dao;
    private Usuario selected;

    // ---------------------- Constructor de la Clase ----------------------
    
    public UsuarioController() {
    }

    public Usuario getSelected() {
        if (selected == null) {
            selected = new Usuario();
        }
        return selected;
    }

    // ---------------------- Métodos del Managed Bean ----------------------
    
    public String index() {
        return "/usuario/index";
    }

    public List<Usuario> listado() {
        return dao.findAll();
    }

    public String create() {
        selected = new Usuario();
        return "/usuario/new";
    }

    public String agregar() {
        Date d = new Date();
        selected.setCreated(d);
        selected.setUpdated(d);
        dao.create(selected);
        return "/usuario/index";
    }

    public String edit(int codigo) {
        selected = dao.find(codigo);
        return "/usuario/edit";
    }

    public String guardar() {
        Date d = new Date();
        selected.setUpdated(d);
        dao.edit(selected);
        return "/usuario/index";
    }

    public String eliminar(int codigo) {
        selected = dao.find(codigo);
        dao.remove(selected);
        return "/usuario/index";
    }
}
