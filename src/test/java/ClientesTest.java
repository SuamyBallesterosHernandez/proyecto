import org.junit.Assert;
import org.junit.Test;


public class ClientesTest {
    private WebDriver driver;

    @Test
    public void testLoginScreen() throws InterruptedException {
        // Configuración del WebDriver
        System.setProperty("webdriver.chrome.driver", "https://start.vaadin.com/app?id=57b6487c-ac4a-4a27-9897-8671052ddc77");
        driver = new ChromeDriver();

        // Navegar a la pantalla de inicio de sesión
        driver.get("http://localhost:8080/");

        // Introducir credenciales de usuario
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        usernameField.sendKeys("usuario");
        passwordField.sendKeys("contraseña");
        loginButton.click();

        // Verificar el resultado esperado
        WebElement welcomeMessage = driver.findElement(By.id("welcomeMessage"));
        String messageText = welcomeMessage.getText();
        Assert.assertEquals("¡Bienvenido!", messageText);

        // Cerrar el WebDriver
        driver.wait();
    }
}
	
	
	  
