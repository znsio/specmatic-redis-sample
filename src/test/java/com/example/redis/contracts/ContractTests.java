package com.example.redis.contracts;

import com.example.redis.DemoApplication;
import in.specmatic.redis.mock.RedisMock;
import in.specmatic.stub.ContractStub;
import in.specmatic.test.SpecmaticJUnitSupport;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static in.specmatic.stub.API.createStub;

public class ContractTests extends SpecmaticJUnitSupport {
    private static final String SPECMATIC_STUB_HOST = "localhost";
    private static final int SPECMATIC_STUB_PORT = 9000;
    private static ContractStub stub;
    private static RedisMock redisMock;
    private static ConfigurableApplicationContext context;
    @BeforeAll
    public static void setUp() {
        System.setProperty("host", "localhost");
        System.setProperty("port", "8080");
        System.setProperty("endpointsAPI", "http://localhost:8080/actuator/mappings");
        System.setProperty("spring.profiles.active", "contract-tests");
        stub = createStub(SPECMATIC_STUB_HOST, SPECMATIC_STUB_PORT);
        context = SpringApplication.run(DemoApplication.class);
    }

    @BeforeEach
    public void before() throws Exception {
        startRedisServer();
    }

    private void startRedisServer() throws IOException {
        redisMock = new RedisMock("localhost", 6379);
        redisMock.start();
        setRedisExpectations();
    }

    private void setRedisExpectations() throws IOException {
        File directoryPath = new File("src/test/resources/redis-expectations");
        File[] filesList = directoryPath.listFiles();
        assert filesList != null;
        Arrays.sort(filesList);
        for (File file : filesList) {
            redisMock.setExpectation(FileUtils.readFileToString(new File(file.getPath())));
        }
    }

    @AfterAll
    public static void tearDown() throws IOException {
        if (stub != null) {
            stub.close();
        }
        if (redisMock != null) {
            redisMock.stop();
        }
        if(context != null){
            context.stop();
        }
    }
}