import com.example.IoCContextImpl;
import com.example.interfaceClass.*;
import com.example.otherClass.MyBean;
import com.example.otherClass.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IoCContextTest {
    @Test
    void should_throw_exception_when_beanClazz_is_null() {
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null, null), "beanClazz is mandatory");
    }

    @Test
    void should_throw_exception_when_beanClazz_not_have_default_constructor() {
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(ClassNotHaveDefaultConstructor.class, null), "ClassNotHaveDefaultConstructor has no default constructor");
    }

    @Test
    void should_throw_exception_when_beanClass_can_not_instantiated() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class, () -> context.registerBean(ClassNotInstanctiated.class, null), "ClassNotInstanctiated is abstract");
    }

    @Test
    void should_return_when_beanClazz_is_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class, MyBean.class);
        assertDoesNotThrow(() -> context.registerBean(MyBean.class, MyBean.class));
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
        context.registerBean(ClassConstructorThrowException.class, ClassConstructorThrowException.class);
        assertThrows(CloneNotSupportedException.class, () -> context.getBean(ClassConstructorThrowException.class), "something happened");
    }

    @Test
    void should_throw_exception_when_registerBean_after_getBean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class, MyBean.class);
        context.getBean(MyBean.class);
        assertThrows(IllegalStateException.class, () -> context.registerBean(MyBean.class, MyBean.class), "not register bean after get bean");
    }

    @Test
    void should_can_work_when_register_good_bean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(ClassCanWork.class, ClassCanWork.class);

        ClassCanWork bean = context.getBean(ClassCanWork.class);
        String actual = bean.getString();

        assertEquals("can work", actual);
    }

    @Test
    void should_can_get_not_same_bean_when_register_one_bean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(ClassCanWork.class, ClassCanWork.class);

        ClassCanWork bean = context.getBean(ClassCanWork.class);
        ClassCanWork anotherBean = context.getBean(ClassCanWork.class);

        assertNotSame(bean, anotherBean);
    }

    @Test
    void should_can_work_when_register_arbitrarily_bean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(ClassCanWork.class, ClassCanWork.class);
        context.registerBean(ClassCanWorkAnother.class, ClassCanWorkAnother.class);

        ClassCanWorkAnother bean = context.getBean(ClassCanWorkAnother.class);

        String actual = bean.getString();

        assertEquals("can work", actual);
    }

    @Test
    void should_can_work_when_register_interface() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBase.class, MyBeanImpl.class);

        MyBeanBase bean = context.getBean(MyBeanBase.class);

        assertEquals("implement class bean can work", bean.getString());
    }

    @Test
    void should_can_work_when_register_superClass() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBaseClass.class, MyBeanImpl.class);

        MyBeanBaseClass bean = context.getBean(MyBeanBaseClass.class);

        assertEquals("getSuperClass", bean.getSuperClass());
    }

    @Test
    void should_override_when_register_repeat_same_resolveClazz() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBase.class, MyBeanImpl.class);
        context.registerBean(MyBeanBase.class, MyBeanImplAnother.class);

        MyBeanBase bean = context.getBean(MyBeanBase.class);

        assertEquals("another bean can work", bean.getString());
    }
}
