package commands;

import static org.mockito.Mockito.*;

import coffeevan.CoffeeVan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheckRemainVolumeCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    @Test
    void testExecute_shouldCallGetRemainingVolume() {
        // Setup
        when(coffeeVan.getRemainingVolume()).thenReturn(750.25);
        CheckRemainVolumeCommand command = new CheckRemainVolumeCommand(coffeeVan);

        // Action
        command.execute();

        // Verify: was getRemainingVolume() called 1 time?
        verify(coffeeVan, times(1)).getRemainingVolume();
    }

    @Test
    void testExecute_zeroVolume() {
        // Setup
        when(coffeeVan.getRemainingVolume()).thenReturn(0.0);
        CheckRemainVolumeCommand command = new CheckRemainVolumeCommand(coffeeVan);

        // Action
        command.execute();

        // Verify
        verify(coffeeVan, times(1)).getRemainingVolume();
    }
}