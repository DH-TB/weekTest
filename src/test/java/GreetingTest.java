import com.example.Greeting;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingTest {
    @Test
    void should_work(){
        String greet = new Greeting().greet();
        assertEquals("Hello World!", greet);
    }
}
