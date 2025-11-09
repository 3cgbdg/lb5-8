package services;

import coffee.Coffee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * This class uses the Mockito extension for JUnit 5.
 * This automatically initializes all fields annotated with @Mock.
 */
@ExtendWith(MockitoExtension.class)
class CoffeeStorageServiceTest {

    // We won't use @InjectMocks because we need the constructor
    // that takes a file path, which we must control.
    private CoffeeStorageService storageService;

    // Create mock Coffee objects
    @Mock
    private Coffee mockCoffee1;

    @Mock
    private Coffee mockCoffee2;

    /**
     * ---------------------------------------------------------------------
     * THIS IS A UNIT TEST (using Mocks and a real temporary file).
     * We are testing *what* the service writes to the file.
     * ---------------------------------------------------------------------
     * JUnit 5 can inject a temporary directory for this test,
     * which will be automatically cleaned up.
     */
    @Test
    void saveToFile_ShouldWriteCorrectLines(@org.junit.jupiter.api.io.TempDir Path tempDir) throws IOException {



        Path tempFile = tempDir.resolve("test_save.txt");


        storageService = new CoffeeStorageService(tempFile.toString());


        List<Coffee> coffeeList = List.of(mockCoffee1, mockCoffee2);

        when(mockCoffee1.toFileString()).thenReturn("BEAN;Brazil;...line1");
        when(mockCoffee2.toFileString()).thenReturn("GROUND;Kenya;...line2");


        storageService.saveToFile(coffeeList,false);


        List<String> actualLines = Files.readAllLines(tempFile);

        assertNotNull(actualLines);
        assertEquals(2, actualLines.size());
        assertEquals("BEAN;Brazil;...line1", actualLines.get(0));
        assertEquals("GROUND;Kenya;...line2", actualLines.get(1));
    }


    /**
     * THIS IS AN INTEGRATION TEST (no Mockito).
     * We are testing if the service can CORRECTLY read a real file
     * and if `Coffee.fromFileString()` works as expected.
     */
    @Test
    void getFromFile_ShouldReadAndParseDataCorrectly(@org.junit.jupiter.api.io.TempDir Path tempDir) throws IOException {

        Path tempFile = tempDir.resolve("test_get.txt");


        String line1 = "BEAN;123;Arabica;250.0;15.99;8.0;9.0;7.0;Paper;250.0;Brazil;MEDIUM";
        String line2 = "GROUND;456;Robusta;500.0;10.50;6.0;7.0;8.0;Plastic;500.0;MEDIUM";

        Files.write(tempFile, List.of(line1, line2));


        storageService = new CoffeeStorageService(tempFile.toString());

        List<Coffee> actualCoffeeList = storageService.getFromFile();


        assertNotNull(actualCoffeeList);
        assertEquals(2, actualCoffeeList.size());

        assertEquals("Arabica", actualCoffeeList.get(0).getName());
        assertEquals("Robusta", actualCoffeeList.get(1).getName());
    }
}

