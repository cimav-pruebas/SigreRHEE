/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.rh.client.domain;

/**
 *
 * @author juan.calderon
 */
public enum Status {

    ADDING(1),
    EDITING(2),
    REMOVING(3),
    RELOADING(5),
    UNSELECTED(4);
    
    private int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
