/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.rh.client.domain;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import java.util.HashMap;
import java.util.List;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

/**
 *
 * @author juan.calderon
 */
public class DeptoDatabase {

    private static final String  URL_REST = "http://localhost:8080/SigreRHEE/api/departamento";
    
    public static Departamento currentDepto;
    
    /**
     * The singleton instance of the database.
     */
    private static DeptoDatabase instance;

    /**
     * Get the singleton instance of the contact database.
     *
     * @return the singleton instance
     */
    public static DeptoDatabase get() {
        if (instance == null) {
            instance = new DeptoDatabase();
        }
        return instance;
    }

    /**
     * The provider that holds the list of contacts in the database.
     */
    private ListDataProvider<Departamento> dataProvider = new ListDataProvider<Departamento>();

    /**
     * Construct a new contact database.
     */
    private DeptoDatabase() {
        // Generate initial data.
        //generateContacts(250);

        dataProvider = new ListDataProvider<>();
    }

    public void updateDepto(Departamento depto) {
        List<Departamento> deptos = dataProvider.getList();
        int idx = deptos.indexOf(depto);
        if (idx >= 0) {
            Departamento deptoInList = deptos.get(idx);
            deptoInList.setCodigo(depto.getCodigo());
            deptoInList.setNombre(depto.getNombre());
        }
    }
    
    /**
     * Add a new contact.
     *
     * @param depto the DeptoInfo to add.
     */
    public void addDepto(Departamento depto) {

        //Create a PersonJsonizer instance
        Departamento.DepartamentoJsonizer dj = (Departamento.DepartamentoJsonizer)GWT.create(Departamento.DepartamentoJsonizer.class);
        //Jsonize
        String jsonStr = dj.asString(depto);
        jsonStr = "{\"departamento\":{\"nombre\":\"Veracruz\",\"codigo\":\"VE\"}}";
        System.out.println(">>> " + jsonStr);
        
        //JSONString deptoJSON = new JSONString(jsonStr);
        JSONValue deptoJSON = new JSONString(jsonStr);
        
        HashMap<String, String> headers = new HashMap<String, String>();
        //headers.put(Resource.HEADER_ACCEPT, "application/json; charset=utf-8");
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
        
        Resource rb = new Resource(URL_REST, headers);
        
        rb.post().json(deptoJSON).send(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: "+exception);
            }
            @Override
            public void onSuccess(Method method, JSONValue response) {
                System.out.println(response);
            }
        });
        
        
        List<Departamento> deptos = dataProvider.getList();
        deptos.remove(depto); // en caso de que existiera, lo elimina
        System.out.println("Removed? " +  deptos.contains(depto) +  " ::: " + depto);
        deptos.add(depto); // lo agrega
    }

    public void removeDepto(final Departamento depto) {

        List<Departamento> deptos = dataProvider.getList();
        deptos.remove(depto);

//        Resource r = new Resource(URL_REST);
//        r.delete().send(new JsonCallback() {
//            @Override
//            public void onFailure(Method method, Throwable exception) {
//                Window.alert("Error: "+exception);
//            }
//            @Override
//            public void onSuccess(Method method, JSONValue response) {
//                List<Departamento> deptos = dataProvider.getList();
//                deptos.remove(depto);
//            }
//        });
        
    }

    /**
     * Add a display to the database. The current range of interest of the
     * display will be populated with data.
     *
     * @param display a {@Link HasData}.
     */
    public void addDataDisplay(HasData<Departamento> display) {
        dataProvider.addDataDisplay(display);
    }

    public ListDataProvider<Departamento> getDataProvider() {
        return dataProvider;
    }

    /**
     * Refresh all displays.
     */
    public void refreshDisplays() {
        dataProvider.refresh();
    }

    public void load() {
        
        dataProvider.getList().clear();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
        
        Resource rb = new Resource(URL_REST, headers);
        rb.get().send(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert(exception.getLocalizedMessage());
                System.out.println("---Vacio--- ");
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                                
                List<Departamento> deptosProvider = dataProvider.getList();
                
                JSONObject obj = (JSONObject) response;
                JSONArray array = obj.get("departamento").isArray();
                for (int i = 0; i < array.size(); ++i) {
                    JSONObject item = array.get(i).isObject();
                    Integer id = (int) item.get("id").isNumber().doubleValue();
                    String codigo = item.get("codigo").isString().stringValue();
                    String nombre = item.get("nombre").isString().stringValue();
                    Integer status = (int) item.get("status").isNumber().doubleValue();
                    //Integer status = (int) item.get("status").isNumber().doubleValue();
                    Departamento depto = new Departamento(id, codigo, nombre);
                    // Remove the contact first so we don\"t add a duplicate.
                    deptosProvider.remove(depto);
                    deptosProvider.add(depto);
                }
                
            }
        });

    }

}
