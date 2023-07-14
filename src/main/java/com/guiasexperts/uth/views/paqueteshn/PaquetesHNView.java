package com.guiasexperts.uth.views.paqueteshn;

import com.guiasexperts.uth.data.cotroller.CustomerInteractor;
import com.guiasexperts.uth.data.cotroller.PaquetesInteractor;
import com.guiasexperts.uth.data.cotroller.PaquetesInteractorImpl;
import com.guiasexperts.uth.data.entity.Paquetes;
import com.guiasexperts.uth.data.service.PaquetesService;
import com.guiasexperts.uth.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Paquetes HN")
@Route(value = "paquetes-HN/:paquetesID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class PaquetesHNView extends Div implements BeforeEnterObserver,paquetesViewModel {

    private final String PAQUETES_ID = "paquetesID";
    private final String PAQUETES_EDIT_ROUTE_TEMPLATE = "paquetes-HN/%s/edit";

    private final Grid<Paquetes> grid = new Grid<>(Paquetes.class, false);

    private TextField destino;
    private DatePicker duracion;
    private DateTimePicker alojamiento;
    private TextField precio;
    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

  //  private final BeanValidationBinder<Paquetes> binder;

    private Paquetes paquete;
    private PaquetesInteractor controlador;
    private PaquetesService paquetesService;

    public PaquetesHNView(PaquetesService paquetesService) {
    	new ArrayList<>();
    	 this.controlador = new PaquetesInteractorImpl (this);
    	 
    	 
        addClassNames("paquetes-hn-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("destino").setAutoWidth(true);
        grid.addColumn("duracion").setAutoWidth(true);
        grid.addColumn("alojamiento").setAutoWidth(true);
        grid.addColumn("precio").setAutoWidth(true);
     //   grid.setItems(query -> paquetesService.list(
          //      PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
            //    .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PAQUETES_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(PaquetesHNView.class);
            }
        });
        
        //AQUI MANDO A TRAER LOS EMPLEADOS DE EL REPOSITORIO
        this.controlador.consultarPaquetes();
        
      

        // Configure Form
     //   binder = new BeanValidationBinder<>(Paquetes.class);

        // Bind fields. This is where you'd define e.g. validation rules
      //  binder.forField(precio).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("precio");

       // binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.paquete == null) {
                    this.paquete = new Paquetes();
                }
                //binder.writeBean(this.paquetes);
               // paquetesService.update(this.paquetes);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(PaquetesHNView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> paquetesId = event.getRouteParameters().get(PAQUETES_ID).map(Long::parseLong);
        if (paquetesId.isPresent()) {
          /*  Optional<Paquetes> paquetesFromBackend = paquetesService.get(paquetesId.get());
            if (paquetesFromBackend.isPresent()) {
                populateForm(paquetesFromBackend.get());
            } else {
                Notification.show(String.format("The requested paquetes was not found, ID = %s", paquetesId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(PaquetesHNView.class);
            }*/
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        destino = new TextField("Destino");
        duracion = new DatePicker("Duracion");
        alojamiento = new DateTimePicker("Alojamiento");
        alojamiento.setStep(Duration.ofSeconds(1));
        precio = new TextField("Precio");
        formLayout.add(destino, duracion, alojamiento, precio);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Paquetes value) {
    
       // binder.readBean((Paquetes) this.paquetes);
        this.paquete = value;
    }

	
		
	

	@Override
	public void refrescarGridPaquetes(List<Paquetes> Paquetes) {
		Collection<Paquetes> items = Paquetes;
		grid.setItems(items);
		this.paquete = paquete;
		
	}





}
		
	


	

    
