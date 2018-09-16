import com.example.IoCContextImpl;
import com.example.autoCloseable.MyBean;
import org.junit.jupiter.api.Test;

class AutoCloseTest {

    @Test
    void should_close_when_context_close_if_implement_AutoCloseable() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.close();

    }
}
