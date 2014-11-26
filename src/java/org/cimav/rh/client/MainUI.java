/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.rh.client;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavList;
import com.github.gwtbootstrap.client.ui.base.IconAnchor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author juan.calderon
 */
public class MainUI extends Composite {

    //static EventBus eventBus = GWT.create(SimpleEventBus.class); 
    
    private static final MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

    interface MainUiBinder extends UiBinder<Widget, MainUI> {
    }

    public static final String OPT_NONE = "optionNone";
    public static final String OPT_PERSONAL = "optionPersonal";
    public static final String OPT_DEPARTAMENTOS = "optionDepartamentos";
    public static final String OPT_TABULADOR = "optionTabulador";
    
    @UiField
    StackLayoutPanel westPanel;
    @UiField
    FlowPanel workPanel;
    @UiField
    Label lTitulo;
    @UiField
    Label lSubTitulo;

    public void setTit(String t) {
        lSubTitulo.setText(t);
    }
    
    @UiHandler({"optionUno", "optionDos", "optionTres", OPT_PERSONAL, OPT_DEPARTAMENTOS, OPT_TABULADOR})
    protected void onClick(ClickEvent e) {

        Iterator<Widget> arrayOfWidgets = westPanel.iterator();
        while (arrayOfWidgets.hasNext()) {
            Widget widgetNavList = arrayOfWidgets.next();
            if (widgetNavList instanceof NavList) {
                NavList navList = (NavList) widgetNavList;
                for (int i = 0; i < navList.getWidgetCount(); i++) {
                    Widget widgetNavLink = navList.getWidget(i);
                    if (widgetNavLink instanceof NavLink) {
                        NavLink navLink = (NavLink) widgetNavLink;
                        navLink.setActive(false);
                    }
                }
            }
        }
        IconAnchor src = (IconAnchor) e.getSource();
        NavLink navLink = (NavLink) src.getParent();
        navLink.setActive(true);
        
        onOptionChange(navLink.getName());
        if (OPT_PERSONAL.equals(navLink.getName())) {
        } else {
        }
    }

    public MainUI() {
        initWidget(uiBinder.createAndBindUi(this));

        westPanel.getElement().setAttribute("id", "west-panel");
    }

    public void setCenterPanel(String titulo, String subTitulo, Widget widget) {
        workPanel.clear(); // remover lo anterior
        
        lTitulo.setText(titulo);
        lSubTitulo.setText(subTitulo);
        
        if (widget != null) {
            workPanel.add(widget); // agregar el enviado
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="interface OptionChangeListener"> 
    public interface OptionChangeListener extends java.util.EventListener {
        void onOptionChange(String option);
    }
    private final ArrayList listeners = new ArrayList();
    public void addOptionChangeListener(OptionChangeListener listener) {
        listeners.add(listener);
    }
    public void removeOptionChangeListener(OptionChangeListener listener) {
        listeners.remove(listener);
    }
    public void onOptionChange(String option) {
        for(Iterator it = listeners.iterator(); it.hasNext();) {
            OptionChangeListener listener = (OptionChangeListener) it.next();
            listener.onOptionChange(option);
        }
    }
    // </editor-fold>

}

