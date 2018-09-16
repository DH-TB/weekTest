import com.example.IoCContextImpl;
import com.example.dependency.MyBean;
import com.example.dependency.MyBeanAnother;
import com.example.dependency.MyDependency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DependencyTest {
    @Test
    void should_throw_exception_when_dependence_not_be_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanAnother.class);

        assertThrows(IllegalStateException.class, () -> context.getBean(MyBeanAnother.class));
    }

        @Test
        void should_throw_exception_when_multiply_dependence_not_be_registered() throws Exception {
            IoCContextImpl context = new IoCContextImpl();
            context.registerBean(MyBeanAnother.class);
            context.registerBean(MyDependency.class);

            assertThrows(IllegalStateException.class, () -> context.getBean(MyBeanAnother.class));
        }

    @Test
    void should_can_work_when_dependence_has_been_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.registerBean(MyDependency.class);

        MyBean bean = context.getBean(MyBean.class);
        assertEquals("my bean string dependence", bean.getString());
    }

    @Test
    void should_can_work_when_execute_dependence() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.registerBean(MyDependency.class);

        MyDependency bean = context.getBean(MyDependency.class);
        assertEquals("MyDependency string", bean.getString());
    }

    @Test
    void should_can_work_when_get_dependence_string() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.registerBean(MyDependency.class);

        MyBean bean = context.getBean(MyBean.class);
        assertEquals("MyDependency string", bean.getDependency());
    }

}
