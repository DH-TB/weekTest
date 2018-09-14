import com.example.IoCContextImpl;
import com.example.otherClass.ClassNotHaveDefaultConstructor;
import com.example.otherClass.ClassNotInstance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class IoCContextTest {
    @Test
    void should_throw_exception_when_beanClazz_is_null(){
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null));
    }

    @Test
    void should_throw_exception_when_beanClazz_not_have_default_constructor(){
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(ClassNotHaveDefaultConstructor.class));
    }

    @Test
    void should_throw_exception_when_beanClass_not_instance(){
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class, () -> context.registerBean(ClassNotInstance.class));
    }

}
