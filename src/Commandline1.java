import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * @author Femke Spaans
 * Commandline
 * Code to run several commands in the terminal
 */
public class Commandline1 {

    /**
     * Creates 2 strings, one for each hashfunction
     * Executes 5 commands (processes), if the command is one of two it will split on the space
     * and add them to the strings.
     * next it checks if the strings are the same, if they are the code continues
     * if they are not the same a custom exception is thrown.
     */
    public Commandline1() throws NotAValidMD5, IOException, NotValidOSSystem {

        String os_system = System.getProperty("os.name");
        if(os_system.equals("Linux")){
            String md5_value1 = "";
            String md5_value2 = "";
            List<String> list_of_commands = Arrays.asList("curl -O ftp://ftp.ncbi.nlm.nih.gov/pub/clinvar/tab_delimited/variant_summary.txt.gz",
                    "curl -O ftp://ftp.ncbi.nlm.nih.gov/pub/clinvar/tab_delimited/variant_summary.txt.gz.md5",
                    "md5sum variant_summary.txt.gz", "cat variant_summary.txt.gz.md5", "gunzip variant_summary.txt.gz");

            for (String command : list_of_commands) {
                Process process = Runtime.getRuntime().exec((command));
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (command.equals("md5sum variant_summary.txt.gz")) {
                        String[] splitting_spaces = line.split(" ");
                        md5_value1 = splitting_spaces[0];
                    }
                    if (command.equals("cat variant_summary.txt.gz.md5")) {
                        String[] splitting_spaces = line.split(" ");
                        md5_value2 = splitting_spaces[0];
                    }
                }
            }

            if (md5_value1.equals(md5_value2)) {
                System.out.println("They are equal");
            } else {
                throw new NotAValidMD5();
            }

        }else{
            throw new NotValidOSSystem();
        }
    }
}

/**
 * Custom exeception which throws an error if the strings arent equal to one another.
 */
class NotAValidMD5 extends Exception{
    public NotAValidMD5(){
        super("The md5check shows different hashfunctions");
    }
}

/**
 * Custom exeception which throws an error if the operating system is not linux.
 */
class NotValidOSSystem extends Exception{
    public NotValidOSSystem(){
        super("Not a valid operating system");
    }
}
