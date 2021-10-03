import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String result = exec(" adb shell ip addr show wlan0");
        String ip = grabIP(result);
        System.out.println(ip);
    }

    /**
     * Grab the IP address string from the result of adb shell command.
     * @param content the result of adb shell command.
     * @return ip address or null result.
     */
    public static String grabIP(String content) {
        String reg = "inet\\s(\\d+?\\.\\d+?\\.\\d+?\\.\\d+?)/\\d+";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        if (!matcher.find()) {
            return null;
        }
        return matcher.group(1);
    }

    /**
     * Execute the inputted command.
     * @param cmd command string.
     * @return command result string.
     */
    public static String exec(String cmd) {
        try {
            StringBuilder sb = new StringBuilder();
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            bufferedReader.close();
            process.destroy();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
