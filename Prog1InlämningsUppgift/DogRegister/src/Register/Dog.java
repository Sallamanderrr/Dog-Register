package Register;


/**
 * @author Abraham Aleman abal7627
 */


public class Dog {
    private String name;
    private String breed;
    private Owner owner;
    private int age;
    private int weight;

    private static final double CONSTANT_TAIL = 3.7;
    private static final double DIVIDE = 10;


    public Dog(String name, String breed, int age, int weight){
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
    }

    public Owner getOwner(){
        return this.owner;
    }

    public void setOwner(Owner owner){
        this.owner = owner;
    }
    @UnderTest(id="U8.6")
    public void removeOwnerFromDog(){
        if (owner != null)  {

            Owner tempOwner = owner;
            removeOwner();
            tempOwner.removeDog(this);

        }
    }

    private void removeOwner(){
        this.owner = null;
    }




    @UnderTest(id="U8.3")
    public void addOwner(Owner owner){
        if (getOwner() == null){
            setOwner(owner);

        }
        if (!owner.hasDogInArray(this)) {
            owner.addDog(this);
        }



    }

    public String getName(){
        return this.name;
    }

    public String getBreed(){
        return this.breed;
    }

    public int getAge(){
        return this.age;
    }

    public int getWeight(){
        return this.weight;
    }

    //public double getTailLength(){
    //    if (this.breed.toLowerCase().contains("tax") || this.breed.toLowerCase().contains("dachshund")){
    //        final double tailLen = 3.7;
    //        return tailLen;
    //    }else{
    //        return (double)((this.age*this.weight)/10.0);
    //    }
    //}

    public double getTailLength() {

        //Om det är en tax eller dachshund så kommer det alltid att bli 3.7
        //Då konstanten CONSTANT_TAIL är alltid 3.7

        if (breed.equalsIgnoreCase("tax") || breed.equalsIgnoreCase("dachshund")) {
            return CONSTANT_TAIL;

        }else {
            //Formulan hur man räknar ut svanslängd
            return getAge() * getWeight() / DIVIDE;

        }

    }


    public void increaseAge(int newAge){
        if (newAge >= 0){
            this.age += newAge;
        }
    }

    public String printOwner(){
        if (owner != null){
            return ", owned by " + owner;
        } else {
            return ", no owner";
        }
    }

    public String toString(){
        return "* " + name + " (" + breed +", "+ age + " years, " +
                weight + " kilo, " + getTailLength() + " cm tail " + printOwner() + ")";
    }
}
