package Register;

import java.util.ArrayList;

public class AssignmentEightPointSix {

    @UnderTest(id="owners")
    private ArrayList<Owner> ownerList = new ArrayList();
    @UnderTest(id="dogs")
    private ArrayList<Dog> listOfDogs = new ArrayList();
    private InputReader userInput = new InputReader();



    public AssignmentEightPointSix(){
        addDogsAndOwners();
    }


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


    @UnderTest(id="U8.6")
    private void removeDogFromOwner() {
        String dogInput = userInput.inputString("Enter the name of the dog");
        Dog dog = findDogName(dogInput);
        if (dog == null) {
            System.out.println("Error: no dog with that name");
        } else if (dog.getOwner() == null) {
            System.out.println("Error: " + dog.getName() + " has no owner");
        } else {

            dog.removeOwnerFromDog();

        }
    }

    @UnderTest(id="U7.2")
    public void takeUserTailInput() {
        if (listOfDogs.size() != 0) {
            double tailLenght = userInput.inputDoubleNr("Smallest tail length to display");
            listTailLength(tailLenght);
        } else {
            System.out.println("Error: No dogs in register!");
        }
    }

    private void listTailLength(double tailLength) {
        ArrayList<Dog> qualifyingDogs = new ArrayList<>();
        for (Dog dog : listOfDogs) {
            if (dog.getTailLength() > tailLength) {
                qualifyingDogs.add(dog);
            }
        }

        if (qualifyingDogs.size() == 0) {
            System.out.println("No dogs fulfill the reqs!");
        } else {
            for (Dog qualiDog : qualifyingDogs) {
                System.out.println(qualiDog);
            }
        }
    }

    @UnderTest(id="U8.4")
    private void listOwner() {
        if (ownerList.size() != 0) {
            for (Owner owner : ownerList) {
                System.out.print(owner.getName() + " ");
                for (Dog dog : listOfDogs) {
                    if (owner.hasDogInArray(dog)) {
                        System.out.print(dog + " ");
                    }
                }

                System.out.println();
            }
        } else {
            System.out.println("Error: no owners in register");
        }
    }














    public void executeMainLoop(){
        String menuInput;
        printMenu();
        do {
            menuInput = handleMainLoopInput();
        }while (!menuInput.equalsIgnoreCase("exit"));
        System.out.println("Goodbye!");
    }

    private void printMenu(){
        System.out.println("* register new dog");
        System.out.println("* list dogs");
        System.out.println("* increase age");
        System.out.println("* remove dog");
        System.out.println("* register new owner");
        System.out.println("* give dog");
        System.out.println("* list owners");
        System.out.println("* remove owned dog");
        System.out.println("* remove owner");
        System.out.println("* exit");
    }

    private String handleMainLoopInput() {
        String menuInput = userInput.inputString("Command");
        switch (menuInput) {
            case "list dogs":
                takeUserTailInput();
                break;
            case "remove dog":
                //deleteDog();
                break;
            case "give dog":
                giveDogToOwner();
                break;
            case "list owners":
                listOwner();
                break;
            case "remove owned dog":
                removeDogFromOwner();
                break;
            case "exit":
                return menuInput;
            default:
                System.out.println("Error: Wrong command please try again");
                printMenu();
        }
        return menuInput;
    }
//----------------------------------------------------------------------------------
// tests

    private void addDogsAndOwners(){
        Dog dog1 = new Dog("Lady", "Grand danois", 6, 6);
        Dog dog2 = new Dog("Gromit", "Dachshund", 11, 9);
        Dog dog3 = new Dog("Rowlf", "Tax", 11, 9);
        Dog dog4 = new Dog("Sparky", "Beagle", 12, 11);
        Dog dog5 = new Dog("Rin Tin Tin", "Beagle", 7, 19);
        Dog dog6 = new Dog("Shiloh", "Golden retriever", 18, 11);
        Dog dog7 = new Dog("Skipper", "Chihuahua", 14, 15);

        listOfDogs.add(dog1);
        listOfDogs.add(dog2);
        listOfDogs.add(dog3);
        listOfDogs.add(dog4);
        listOfDogs.add(dog5);
        listOfDogs.add(dog6);
        listOfDogs.add(dog7);

        Owner owner1 = new Owner("H");
        Owner owner2 = new Owner("J");
        Owner owner3 = new Owner("K");
        Owner owner4 = new Owner("L");
        Owner owner5 = new Owner("E");

        ownerList.add(owner1);
        ownerList.add(owner2);
        ownerList.add(owner3);
        ownerList.add(owner4);
        ownerList.add(owner5);
    }












}
