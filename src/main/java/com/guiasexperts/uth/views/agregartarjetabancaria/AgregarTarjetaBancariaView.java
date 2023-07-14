package com.guiasexperts.uth.views.agregartarjetabancaria;

import com.guiasexperts.uth.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Agregar Tarjeta Bancaria")
@Route(value = "credit-card-form", layout = MainLayout.class)
@AnonymousAllowed
public class AgregarTarjetaBancariaView extends Div {

    private TextField cardNumber;
    private TextField cardholderName;
    private Select<Integer> month;
    private Select<Integer> year;
    private ExpirationDateField expiration;
    private PasswordField csc;
    private Button cancel;
    private Button submit;

    /**
     * Matches Visa, MasterCard, American Express, Diners Club, Discover, and JCB
     * cards. See https://stackoverflow.com/a/9315696
     */
    private String CARD_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35d{3})d{11})$";

    public AgregarTarjetaBancariaView() {
        addClassName("agregar-tarjeta-bancaria-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        cancel.addClickListener(e -> {
            Notification.show("Not implemented");
        });
        submit.addClickListener(e -> {
            Notification.show("Not implemented");
        });
    }

    private Component createTitle() {
        return new H3("Tarjeta Credito/Debito");
    }

    private Component createFormLayout() {
        cardNumber = new TextField("Numero de la Tarjeta");
        cardNumber.setPlaceholder("1234 5678 9123 4567");
        cardNumber.setPattern(CARD_REGEX);
        cardNumber.setAllowedCharPattern("[\\d ]");
        cardNumber.setRequired(true);
        cardNumber.setErrorMessage("Please enter a valid credit card number");

        cardholderName = new TextField("Nombre del Titular");

        month = new Select<>();
        month.setPlaceholder("Mes");
        month.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        year = new Select<>();
        year.setPlaceholder("Año");
        year.setItems(23, 24, 25, 26, 27, 28);

        expiration = new ExpirationDateField("Fecha Expiracion", month, year);
        csc = new PasswordField("CSC");

        FormLayout formLayout = new FormLayout();
        formLayout.add(cardNumber, cardholderName, expiration, csc);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");

        submit = new Button("Guardar");
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancel = new Button("Cancelar");

        buttonLayout.add(submit);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private class ExpirationDateField extends CustomField<String> {
        public ExpirationDateField(String label, Select<Integer> month, Select<Integer> year) {
            setLabel(label);
            HorizontalLayout layout = new HorizontalLayout(month, year);
            layout.setFlexGrow(1.0, month, year);
            month.setWidth("100px");
            year.setWidth("100px");
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            // Unused as month and year fields part are of the outer class
            return "";
        }

        @Override
        protected void setPresentationValue(String newPresentationValue) {
            // Unused as month and year fields part are of the outer class
        }

    }

}
