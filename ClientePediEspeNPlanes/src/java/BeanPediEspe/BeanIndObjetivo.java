/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanPediEspe;

import Servidor.EspePlan;
import Servidor.EspePlanService;
import Servidor.Indicadoresp;
import Servidor.Objetivoind;
import Servidor.Objetivosexternos;
import Servidor.Plannacionalp;
import Servidor.Planpedip;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Kleber
 */
@ManagedBean
@ViewScoped
public class BeanIndObjetivo implements Serializable {

    private EspePlanService service = new EspePlanService();
    Servidor.EspePlan port = service.getEspePlanPort();

    String ind;
    String obj;
    String nombrePlan;
    int por;
    String codigo;
    List<Objetivosexternos> listaObj;
    List<Indicadoresp> listaI;
    List<Objetivoind> listaOI;
    Objetivosexternos objSelec;
    Indicadoresp indSelec = new Indicadoresp();
    List<Objetivoind> tblRelac;
    Objetivoind obj1 = new Objetivoind();

    public Objetivoind getObj1() {
        return obj1;
    }

    public void setObj1(Objetivoind obj1) {
        this.obj1 = obj1;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public EspePlanService getService() {
        return service;
    }

    public List<Objetivoind> getTblRelac() {
        return tblRelac;
    }

    public void setTblRelac(List<Objetivoind> tblRelac) {
        this.tblRelac = tblRelac;
    }

    public void setService(EspePlanService service) {
        this.service = service;
    }

    public EspePlan getPort() {
        return port;
    }

    public void setPort(EspePlan port) {
        this.port = port;
    }

    public List<Objetivoind> getListaOI() {
        return listaOI;
    }

    public void setListaOI(List<Objetivoind> listaOI) {
        this.listaOI = listaOI;
    }

    public String getInd() {
        return ind;
    }

    @PostConstruct
    public void init() {

        listaObj = port.obtenerObj();
        listaI = port.obtenerI();
        listaOI = port.obtenerIndicadiresObj();
        tblRelac = new ArrayList<Objetivoind>();

        for (Objetivoind tblRelac1 : listaOI) {
            Objetivoind objIndicador = new Objetivoind();

            for (Indicadoresp listaI1 : listaI) {
                if (listaI1.getIndCodigo().equals(tblRelac1.getObjiIndicador())) {
                    objIndicador.setObjiIndicador(listaI1.getIndDescripcion());

                    break;
                }
            }

            for (Objetivosexternos listaO1 : listaObj) {
                if (listaO1.getObjCodigo().equals(tblRelac1.getObjiObjetivo())) {

                    objIndicador.setObjiObjetivo(listaO1.getObjDescripcion());

                    break;
                }
            }
            objIndicador.setObjiPorcentaje(tblRelac1.getObjiPorcentaje());
            objIndicador.setObjiCodigo(tblRelac1.getObjiCodigo());
            objIndicador.setObjiNomplan(tblRelac1.getObjiNomplan());
            objIndicador.setObjiIndicador(tblRelac1.getObjiIndicador());
            objIndicador.setObjiObjetivo(tblRelac1.getObjiObjetivo());
            tblRelac.add(objIndicador);

        }

        List<Plannacionalp> listaN = port.obtenerPlanN();
        List<Planpedip> listac = port.obtenerPlan();
        List<Objetivosexternos> listap = port.obtenerObj();
        List<Indicadoresp> listai = port.obtenerI();

        for (Objetivoind tblRelac1 : tblRelac) {

            for (Planpedip elem : listac) {
                if (elem.getPlaCodigo().equals(tblRelac1.getObjiNomplan())) {
                    tblRelac1.setObjiNomplan(elem.getPlaNombre());
                    break;
                }

            }
            for (Plannacionalp c1 : listaN) {
                if (c1.getPlanCodigo().equals(tblRelac1.getObjiNomplan())) {;
                    tblRelac1.setObjiNomplan(c1.getPlanNombre());
                    break;
                }
            }
            for (Objetivosexternos c3 : listap) {
                if (c3.getObjCodigo().equals(tblRelac1.getObjiObjetivo())) {;
                    tblRelac1.setObjiObjetivo(c3.getObjDescripcion());
                    break;
                }
            }
            for (Indicadoresp c4 : listai) {
                if (c4.getIndCodigo().equals(tblRelac1.getObjiIndicador())) {;
                    tblRelac1.setObjiIndicador(c4.getIndDescripcion());
                    break;
                }
            }
        }
    }

    public List<Objetivosexternos> getListaObj() {
        return listaObj;
    }

    public void setListaObj(List<Objetivosexternos> listaObj) {
        this.listaObj = listaObj;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public int getPor() {
        return por;
    }

    public void setPor(int por) {
        this.por = por;
    }

    public List<Indicadoresp> getListaI() {
        return listaI;
    }

    public void setListaI(List<Indicadoresp> listaI) {
        this.listaI = listaI;
    }

    public void limpiar() {
        this.setObj("");
        this.setInd("");
        this.setPor(0);
    }

    public void insertarObjInd() {
        port.insertarIndObj(ind, obj, objSelec.getObjNombre(), por);
        this.limpiar();

    }

    public void obtenerDescrp() {
        System.out.println(obj);
        for (Objetivosexternos s : this.listaObj) {
            System.out.println(s.getObjDescripcion());
            if (s.getObjCodigo().equals(obj)) {
                objSelec = s;

            }
        }
        List<Plannacionalp> listaN = port.obtenerPlanN();
        List<Planpedip> listac = port.obtenerPlan();

        for (Planpedip elem : listac) {
            if (elem.getPlaCodigo().equals(objSelec.getObjNombre())) {
                objSelec.setObjNombre(elem.getPlaNombre());
                break;
            }

        }
        for (Plannacionalp c1 : listaN) {
            if (c1.getPlanCodigo().equals(objSelec.getObjNombre())) {;
                objSelec.setObjNombre(c1.getPlanNombre());
                break;
            }
        }

    }

    public void obtenerDesInd() {
        System.out.println("555555" + ind);
        for (Indicadoresp p : this.listaI) {
            System.out.println("hola mundo" + p.getIndDescripcion());
            if (p.getIndCodigo().equals(ind)) {
                indSelec = p;
            }
        }
    }

    public Objetivosexternos getObjSelec() {
        return objSelec;
    }

    public void setObjSelec(Objetivosexternos objSelec) {
        this.objSelec = objSelec;
    }

    public Indicadoresp getIndSelec() {
        return indSelec;
    }

    public void setIndSelec(Indicadoresp indSelec) {
        this.indSelec = indSelec;
    }

    public void eliminar(String s) {
        port.eliminarOI(s);
        this.init();

        System.out.println("se elimijo");
    }

    public void editarOI(Objetivoind indi) {
        codigo = indi.getObjiCodigo();
//        ind = indi.getObjiIndicador();
//        obj = indi.getObjiObjetivo();
//        nombrePlan = indi.getObjiNomplan();
        por = indi.getObjiPorcentaje();

    }

    public void modificarOI() {
        // port.modificarIndObj(codigo, ind, obj, objSelec.getObjNombre(), por);\
        port.modificarIndObj(codigo, por);
        this.init();
        //tblRelac = port.obtenerIndicadiresObj();
    }

}
