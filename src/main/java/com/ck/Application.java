package com.ck;

import com.ck.annotation.ScanPackage;
import com.ck.registrar.ImplementRegistrar;
import com.ck.registrar.InterfaceRegistrar;
import com.ck.service.AudiCar;
import com.ck.service.Car;
import com.ck.service.Developer;
import com.ck.service.JavaDeveloper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * test
 * 2019/9/9 15:14
 * 启动类
 *
 * @author ck
 * @since
 **/
@SpringBootApplication
@ScanPackage(packages = {"com.ck.service"})
@Import({InterfaceRegistrar.class,ImplementRegistrar.class})
public class Application implements CommandLineRunner {

    @Autowired
    private Developer developer;

    @Autowired
    private Car audiCar;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        developer.code("ck");

        audiCar.color("audi");
    }
}
