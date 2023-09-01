package com.george.doctors_appointment_portal.runner;

import com.george.doctors_appointment_portal.modelImpl.AdminPageTest;
import com.george.doctors_appointment_portal.modelImpl.CustomerPageTest;
import com.george.doctors_appointment_portal.modelImpl.DoctorPageTest;
import com.george.doctors_appointment_portal.modelImpl.HomepageTest;
import org.graphwalker.java.test.Executor;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutor;
import org.graphwalker.websocket.WebSocketServer;

import java.io.IOException;

public class WebSocketApplication {
    public static void main(String[] args) throws IOException {
        Executor executor = new TestExecutor(HomepageTest.class, CustomerPageTest.class, AdminPageTest.class, DoctorPageTest.class);

        WebSocketServer server = new WebSocketServer(8887, executor.getMachine());
        server.start();

        Result result = executor.execute(true);
        if (result.hasErrors()) {
            for (String error : result.getErrors()) {
                System.out.println(error);
            }
        }
        System.out.println("Done: [" + result.getResults().toString(2) + "]");
    }
}
