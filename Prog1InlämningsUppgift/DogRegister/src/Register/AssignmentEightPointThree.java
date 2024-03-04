package Register;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abraham Aleman abal7627
 */


public class AssignmentEightPointThree {

    @UnderTest(id="owners")
    private ArrayList<Owner> ownerList = new ArrayList();
    @UnderTest(id="dogs")
    private ArrayList<Dog> listOfDogs = new ArrayList();
    private InputReader userInput = new InputReader();




    @UnderTest(id="U8.3")
    private void giveDogToOwner() {

        String dogInput = userInput.inputString("Enter the name of the dog");
        Dog dog = findDogName(dogInput);
        if (dog == null) {
            System.out.println("Error: no dog with that name");
            return;
        }
        if (dog.getOwner() != null) {
            System.out.println("Error: the dog already has an owner");
            return;
        }
        String ownerInput = userInput.inputString("Enter the name of the owner");
        Owner owner = findOwner(ownerInput);
        if (owner == null) {
            System.out.println("Error: no owner with that name");
            return;
        }
        owner.addDog(dog);
        System.out.println(owner.getName() + " now owns " + dog.toString());


    }


    private Owner findOwner(String ownerName) {
        for (Owner owner : ownerList) {
            if (owner.getName().equalsIgnoreCase(ownerName)) {
                return owner;
            }
        }
        return null;
    }


    private Dog findDogName(String dogName) {
        for (Dog dog : listOfDogs) {
            if (dog.getName().equalsIgnoreCase(dogName)) {
                return dog;
            }
        }
        return null;
    }





}
