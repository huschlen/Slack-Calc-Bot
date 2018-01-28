package calcbot.slack;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.SlackService;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SlackCalcBotTest {
  
  
    private static String USER_NAME = "SlackBot";
    private static String USER_ID = "UEADGH12S";
  
    @Mock
    private WebSocketSession session;
  
    @Mock
    private SlackService slackService;
  
    @InjectMocks
    private TestSlackCalcBot bot;
  
    @Rule
    public OutputCapture capture = new OutputCapture();
  
    @Before
    public void init() {
        // set user
        User user = new User();
        user.setName(USER_NAME);
        user.setId(USER_ID);
        // set rtm
        when(slackService.getDmChannels()).thenReturn(Arrays.asList("D1E79BACV", "C0NDSV5B8"));
        when(slackService.getCurrentUser()).thenReturn(user);
        when(slackService.getWebSocketUrl()).thenReturn("");
    }

    @Test
    public void when_DirectMention_Should_InvokeOnDirectMention() throws Exception {
        TextMessage textMessage = new TextMessage("{\"type\": \"message\"," +
                "\"ts\": \"1358878749.000002\"," +
                "\"user\": \"U023BECGF\"," +
                "\"text\": \"<@UEADGH12S>: Hello\"}");
        bot.handleTextMessage(session, textMessage);
        assertThat(capture.toString(), containsString("Hi, I am"));
    }

    @Test
    public void when_DirectMessage_Should_InvokeOnDirectMessage() throws Exception {
        TextMessage textMessage = new TextMessage("{\"type\": \"message\"," +
                "\"ts\": \"1358878749.000002\"," +
                "\"channel\": \"D1E79BACV\"," +
                "\"user\": \"U023BECGF\"," +
                "\"text\": \"Hello\"}");
        bot.handleTextMessage(session, textMessage);
        assertThat(capture.toString(), containsString("this is a direct message"));
    }
    
    /**
     * Slack Bot for unit tests.
     */
    public static class TestSlackCalcBot extends Bot {
        
        @Override
        public String getSlackToken() {
            return "slackToken";
        }

        @Override
        public Bot getSlackBot() {
            return this;
        }

        @Controller(events = EventType.DIRECT_MENTION)
        public void onDirectMention(WebSocketSession session, Event event) {
            System.out.println("Hi, I am SlackBot");
        }

        @Controller(events = EventType.DIRECT_MESSAGE)
        public void onDirectMessage(WebSocketSession session, Event event) {
            System.out.println("Hi, this is a direct message.");
        }

    }

}
