import java.io.*;
import java.util.*;

/**
 * @author Femke Spaans
 * OpenAndCompareParents
 * Code to compare several parental files
 */
class OpenAndComparesParents {

    /**
     * Constructor for OpenAndComparesParents
     * Creates several hashmaps to save the information from the parents in.
     * next it creates an arraylist which will hold temporary values.
     * next it creates a list which contains 6 filenames.
     * It loops over the different filenames and opens them, splits them on the tabs and adds a hashmap.
     * next it creates an arraylist to add all the hashmaps into of the parents.
     * it also creates an arraylist for the child
     * next it loops over the parents, so every parent can be matched with every parent.
     * it then adds the overlapping keys from the hashmaps to an arraylist.
     * next it loops over the keys and compares them to the keys in the file variant_summary.txt
     * @param variant_hashmap
     * @throws IOException
     */
    public OpenAndComparesParents(HashMap<Integer, Variant2> variant_hashmap) throws IOException, NotValidFile {

        HashMap<String, ArrayList<String>> parent1 = new HashMap<>();
        HashMap<String, ArrayList<String>> parent2 = new HashMap<>();
        HashMap<String, ArrayList<String>> parent3 = new HashMap<>();
        HashMap<String, ArrayList<String>> parent4 = new HashMap<>();
        HashMap<String, ArrayList<String>> parent5 = new HashMap<>();
        HashMap<String, ArrayList<String>> parent6 = new HashMap<>();

        ArrayList <String> temp_values = new ArrayList<>();

        List<String> list_of_files = Arrays.asList("5443.23andme.3943", "8608.23andme.6967", "8998.23andme.7341",
                "9489.23andme.7786", "9590.23andme.8112", "9684.23andme.7952");

        for (String file : list_of_files) {
            File filename = new File(file);
            BufferedReader buffered_reader = new BufferedReader(new FileReader(filename));
            String line = buffered_reader.readLine();
            if(line!=null){
                if(line.contains("23andMe")){
                    while ((line = buffered_reader.readLine()) != null) {
                        if (line.startsWith("rs")) {
                            String[] splitting_spaces = line.split("\t");
                            if (filename.toString().equals("5443.23andme.3943")) {
                                temp_values.add(splitting_spaces[1]);
                                temp_values.add(splitting_spaces[2]);
                                temp_values.add(splitting_spaces[3]);
                                parent1.put(splitting_spaces[0], temp_values);
                            }
                            if (filename.toString().equals("8608.23andme.6967")) {
                                temp_values.add(splitting_spaces[1]);
                                temp_values.add(splitting_spaces[2]);
                                temp_values.add(splitting_spaces[3]);
                                parent2.put(splitting_spaces[0], temp_values);
                            }
                            if (filename.toString().equals("8998.23andme.7341")) {
                                temp_values.add(splitting_spaces[1]);
                                temp_values.add(splitting_spaces[2]);
                                temp_values.add(splitting_spaces[3]);
                                parent3.put(splitting_spaces[0], temp_values);
                            }
                            if (filename.toString().equals("9489.23andme.7786")) {
                                temp_values.add(splitting_spaces[1]);
                                temp_values.add(splitting_spaces[2]);
                                temp_values.add(splitting_spaces[3]);
                                parent4.put(splitting_spaces[0], temp_values);
                            }
                            if (filename.toString().equals("9590.23andme.8112")) {
                                temp_values.add(splitting_spaces[1]);
                                temp_values.add(splitting_spaces[2]);
                                temp_values.add(splitting_spaces[3]);
                                parent5.put(splitting_spaces[0], temp_values);
                            }
                            if (filename.toString().equals("9684.23andme.7952")) {
                                temp_values.add(splitting_spaces[1]);
                                temp_values.add(splitting_spaces[2]);
                                temp_values.add(splitting_spaces[3]);
                                parent6.put(splitting_spaces[0], temp_values);
                            }
                        }
                    }
                }else{
                    throw new NotValidFile();
                }
            }
        }

        ArrayList<HashMap<String, ArrayList<String>>> list_of_parents = new ArrayList<>();
        list_of_parents.add(parent1);
        list_of_parents.add(parent2);
        list_of_parents.add(parent3);
        list_of_parents.add(parent4);
        list_of_parents.add(parent5);
        list_of_parents.add(parent6);

        ArrayList<ArrayList<String>> kid = new ArrayList<>();


        for (int i = 0; i < 5; i++){
            for (int j = i + 1; j < 5; j++) {
                ArrayList<String> overlappingKeys = CompareKeys(list_of_parents.get(i), list_of_parents.get(j));
                for(String key: overlappingKeys){
                    try{
                        int rs_key  = Integer.parseInt(key.substring(2));
                        Variant2 variant = variant_hashmap.get(rs_key);
                        String alternate_allele = variant.getAlternate_allele(); // dit gaat mis, dit blijft null
                        if(list_of_parents.get(i).get(key).get(2).contains(alternate_allele) &&
                                list_of_parents.get(j).get(key).get(2).contains(alternate_allele) && variant.getPathogenicity()==1){
                            kid.add(new ArrayList<>(Arrays.asList(key, alternate_allele + alternate_allele, variant.getChromosome(),
                                    list_of_parents.get(i).get(key).get(2), list_of_parents.get(j).get(key).get(2),
                                    list_of_files.get(i), list_of_files.get(j))));
                        }
                    } catch (NullPointerException ignored) {
                    }

                }
            }
        }
        WriteFile(kid);
    }

    /**
     * Creates a file to write the output of the comparing parents in,
     * which will show what diseases a child will have if created by these parents.
     * @param kid an arraylist of arraylists
     * @throws IOException
     */
    public void WriteFile(ArrayList<ArrayList<String>> kid) throws IOException {
        FileWriter myWriter = new FileWriter("Output.txt");
        myWriter.write("Rsid\tGenotype child\tChromosome\tAllele parent 1\tAllele parent 2\tFile parent 1\tFile parent 2\n");
        for(ArrayList<String> disease:kid){
            myWriter.append(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\n",disease.get(0), disease.get(1), disease.get(2),
                    disease.get(3), disease.get(4), disease.get(5), disease.get(6)));
        }
        myWriter.close();
    }

    /**
     * Compares keys with one another, if the keys are identical it saves the keys
     * @param hashMap contains keys of the first parent
     * @param hashMap1 contains keys of the second parent
     * @return found_keys
     */
    private ArrayList<String> CompareKeys(HashMap<String,ArrayList<String>> hashMap, HashMap<String, ArrayList<String>> hashMap1) {
        ArrayList<String> found_keys = new ArrayList<>();
        for(String key:hashMap.keySet()){
            if(hashMap1.containsKey(key)){
                found_keys.add(key);
            }
        }
        return found_keys;
    }

    }

/**
 * Custom exeception which throws an error if the files used are not 23andme files.
 */
class NotValidFile extends Exception{
    public NotValidFile(){
        super("Not a valid file, please only use 23andme files.");
    }
}
