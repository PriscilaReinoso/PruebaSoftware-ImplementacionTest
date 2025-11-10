package prueba.soft.Selenium_Cucumber.test;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Dimension;
import org.junit.Assert;

public class SauceDemoSteps {
    private WebDriver driver;
    private static final String TIPO_DRIVER = "webdriver.edge.driver";
    private static final String PATH_DRIVER = "./src/main/resources/webDriver/msedgedriver.exe";

    // --------------------------
    // Escenario 1: Login exitoso
    // --------------------------
    @Given("el usuario abre el navegador en la página de SauceDemo")
    public void abrirPaginaSauceDemo() {
        System.setProperty(TIPO_DRIVER, PATH_DRIVER);
        driver = new EdgeDriver();
        driver.manage().window().setSize(new Dimension(1552, 832));
        driver.get("https://www.saucedemo.com/");
    }

    @When("ingresa el nombre de usuario {string} y la contraseña {string}")
    public void ingresarCredenciales(String username, String password) {
        driver.findElement(By.cssSelector("*[data-test='username']")).sendKeys(username);
        driver.findElement(By.cssSelector("*[data-test='password']")).sendKeys(password);
    }

    @When("hace clic en el botón de inicio de sesión")
    public void clicEnLogin() {
        driver.findElement(By.cssSelector("*[data-test='login-button']")).click();
    }

    @Then("el usuario debería ver la página de productos")
    public void validarPaginaProductos() {
        boolean visible = driver.findElement(By.cssSelector(".inventory_list")).isDisplayed();
        Assert.assertTrue("No se encontró la lista de productos", visible);
        driver.quit();
    }

    // ------------------------------------
    // Escenario 3: Compra exitosa de dos productos
    // ------------------------------------
    @Given("el usuario ha iniciado sesión correctamente en SauceDemo")
    public void iniciarSesionPrevio() {
        System.setProperty(TIPO_DRIVER, PATH_DRIVER);
        driver = new EdgeDriver();
        driver.manage().window().setSize(new Dimension(1552, 832));
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.cssSelector("*[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("*[data-test='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("*[data-test='login-button']")).click();
    }

    @When("agrega dos productos al carrito")
    public void agregarProductosAlCarrito() {
        driver.findElement(By.cssSelector("*[data-test='add-to-cart-sauce-labs-fleece-jacket']")).click();
        driver.findElement(By.cssSelector("*[data-test='add-to-cart-sauce-labs-onesie']")).click();
        driver.findElement(By.cssSelector("*[data-test='shopping-cart-link']")).click();
        driver.findElement(By.cssSelector("*[data-test='checkout']")).click();
    }

    @When("completa el proceso de compra con los datos {string} {string} {string}")
    public void completarCompra(String nombre, String apellido, String codigoPostal) {
        driver.findElement(By.cssSelector("*[data-test='firstName']")).sendKeys(nombre);
        driver.findElement(By.cssSelector("*[data-test='lastName']")).sendKeys(apellido);
        driver.findElement(By.cssSelector("*[data-test='postalCode']")).sendKeys(codigoPostal);
        driver.findElement(By.cssSelector("*[data-test='continue']")).click();
        driver.findElement(By.cssSelector("*[data-test='finish']")).click();
    }

    @Then("debería ver el mensaje de confirmación de compra")
    public void validarConfirmacionCompra() {
        WebElement mensaje = driver.findElement(By.cssSelector(".complete-header"));
        Assert.assertTrue("No se mostró mensaje de compra exitosa", mensaje.isDisplayed());
        driver.quit();
    }

    // ------------------------------------
    // Escenario 2: Agregar productos al carrito
    // ------------------------------------
    @When("inicia sesión con el usuario {string} y contraseña {string}")
    public void iniciarSesionRapido(String username, String password) {
        System.setProperty(TIPO_DRIVER, PATH_DRIVER);
        driver = new EdgeDriver();
        driver.manage().window().setSize(new Dimension(974, 1032));
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.cssSelector("*[data-test='username']")).sendKeys(username);
        driver.findElement(By.cssSelector("*[data-test='password']")).sendKeys(password + Keys.ENTER);
    }

    @When("agrega los productos {string} y {string} al carrito")
    public void agregarProductosEspecificos(String producto1, String producto2) {
        driver.findElement(By.cssSelector("*[data-test='add-to-cart-" + producto1 + "']")).click();
        driver.findElement(By.cssSelector("*[data-test='add-to-cart-" + producto2 + "']")).click();
        driver.findElement(By.cssSelector("*[data-test='shopping-cart-link']")).click();
    }

    @Then("debería ver ambos productos en el carrito")
    public void verificarProductosEnCarrito() {
        WebElement primerProducto = driver.findElement(By.cssSelector(".cart_item:nth-child(3) .cart_item_label"));
        WebElement segundoProducto = driver.findElement(By.cssSelector(".cart_item:nth-child(4) .cart_item_label"));

        Assert.assertTrue("No se encontró el primer producto en el carrito", primerProducto.isDisplayed());
        Assert.assertTrue("No se encontró el segundo producto en el carrito", segundoProducto.isDisplayed());

        driver.quit();
    }

    // ------------------------------------
    // Escenario 4: Cerrar sesión (Logout)
    // ------------------------------------
    @When("el usuario inicia sesión y luego cierra sesión")
    public void cerrarSesion() {
        System.setProperty(TIPO_DRIVER, PATH_DRIVER);
        driver = new EdgeDriver();
        driver.manage().window().setSize(new Dimension(974, 1032));
        driver.get("https://www.saucedemo.com/");

        // Login
        driver.findElement(By.cssSelector("*[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("*[data-test='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("*[data-test='login-button']")).click();

        // Esperar a que se cargue la página principal
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(
                        By.id("react-burger-menu-btn")
                ));

        // Abrir menú lateral
        driver.findElement(By.id("react-burger-menu-btn")).click();

        // Esperar a que aparezca el enlace de logout
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(
                        By.cssSelector("*[data-test='logout-sidebar-link']")
                ));

        // Hacer clic en Logout
        driver.findElement(By.cssSelector("*[data-test='logout-sidebar-link']")).click();
    }


    @Then("el usuario debería ver nuevamente la página de inicio de sesión")
    public void validarLogout() {
        boolean visible = driver.findElement(By.cssSelector("*[data-test='login-button']")).isDisplayed();
        Assert.assertTrue("No se redirigió a la pantalla de login tras cerrar sesión", visible);
        driver.quit();
    }
}