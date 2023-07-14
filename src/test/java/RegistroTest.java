
import org.junit.Assert;
import org.junit.Test;



public class RegistroTest {
    private WebDriver driver;

    @Test
    public void testAddItemToListScreen() throws InterruptedException {
        // Configuración del WebDriver
        System.setProperty("webdriver.chrome.driver", "https://start.vaadin.com/app?id=57b6487c-ac4a-4a27-9897-8671052ddc77");
        driver = new ChromeDriver();

        // Navegar a la pantalla de lista
        driver.get("http://localhost:8080/list");

        // Obtener el número inicial de elementos en la lista
        WebElement initialListItems = driver.findElement(By.className("list-item"));
        int initialItemCount = initialListItems.size();

        // Hacer clic en el botón "Agregar elemento"
        WebElement addButton = driver.findElement(By.id("addButton"));
        addButton.click();

        // Esperar a que aparezca el formulario de nuevo elemento
        WebElement newItemForm = driver.findElement(By.id("newItemForm"));

        // Rellenar el formulario con los datos del nuevo elemento
        WebElement nameField = newItemForm.findElement(By.id("nameField"));
        WebElement descriptionField = newItemForm.findElement(By.id("descriptionField"));
        WebElement saveButton = newItemForm.findElement(By.id("saveButton"));

        nameField.sendKeys("Nuevo elemento");
        descriptionField.sendKeys("Descripción del nuevo elemento");
        saveButton.click();

        // Obtener el número actualizado de elements en la lista
        WebElement updatedListItems = driver.findElement(By.className("list-item"));
        int updatedItemCount = updatedListItems.size();

        // Verificar que el número de elementos se haya incrementado en 1
        Assert.assertEquals(initialItemCount + 1, updatedItemCount);

        // Cerrar el WebDriver
        driver.wait();
    }
    
}
	
	