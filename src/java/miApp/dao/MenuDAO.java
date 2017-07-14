/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miApp.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import miApp.models.Menu;

/**
 *
 * @author hramirez
 */
@Stateless
public class MenuDAO extends AbstractDAO<Menu> {

    @PersistenceContext(unitName = "miAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuDAO() {
        super(Menu.class);
    }

    public List<Menu> findAllOrderMenu() {
        try {
            Query query = em.createNamedQuery("Menu.findAllOrderMenu");
            return (List<Menu>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public Menu findByAction(String action) {
        try {
            Query query = em.createNamedQuery("Menu.findByAction");
            query.setParameter("action", action);
            return (Menu) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
