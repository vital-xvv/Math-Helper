package io.vital.mathematicalhelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static io.vital.mathematicalhelper.Util.realNumberRegex;

public class ExpressionResolver {

    private String expression;
    private String newExpression;
    private String firstFound;
    private static final Pattern pattern = Pattern.compile(realNumberRegex + "[+-/*]" + realNumberRegex);
    private static final Pattern patternWithBraces = Pattern.compile("\\(" + realNumberRegex + "[+-/*]" + realNumberRegex + "\\)");
    private static final Pattern patternBasicMultiDiv = Pattern.compile(realNumberRegex + "[/*]" + realNumberRegex);
    private static final Pattern patternBasicPlusMinus = Pattern.compile(realNumberRegex + "[+-]" + realNumberRegex);
    private static final Pattern numberPattern = Pattern.compile(realNumberRegex);
    public final Matcher matcher;
    public final Matcher matcherWithBraces;
    private String sign;
    private List<String> operands;
    private double result;
    private boolean solved;

    public ExpressionResolver(String expression) {
        this.expression = expression;
        matcher = pattern.matcher(expression);
        matcherWithBraces = patternWithBraces.matcher(expression);
        Matcher matcherMultiDiv = patternBasicMultiDiv.matcher(expression);
        Matcher matcherPlusMinus = patternBasicPlusMinus.matcher(expression);

        if(matcherWithBraces.find()) {
            //System.out.println("braces");
            firstFound = expression.substring(matcherWithBraces.start(), matcherWithBraces.end());
            sign = firstFound.replaceAll("[)(]", "").replaceAll(realNumberRegex, "");
            if(sign.isBlank()) {
                sign = "+";
            }
            Matcher number = numberPattern.matcher(firstFound);
            operands = new ArrayList<>();
            while(operands.size() != 2) {
                if(number.find()) operands.add(firstFound.substring(number.start(), number.end()));
            }
            //System.out.println(operands);
            calculateResult();
            newExpression = expression.replace(firstFound, String.valueOf(result));

        }else {
            if(matcher.find()){
                System.out.println("basic");
                if(matcherMultiDiv.find()) {
                    //System.out.println("Multiply or Divide");
                    firstFound = expression.substring(matcherMultiDiv.start(), matcherMultiDiv.end());
                    sign = firstFound.replaceAll(realNumberRegex, "");
                    operands = Arrays.asList(firstFound.split("[/*]"));
                }else if(matcherPlusMinus.find()){
                    //System.out.println("Plus Minus");
                    firstFound = expression.substring(matcherPlusMinus.start(), matcherPlusMinus.end());
                    sign = firstFound.replaceAll(realNumberRegex, "");
                    operands = Arrays.asList(firstFound.split("[+-]"));
                }
                calculateResult();
                newExpression = expression.replace(firstFound, String.valueOf(result));

            }
        }
    }

    private void calculateResult(){
        switch (sign) {
            case "-" -> result = Double.parseDouble(operands.get(0)) - Double.parseDouble(operands.get(1));
            case "*" -> result = Double.parseDouble(operands.get(0)) * Double.parseDouble(operands.get(1));
            case "/" -> result = Double.parseDouble(operands.get(0)) / Double.parseDouble(operands.get(1));
            default -> result = Double.parseDouble(operands.get(0)) + Double.parseDouble(operands.get(1));
        }
    }

    public String getNewExpression(){
        return newExpression;
    }

    public boolean isSolved() {
        if(newExpression!= null && newExpression.matches(String.format("%s=%s", realNumberRegex, realNumberRegex))){
            solved = !solved;
        }
        return solved;
    }

}
