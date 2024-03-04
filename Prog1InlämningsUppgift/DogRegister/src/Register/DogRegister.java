package Register;
    
    import java.util.ArrayList;
    import java.util.Collections;

    /**
     * @author Abraham Aleman abal7627
     */
    public class DogRegister {
        @UnderTest(id = "owners")
        private ArrayList<Owner> ownerList = new ArrayList();
        @UnderTest(id = "dogs")
        private ArrayList<Dog> listOfDogs = new ArrayList();
        private InputReader userInput = new InputReader();

    //----------------------------------------------------------------------
    // Add/create new dog tog to listOfDogs
        @UnderTest(id="U6.4")
        private Dog createDog() {
            String dogName = userInput.inputString("Name");
            String dogBreed = userInput.inputString("Breed");
            int dogAge = userInput.inputIntNr("Age");
            int dogWeight = userInput.inputIntNr("Weight");
    
            Dog newDog = new Dog(dogName, dogBreed, dogAge, dogWeight);
            System.out.println(newDog.getName() + " added to the register");
    
            return newDog;
        }
        private void addDogToList(){
            Dog newDog = createDog();
            listOfDogs.add(newDog);
        }

    //-----------------------------------------------------------------------------
    //list dogs
        @UnderTest(id = "U7.2")
        public void takeUserTailInput() {
            if (listOfDogs.size() != 0) {
                double tailLength = userInput.inputDoubleNr("Smallest tail length to display");
                listTailLength(tailLength);
            } else {
                System.out.println("Error: No dogs in register!");
            }
        }
        private void listTailLength(double tailLength) {
            sortDogsInList();
            ArrayList<Dog> qualifyingDogs = new ArrayList<>();
            for (Dog dog : listOfDogs) {
                if (dog.getTailLength() >= tailLength) {
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
    
    //----------------------------------------------------------------------------
    // Sort list of dogs
        @UnderTest(id="U7.6.1.1")
        private void swapDogs(int i, int j){
            Dog element = listOfDogs.get(i);
            listOfDogs.set(i, listOfDogs.get(j));
            listOfDogs.set(j, element);
        }
        @UnderTest(id="U7.6.1.2")
        private void swapTwoDogs(int i, int j){
            Collections.swap(listOfDogs, i,j);
        }

        @UnderTest(id="U7.6.2")
        private int compareTwoDogs(Dog i, Dog j) {
            int tailResult = compareTailLength(i, j);
            if (tailResult != 0) {
                return tailResult;
            }
            return compareName(i, j);
        }
        private int compareTailLength(Dog i, Dog j) {
            return Double.compare(i.getTailLength(), j.getTailLength());
        }
        private int compareName(Dog i, Dog j) {
            int comp = i.getName().compareToIgnoreCase(j.getName());
            if (comp > 0) {
                return 1;
            }
            return 0;
        }
        @UnderTest(id="U7.6.3")
        private int findMinDog(int startIndex) {
            int minDogIndex = startIndex;
            for (int i = startIndex + 1; i < listOfDogs.size(); i++) {
                int compResult = compareTwoDogs(listOfDogs.get(minDogIndex), listOfDogs.get(i));
                if (compResult == 1) {
                    minDogIndex = i;
                }
            }
            return minDogIndex;
        }
        @UnderTest(id="U7.6.4")
        private void sortDogsInList() {
            for (int i = 0; i < listOfDogs.size(); i++) {
                int swapIndex = findMinDog(i);
                if (i != swapIndex) {
                    swapTwoDogs(i, swapIndex);
                }
            }
        }

    //----------------------------------------------------------------------------
    // increase age of dog
        @UnderTest(id="U7.4")
        private void increaseDogAge(){
            String inputFromUser = userInput.inputString("Enter the name of the dog");
            Dog dogToChangeAge = findDogName(inputFromUser);
            if (dogToChangeAge == null){
                System.out.println("Error: no such dog");
            } else{
                dogToChangeAge.increaseAge(1);
                System.out.println(dogToChangeAge.getName() + " is now one year older");
            }
        }
        private Dog findDogName(String dogName) {
            for (Dog dog : listOfDogs) {
                if (dog.getName().equalsIgnoreCase(dogName)) {
                    return dog;
                }
            }
            return null;
        }
    
    //-----------------------------------------------------------------------------
    //removeDog
        @UnderTest(id = "U8.8")
        public void removeDog() {
            String dogName = userInput.inputString("Enter the name of the dog");
            Dog dogToDelete = findDogName(dogName);
    
            if (dogToDelete == null) {
                System.out.println("Error: no such dog");
                return;
            }
            listOfDogs.remove(dogToDelete);
            dogToDelete.removeOwnerFromDog();
            System.out.println(dogToDelete.getName() + " is removed from the register");
    
        }

    //-----------------------------------------------------------------------------
    //create new Owner
        @UnderTest(id="U8.1")
        private void addOwnerToList(){
            Owner newOwner = createOwner();
            ownerList.add(newOwner);
            System.out.println(newOwner.getName() + " added to register");
        }

        private Owner createOwner(){
            String ownerName = userInput.inputString("Name");
            return new Owner(ownerName);
        }
    
    //-----------------------------------------------------------------------------
    // give dog to owner
    


        private Owner findOwner(String ownerName) {
            for (Owner owner : ownerList) {
                if (owner.getName().equalsIgnoreCase(ownerName)) {
                    return owner;
                }
            }
            return null;
        }

    //----------------------------------------------------------------------------
    //list owners
        @UnderTest(id = "U8.4")
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

    //-----------------------------------------------------------------------------
    // remove owned dog
    
        @UnderTest(id = "U8.6")
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
    
    //-----------------------------------------------------------------------------
    // remove owner
    
        public void removeOwnerFromRegister() {
            String ownerName = userInput.inputString("Enter the name of the user");
            Owner owner = findOwner(ownerName);
            if (owner != null) {
                ownerList.remove(owner);
                owner.removeAllDogsFromRegister(listOfDogs);
                System.out.println(owner.getName() + " is removed from register");
            } else {
                System.out.println("Error: no such owner");
            }
        }
    
    //--------------------------------------------------------------------------------
    // main menu
        public void executeMainLoop(){
            String menuInput;
            printMenu();
            do {
                menuInput = handleMainLoopInput();
            }while (!menuInput.equalsIgnoreCase("exit"));
            System.out.println("Goodbye!");
        }
        private void printMenu(){
    
            System.out.println("* Hi and welcome to the dog register program!");
            System.out.println("* Remember to either type in your command or use the shortcut numbers");
            System.out.println("* add dogs and owners/press 0");
            System.out.println("* register new dog/press 1");
            System.out.println("* list dogs/press 2");
            System.out.println("* increase age/press 3");
            System.out.println("* remove dog/press 4");
            System.out.println("* register new owner/press 5");
            System.out.println("* give dog/press 6");
            System.out.println("* list owners/press 7");
            System.out.println("* remove owned dog/press 8");
            System.out.println("* remove owner/press 9");
            System.out.println("* exit/press 10");
        }

        private String handleMainLoopInput() {
            String menuInput = userInput.inputString("Command");
            switch (menuInput) {
    
                case "0":
                case "add dogs and owners":
                    addDogsAndOwners();
                    break;
                case "1":
                case "register new dog":
                    addDogToList();
                    break;
                case "2":
                case "list dogs":
                    takeUserTailInput();
                    break;
                case "3":
                case "increase age":
                    increaseDogAge();
                    break;
                case "4":
                case "remove dog":
                    removeDog();
                    break;
                case "5":
                case"register new owner":
                    addOwnerToList();
                    break;
                case "6":
                case "7":
                case "list owners":
                    listOwner();
                    break;
                case "8":
                case "remove owned dog":
                    removeDogFromOwner();
                    break;
                case "9":
                case "remove owner":
                    removeOwnerFromRegister();
                    break;
                case "10":
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
    
            System.out.println("* Dogs and owners have benn added!");
    
            Dog dog1 = new Dog("Emma", "Grand danois", 6, 6);
            Dog dog2 = new Dog("Ross", "Dachshund", 11, 9);
            Dog dog3 = new Dog("Leon", "Tax", 11, 9);
            Dog dog4 = new Dog("Stich", "Beagle", 12, 11);
            Dog dog5 = new Dog("Marvin", "Beagle", 7, 19);
            Dog dog6 = new Dog("Luna", "Golden retriever", 18, 11);
            Dog dog7 = new Dog("Lassie", "Chihuahua", 14, 15);
    
            listOfDogs.add(dog1);
            listOfDogs.add(dog2);
            listOfDogs.add(dog3);
            listOfDogs.add(dog4);
            listOfDogs.add(dog5);
            listOfDogs.add(dog6);
            listOfDogs.add(dog7);
    
            Owner owner1 = new Owner("Henrik");
            Owner owner2 = new Owner("John");
            Owner owner3 = new Owner("Kleyton");
            Owner owner4 = new Owner("Larry");
            Owner owner5 = new Owner("Esteban");
    
            ownerList.add(owner1);
            ownerList.add(owner2);
            ownerList.add(owner3);
            ownerList.add(owner4);
            ownerList.add(owner5);
        }
    
    }
