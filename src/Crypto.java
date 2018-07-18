import javax.xml.bind.SchemaOutputResolver;
import java.util.Scanner;

public class Crypto {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter String: ");
        String text = scan.nextLine();
        System.out.print("\nEnter number of shift: ");
        int shift  = scan.nextInt();
        System.out.print("\nEnter number of groups: ");
        int groupNum = scan.nextInt();
        System.out.println("\nEncrypted: " + encryptString(text, shift,groupNum));
        System.out.print("\nWant to decrypt text?[y/n]: ");
        while(true){
            String ans = scan.nextLine();
            if(ans.startsWith("y") || ans.startsWith("Y")){
                System.out.print("Enter String: ");
                text = scan.nextLine();
                System.out.print("\nEnter number of shift: ");
                shift  = scan.nextInt();
                System.out.println("Decrypted: " + decryptString(text, (shift *= -1)));
                break;
            }else if(ans.startsWith("n") || ans.startsWith("N")){
                System.out.println("Okay, bye!");
                break;
            }
        }
    }

    public static String normalizeText(String text){
        text = text.replaceAll("[^A-Za-z]+", "").toUpperCase();
        return text;
    }

    public static String shiftAlphabet(int shift) {
        int start = 0; //the ascii value of starting letter
        if (shift < 0) {
            start = (int) 'Z' + shift + 1;
        } else {
            start = 'A' + shift;
        }
        String result = "";
        char currChar = (char) start;
        for(; currChar <= 'Z'; ++currChar) {
            result = result + currChar;
        }
        if(result.length() < 26) {
            for(currChar = 'A'; result.length() < 26; ++currChar) {
                result = result + currChar;
            }
        }
        return result;
    }

    public static String caesarify(String txt, int shift){
        String alphabet = shiftAlphabet(shift);
        String res = "";
        for (int i = 0; i < txt.length(); i++) {
            int idx = alphabet.indexOf(txt.charAt(i));
            if (shift < 0) {
                if((idx - shift) < 0) {
                    idx -= shift;
                    idx = alphabet.length() - (idx+1);
                    res += alphabet.charAt(idx);
                }else{
                    if(shift < 0)
                        res += alphabet.charAt(idx + shift);
                    else
                        res += alphabet.charAt(idx + shift);
                }
            }else{
                if((idx + shift) >= alphabet.length()){
                    System.out.println("Index of character: " + idx);
                    idx =Math.abs((alphabet.length() - idx) - shift);
                    System.out.println("index: "+idx);
                    res += alphabet.charAt(idx);
                }else{
                    if(shift < 0)
                        res += alphabet.charAt(idx + shift);
                    else
                        res += alphabet.charAt(idx + shift);
                }
            }
        }
        return res;
    }

    public static String groupify(String txt, int num){
        String res = "";
        String oddLetter = "";
        if(txt.length() % num != 0){
            oddLetter = txt.substring((txt.length() - (txt.length() % num )), txt.length());
            txt = txt.substring(0, (txt.length() - (txt.length() % num )));
            while(oddLetter.length() != num){
                oddLetter += "x";
            }
        }
        for (int i = 0; i < txt.length(); i+=num) {
            res = res + txt.substring(i, (i + num)) + " ";
        }
        res  = res + oddLetter;
        return res.trim();
    }

    public static String encryptString(String txt, int shift, int groupBy){
        txt = normalizeText(txt);
        //no obify
        txt = caesarify(txt, shift);
        txt = groupify(txt, groupBy);

        return txt;
    }

    public static String decryptString(String txt, int shift){
        //remove x
        txt = ungroupify(txt);
        txt = normalizeText(txt);
        txt = caesarify(txt, shift);
        return txt;
    }

    public static String ungroupify(String txt){
        return normalizeText(txt.replaceAll("x", ""));
    }
}
