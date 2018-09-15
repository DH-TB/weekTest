import com.example.IoCContextImpl;
import com.example.dependency.MyBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DependencyTest {
    @Test
    void should_throw_exception_when_dependence_not_be_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);

        assertThrows(IllegalStateException.class, () -> context.getBean(MyBean.class));
    }
}
