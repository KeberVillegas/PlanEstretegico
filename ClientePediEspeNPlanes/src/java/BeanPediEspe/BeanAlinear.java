/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanPediEspe;

import Servidor.Alinearp;
import Servidor.EspePlanService;
import Servidor.Objetivosexternos;
import Servidor.Plannacionalp;
import Servidor.Planpedip;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Kleber
 */
@ManagedBean
@ViewScoped
public class BeanAlinear implements Serializable {

    private EspePlanService service = new EspePlanService();
    Servidor.EspePlan port = service.getEspePlanPort();
    List<Objetivosexternos> objetivosPlanNacional;
    String objetivosPlanNacionalSelect, tiposObjetivosPlanEspeSelect, objetivosXTipoPlanEspeSelect;
    Collection<Planpedip> tiposObjetivosPlanEspe;
    List<Objetivosexternos> objetivosXTipoPlanEspe;
    List<Planpedip> objTipos;
    Objetivosexternos temp;
    List<Arbol> arbol;

    /**
     * Creates a new instance of BeanAlinear
     */
    public BeanAlinear() {
    }

    @PostConstruct
    public void init() {
//        Obtiene los objetivos pertenecientes al Plan Nacional
        objetivosPlanNacional = port.obtenerObj();
        
        List<Plannacionalp> pl = port.obtenerPlanN();
        Iterator it = objetivosPlanNacional.iterator();
        while (it.hasNext()) {
            Objetivosexternos obje = (Objetivosexternos) it.next();
            boolean encontrado = false;

            for (Plannacionalp elem : pl) {
                if ((obje.getObjNombre().equals(elem.getPlanCodigo()))) {
                    encontrado = true;
                    break;
                }
                 if ((obje.getObjNombre().equals(elem.getPlanNombre()))) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                it.remove();
            }
        }
//        Obtiene los tipos de Objetivo del Plan ESPE
        tiposObjetivosPlanEspe = port.obtenerPlan();

//        Tabla con la jerarquia de objetivos
        List<Objetivosexternos> codigoXDescp = port.obtenerObj();
        List<Alinearp> objetivoToObjetivo = port.obtenerAli();
        arbol = new ArrayList<>();

//        objetivos nacionales sin repeticion
        List listaLimpia = new ArrayList();

        Map<String, Alinearp> mapObjetivoToObjetivo = new HashMap<>();

        for (Alinearp x : objetivoToObjetivo) {
            mapObjetivoToObjetivo.put(x.getAliNacional(), x);
        }

        System.out.println("Lista sin repetidos:");
        for (Entry<String, Alinearp> p : mapObjetivoToObjetivo.entrySet()) {
            listaLimpia.add(p.getValue().getAliNacional());
            System.out.println(p.getValue().getAliNacional());
        }

        for (Object listaLimpia1 : listaLimpia) {
            List objetivoNacional = new ArrayList();
            for (Alinearp x : objetivoToObjetivo) {
                if (x.getAliNacional().equals(listaLimpia1.toString())) {
                    objetivoNacional.add(x.getAliEspe());
                }

            }

            Arbol arb = new Arbol((String) listaLimpia1, objetivoNacional);
            arbol.add(arb);
            System.out.println(listaLimpia1.toString());

        }

        //////nombres
        for (Arbol arbol1 : arbol) {

            for (Objetivosexternos codigoXDescp1 : codigoXDescp) {
                if (codigoXDescp1.getObjCodigo().equals(arbol1.padre)) {
                    arbol1.setPadre(codigoXDescp1.getObjTitulo());
                    break;
                }

            }
            List a = new ArrayList<>();
            for (Object arbol2 : arbol1.hijo) {

                for (Objetivosexternos codigoXDescp1 : codigoXDescp) {
                    if (codigoXDescp1.getObjCodigo().equals(arbol2.toString())) {
                        a.add(codigoXDescp1.getObjTitulo());
                        break;
                    }
                }
                arbol1.setHijo(a);
            }
        }
    }

    public void obtenerDescripcion(){
        
        for(Objetivosexternos m : this.objetivosPlanNacional){
            if(m.getObjCodigo().equals(objetivosPlanNacionalSelect)){
                temp = m;
                break;
            }
        }
        
    }
    
    public void guardarObjetivoObjetivo() {
        port.insertarAlinear(objetivosXTipoPlanEspeSelect, objetivosPlanNacionalSelect);
        this.init();
        limpiar();
    }

    public Objetivosexternos getTemp() {
        return temp;
    }

    public void setTemp(Objetivosexternos temp) {
        this.temp = temp;
    }

    
    
    public void limpiar() {
        this.setTiposObjetivosPlanEspeSelect("");
        this.setObjetivosPlanNacionalSelect("");
        this.setObjetivosXTipoPlanEspeSelect("");

    }

    public void obtenerTipoObjetivoPlanEspe() {
        
        System.out.println("codigos plan estrategico25, plan operacional27 "+tiposObjetivosPlanEspeSelect);
        
        objetivosXTipoPlanEspe = port.obtenerObj();

        Iterator it = objetivosXTipoPlanEspe.iterator();

        while (it.hasNext()) {

            Objetivosexternos obje = (Objetivosexternos) it.next();
            boolean encontrado = false;
           // System.out.print("combo "+obje.getObjNombre());

            if (obje.getObjNombre().equals(tiposObjetivosPlanEspeSelect)) {
                encontrado = true;
                //break;
            }

            if (!encontrado) {

                it.remove();
            }
        }

    }

    public String getObjetivosXTipoPlanEspeSelect() {
        return objetivosXTipoPlanEspeSelect;
    }

    public void setObjetivosXTipoPlanEspeSelect(String objetivosXTipoPlanEspeSelect) {
        this.objetivosXTipoPlanEspeSelect = objetivosXTipoPlanEspeSelect;
    }

    public List<Objetivosexternos> getObjetivosXTipoPlanEspe() {
            return objetivosXTipoPlanEspe;
        //return port.obtenerObj();
    }

    public void setObjetivosXTipoPlanEspe(List<Objetivosexternos> objetivosXTipoPlanEspe) {
        this.objetivosXTipoPlanEspe = objetivosXTipoPlanEspe;
    }

    public String getObjetivosPlanNacionalSelect() {
        return objetivosPlanNacionalSelect;
    }

    public void setObjetivosPlanNacionalSelect(String objetivosPlanNacionalSelect) {
        this.objetivosPlanNacionalSelect = objetivosPlanNacionalSelect;
    }

    public List<Objetivosexternos> getObjetivosPlanNacional() {
        return objetivosPlanNacional;
    }

    public void setObjetivosPlanNacional(List<Objetivosexternos> objetivosPlanNacional) {
        this.objetivosPlanNacional = objetivosPlanNacional;
    }

    public Collection<Planpedip> getTiposObjetivosPlanEspe() {
        return tiposObjetivosPlanEspe;
    }

    public void setTiposObjetivosPlanEspe(Collection<Planpedip> tiposObjetivosPlanEspe) {
        this.tiposObjetivosPlanEspe = tiposObjetivosPlanEspe;
    }

    public String getTiposObjetivosPlanEspeSelect() {
        return tiposObjetivosPlanEspeSelect;
    }

    public void setTiposObjetivosPlanEspeSelect(String tiposObjetivosPlanEspeSelect) {
        this.tiposObjetivosPlanEspeSelect = tiposObjetivosPlanEspeSelect;
    }

    public List<Arbol> getArbol() {
        return arbol;
    }

    public void setArbol(List<Arbol> arbol) {
        this.arbol = arbol;
    }

}
