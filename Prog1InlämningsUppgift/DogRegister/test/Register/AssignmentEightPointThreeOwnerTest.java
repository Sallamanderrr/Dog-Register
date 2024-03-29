package Register;// JUnit-testfallen i denna fil testar ägarklassens array av hundar U8.3
// För mer information se README.txt-filen

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;


public class AssignmentEightPointThreeOwnerTest extends AssignmentEightFunctionBaseTest {

 	private Dog dog = new Dog("Karo", "Basset", 1, 2);
 	private Owner owner = new Owner("Columbine");

 	public AssignmentEightPointThreeOwnerTest() {
 		super(Owner.class, "U8.3");
 	}

 	@Test
 	public void noStaticFieldsExceptPossiblyConstants() {
 		assertNoStaticFields(cut, true);
 	}

 	@Test
 	public void noStaticMethods() {
 		assertNoStaticMethods(cut);
 	}

 	@Test
 	public void onlyOneArrayOfDogsAtClassLevel() {
 		assertAtMostXFieldsOfType(cut, Dog[].class, 1,
 				"Behöver du mer än en array av hundar ska de övriga nästan säkert vara lokala variabler i någon metod.");
 	}

 	@Test
 	public void addingDogToOwnerPutsDogInArray() {
 		callMethodUnderTest(owner, dog);
 		assertOwnDog(owner, dog);
 	}

 	@Test
 	public void addingDogToOwnerAlsoSetsOwnerOnDog() {
 		callMethodUnderTest(owner, dog);
 		assertHasFieldSetTo(dog, Owner.class, owner);
 	}

 	@Test
 	public void addingAlreadyOwnedDogDoesNotAddDogToArray() {
 		callMethodUnderTest(owner, dog);

 		Owner anotherOwner = new Owner("Hawkeye");
 		callMethodUnderTest(anotherOwner, dog);

 		assertDoesNotOwnDog(anotherOwner, dog);
 	}

 	@Test
 	public void addingAlreadyOwnedDogDoesNotChangeOwner() {
 		callMethodUnderTest(owner, dog);

 		Owner anotherOwner = new Owner("Hawkeye");
 		callMethodUnderTest(anotherOwner, dog);

 		assertHasFieldSetTo(dog, Owner.class, owner);
 		assertDoesNotHasFieldSetTo(dog, Owner.class, anotherOwner);
 	}

 	@Test
 	public void addingAlreadyOwnedDogToSameOwnerDoesNothing() {
 		callMethodUnderTest(owner, dog);
 		callMethodUnderTest(owner, dog);

 		Dog[] dogs = (Dog[]) accessOnlyFieldOfType(owner, Dog[].class);
 		int count = 0;

 		for (Dog d : dogs) {
 			if (d == dog)
 				count++;
 		}

 		assertHasFieldSetTo(dog, Owner.class, owner);
 		assertOwnDog(owner, dog);
 		assertEquals(1, count, String.format("Fel antal av hunden %s hos %s", dog, owner));
 	}

 	private int addEnoughDogsToCauseAtLeastOneResize(Dog firstDog, Dog lastDog) {
 		Dog[] dogs = (Dog[]) accessOnlyFieldOfType(owner, Dog[].class);

 		int dogsNeeded = dogs.length + 1;

 		callMethodUnderTest(owner, firstDog);

 		for (int i = 1; i < dogsNeeded; i++) {
 			Dog dog = new Dog("Dog #" + i, "Breed", 1, 2);
 			callMethodUnderTest(owner, dog);
 		}

 		callMethodUnderTest(owner, lastDog);

 		return dogsNeeded;
 	}

 	@Test
 	public void addingMoreDogsIncreasesArraySize() {
 		int dogsAdded = addEnoughDogsToCauseAtLeastOneResize(new Dog("A", "B", 1, 2), new Dog("C", "D", 3, 4));

 		Dog[] dogs = (Dog[]) accessOnlyFieldOfType(owner, Dog[].class);

 		assertTrue(dogs.length >= dogsAdded,
 				String.format(
 						"Alltför få platser (%d) i ägarens array för att få plats med alla hundar som lagts till (%d)",
 						dogs.length, dogsAdded));
 	}

 	@Test
 	public void addingMoreDogsAddsThemToArray() {
 		Dog first = new Dog("First", "Breed", 1, 2);
 		Dog last = new Dog("Last", "Breed", 3, 4);

 		addEnoughDogsToCauseAtLeastOneResize(first, last);

 		assertOwnDog(owner, first);
 		assertOwnDog(owner, last);
 	}

 	@Test
 	public void noCollectionsInOwner() {
 		assertNoFieldsOfTypes(cut, false, Collection.class);
 	}

 	@Test
 	@EnabledOnOs({ OS.LINUX })
 	public void reallyNoCollectionsInOwner() {
 		assertDoesNotThrow(() -> {
 			String code = Files.readString(Paths.get("Owner.java"));

 			// Detta test är ganska svagt, men det gör i alla fall ett försök att
 			// kontrollera att allt arbete gällande hundarna görs med arrayer
 			String[] forbidden = { "<Dog>", "<Dog", "Dog>", "asList", "stream", "Spliterator" };

 			for (String x : forbidden) {
 				assertFalse(code.contains(x), String.format(
 						"Koden till Owner-klassen innehåller \"%s\" vilket testfallet tolkar som ett möjligt försök att kringgå restriktionen om att arrayer är det enda som ska användas för att hantera ägarens hundar.",
 						x));
 			}

 		});
 	}

 	@Test
 	public void noArrayOfNames() {
 		assertNoFieldsOfTypes(cut, true, String[].class);
 	}


}
