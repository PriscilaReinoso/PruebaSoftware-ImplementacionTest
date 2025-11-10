Feature: Pruebas de SauceDemo

  Scenario: Login exitoso
    Given el usuario abre el navegador en la página de SauceDemo
    When ingresa el nombre de usuario "standard_user" y la contraseña "secret_sauce"
    And hace clic en el botón de inicio de sesión
    Then el usuario debería ver la página de productos

  Scenario: Agregar productos al carrito
     Given el usuario abre el navegador en la página de SauceDemo
     When inicia sesión con el usuario "standard_user" y contraseña "secret_sauce"
     And agrega los productos "sauce-labs-backpack" y "sauce-labs-bike-light" al carrito
     Then debería ver ambos productos en el carrito

  Scenario: Compra exitosa de dos productos
    Given el usuario ha iniciado sesión correctamente en SauceDemo
    When agrega dos productos al carrito
    And completa el proceso de compra con los datos "Armando" "Paredes" "1234"
    Then debería ver el mensaje de confirmación de compra

  Scenario: Cerrar sesión exitosamente
    When el usuario inicia sesión y luego cierra sesión
    Then el usuario debería ver nuevamente la página de inicio de sesión



