import com.example.IoCContextImpl;
import com.example.autoCloseable.MyBean;
import com.example.autoCloseable.MyBeanAnother;
import org.junit.jupiter.api.Test;

class AutoCloseTest {

    @Test
    void should_close_when_context_close_if_implement_AutoCloseable() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.close();

    }

    @Test
    void should_close_reverse_order_when_context_close_if_implement_AutoCloseable() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.registerBean(MyBeanAnother.class);
        context.close();

    }
}
