import com.example.IoCContextImpl;
import com.example.autoCloseable.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AutoCloseTest {

    @Test
    void should_close_when_context_close_if_implement_AutoCloseable() throws Exception {
        List<String> list = IoCContextImpl.countCloseList;
        list.clear();

        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.close();

        assertEquals(1, list.size());
        assertTrue(list.contains("MyBean"));
    }

    @Test
    void should_close_reverse_order_when_context_close_if_implement_AutoCloseable() throws Exception {
        List<String> list = IoCContextImpl.countCloseList;
        list.clear();

        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.registerBean(MyBeanAnother.class);
        context.close();


        assertEquals(2, list.size());
        assertTrue(list.containsAll(Arrays.asList("MyBean", "MyBeanAnother")));
        assertEquals("MyBeanAnother", list.get(0));
    }

    @Test
    void should_throw_exception_when_context_close() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyExceptionBean.class);
        context.registerBean(MyExceptionBeanAnother.class);

        assertThrows(IllegalAccessException.class, context::close);
    }
}
