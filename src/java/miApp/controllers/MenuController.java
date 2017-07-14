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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import miApp.dao.MenuDAO;
import miApp.models.Menu;

/**
 *
 * @author hramirez
 */
@Named(value = "menuController")
@SessionScoped
public class MenuController implements Serializable {

    @EJB
    private MenuDAO dao;
    private Menu selected;

    // ---------------------- Constructor de la Clase ----------------------
    
    public MenuController() {
    }

    public Menu getSelected() {
        if (selected == null) {
            selected = new Menu();
        }
        return selected;
    }

    // ---------------------- Métodos del Managed Bean ----------------------
    
    public String index() {
        return "/menu/index";
    }

    public List<Menu> listado() {
        return dao.findAllOrderMenu();
    }

    public String create() {
        selected = new Menu();
        return "/menu/new";
    }

    public String agregar() {
        Date d = new Date();
        selected.setCreated(d);
        selected.setUpdated(d);
        dao.create(selected);
        return "/menu/index";
    }

    public String edit(int codigo) {
        selected = dao.find(codigo);
        return "/menu/edit";
    }

    public String guardar() {
        Date d = new Date();
        selected.setUpdated(d);
        dao.edit(selected);
        return "/menu/index";
    }

    public String eliminar(int codigo) {
        selected = dao.find(codigo);
        try {
            dao.remove(selected);
        } catch (Exception e) {
            SessionUtil.addErrorMessage("No se puede eliminar, posibles datos asociados");
        }
        return "/menu/index";
    }

    // --------------------- Métodos de Ayuda para acceder al Bean por otras Clases ---------------------
    
    public SelectItem[] getItemsAvailableSelectOne() {
        return getSelectItems(dao.findAll(), true);
    }

    // Genera una lista con los items seleccionados (uno o muchos según selectOne). Para tablas relacionadas.
    
    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {

        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;

    }

    // ------------------ Clase para conversiones, se deben implementar todos los métodos ------------------
    
    @FacesConverter(forClass = Menu.class)
    public static class MenuControllerConverter implements Converter {

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        } // Fin java.lang.Integer getKey

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        } // Fin String getStringKey

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MenuController controller = (MenuController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "menuController");
            return controller.dao.find(getKey(value));
        } // Fin public Object getAsObject

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Menu) {
                Menu o = (Menu) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + MenuController.class.getName());
            }
        } // Fin public String getAsString

    }
}
