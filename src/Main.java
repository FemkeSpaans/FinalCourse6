import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, NotValidFile {
        try{
            new Commandline1();
            new OpenAndComparesParents(ReadSummary.read_file());
        } catch (NotAValidMD5 | NotValidOSSystem | NotValidFile notAValidMD5) {
            notAValidMD5.printStackTrace();
        } catch (IOException exception) {
            System.out.println("Something went wrong, try again.");
        }
    }
}

