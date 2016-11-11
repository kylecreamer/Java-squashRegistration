package info.hccis.cis2232example;

public class Utility {

    public static String getPaddedString(String unpadded, int length) {

        int lengthIn = unpadded.length();
        String output = unpadded;
        for (int i = lengthIn; i < length; ++i) {
            output += " ";
        }
        //int lengthOut = output.length();
        return output;
        
    }

}
