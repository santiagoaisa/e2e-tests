# language: en
@gestionarPropietarios
Feature: Gestionar propietarios

  @listarPropietarios
  Scenario: Listar propietarios
    Given el cliente abre el navegador
    And el cliente navega al menú propietarios
    When el cliente selecciona la opción buscar
    Then la página debe mostrar una lista de propietarios

  @registrarPropietario
  Scenario Outline: Registrar un propietario
    Given el cliente tiene los siguientes datos del propietario:
      | firstName   | lastName   | address   | city   | telephone   |
      | <firstName> | <lastName> | <address> | <city> | <telephone> |
    And el cliente abre el navegador
    And el cliente navega al menú propietarios
    And el cliente selecciona la opción agregar nuevo
    And el cliente ingresa los datos del propietario
    And el cliente guarda el propietario
    Then la página debe mostrar la información del propietario registrado
    Examples:
      | firstName | lastName | address      | city | telephone |
      | John      | Doe      | 1234 Main St | NY   | 1234567890 |
      | Jane      | Smith    | 5678 Elm St  | LA   | 9876543210 |
      | Alice     | Johnson  | 9101 Oak St  | SF   | 5555555550 |
