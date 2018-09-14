import com.example.IoCContextImpl;
import com.example.otherClass.NoHaveDefaultConstructor;
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

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(NoHaveDefaultConstructor.class));
    }
}
