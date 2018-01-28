package calcbot.slack;

import java.util.regex.Matcher;

import lombok.extern.slf4j.Slf4j;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import calcbot.util.Expression;


@Component
@Slf4j
public class SlackCalcBot extends Bot {
  
    @Autowired
    Expression expression;

    /**
     * Slack token from application.properties file.
     */
    @Value("${slackBotToken}")
    private String slackToken;

    @Override
    public  String getSlackToken() {
        return slackToken;
    }

    @Override
    public  Bot getSlackBot() {
        return this;
    }

    /**
     * Invoked when the bot receives a direct mention (@botname: message)
     * or a direct message. NOTE: These two event types are added by jbot
     * to make your task easier, Slack doesn't have any direct way to
     * determine these type of events.
     *
     * @param session
     * @param event
     */
    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {
        log.info("Event Received: {}", event.getType());
        String msgWithoutMentionedNames = removeMentionedNames(event.getText());
        boolean msgHasBackslashes = hasBackslashes(msgWithoutMentionedNames);
        String arithmeticExpressionString = removeSpecialChars(msgWithoutMentionedNames);
        if( !msgHasBackslashes &&
            hasAnOperator(arithmeticExpressionString) &&
            isValidExpression(arithmeticExpressionString) ) {
            reply(session, event, new Message("It is " + expression.eval(arithmeticExpressionString)));
        }
        else {
            reply(session, event, new Message("Would you like a cup of coffee?"));
        }
    }
    
    /**
     * Utility function to remove mentioned names
     * @param msg
     * @return message without mentioned names
     */
    private static String removeMentionedNames(String msg) {
        return msg.replaceAll("[<]{1}[@]{1}([\\d\\D]{9})[>]{1}", "");
    }
    
    /**
     * Utility function to check if the message has a backslash
     * @param msg
     * @return true if the message has a backslash
     */
    private static boolean hasBackslashes(String msg) {
        char[] charArray = msg.toCharArray();
        for(char ch: charArray) {
            if(ch == '\\') {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Utility function to remove special characters
     * @param msg
     * @return message without special characters
     */
    private static String removeSpecialChars(String msg) {
        return msg.replaceAll("[\\\\~`!@#\\$%&_={}|\\[\\]:;\"'<,>.\\?]", "");
    }
    
    /**
     * Utility function to check if a given expression has valid oparator
     * @param expression
     * @return true if a given expression has valid oparator
     */
    public static boolean hasAnOperator(String expression){
        return ((expression.contains("+")) ||
                (expression.contains("-")) ||
                (expression.contains("*")) ||
                (expression.contains("/")) );
    }
    
    /**
     * Utility function to check if a given character is an arithmetic operator
     * @param c
     * @return true if operator, false if not
     */
    public static boolean isAnOperator(char c){
        return (c == '*' || c == '/' || c == '+' || c == '-');
    }
    
    /**
     * Checks position and placement of (, ), and operators in a string
     * to make sure it is a valid arithmetic expression
     * @param expression
     * @return true if the string is a valid arithmetic expression, false if not
     */
    private static boolean isValidExpression(String expression){
        //remove unnecessary whitespaces
        expression = expression.replaceAll("\\s+", "");
        //TEST 1: False if expression starts or ends with an operator
        if (isAnOperator(expression.charAt(0)) || isAnOperator(expression.charAt(expression.length()-1)))
            return false;

        //TEST 2: False if test has mismatching number of opening and closing parantheses
        int unclosedParenthesis = 0;

        for (int i = 0; i < expression.length(); i++){
            if (expression.charAt(i) == '('){
                unclosedParenthesis++; 

                //SUBTEST: False if expression ends with '('
                if (i == expression.length()-1) return false;
            }
            if (expression.charAt(i) == ')'){
                unclosedParenthesis--;
                //SUBTEST: False if expression starts with ')'
                if (i == 0) return false;

            }
            if (isAnOperator(expression.charAt(i))){
                //TEST 3: False if operator is preceded by an operator or opening paranthesis 
                //or followed by closing paranthesis
                if (expression.charAt(i-1) == '(' || expression.charAt(i+1) == ')' || isAnOperator(expression.charAt(i+1))){
                    return false; 
                }

            }

        }
        return (unclosedParenthesis == 0);
    }

}
