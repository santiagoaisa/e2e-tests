package com.mycompany.pages.owners;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class OwnerPage extends PageObject {

    @Step("Click on the owner menu link")
    public void clickOnOwnerMenuLink() {
        clickOnOwnerMenu();
    }

    @Step("Click on the owner option button")
    public void clickOnOwnerOptionButton() {
        By buttonLocator = By.cssSelector("button[type='submit'], .btn-primary");
        waitFor(ExpectedConditions.elementToBeClickable(buttonLocator));
        find(buttonLocator).click();
    }

    @Step("Get Owners header text")
    public String getOwnersHeaderText() {
        By titleLocator = By.xpath("//h2[contains(text(),'Owners') or contains(text(),'OWNERS')]");
        return find(titleLocator).waitUntilVisible().getText();
    }

    @Step("Click on the owner menu")
    public void clickOnOwnerMenu() {
        // 1. Maximizar navegador para garantizar despliegue del menú
        getDriver().manage().window().maximize();

        // 2. Esperar a que el JS de Angular inserte el botón/enlace de OWNERS en el DOM
        By ownerMenuLocator = By.xpath("//*[self::a or self::button or self::span][contains(text(),'OWNERS') or contains(text(),'Owners')]");
        waitFor(ExpectedConditions.presenceOfElementLocated(ownerMenuLocator));

        // 3. Ejecutar clic vía JS para evitar desfases de carga
        evaluateJavascript("arguments[0].click();", find(ownerMenuLocator));
    }

    @Step("Click on the owner search option menu")
    public void clickOnOwnerSearchOptionMenu() {
        By searchLocator = By.xpath("//*[self::a or self::button or self::span][contains(text(),'Search') or contains(text(),'SEARCH')]");
        waitFor(ExpectedConditions.presenceOfElementLocated(searchLocator));
        evaluateJavascript("arguments[0].click();", find(searchLocator));
    }

    @Step("Get Owners table")
    public int getOwnersTable() {
        By tableRowsLocator = By.cssSelector("#ownersTable tbody tr, table tbody tr");
        return findAll(tableRowsLocator).size();
    }

    // Add Owner
    @Step("Click Add Owner button option")
    public void clickAddOwnerButtonOption() {
        By addOwnerOptionLocator = By.xpath("//*[self::a or self::button or self::span][contains(text(),'Add New') or contains(text(),'ADD NEW')]");
        waitFor(ExpectedConditions.presenceOfElementLocated(addOwnerOptionLocator));
        evaluateJavascript("arguments[0].click();", find(addOwnerOptionLocator));
    }

    @Step("Enter first name {0}")
    public void enterFirstName(String firstName) {
        find(By.id("firstName")).waitUntilVisible().type(firstName);
    }

    @Step("Enter last name {0}")
    public void enterLastName(String lastName) {
        find(By.id("lastName")).waitUntilVisible().type(lastName);
    }

    @Step("Enter address {0}")
    public void enterAddress(String address) {
        find(By.id("address")).waitUntilVisible().type(address);
    }

    @Step("Enter city {0}")
    public void enterCity(String city) {
        find(By.id("city")).waitUntilVisible().type(city);
    }

    @Step("Enter telephone {0}")
    public void enterTelephone(String telephone) {
        find(By.id("telephone")).waitUntilVisible().type(telephone);
    }

    @Step("Enter owner data")
    public void enterOwnerData() {
        String firstName = Serenity.sessionVariableCalled("firstName");
        String lastName = Serenity.sessionVariableCalled("lastName");
        String address = Serenity.sessionVariableCalled("address");
        String city = Serenity.sessionVariableCalled("city");
        String telephone = Serenity.sessionVariableCalled("telephone");
        enterFirstName(firstName);
        enterLastName(lastName);
        enterAddress(address);
        enterCity(city);
        enterTelephone(telephone);
    }

    @Step("Click Add Owner button")
    public void clickAddOwnerButton() {
        By submitBtnLocator = By.cssSelector("button[type='submit']");
        find(submitBtnLocator).waitUntilClickable().click();
    }

    @Step("Get full name")
    public String getFullName() {
        List<WebElementFacade> rows = findAll(By.cssSelector("#ownersTable tbody tr, table tbody tr"));
        WebElementFacade lastRow = rows.get(rows.size() - 1);
        return lastRow.findElement(By.cssSelector("td a")).getText();
    }
}