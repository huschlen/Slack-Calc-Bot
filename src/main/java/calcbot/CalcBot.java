package calcbot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "calcbot.slack", "calcbot.util.expressionImpl"})
public class CalcBot {
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(CalcBot.class, args);
    }

}
