package commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import coffee.BeanCoffee;
import coffee.GroundCoffee;
import coffee.InstantCoffee;
import coffeevan.CoffeeVan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
@ExtendWith(MockitoExtension.class)
class LoadVanCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() throws Exception {
        System.setOut(originalOut);
    }

    @Test
    void testExecute_noVolume() {
        when(coffeeVan.getRemainingVolume()).thenReturn(0.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(1000.0);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("There is no volume in the van!"));
        verify(coffeeVan, never()).addCoffee(any());
    }

    @Test
    void testExecute_noBudget() {
        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(0.0);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("There is no budget left!"));
        verify(coffeeVan, never()).addCoffee(any());
    }

    @Test
    void testExecute_beanCoffeeSuccess() {
        String input = "Arabica\n250.0\n15.99\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n250.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).addCoffee(any(BeanCoffee.class));

        String output = outputStream.toString();
        assertTrue(output.contains("Item has been successfully loaded!"));
    }

    @Test
    void testExecute_groundCoffeeSuccess() {
        String input = "Robusta\n100.0\n5.99\nground\nCOARSE\n7.0\n6.0\n8.0\nPlastic\n100.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(GroundCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).addCoffee(any(GroundCoffee.class));

        String output = outputStream.toString();
        assertTrue(output.contains("Item has been successfully loaded!"));
    }

    @Test
    void testExecute_instantCoffeeSuccess() {
        String input = "Nescafe\n50.0\n3.99\ninstant\nHIGH\n6.0\n7.0\n5.0\nJar\n50.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(InstantCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).addCoffee(any(InstantCoffee.class));

        String output = outputStream.toString();
        assertTrue(output.contains("Item has been successfully loaded!"));
    }

    @Test
    void testExecute_invalidCoffeeType() {
        String input = "Test\n100.0\n10.0\nwrong\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n100.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid type!"));
    }

    @Test
    void testExecute_negativeWeight() {
        String input = "Test\n-100.0\n100.0\n10.0\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n100.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Weight must be positive!"));
    }

    @Test
    void testExecute_negativePrice() {
        String input = "Test\n100.0\n-10.0\n10.0\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n100.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Price must be positive!"));
    }

    @Test
    void testExecute_priceExceedsBudget() {
        String input = "Test\n100.0\n1000.0\n10.0\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n100.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(50.0);
        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("There is not enough budget to afford it!"));
    }

    @Test
    void testExecute_volumeExceedsAvailable() {
        String input = "Test\n100.0\n10.0\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n2000.0\n100.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(500.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("There is not enough volume in the van!"));
    }

    @Test
    void testExecute_invalidRoastLevel() {
        String input = "Arabica\n250.0\n15.99\nbean\nBrazil WRONG\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n250.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid roast level!"));
    }

    @Test
    void testExecute_invalidGrindSize() {
        String input = "Robusta\n100.0\n5.99\nground\nWRONG\nCOARSE\n7.0\n6.0\n8.0\nPlastic\n100.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(GroundCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid grind size!"));
    }

    @Test
    void testExecute_invalidConcentrationLevel() {
        String input = "Nescafe\n50.0\n3.99\ninstant\nWRONG\nHIGH\n6.0\n7.0\n5.0\nJar\n50.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(InstantCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid concentration level!"));
    }

    @Test
    void testExecute_scoreOutOfRange() {
        String input = "Arabica\n250.0\n15.99\nbean\nBrazil MEDIUM\n15.0\n8.0\n9.0\n7.0\nPaper\n250.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Score must be between 1 and 10!"));
    }

    @Test
    void testExecute_emptyName() {
        String input = "\n\nArabica\n250.0\n15.99\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n250.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Name cannot be empty"));
    }

    @Test
    void testExecute_multipleItemsBeforeStopping() {
        String input = "Coffee1\n100.0\n10.0\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n100.0\nn\n" +
                "Coffee2\n100.0\n10.0\nground\nFINE\n8.0\n9.0\n7.0\nPlastic\n100.0\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
        when(coffeeVan.addCoffee(any())).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(2)).addCoffee(any());
    }
}