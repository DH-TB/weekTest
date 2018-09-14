import com.example.IoCContextImpl;
import com.example.MyBean;
import com.example.otherClass.ClassCanWork;
import com.example.otherClass.ClassConstructorThrowException;
import com.example.otherClass.ClassNotHaveDefaultConstructor;
import com.example.otherClass.ClassNotInstanctiated;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IoCContextTest {
    @Test
    void should_throw_exception_when_beanClazz_is_null() {
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null));
    }

    @Test
    void should_throw_exception_when_beanClazz_not_have_default_constructor() {
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(ClassNotHaveDefaultConstructor.class));
    }

    @Test
    void should_throw_exception_when_beanClass_can_not_instantiated() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class, () -> context.registerBean(ClassNotInstanctiated.class));
    }

    @Test
    void should_return_when_beanClazz_is_registered() {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        assertDoesNotThrow(() -> context.registerBean(MyBean.class));
    }

    @Test
    void should_throw_exception_when_resolveClazz_is_null() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class, () -> context.getBean(null));
    }

    @Test
    void should_throw_exception_when_resolveClass_not_register() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalStateException.class, () -> context.getBean(MyBean.class));
    }

    @Test
    void should_continue_throw_exception_when_getBean_getConstructors_throw_exception() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalStateException.class, () -> context.getBean(ClassConstructorThrowException.class));
    }

    @Test
    void should_throw_exception_when_registerBean_after_getBean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.getBean(MyBean.class);
        assertThrows(IllegalStateException.class, () -> context.registerBean(MyBean.class));
    }

    @Test
    void should_can_work_when_register_good_bean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(ClassCanWork.class);

        ClassCanWork bean = context.getBean(ClassCanWork.class);
        String actual = bean.getString();

        assertEquals("can work", actual);
    }
}
