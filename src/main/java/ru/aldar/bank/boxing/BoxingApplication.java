package ru.aldar.bank.boxing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.aldar.bank.boxing.mapper.ReadXMLFile;

@SpringBootApplication
public class BoxingApplication implements ApplicationRunner {

    private
    ReadXMLFile readXMLFile;

    @Autowired
    public BoxingApplication(ReadXMLFile readXMLFile) {
        this.readXMLFile = readXMLFile;
    }

    public static void main(String[] args) {

        SpringApplication.run(BoxingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String arg : args.getSourceArgs()) {
            System.out.println(arg);
            if (arg.contains(".xml")) {
                readXMLFile.parse(arg);
            }
        }
    }
}
/*
java -jar boxing-1.jar C:\Java\boxing\storage.xml
 */
