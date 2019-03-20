package BeanPediEspe;

import Servidor.EspePlanService;
import Servidor.Plannacionalp;
import Servidor.Planpedip;
import Servidor.Tipoplan;
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
public class BeanPlanEspe implements Serializable {

    private EspePlanService service = new EspePlanService();
    Servidor.EspePlan port = service.getEspePlanPort();

    ///////////////
    List<Tipoplan> listap;
    List<Planpedip> listac;
    String codigo;
    String titulo;
    String descripcion;
    String mision;
    String vision;
    String convert;
    String convert1;
    Date fechaI;
    Date fechaF;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

     String tipoPlan;
    Planpedip plan = new Planpedip();

    public List<Tipoplan> getListap() {
        return port.obtenerTipoPlan();
    }

    public void setListap(List<Tipoplan> listap) {
        this.listap = listap;
    }

    public List<Planpedip> getListac() {
        return port.obtenerPlan();
    }

    public void setListac(List<Planpedip> listac) {
        this.listac = listac;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public Planpedip getPlan() {
        return plan;
    }

    public void setPlan(Planpedip plan) {
        this.plan = plan;
    }

    /////////////
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }
///////////////////////////////////
      public void editarPlan(Planpedip indi){
          
        
                  
        codigo = indi.getPlaCodigo();
        tipoPlan=indi.getPlaTipo();
     
        titulo = indi.getPlaNombre();
        descripcion= indi.getPlaDescripcion();
        mision=indi.getPlaMision();
        vision=indi.getPlaVision();
        fechaI = indi.getPlaFechai().toGregorianCalendar().getTime();
        fechaF = indi.getPlaFechaf().toGregorianCalendar().getTime();
        
    }
    //////INSERTAR PLAN///
    public void insertarPlan() throws DatatypeConfigurationException {
//        System.out.println("hola jj");
//        DateFormat fecha1 = new SimpleDateFormat("dd-MMM-yy");
//        convert = fecha1.format(fechaI);
//        convert1 = fecha1.format(fechaF);
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(fechaI);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        
        
        GregorianCalendar d = new GregorianCalendar();
        d.setTime(fechaF);
        XMLGregorianCalendar date1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        
        port.insertar(tipoPlan, titulo, descripcion, mision, vision, date2, date1);
        this.limpiar();
    }

    ///////////////////
    public void eliminarPlan(String s) {
        port.eliminarPlan(s);
    }

    ////////////////////
    public void modificarPlan() throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(fechaI);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        
        GregorianCalendar d = new GregorianCalendar();
        d.setTime(fechaF);
        XMLGregorianCalendar date1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        
        port.modificarPlan(codigo, tipoPlan,titulo, descripcion, vision, mision, date2, date1);
        
    
    }

////////////////////   
    public void limpiar() {
        this.setTipoPlan("");
        this.setTitulo("");
        this.setDescripcion("");
        this.setMision("");
        this.setVision("");
        this.setFechaI(null);
        this.setFechaF(null);
    }
}
