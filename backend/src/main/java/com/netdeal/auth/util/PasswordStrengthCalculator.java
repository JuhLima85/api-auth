package com.netdeal.auth.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordStrengthCalculator {

    public static int calculatePasswordStrength(String password) {
        int score = 0;
        int len = password.length();
        
        score += len * 4;
        
        int uppercaseCount = countUppercaseLetters(password);
        if (uppercaseCount > 0) {
            score += (len - uppercaseCount) * 3;
        }
       
        int lowercaseCount = countLowercaseLetters(password);
        if (lowercaseCount > 0) {
            score += (len - lowercaseCount) * 3;
        }
       
        int digitCount = countDigits(password);
        score += digitCount * 4;
       
        int symbolCount = len - (uppercaseCount + lowercaseCount + digitCount);
        score += symbolCount * 6;

        int intermediateCount = countIntermediateCharacters(password);
        score += intermediateCount * 2;
       
        if (len >= 8 && (uppercaseCount > 0 || lowercaseCount > 0 || digitCount > 0 || symbolCount > 0)) {
            score += 2;
        }
      
        int varietyBonus = calculateVarietyBonus(password);
        score += varietyBonus;
        
        score -= len; 
        score -= countRepeatingCharacters(password); 
        score -= countConsecutiveLetters(password) * 2; 
        score -= countConsecutiveDigits(password) * 2; 
        score -= countConsecutiveCharacters(password) * 3; 
        
        score = Math.max(0, score); 
        
        score = Math.min(100, score); 

        return score;
    }

    private static int countUppercaseLetters(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                count++;
            }
        }
        return count;
    }

    private static int countLowercaseLetters(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                count++;
            }
        }
        return count;
    }

    private static int countDigits(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                count++;
            }
        }
        return count;
    }

    private static int countIntermediateCharacters(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                count++;
            }
        }
        return count;
    }

    private static int countRepeatingCharacters(String password) {
        int repetitions = 0;
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                repetitions++;
            }
        }
        return repetitions;
    }

    private static int countConsecutiveLetters(String password) {
        int consecutiveCount = 0;
        for (int i = 0; i < password.length() - 1; i++) {
            if (Character.isLetter(password.charAt(i)) && Character.isLetter(password.charAt(i + 1))) {
                consecutiveCount++;
            }
        }
        return consecutiveCount;
    }

    private static int countConsecutiveDigits(String password) {
        int consecutiveCount = 0;
        for (int i = 0; i < password.length() - 1; i++) {
            if (Character.isDigit(password.charAt(i)) && Character.isDigit(password.charAt(i + 1))) {
                consecutiveCount++;
            }
        }
        return consecutiveCount;
    }

    private static int countConsecutiveCharacters(String password) {
        int consecutiveCount = 0;
        for (int i = 0; i < password.length() - 2; i++) {
            if ((Character.isLetter(password.charAt(i)) && Character.isLetter(password.charAt(i + 1)) &&
                    Character.isLetter(password.charAt(i + 2))) ||
                    (Character.isDigit(password.charAt(i)) && Character.isDigit(password.charAt(i + 1)) &&
                            Character.isDigit(password.charAt(i + 2)))) {
                consecutiveCount++;
            }
        }
        return consecutiveCount;
    }

    private static int calculateVarietyBonus(String password) {
        int varietyBonus = 0;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSymbol = true;
            }
        }

        if (hasUppercase) varietyBonus++;
        if (hasLowercase) varietyBonus++;
        if (hasDigit) varietyBonus++;
        if (hasSymbol) varietyBonus++;

        return varietyBonus * 4;
    }
}
