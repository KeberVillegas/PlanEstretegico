package BeanPediEspe;

import Servidor.EspePlanService;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Kleber
 */
@ManagedBean
@ViewScoped

public class BeanTipoEspe implements Serializable {

    // @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ServidorEspePedi/EspePlanService.wsdl")
    //private EspePlanService service;
    private EspePlanService service = new EspePlanService();
    Servidor.EspePlan port = service.getEspePlanPort();

    ///////////////
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void insertar() {
        System.out.println("hola jj");
        port.insertarTipo(tipo);
        this.limpiar();

    }

    public void limpiar() {
        this.setTipo("");
    }
}
