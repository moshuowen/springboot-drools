package cn.msw;

import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DroolsTest {

    @Autowired
    private KieBase kieBase;

    @Test
    public void droolsTest(){
        Order order = new Order();
        order.setAmount(700);
        //创建会话对象，用于和规则交互的引擎
        KieSession kieSession = kieBase.newKieSession();
        //讲数据交给规则引擎，规则迎请会根据提供的数据进行规则匹配
        kieSession.insert(order);
        //激活规则引擎，如果匹配成功则执行
        kieSession.fireAllRules();
        //关闭会话
        kieSession.dispose();
        System.out.println("订单金额："+order.getAmount()+",订单积分："+order.getScore());
    }

    @Test
    public void agendaTest(){
        //创建会话对象，用于和规则交互的引擎
        KieSession kieSession = kieBase.newKieSession();
        // 指定agenda
        kieSession.getAgenda().getAgendaGroup("group_1").setFocus();
        //讲数据交给规则引擎，规则迎请会根据提供的数据进行规则匹配
        //激活规则引擎，如果匹配成功则执行
        kieSession.fireAllRules();
        //关闭会话
        kieSession.dispose();
    }
    @Test
    public void agendaTest2(){
        //创建会话对象，用于和规则交互的引擎
        KieSession kieSession = kieBase.newKieSession();
        // 指定agenda
//        kieSession.getAgenda().getAgendaGroup("group_1").setFocus();
        //讲数据交给规则引擎，规则迎请会根据提供的数据进行规则匹配
        //激活规则引擎，如果匹配成功则执行
        kieSession.fireAllRules();
        //关闭会话
        kieSession.dispose();
    }

    @Test
    public void testTimer() throws InterruptedException {
//        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm");
        final KieSession kieSession = kieBase.newKieSession();

        new Thread(kieSession::fireUntilHalt).start();

        Thread.sleep(10000);

        kieSession.halt();

        kieSession.dispose();


    }

    @Test
    public void testGlobal(){
        KieSession kieSession = kieBase.newKieSession();
        kieSession.setGlobal("count", 1); // count 初始值
        kieSession.fireAllRules(
                new RuleNameStartsWithAgendaFilter("test_global")
        );
        kieSession.dispose();
    }


}

