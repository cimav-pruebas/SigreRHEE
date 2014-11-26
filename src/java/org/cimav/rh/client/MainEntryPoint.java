/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.rh.client;

import org.cimav.rh.client.departamentos.DepartamentosUI;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import javax.validation.constraints.NotNull;


import org.fusesource.restygwt.client.Defaults;

/**
 * Main entry point.
 *
 * @author juan.calderon
 */
public class MainEntryPoint implements EntryPoint {

    private final MainUI mainUi;

    /**
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
        mainUi = new MainUI();
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {

        Defaults.setServiceRoot(GWT.getHostPageBaseURL());

        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(@NotNull Throwable e) {
                ensureNotUmbrellaError(e);
            }
        });

        //RootPanel.get("gwtContainer").add(mainUiBinder);
        RootLayoutPanel.get().add(mainUi);

        mainUi.addOptionChangeListener(new MainUI.OptionChangeListener() {

            @Override
            public void onOptionChange(String option) {
                if (option.equals(MainUI.OPT_PERSONAL)) {
                    mainUi.setCenterPanel("Personal", "Consultas, altas, bajas y cambios", null);
                } else if (option.equals(MainUI.OPT_DEPARTAMENTOS)) {
                    mainUi.setCenterPanel("Departamentos", "Consultas, altas, bajas y cambios", new DepartamentosUI());
                } else {
                    mainUi.setCenterPanel(option, "Not Yet Implemented...", null);
                }

            }
        });


    }

    private static void ensureNotUmbrellaError(@NotNull Throwable e) {
        for (Throwable th : ((UmbrellaException) e).getCauses()) {
            if (th instanceof UmbrellaException) {
                ensureNotUmbrellaError(th);
            } else {
                System.err.println(th);
            }
        }
    }

}
