/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.rh.client.domain;

//import org.codehaus.jackson.annotate.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gwt.view.client.ProvidesKey;

/**
 *
 * @author juan.calderon
 */
public class Departamento implements Comparable<Departamento> {

        /**
         * The key provider that provides the unique ID of a contact.
         */
        public static final ProvidesKey<Departamento> KEY_PROVIDER = new ProvidesKey<Departamento>() {
            @Override
            public Object getKey(Departamento item) {
                Object r = item == null ? null : item.getId();
                return r;
            }
        };

        @Override
        public int compareTo(Departamento item) {
            //TODO compareTo(DeptoInfo item) hacerlo bien
            int r = (item == null || item.nombre == null) ? -1 : -item.nombre.compareTo(nombre);
            return r;
        }

        private Integer id;
        private String codigo;
        private String nombre;

        public Departamento() {
        }
        
        public Departamento(@JsonProperty("id") Integer id, @JsonProperty("codigo") String codigo, @JsonProperty("nombre") String nombre) {
            this.id = id;
            this.codigo = codigo;
            this.nombre = nombre;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Departamento)) {
                return false;
            }
            Departamento other = (Departamento) obj;
            if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
                return false;
            }
            return false;
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 89 * hash + this.id;
            return hash;
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
        
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

    @Override
    public String toString() {
        return "Departamento{" + "id=" + id + ", codigo=" + codigo + '}';
    }
        
        
    
}
