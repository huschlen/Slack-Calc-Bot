package calcbot.util;

@FunctionalInterface
public interface Expression {
    double eval(final String str);
}
