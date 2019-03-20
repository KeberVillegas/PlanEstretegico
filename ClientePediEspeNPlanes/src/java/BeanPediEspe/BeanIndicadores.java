/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanPediEspe;

import Servidor.EspePlanService;
import Servidor.Indicadoresp;
import Servidor.Objetivosexternos;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kleber
 */
@ManagedBean
@ViewScoped
public class BeanIndicadores implements Serializable {

    private EspePlanService service = new EspePlanService();
    Servidor.EspePlan port = service.getEspePlanPort();
    String indicadorId;
    String obj;
    String des;
    Date fecha;
    XMLGregorianCalendar fecha2;
    String convertFecha;
    List<Objetivosexternos> listaObj;
    List<Indicadoresp> listaI;
    Date fechaActual = new Date();
    Indicadoresp ind = new Indicadoresp();
/////////////////////////////

    public XMLGregorianCalendar getFecha2() {
        return fecha2;
    }

    public void setFecha2(XMLGregorianCalendar fecha2) {
        this.fecha2 = fecha2;
    }
    
   
    //////////////////////////////
    public Indicadoresp getInd() {
        return ind;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
    
    

    public void setInd(Indicadoresp ind) {
        this.ind = ind;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Objetivosexternos> getListaObj() {
        return port.obtenerObj();
    }

    public void setListaObj(List<Objetivosexternos> listaObj) {
        this.listaObj = listaObj;
    }

    public List<Indicadoresp> getListaI() {
        return port.obtenerI();
    }

    public void setListaI(List<Indicadoresp> listaI) {
        this.listaI = listaI;
    }

    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }

    //////////////////////////////////////////////////////
    public void insertarI() throws DatatypeConfigurationException {
        DateFormat fecha1 = new SimpleDateFormat("dd-MMM-yy");
        convertFecha = fecha1.format(fecha);
        
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(fecha);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);


        port.insertarIndicadores(obj, des,  date2);
        this.limpiar();

    }

    public void eliminarI(String s) {
     
        port.eliminarIndicadores(s);
    }
    
    public void editarIndicador(Indicadoresp indi){
        indicadorId = indi.getIndCodigo();
        obj = indi.getIndObjetivo();
        des = indi.getIndDescripcion();
        fecha = indi.getIndFechai().toGregorianCalendar().getTime();
        
    }

    public String getIndicadorId() {
        return indicadorId;
    }

    public void setIndicadorId(String indicadorId) {
        this.indicadorId = indicadorId;
    }

    
    
    public void modificarI() throws DatatypeConfigurationException {

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(fecha);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
   
        port.modificarIndicadores(indicadorId, obj,des ,date2);
       
    }

    public void limpiar() {
        this.setObj("");
        this.setDes("");
        this.setFecha(null);
    }
}
