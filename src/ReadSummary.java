import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Femke Spaans
 * ReadSummary
 * Code to read summary file
 */
public class ReadSummary {

    static String filename = "variant_summary.txt";

    /**
     * The method read_file starts by making a HashMap to save the information in from the file variant_summarry.txt
     * Next it reads the file making use of a BufferedReader, it does so for every line in the file,
     * until the file is empty and starts with the lines which do not contain a #.
     * It then splits every line on the tabs, because variant_summary.txt is a tab delimited file.
     * Afterwards it stores every important variable making use of the class Variant.
     * If it does not contain the key already, it adds the new variable to a HashMap.
     * @throws IOException
     */
    public static HashMap<Integer, Variant2> read_file() throws IOException {
        HashMap<Integer, Variant2> variant_hashmap = new HashMap<>();
        BufferedReader buffered_reader1 = new BufferedReader(new FileReader(filename));
        String line = "";
        while ((line = buffered_reader1.readLine()) != null ) { //&& !line.startsWith("#")
            if(!line.startsWith("#")){
                String[] splitting_tabs = line.split("\t");
                Variant2 variant_object = new Variant2(Integer.parseInt(splitting_tabs[0]), Integer.parseInt(splitting_tabs[19]),
                        Integer.parseInt(splitting_tabs[7]), Integer.parseInt(splitting_tabs[3]), Integer.parseInt(splitting_tabs[9]),
                        splitting_tabs[1], splitting_tabs[33], splitting_tabs[13], splitting_tabs[32], splitting_tabs[18]);
                if(!variant_hashmap.containsKey(variant_object.getRs())){
                    variant_hashmap.put(variant_object.getRs(), variant_object);
                }
            }
        }
        return variant_hashmap;
    }
}


