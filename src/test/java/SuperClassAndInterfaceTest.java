import com.example.IoCContextImpl;
import com.example.interfaceClass.MyBeanBase;
import com.example.interfaceClass.MyBeanBaseClass;
import com.example.interfaceClass.MyBeanImpl;
import com.example.interfaceClass.MyBeanImplAnother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

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


}
