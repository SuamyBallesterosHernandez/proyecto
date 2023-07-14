package com.guiasexperts.uth.views.gestionreserva;

import com.guiasexperts.uth.data.entity.SampleAddress;
import com.guiasexperts.uth.data.service.SampleAddressService;
import com.guiasexperts.uth.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Gestion Reserva")
@Route(value = "gestion-reserva", layout = MainLayout.class)
@AnonymousAllowed
public class GestionReservaView extends Div {

    private TextField street = new TextField("Direccion");
    private TextField postalCode = new TextField("Codigo Postal");
    private TextField city = new TextField("Ciudad");
    private  TextField state = new  TextField("Estado");
    private  TextField country = new  TextField("Pais");

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");

    private Binder<SampleAddress> binder = new Binder<>(SampleAddress.class);

    public GestionReservaView(SampleAddressService addressService) {
        addClassName("gestion-reserva-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            addressService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            clearForm();
        });
    }

    private Component createTitle() {
        return new H3("Reservacion");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(street, 2);
        postalCode.setAllowedCharPattern("\\d");
        formLayout.add(postalCode, city, state, country);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private void clearForm() {
        this.binder.setBean(new SampleAddress());
    }

}
