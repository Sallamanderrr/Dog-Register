package Register;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author Abraham Aleman abal7627
 */

public class Owner {
    private String name;
    private Dog[] listOfOwnerDogs = new Dog[0];

    public Owner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + " ";
    }

    @UnderTest(id="U8.3")
    public void addDog(Dog dog) {

        if (dog == null || dog.getOwner() != null && dog.getOwner() != this) return;

        if (!hasDogInArray(dog)) {
            addDogToArray(dog);

        }

            dog.setOwner(this);
    }


    private void addDogToArray(Dog dog) {
        //this.dog = dog;
        listOfOwnerDogs = Arrays.copyOf(listOfOwnerDogs, listOfOwnerDogs.length + 1);
        listOfOwnerDogs[listOfOwnerDogs.length - 1] = dog;
    }

    public boolean hasDogInArray(Dog dog) {
        for (Dog dogToCheck : listOfOwnerDogs) {
            if (dog == dogToCheck) {
                return true;
            }
        }
        return false;
    }

    public void removeDogFromOwner(Dog dog) {
        dog.removeOwnerFromDog();
    }


    private void deleteDogFromOwner(Dog dog) {
        int index = findDogIndex(dog);
        if (index >= 0) {
            Dog[] copyArray = new Dog[listOfOwnerDogs.length - 1];
            System.arraycopy(listOfOwnerDogs, 0, copyArray, 0, index);
            System.arraycopy(listOfOwnerDogs, index + 1, copyArray, index, listOfOwnerDogs.length - index - 1);
            listOfOwnerDogs = copyArray;

        }
    }
    @UnderTest(id="U8.6")
    public void removeDog(Dog dog) {
        if (hasDogInArray(dog)) {
            deleteDogFromOwner(dog);
        }

        if (dog.getOwner() == this) {
            dog.removeOwnerFromDog();
        }

    }

    public int findDogIndex(Dog dog) {
        for (int i = 0; i < listOfOwnerDogs.length; i++) {
            if (dog == listOfOwnerDogs[i]) {
                return i;
            }
        }
        return -1;
    }

    //UnderTest(id="U8.5")
    public boolean ownDog(Dog dog) {
        return !hasDogInArray(dog);
    }

    public void printOwnerArray() {
        for (int i = 0; i < listOfOwnerDogs.length; i++) {
            System.out.println(listOfOwnerDogs[i]);
        }
    }

    public void removeAllDogsFromRegister(ArrayList<Dog> dogList) {
        for (Dog dogRemove : listOfOwnerDogs) {
            dogList.remove(dogRemove);
        }

    }
}