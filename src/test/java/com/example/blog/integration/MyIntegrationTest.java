package com.example.blog.integration;

import com.example.blog.Application;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class MyIntegrationTest {
    @Inject
    Environment environment;

    @Test
    public void notLoginByDefault() throws IOException, InterruptedException {
        String port = environment.getProperty("local.server.port");
        System.out.println("端口是" + port);
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:" + port + "/auth");
        HttpResponse response = client.execute(httpGet);
        Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
        Assertions.assertTrue(EntityUtils.toString(response.getEntity()).contains("用户未登录"));
    }
}