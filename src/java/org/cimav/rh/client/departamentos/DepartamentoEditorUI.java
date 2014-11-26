/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.rh.client.departamentos;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import org.cimav.rh.client.domain.Departamento;
import org.cimav.rh.client.domain.DeptoDatabase;

/**
 *
 * @author juan.calderon
 */
public final class DepartamentoEditorUI extends Composite {

    private static DepartamentoEditorUIUiBinder uiBinder = GWT.create(DepartamentoEditorUIUiBinder.class);

    interface DepartamentoEditorUIUiBinder extends UiBinder<Widget, DepartamentoEditorUI> {
    }

    @UiField HTMLPanel mainPanel;
    @UiField FlexTable editor;
    @UiField Button btnGuardar;
    @UiField Button btnCancelar;

    
    com.google.gwt.user.client.ui.TextBox txtCodigo;
    com.github.gwtbootstrap.client.ui.TextBox txtNombre;
//    com.github.gwtbootstrap.client.ui.TextArea txt3;

    public DepartamentoEditorUI() {
        initWidget(uiBinder.createAndBindUi(this));

// Create a table to layout the form options
        //FlexTable layout = new FlexTable();
        editor.setCellSpacing(6);
        FlexCellFormatter cellFormatter = editor.getFlexCellFormatter();

//        // Add a title to the form
//        layout.setHTML(0, 0, "Titulo Enorme que avance varios lugares.");
//        cellFormatter.setColSpan(0, 0, 2);
//        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        editor.getColumnFormatter().setWidth(0, "50px;");

        txtCodigo = new com.google.gwt.user.client.ui.TextBox();
        txtCodigo.setWidth("50px");
        txtCodigo.setVisibleLength(8);
        txtNombre = new com.github.gwtbootstrap.client.ui.TextBox();
        txtNombre.setWidth("500px");
        txtNombre.setVisibleLength(200);
//        txt3 = new com.github.gwtbootstrap.client.ui.TextArea();
//        txt3.setWidth("575px");

        // Add some standard form options
        // r, c, obj
        editor.setHTML(0, 0, "Código");
        editor.setWidget(1, 0, txtCodigo);
        editor.setHTML(0, 1, "Nombre");
        editor.setWidget(1, 1, txtNombre);
//        editor.setHTML(2, 0, "Descripción");
//        cellFormatter.setColSpan(2, 0, 2);
//        cellFormatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
//        editor.setWidget(3, 0, txt3);
//        cellFormatter.setColSpan(3, 0, 2);

        // De inicio, poner en nulo
        this.setDepartamento(null);
        
        
        btnGuardar.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // TODO Validar campos
                Departamento depto = new Departamento();
                depto.setCodigo(txtCodigo.getText());
                depto.setNombre(txtNombre.getText());
                
                if (currentDeptoId <= 0)  {
                    // Nuevo Depto
                    DeptoDatabase.get().addDepto(depto);
                } else {
                    // Actualizar Depto
                    DeptoDatabase.get().updateDepto(depto);
                }
            }
        });
        
    }
    
    //TODO Falta notificar si es nuevo o edición
    
    private int currentDeptoId = 0;
    
    public void setDepartamento(Departamento departamento) {
        currentDeptoId = 0; //<< 0
        boolean isNotNull = departamento != null;
        if (isNotNull) {
            txtCodigo.setText(departamento.getCodigo());
            txtNombre.setText(departamento.getNombre());
            currentDeptoId = null == departamento.getId() ? 0 : departamento.getId(); // si el Id es null, lo pone en 0.
        } else {
            txtCodigo.setText("");
            txtNombre.setText("");
        }
        
        txtCodigo.setEnabled(isNotNull);
        txtNombre.setEnabled(isNotNull);
        btnGuardar.setEnabled(isNotNull);
        btnCancelar.setEnabled(isNotNull);
    }
}
