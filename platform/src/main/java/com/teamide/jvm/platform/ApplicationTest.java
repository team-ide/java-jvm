//package com.teamide.jvm;
//
//import com.sun.tools.attach.VirtualMachine;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.stereotype.Component;
//import sun.management.ConnectorAddressLink;
//
//import javax.management.*;
//import javax.management.remote.JMXConnector;
//import javax.management.remote.JMXConnectorFactory;
//import javax.management.remote.JMXServiceURL;
//import java.io.IOException;
//import java.util.*;
//
//@SpringBootApplication
//@Component
//public class ApplicationTest {
//
//
//    public static void main(String[] args) throws Exception {
////        SpringApplication.run(ApplicationMain.class, args);
////        ManagementFactory.getPlatformMBeanServer().createMBean()
//        VirtualMachine virtualMachine = VirtualMachine.attach("6156");
//
//
//
//        JMXServiceURL serviceURL = new JMXServiceURL(virtualMachine.startLocalManagementAgent());
//        JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
//        String[] domains = connector.getMBeanServerConnection().getDomains();
//        Arrays.stream(domains).iterator().forEachRemaining(str -> {
//            System.out.println("domain:" + str);
//        });
//        Set<ObjectName> objectNames = connector.getMBeanServerConnection().queryNames(new ObjectName("*:*"), null);
//
//        objectNames.iterator().forEachRemaining(objectName -> {
//            System.out.println("objectName:" + objectName);
//        });
//        MBeanServerConnection connection = connector.getMBeanServerConnection();
////        ObjectName mbeanName = new ObjectName("com.sun.management:type=HotSpotDiagnostic");
//        ObjectName mbeanName = new ObjectName("com.teamide:type=Hello");
//
//        MBeanInfo mBeanInfo = connection.getMBeanInfo(mbeanName);
//
//        System.out.println(mBeanInfo.getClassName());
////        System.out.println(mBeanInfo.);
//        // Create a dedicated proxy for the MBean instead of
//        // going directly through the MBean server connection
////        HelloMBean mbeanProxy =
////                JMX.newMBeanProxy(connection, mbeanName, HelloMBean.class, false);
////        mbeanProxy.setCacheSize(1);
////        System.out.println(mbeanProxy.sayHello());
//        connector.close();
//    }
//
//
//    private static JMXServiceURL getLocalStubServiceURLFromPID(int pid)
//            throws IOException {
//        String address = ConnectorAddressLink.importFrom(pid);
//        if (address != null) {
//            return new JMXServiceURL(address);
//        }
//        return null;
//    }
//
////    private static void executeCommandForPID(VirtualMachine vm, String pid,
////                                             String cmd) throws IOException {
////        HotSpotVirtualMachine hsvm = (HotSpotVirtualMachine) vm;
////        hsvm.executeJCmd(cmd);
////    }
//
//}
