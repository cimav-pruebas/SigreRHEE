/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.rh.client.departamentos;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import java.util.Comparator;
import org.cimav.rh.client.domain.Departamento;
import org.cimav.rh.client.domain.DeptoDatabase;

/**
 *
 * @author juan.calderon
 */
public class DepartamentosUI extends Composite {

    private static DepartamentosUIUiBinder uiBinder = GWT.create(DepartamentosUIUiBinder.class);

    interface DepartamentosUIUiBinder extends UiBinder<Widget, DepartamentosUI> {
    }

    /**
     * The main DataGrid.
     */
    @UiField(provided = true) 
    DataGrid<Departamento> dataGrid;
    
    @UiField TabLayoutPanel tabLayout;
    @UiField DepartamentoEditorUI departamentoEditorUI;
    
    @UiField Button btnReload;
    @UiField Button btnAdd;
    @UiField Button btnEditar;
    @UiField Button btnEliminar;
    
    //private Departamento currentDepto;
    
    public DepartamentosUI() {

        buildGrid();

        initWidget(uiBinder.createAndBindUi(this));
        
        // 1era carga de Datos
        DeptoDatabase.get().load();
        // de inicio, poner en Nulo
        this.clearSelection();
        this.updateWidgets();
        
        tabLayout.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem() == 0) {
                    // Tab del Grid
                    
                    // DataGrid es un Widget tipo RequireSize; por lo tanto debe ser hijo de un widget tipo ProvidesResize. 
                    // De lo contrario se "desaparece" al no poder ajustar a los cambios de tamaño.
                    dataGrid.setWidth("100%");
                    dataGrid.setHeight("100%");
                    dataGrid.redraw();

                } else if (event.getSelectedItem() == 1) {
                    // Tab del Editor
                }
            }
        });
        
        btnReload.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clearSelection();
                DeptoDatabase.get().load();
            }
        });
        btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                
                // el nuevo debe ser id < 0 y garantizar que si el anterior tambien es nuevo, lo sustituya
                int idNegativo = DeptoDatabase.currentDepto != null && DeptoDatabase.currentDepto.getId() != null && DeptoDatabase.currentDepto.getId() <= 0 
                        ? DeptoDatabase.currentDepto.getId() - 1 : -1;
                
                // crear nuevo Departamento
                Departamento nuevoDepto = new Departamento();
                // con -1 para indicar que es nuevo
                nuevoDepto.setId(idNegativo);
                nuevoDepto.setCodigo("NN");
                DeptoDatabase.currentDepto = nuevoDepto;
                
                // en vez del clearSelection
                dataGrid.getSelectionModel().setSelected(DeptoDatabase.currentDepto, true); 
                // dispara el OnSelect (si cambia dado que )
                //clearSelection();
                
                // BUG TODO: En agregar y con el tabLayout.selectTab(1), se "esconde" el contenido del grid
                tabLayout.selectTab(1);
            }
        });
        btnEditar.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // simple selecciona el tab del Editor
                tabLayout.selectTab(1);
            }
        });
        btnEliminar.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                
                // TODO Falta msg de autorización de remove

                // keep el depto a remove
                Departamento deptoToRemove = DeptoDatabase.currentDepto;
                // limpia la selección
                clearSelection();
                updateWidgets();
                // elimina el depto a remove
                DeptoDatabase.get().removeDepto(deptoToRemove);
            }
        });
        
    }

    private void buildGrid() {
        // super.onLoad(); //To change body of generated methods, choose Tools | Templates.

        /*
         * Set a key provider that provides a unique key for each contact. If key is
         * used to identify contacts when fields (such as the name and address)
         * change.
         */
        dataGrid = new DataGrid<>(Departamento.KEY_PROVIDER);
        dataGrid.setWidth("100%");
        dataGrid.setHeight("100%");

        /*
         * Do not refresh the headers every time the data is updated. The footer
         * depends on the current data, so we do not disable auto refresh on the
         * footer.
         */
        dataGrid.setAutoHeaderRefreshDisabled(true);

        // Set the message to display when the table is empty.
        dataGrid.setEmptyTableWidget(new Label("No hay departamentos"));

        // Attach a column sort handler to the ListDataProvider to sort the list.
         ListHandler<Departamento> sortHandler = new ListHandler<>(DeptoDatabase.get().getDataProvider().getList());
        dataGrid.addColumnSortHandler(sortHandler);
        
        // Add a selection model so we can select cells.
        final SelectionModel<Departamento> selectionModel = new SingleSelectionModel<>(Departamento.KEY_PROVIDER);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                //System.out.println("123> " + event.getSource() + " - " + event.getAssociatedType());
                if (event.getSource() instanceof SingleSelectionModel) {
                    
                    SingleSelectionModel selecter = (SingleSelectionModel) event.getSource();
                    Departamento sel = (Departamento) selecter.getSelectedObject();

                    // Seleccion actual
                    DeptoDatabase.currentDepto = sel;
                    
                    System.out.println("Depto.Id --> " + DeptoDatabase.currentDepto);
                    
                    departamentoEditorUI.setDepartamento(DeptoDatabase.currentDepto);
                    
                    // Actualizar botones
                    updateWidgets();                    
                }
            }
        });
        dataGrid.setSelectionModel(selectionModel);
        
        initTableColumns(sortHandler);

        // Add the CellList to the adapter in the database.
        DeptoDatabase.get().addDataDisplay(dataGrid);
        
    }

    /**
     * Add the columns to the table.
     */
    private void initTableColumns(ListHandler<Departamento> sortHandler) {

        // ID
        Column<Departamento, String> idCol
                = new Column<Departamento, String>(new TextCell()) {
                    @Override
                    public String getValue(Departamento object) {
                        return object.getId().toString();
                    }
                };
        dataGrid.addColumn(idCol, "ID");
        dataGrid.setColumnWidth(idCol, 20, Unit.PCT);

        // Clave
        Column<Departamento, String> codigoCol = new Column<Departamento, String>((new TextCell())) {
            @Override
            public String getValue(Departamento object) {
                return object.getCodigo();
            }
        };
        codigoCol.setSortable(true);
        sortHandler.setComparator(codigoCol, new Comparator<Departamento>() {
            @Override
            public int compare(Departamento o1, Departamento o2) {
                return o1.getCodigo().compareTo(o2.getCodigo());
            }
        });
        dataGrid.addColumn(codigoCol, "Código");
        dataGrid.setColumnWidth(codigoCol, 15, Unit.PCT);
        
        // Nombre
        Column<Departamento, String> nombreCol
                = new Column<Departamento, String>(new TextCell()) {
                    @Override
                    public String getValue(Departamento object) {
                        return object.getNombre();
                    }
                };
        nombreCol.setSortable(true);
        sortHandler.setComparator(nombreCol, new Comparator<Departamento>() {
            @Override
            public int compare(Departamento o1, Departamento o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });
        dataGrid.addColumn(nombreCol, "Nombre");
        dataGrid.setColumnWidth(nombreCol, 60, Unit.PCT);
        
    }

    private void clearSelection() {
        if (DeptoDatabase.currentDepto != null && dataGrid.getSelectionModel().isSelected(DeptoDatabase.currentDepto)) {
            dataGrid.getSelectionModel().setSelected(DeptoDatabase.currentDepto, false); 
        }
        DeptoDatabase.currentDepto = null; 
    }
    private void updateWidgets() {
        boolean isNotNull = DeptoDatabase.currentDepto != null;
        btnEditar.setEnabled(isNotNull);
        btnEliminar.setEnabled(isNotNull);
    }
}
