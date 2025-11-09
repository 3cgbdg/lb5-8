package commands;

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
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
class LoadVanCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        // Setup mocks for the van for all tests
        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        lenient().when(coffeeVan.getRemainingBudget()).thenReturn(500.0);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Important: restore System.in
        System.setIn(originalIn);
    }

    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    void testExecute_noVolume() {
        // Override setup
        when(coffeeVan.getRemainingVolume()).thenReturn(0.0);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        // Verify: addCoffee should not have been called
        verify(coffeeVan, never()).addCoffee(any());
    }

    @Test
    void testExecute_noBudget() {
        // Override setup
        when(coffeeVan.getRemainingVolume()).thenReturn(1000.0);
        when(coffeeVan.getRemainingBudget()).thenReturn(0.0);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        // Verify: addCoffee should not have been called
        verify(coffeeVan, never()).addCoffee(any());
    }

    @Test
    void testExecute_beanCoffeeSuccess() {
        // Simulate full successful input
        String input = "Arabica\n250.0\n15.99\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n250.0\ny\n";
        simulateInput(input);

        when(coffeeVan.addCoffee(any(BeanCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        // Verify: was addCoffee called 1 time with any BeanCoffee?
        verify(coffeeVan, times(1)).addCoffee(any(BeanCoffee.class));
    }

    @Test
    void testExecute_groundCoffeeSuccess() {
        String input = "Robusta\n100.0\n5.99\nground\nCOARSE\n7.0\n6.0\n8.0\nPlastic\n100.0\ny\n";
        simulateInput(input);

        when(coffeeVan.addCoffee(any(GroundCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).addCoffee(any(GroundCoffee.class));
    }

    @Test
    void testExecute_instantCoffeeSuccess() {
        String input = "Nescafe\n50.0\n3.99\ninstant\nHIGH\n6.0\n7.0\n5.0\nJar\n50.0\ny\n";
        simulateInput(input);

        when(coffeeVan.addCoffee(any(InstantCoffee.class))).thenReturn(true);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).addCoffee(any(InstantCoffee.class));
    }


    @Test
    void testExecute_handlesInvalidCoffeeTypeLoop() {
        // 1. "wrong" (incorrect) -> 2. "bean" (correct)
        String input = "Test\n100.0\n10.0\nwrong\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n100.0\ny\n";
        simulateInput(input);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        // Verify: despite the error, the command continued
        // and finally called addCoffee 1 time.
        verify(coffeeVan, times(1)).addCoffee(any(BeanCoffee.class));
    }

    @Test
    void testExecute_handlesNegativeWeightLoop() {
        // 1. "-100.0" (incorrect) -> 2. "100.0" (correct)
        String input = "Test\n-100.0\n100.0\n10.0\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n100.0\ny\n";
        simulateInput(input);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        // Verify: addCoffee was called 1 time at the end.
        verify(coffeeVan, times(1)).addCoffee(any(BeanCoffee.class));
    }

    @Test
    void testExecute_handlesPriceExceedsBudgetLoop() {
        // Budget 500. 1. "1000.0" (incorrect) -> 2. "10.0" (correct)
        String input = "Test\n100.0\n1000.0\n10.0\nbean\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n100.0\ny\n";
        simulateInput(input);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).addCoffee(any(BeanCoffee.class));
    }

    @Test
    void testExecute_handlesInvalidRoastLevelLoop() {
        // 1. "Brazil WRONG" -> 2. "Brazil MEDIUM"
        String input = "Arabica\n250.0\n15.99\nbean\nBrazil WRONG\nBrazil MEDIUM\n8.0\n9.0\n7.0\nPaper\n250.0\ny\n";
        simulateInput(input);

        LoadVanCommand command = new LoadVanCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).addCoffee(any(BeanCoffee.class));
    }
}