package com.chat.application.common;

public class RandomStringGenerator{

    private static int randomStringLength = 25 ;
    private static boolean allowSpecialCharacters = true ;
    private static String specialCharacters = "!@$%*-_+:";
    private static boolean allowDuplicates = false ;

    private static boolean isAlphanum = false;
    private static boolean isNumeric = false;
    private static boolean isAlpha = false;
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static boolean mixCase = false;
    private static final String capAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String num = "0123456789";

    public static String getRandomString() {
        String returnVal = "";
        int specialCharactersCount = 0;
        int maxspecialCharacters = randomStringLength/4;

        try {
            StringBuffer values = buildList();
            for (int inx = 0; inx < randomStringLength; inx++) {
                int selChar = (int) (Math.random() * (values.length() - 1));
                if (allowSpecialCharacters)
                {
                    if (specialCharacters.indexOf("" + values.charAt(selChar)) > -1)
                    {
                        specialCharactersCount ++;
                        if (specialCharactersCount > maxspecialCharacters)
                        {
                            while (specialCharacters.indexOf("" + values.charAt(selChar)) != -1)
                            {
                                selChar = (int) (Math.random() * (values.length() - 1));
                            }
                        }
                    }
                }
                returnVal += values.charAt(selChar);
                if (!allowDuplicates) {
                    values.deleteCharAt(selChar);
                }
            }
        } catch (Exception e) {
            returnVal = "Error While Processing Values";
        }
        return returnVal;
    }

    private static StringBuffer buildList() {
        StringBuffer list = new StringBuffer(0);
        if (isNumeric || isAlphanum) {
            list.append(num);
        }
        if (isAlpha || isAlphanum) {
            list.append(alphabet);
            if (mixCase) {
                list.append(capAlpha);
            }
        }
        if (allowSpecialCharacters)
        {
            list.append(specialCharacters);
        }
        int currLen = list.length();
        String returnVal = "";
        for (int inx = 0; inx < currLen; inx++) {
            int selChar = (int) (Math.random() * (list.length() - 1));
            returnVal += list.charAt(selChar);
            list.deleteCharAt(selChar);
        }
        list = new StringBuffer(returnVal);
        return list;
    }   

}