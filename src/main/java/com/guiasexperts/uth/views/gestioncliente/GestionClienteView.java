package com.guiasexperts.uth.views.gestioncliente;

import com.guiasexperts.uth.data.cotroller.CustomerInteractor;
import com.guiasexperts.uth.data.cotroller.CustomerInteractorImpl;
import com.guiasexperts.uth.data.entity.Clientes;
import com.guiasexperts.uth.data.entity.Paquetes;
import com.guiasexperts.uth.data.service.ClientesService;
import com.guiasexperts.uth.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import jakarta.annotation.security.RolesAllowed;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Gestion Cliente")
@Route(value = "gestion-cliente/:clientesID?/:action?(edit)", layout = MainLayout.class)

@RolesAllowed("ADMIN")
public class GestionClienteView extends Div implements BeforeEnterObserver, customerViewModel {

    private final String CLIENTES_ID = "clientesID";
    private final String CLIENTES_EDIT_ROUTE_TEMPLATE = "gestion-cliente/%s/edit";

    private final Grid<Clientes> grid = new Grid<>(Clientes.class, false);

    private TextField nombre;
    private TextField edad;
    private TextField telefono;
    private TextField direccion;
    private List<Clientes> Cliente;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

    private final BeanValidationBinder<Clientes> binder;

    private Clientes clientes;
    
 
    private  ClientesService clientesService;
    private CustomerInteractor controlador;

    public  GestionClienteView(ClientesService clientesService) {
    	
    	 Cliente = new ArrayList<>();
    	 this.controlador = new CustomerInteractorImpl(this);
        addClassNames("gestion-cliente-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
        
        // Configure Grid
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("edad").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("direccion").setAutoWidth(true);
       // grid.setItems(query -> clientesService.list(
            //    PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
             //   .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(CLIENTES_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(GestionClienteView.class);
            }
        });
        //AQUI MANDO A TRAER LOS EMPLEADOS DE EL REPOSITORIO
        this.controlador.consultarClientes();
        // Configure Form
        binder = new BeanValidationBinder<>(Clientes.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(edad).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("edad");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.clientes == null) {
                    this.clientes = new Clientes();
                }
                binder.writeBean(this.clientes);
                clientesService.update(this.clientes);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(GestionClienteView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> clientesId = event.getRouteParameters().get(CLIENTES_ID).map(Long::parseLong);
        if (clientesId.isPresent()) {
            Optional<Clientes> clientesFromBackend = clientesService.get(clientesId.get());
            if (clientesFromBackend.isPresent()) {
                populateForm(clientesFromBackend.get());
            } else {
                Notification.show(String.format("The requested clientes was not found, ID = %s", clientesId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(GestionClienteView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nombre = new TextField("Nombre");
        edad = new TextField("Edad");
        telefono = new TextField("Telefono");
        direccion = new TextField("Direccion");
        formLayout.add(nombre, edad, telefono, direccion);

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

    private void populateForm(Clientes value) {
        this.clientes = value;
        binder.readBean(this.clientes);

    }

    @Override
	public void refrescarGridClientes(List<Clientes> Clientes) {
		Collection<Clientes> items = Clientes;
		grid.setItems(items);
		this.clientes = clientes;
		
	}


		
	}


