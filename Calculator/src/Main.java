import java.util.Scanner;

public class Main {

    enum RomanNumeral {
        I(1), V(5), X(10), L(50), C(100);
        final private int value;
        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    static void checkForRoman (String arg) throws Exception {
        int countI = 0, countV = 0, countX = 0, countIncr = 0;
        for (int i = 0; i < arg.length(); i++) {
            switch (arg.charAt(i)) {
                case 'I':
                    countI++;
                    break;
                case 'V':
                    countV++;
                    break;
                case 'X':
                    countX++;
                    break;
                default:
                    throw new Exception("Non roman nor integer values");
            }
        }
        for (int i = 0; i < arg.length() - 1; i++) {
            if (changeLexemToNumber(arg.charAt(i)) < changeLexemToNumber(arg.charAt(i+1))) {
                ++countIncr;
            }
        }
        if (countIncr > 1)
            throw new Exception("Incorrect roman value");
        if (countI > 3 || countV > 1 || countX > 1)
            throw new Exception("Incorrect roman value");
        if (countIncr > 0 && countI > 1)
            throw new Exception("Incorrect roman value");
    }
    static boolean checkForInt (String arg)  {
        try {
            Integer.parseInt(arg);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    static int convertRomanToInt (String romanArg) {
        int arabResult = 0, currentValue, nextValue;
        for (int i = 0; i < romanArg.length()-1;i++) {
            currentValue = changeLexemToNumber(romanArg.charAt(i));
            nextValue = changeLexemToNumber(romanArg.charAt(i+1));
            if (currentValue < nextValue)
                arabResult -= currentValue;
            else
                arabResult += currentValue;
        }
        arabResult += changeLexemToNumber(romanArg.charAt(romanArg.length()-1));

        return arabResult;
    }

    static String convertIntToRoman (int arabResult) {
        int steps;
        int[] romanValues = {
                100,
                50,
                10,
                5,
                0
        };
        StringBuilder result = new StringBuilder("");

        for (int i=0;arabResult>0;i++) {

            if (arabResult / 10 == 0)
                steps = 1;
            else
                steps = 10;

            if (arabResult >= romanValues[i]) {
                arabResult = arabResult - romanValues[i];
                result.append(romanValues[i]);
                result.append(' ');
                while (arabResult >= steps) {
                    arabResult -= steps;
                    result.append(steps);
                    result.append(' ');

                }
                continue;
            }
            if (arabResult < romanValues[i] && arabResult + steps >= romanValues[i]) {
                arabResult = arabResult + steps - romanValues[i];
                result.append(steps);
                result.append(' ');
                result.append(romanValues[i]);
                result.append(' ');

            }
        }

        String[] arabNumbArr = result.toString().split(" ");
        StringBuilder finalResult = new StringBuilder();
        for (int i = 0; i < arabNumbArr.length; i++) {
            switch (arabNumbArr[i]) {
                case "100": finalResult.append('C');
                    break;
                case "50": finalResult.append('L');
                    break;
                case "10": finalResult.append('X');
                    break;
                case "5": finalResult.append('V');
                    break;
                case "1": finalResult.append('I');
                    break;
                default:
                    break;

            }
        }
        return finalResult.toString();
    }

    static int changeLexemToNumber (char currentValue) {
        return RomanNumeral.valueOf(Character.toString(currentValue)).getValue();
    }

    public static String calc(String expression) throws Exception {

        String[] exprArgum = expression.split(" ");
        if (exprArgum.length != 3)
            throw new Exception("incorrect input");
        int result, arabArg1, arabArg2;

        if (checkForInt(exprArgum[0]) ==  checkForInt(exprArgum[2])) {
            if (!checkForInt(exprArgum[0])) {
                checkForRoman(exprArgum[0].toUpperCase());
                checkForRoman(exprArgum[2].toUpperCase());
                arabArg1 = convertRomanToInt(exprArgum[0].toUpperCase());
                arabArg2 = convertRomanToInt(exprArgum[2].toUpperCase());
            }
            else {
                arabArg1 = Integer.parseInt(exprArgum[0]);
                arabArg2 = Integer.parseInt(exprArgum[2]);
            }

        }
        else {
            throw new Exception("different types of values");
        }

        if (arabArg1 > 10 || arabArg1 < 0 || arabArg2 < 0 || arabArg2 > 10)
            throw new Exception("values are too high or too small");

        switch (exprArgum[1]) {
            case "+":
                result = arabArg1 + arabArg2;
                break;
            case "-":
                result = arabArg1 - arabArg2;
                break;
            case "*":
                result = arabArg1 * arabArg2;
                break;
            case "/":
                result = arabArg1 / arabArg2;
                break;
            default:
                throw new Exception("Unknown operation");
        }

        if (checkForInt(exprArgum[0])) {

            return Integer.toString(result);
        }

        if (result <= 0) {
            throw new Exception ("roman numbers cant be zero or lower");
        }
        return (convertIntToRoman(result));
    }
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner (System.in);
        System.out.println("Input an expression: ");
        System.out.println(calc(in.nextLine()));
    }
}

