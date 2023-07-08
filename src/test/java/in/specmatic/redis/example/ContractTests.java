package in.specmatic.redis.example;

import in.specmatic.redis.example.DemoApplication;
import in.specmatic.redis.stub.RedisStub;
import in.specmatic.stub.ContractStub;
import in.specmatic.test.SpecmaticJUnitSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

import static in.specmatic.stub.API.createStub;
import static org.springframework.boot.SpringApplication.run;

public class ContractTests extends SpecmaticJUnitSupport {
    private static final String LOCALHOST = "localhost";
    private static final int SPECMATIC_STUB_PORT = 9000;
    private static final String APP_PORT = "8080";
    private static final int REDIS_PORT = 6379;
    private static ContractStub stub;
    private static RedisStub redisStub;
    private static ConfigurableApplicationContext context;
    @BeforeAll
    public static void setUp() {
        System.setProperty("host", LOCALHOST);
        System.setProperty("port", APP_PORT);
        System.setProperty("endpointsAPI", "http://localhost:8080/actuator/mappings");
        System.setProperty("spring.profiles.active", "contract-tests");
        startRedisMock();
        stub = createStub(LOCALHOST, SPECMATIC_STUB_PORT);
        context = run(DemoApplication.class);
    }

    private static void startRedisMock() {
        redisStub = new RedisStub(LOCALHOST, REDIS_PORT);
        redisStub.start();
        setUpRedisMock();
    }

    private static void setUpRedisMock() {
        redisStub
                .when("get")
                .with(new String[]{"Description-1"})
                .thenReturnString("Grocery store with free home delivery!");
        redisStub
                .when("sadd")
                .with(new String[]{"Products-1","912340","956780"})
                .thenReturnLong(2);
        redisStub
                .when("zrevrange")
                .with(new String[]{"Products-1","0","(string)"})
                .thenReturnArray(new String[]{"ABC Brand Powder","XYZ Brand Soap"});
    }

    @AfterAll
    public static void tearDown() throws IOException {
        if (stub != null) {
            stub.close();
        }
        if (redisStub != null) {
            redisStub.stop();
        }
        if(context != null){
            context.stop();
        }
    }
}