import com.example.IoCContextImpl;
import com.example.interfaceImplementClass.MyBeanBase;
import com.example.interfaceImplementClass.MyBeanBaseClass;
import com.example.interfaceImplementClass.MyBeanImpl;
import com.example.interfaceImplementClass.MyBeanImplAnother;
import com.example.otherClass.ClassConstructorThrowException;
import com.example.otherClass.ClassNotHaveDefaultConstructor;
import com.example.otherClass.ClassNotInstanctiated;
import com.example.otherClass.MyBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SuperClassAndInterfaceTest {
    @Test
    void should_can_work_when_register_interface() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBase.class, MyBeanImpl.class);

        MyBeanBase bean = context.getBean(MyBeanBase.class);

        assertEquals("bean can work", bean.getString());
    }

    @Test
    void should_can_work_when_register_super_class() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBaseClass.class, MyBeanImpl.class);

        MyBeanBaseClass bean = context.getBean(MyBeanBaseClass.class);

        assertEquals("super class can work", bean.getSuperClass());
    }

    @Test
    void should_override_when_register_repeat_same_resolveClazz() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBase.class, MyBeanImpl.class);
        context.registerBean(MyBeanBase.class, MyBeanImplAnother.class);

        MyBeanBase bean = context.getBean(MyBeanBase.class);

        assertEquals("override another bean can work", bean.getString());
    }

    @Test
    void should_can_get_not_same_bean_when_register_interface() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBase.class, MyBeanImpl.class);

        MyBeanBase bean = context.getBean(MyBeanBase.class);
        MyBeanBase beanAnother = context.getBean(MyBeanBase.class);

        assertNotSame(bean, beanAnother);
    }

    @Test
    void should_can_get_not_same_bean_when_register_super_class() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBaseClass.class, MyBeanImpl.class);

        MyBeanBaseClass bean = context.getBean(MyBeanBaseClass.class);
        MyBeanBaseClass beanAnother = context.getBean(MyBeanBaseClass.class);

        assertNotSame(bean, beanAnother);
    }

    @Test
    void should_sub_class_can_get_bean_when_register_interface() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBase.class, MyBeanImpl.class);
        context.registerBean(MyBeanBaseClass.class);

        MyBeanImpl bean = context.getBean(MyBeanImpl.class);

        assertEquals("bean can work", bean.getString());
    }

    @Test
    void should_sub_class_can_get_bean_when_register_super_class() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBeanBaseClass.class, MyBeanImpl.class);

        MyBeanImpl bean = context.getBean(MyBeanImpl.class);

        assertEquals("bean can work", bean.getString());
    }

    @Test
    void should_throw_exception_when_beanClazz_or_resolveClazz_is_null() {
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(MyBean.class, null), "beanClazz or resolveClazz is mandatory");
        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null, MyBean.class), "beanClazz or resolveClazz is mandatory");
        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null, null), "beanClazz or resolveClazz is mandatory");
    }

    @Test
    void should_throw_exception_when_beanClazz_or_resolveClazz_not_have_default_constructor() {
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(ClassNotHaveDefaultConstructor.class, ClassNotHaveDefaultConstructor.class), "ClassNotHaveDefaultConstructor has no default constructor");
    }

    @Test
    void should_throw_exception_when_beanClazz_or_resolveClazz_can_not_instantiated() {
        IoCContextImpl context = new IoCContextImpl();

        assertThrows(IllegalArgumentException.class, () -> context.registerBean(ClassNotInstanctiated.class, ClassNotInstanctiated.class), "ClassNotInstantiated is abstract");
    }

    @Test
    void should_return_when_beanClazz_or_resolveClazz_is_registered() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class, MyBean.class);
        assertDoesNotThrow(() -> context.registerBean(MyBean.class, MyBean.class));
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
}
