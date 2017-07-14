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
import miApp.dao.MenuTipousuarioDAO;
import miApp.models.MenuTipousuario;

/**
 *
 * @author hramirez
 */
@Named(value = "menuTipousuarioController")
@SessionScoped
public class MenuTipousuarioController implements Serializable {

    @EJB
    private MenuTipousuarioDAO dao;
    private MenuTipousuario selected;

    // ---------------------- Constructor de la Clase ----------------------
    
    public MenuTipousuarioController() {
    }

    public MenuTipousuario getSelected() {
        if (selected == null) {
            selected = new MenuTipousuario();
        }
        return selected;
    }

    // ---------------------- Métodos del Managed Bean ----------------------
    
    public String index() {
        return "/menu_tipousuario/index";
    }

    public List<MenuTipousuario> listado() {
        return dao.findAll();
    }

    public String create() {
        selected = new MenuTipousuario();
        return "/menu_tipousuario/new";
    }

    public String agregar() {
        Date d = new Date();
        selected.setCreated(d);
        selected.setUpdated(d);
        dao.create(selected);
        return "/menu_tipousuario/index";
    }

    public String edit(int codigo) {
        selected = dao.find(codigo);
        return "/menu_tipousuario/edit";
    }

    public String guardar() {
        Date d = new Date();
        selected.setUpdated(d);
        dao.edit(selected);
        return "/menu_tipousuario/index";
    }

    public String eliminar(int codigo) {
        selected = dao.find(codigo);
        dao.remove(selected);
        return "/menu_tipousuario/index";
    }
}
