package Register;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @author Abraham Aleman abal7627
 */
public class InputReader {
    private static List<InputStream> listOfStreams = new ArrayList<>();
    private Scanner input;

    public InputReader(InputStream inputStream){
        if(listOfStreams.contains(inputStream)){
            throw new IllegalStateException("Input stream existerar redan!");
        }
        listOfStreams.add(inputStream);
        this.input = new Scanner(inputStream);
    }

    public InputReader(){
        this(System.in);
    }


    public int inputIntNr(String text){
        System.out.print(text + "?> ");
        int numberInput = input.nextInt();
        input.nextLine();
        return numberInput;
    }

    public double inputDoubleNr(String text){
        System.out.print(text + "?> ");
        double doubleInput = Double.parseDouble(input.next().replace("," , "."));
        input.nextLine();
        return doubleInput;
    }

    public String inputString(String text) {
        String inputText = "";
        while (inputText.trim().length() == 0){
            System.out.print(text + "?> ");
            inputText = input.nextLine();
            if (inputText.trim().length() == 0){
                System.out.println("Error: the name can´t be empty");
            }
        }
        return inputText.trim();
    }
}

