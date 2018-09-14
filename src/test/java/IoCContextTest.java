import com.example.IoCContextImpl;
import com.example.otherClass.MyBean;
import com.example.otherClass.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IoCContextTest {
    @Test
    void should_throw_exception_when_beanClazz_is_null() {
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null,null), "beanClazz is mandatory");
    }

    @Test
    void should_throw_exception_when_beanClazz_not_have_default_constructor() {
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null, ClassNotHaveDefaultConstructor.class), "ClassNotHaveDefaultConstructor has no default constructor");
    }

    @Test
    void should_throw_exception_when_beanClass_can_not_instantiated() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null, ClassNotInstanctiated.class), "ClassNotInstanctiated is abstract");
    }

    @Test
    void should_return_when_beanClazz_is_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(null,MyBean.class);
        assertDoesNotThrow(() -> context.registerBean(null, MyBean.class));
    }

    @Test
    void should_throw_exception_when_resolveClazz_is_null() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class, () -> context.getBean(null), "resolveClazz is null");
    }

    @Test
    void should_throw_exception_when_resolveClass_not_register() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalStateException.class, () -> context.getBean(MyBean.class), "resolveClazz not registered");
    }

    @Test
    void should_continue_throw_exception_when_getBean_getConstructors_throw_exception() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(null, ClassConstructorThrowException.class);
        assertThrows(CloneNotSupportedException.class, () -> context.getBean(ClassConstructorThrowException.class), "something happened");
    }

    @Test
    void should_throw_exception_when_registerBean_after_getBean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(null, MyBean.class);
        context.getBean(MyBean.class);
        assertThrows(IllegalStateException.class, () -> context.registerBean(null, MyBean.class), "not register bean after get bean");
    }

    @Test
    void should_can_work_when_register_good_bean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(null, ClassCanWork.class);

        ClassCanWork bean = context.getBean(ClassCanWork.class);
        String actual = bean.getString();

        assertEquals("can work", actual);
    }

    @Test
    void should_can_get_not_same_bean_when_register_one_bean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(null, ClassCanWork.class);

        ClassCanWork bean = context.getBean(ClassCanWork.class);
        ClassCanWork anotherBean = context.getBean(ClassCanWork.class);

        assertNotSame(bean, anotherBean);
    }

    @Test
    void should_can_get_one_work_bean_when_register_repeat_same_bean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(null, ClassCanWork.class);
        context.registerBean(null, ClassCanWork.class);

        ClassCanWork bean = context.getBean(ClassCanWork.class);

        String actual = bean.getString();

        assertEquals("can work", actual);
    }

    @Test
    void should_can_work_when_register_arbitrarily_bean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(null, ClassCanWork.class);
        context.registerBean(null, ClassCanWorkAnother.class);

        ClassCanWorkAnother bean = context.getBean(ClassCanWorkAnother.class);

        String actual = bean.getString();

        assertEquals("can work", actual);
    }

}
