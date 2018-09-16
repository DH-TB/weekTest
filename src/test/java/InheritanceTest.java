import com.example.IoCContextImpl;
import com.example.dependency.MyBeanAnother;
import com.example.dependency.MyDependency;
import com.example.inheritance.MyClass;
import com.example.inheritance.MyInheritSuperClass;
import com.example.inheritance.MyInheritance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InheritanceTest {
    @Test
    void should_throw_exception_when_dependence_super_class_not_be_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyClass.class);

        assertThrows(IllegalStateException.class, () -> context.getBean(MyClass.class));
    }

    @Test
    void should_throw_exception_when_multiply_super_class_not_be_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyClass.class);
        context.registerBean(MyInheritance.class);

        assertThrows(IllegalStateException.class, () -> context.getBean(MyClass.class));
    }

    @Test
    void should_not_throw_exception_when_multiply_super_class_has_be_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyClass.class);
        context.registerBean(MyInheritance.class);
        context.registerBean(MyInheritSuperClass.class);

        assertDoesNotThrow(() -> context.getBean(MyClass.class));
    }

}
