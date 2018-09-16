import com.example.IoCContextImpl;
import com.example.inheritance.MyInheritance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InheritanceTest {
    @Test
    void should_throw_exception_when_dependence_super_class_not_be_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyInheritance.class);

        assertThrows(IllegalStateException.class, () -> context.getBean(MyInheritance.class));
    }


}
