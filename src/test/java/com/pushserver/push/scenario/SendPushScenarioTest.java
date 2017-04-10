package com.pushserver.push.scenario;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.pushserver.Application;
import com.pushserver.pushbullet.PushBulletException;
import com.pushserver.user.domain.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(properties = {"push.bullet.url=http://localhost:8089"})
public class SendPushScenarioTest {

    @Autowired
    private SendPushScenario sendPushScenario;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void shouldSendPush() throws Exception {
        //given
        String token = "token2";

        SendPushCommand command = new SendPushCommand();
        command.setTitle("Title");
        command.setBody("Body");

        User user = new User("bbcUser1", token);

        String pushId = "push1";
        stubPushBulletPushesEndpoint(token, pushId);

        //when
        SendPushResponse actual = sendPushScenario.send(command, user);
        assertEquals(pushId, actual.getPushId());
    }

    @Test
    public void shouldThrowExceptionIfSendPushEndpointReturnsError() throws Exception {
        //given
        String token = "token2";

        SendPushCommand command = new SendPushCommand();
        command.setTitle("Title");
        command.setBody("Body");

        User user = new User("bbcUser1", token);

        String error = "custom_error";
        String message = "Error message";

        stubPushBulletPushesEndpointReturningError(HttpStatus.NOT_ACCEPTABLE, error, message);
        try {
            sendPushScenario.send(command, user);
            fail("Expected " + PushBulletException.class.getSimpleName());
        } catch (Throwable e) {
            assertThat(e).isInstanceOf(PushBulletException.class);
            PushBulletException pushBulletException = (PushBulletException) e;
            assertEquals(error, pushBulletException.getErrorCode());
            assertEquals(message, pushBulletException.getMessage());
            assertEquals(HttpStatus.NOT_ACCEPTABLE, pushBulletException.getHttpStatus());
        }
    }


    private void stubPushBulletPushesEndpoint(String token, String pushId) {
        WireMock.stubFor(WireMock.post("/pushes")
            .withHeader("Access-Token", WireMock.equalTo(token))
            .willReturn(WireMock.aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"iden\":\"" + pushId + "\"}")));
    }


    private void stubPushBulletPushesEndpointReturningError(HttpStatus status, String errorCode, String message) {
        WireMock.stubFor(WireMock.post("/pushes")
            .willReturn(WireMock.aResponse()
                .withStatus(status.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"error\": {\"code\": \"" + errorCode + "\",\"message\": \"" + message + "\"}}")));
    }

}
