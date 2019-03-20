/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanPediEspe;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Kleber
 */

public class Arbol {

     String padre;
     List hijo;
    
    /**
     * Creates a new instance of Arbol
     */
    public Arbol() {
    }

    public Arbol(String padre, List hijo) {
        this.padre = padre;
        this.hijo = hijo;
    }
    
    

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public List getHijo() {
        return hijo;
    }

    public void setHijo(List hijo) {
        this.hijo = hijo;
    }
    
    
    
    
}
