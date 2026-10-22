package com.mycompany.steps;

import com.mycompany.pages.owners.OwnerPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;

import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OwnersSteps {


    OwnerPage ownerPage;

    @Given("el cliente abre el navegador")
    public void elClienteAbreElNavegador() {
        ownerPage.open();
    }

    @Given("el cliente navega al menú propietarios")
    public void elClienteNavegaAlMenuPropietarios() {
        ownerPage.clickOnOwnerMenu();
    }

    @When("el cliente selecciona la opción buscar")
    public void elClienteSeleccionaLaOpcionBuscar() {
        ownerPage.clickOnOwnerSearchOptionMenu();
    }

    @Then("la página debe mostrar una lista de propietarios")
    public void laPaginaDebeMostrarUnaListaDePropietarios() {
        int rows = ownerPage.getOwnersTable();
//        assertEquals(10, rows);
        assertThat(ownerPage.getOwnersTable()).isPositive();
    }

    @Given("el cliente tiene los siguientes datos del propietario:")
    public void elClienteTieneLosSiguientesDatosDelPropietario(DataTable dataTable) {
//        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
//        for (Map<String, String> row : data) {
//            for (Map.Entry<String, String> entry : row.entrySet()) {
//                String header = entry.getKey();
//                String cell = entry.getValue();
//                Serenity.setSessionVariable(header).to(cell);
//            }
//        }
        dataTable.asMaps(String.class, String.class)
                .forEach(row -> row.forEach((header, cell) -> Serenity.setSessionVariable(header).to(cell)));
    }

    @And("el cliente selecciona la opción agregar nuevo")
    public void elClienteSeleccionaLaOpciónAgregarNuevo() {
        ownerPage.clickAddOwnerButtonOption();
    }

    @And("el cliente ingresa los datos del propietario")
    public void elClienteIngresaLosDatosDelPropietario() {
        ownerPage.enterOwnerData();
    }

    @And("el cliente guarda el propietario")
    public void elClienteGuardaElPropietario() {
        ownerPage.clickAddOwnerButton();
    }

    @Then("la página debe mostrar la información del propietario registrado")
    public void laPaginaDebeMostrarLaInformacionDelPropietarioRegistrado() {
        String firstName = Serenity.sessionVariableCalled("firstName");
        String lastName = Serenity.sessionVariableCalled("lastName");
        String fullName = firstName + " " + lastName;
        assertEquals(fullName, ownerPage.getFullName());
    }
}
