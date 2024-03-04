package Register;
/*
 * Klassen i denna fil innehåller funktionalitet
 * som är gemensam för många av testen, till exempel 
 * funktionalitet för att hitta klassen eller metoden
 * som ska testas. För att dessa test ska fungera måste
 * denna fil finnas i samma katalog.
 */

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.*;

import org.junit.jupiter.api.Test;

public abstract class BaseTest extends CUTTest {

	/*
	 * Denna konstruktor är nödvändig för testkompilering av filerna som läggs upp i
	 * ilearn där allt är utkommenterat. Den ska inte användas!
	 */
	protected BaseTest() {
		throw new IllegalStateException("No cut set");
	}

	public BaseTest(Class<?> cut) {
		super(cut);
	}

	public BaseTest(Class<?> cut, String mutId) {
		super(cut, mutId);
	}

	// TODO: sortera metoderna i denna klass i mer logiska grupper

	protected void setIn(String input) {
		System.setIn(new ByteArrayInputStream(input.getBytes()));
	}

	/**
	 * Kontrollerara att ett fält av en viss typ och ett visst värde finns hos
	 * objektet.
	 * 
	 * @param obj      Objektet som ska ha fältet.
	 * @param type     Den förväntade typen.
	 * @param expected Det förväntade värdet.
	 */
	protected void assertHasFieldSetTo(Object obj, Class<?> type, Object expected) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			if (f.getType() == type) {
				f.setAccessible(true);
				Object actual = accessFieldUnderTest(obj, f);
				if (actual == expected)
					return; // Done, field found
			}
		}

		fail(String.format("Kunde inte hitta ett fält i %s med typen %s och värdet %s", obj, type, expected));
	}

	protected void assertDoesNotHasFieldSetTo(Object obj, Class<?> type, Object unexpected) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			if (f.getType() == type) {
				f.setAccessible(true);
				Object actual = accessFieldUnderTest(obj, f);
				if (actual == unexpected)
					fail(String.format("Hittade ett fält i %s med typen %s och värdet %s som inte borde finnas", obj,
							type, unexpected));
			}
		}
		// Klara, inget att göra här
	}

	private void checkProtectionLevel(Member[] members, boolean publicOk) {
		for (Member m : members) {
			if (publicOk) {
				assertTrue(isPublic(m) || isPrivate(m), String
						.format("%s har fel skyddsnivå. Enbart public och private är tillåtna enligt uppgiften.", m));
			} else {
				assertTrue(isPrivate(m),
						String.format("%s har fel skyddsnivå. Enbart private är tillåtet enligt uppgiften.", m));
			}
		}
	}

	protected void assertEqualsIgnoreCase(String expected, Object actual) {
		assertTrue(actual instanceof String, actual + " is not a String");
		assertEquals(expected.toLowerCase(), ((String) actual).toLowerCase(), String.format(
				"Var \"%s\", men borde varit \"%s\" (utan hänsyn till stora och små bokstäver)", actual, expected));
	}

	protected void assertEqualsIgnoreCase(String expected, Object actual, String msg) {
		assertTrue(actual instanceof String, actual + " is not a String");
		assertEquals(expected.toLowerCase(), ((String) actual).toLowerCase(),
				String.format(msg + ",var \"%s\", men borde varit \"%s\" (utan hänsyn till stora och små bokstäver)",
						actual, expected));
	}

	protected void assertContains(Iterable<?> collection, Object target, String msg) {
		for (Object o : collection) {
			if (o == target)
				return; // Klart, inget att göra här
		}

		fail(msg + ", kunde inte hitta det förväntade värdet " + target + " i " + collection);
	}

	protected void assertDoesNotContains(Iterable<?> collection, Object target, String msg) {
		for (Object o : collection) {
			if (o == target)
				fail(msg + ", hittade " + target + " som inte borde finnas i " + collection);
		}
		// Klart, inget att göra här
	}

	private boolean isAnyOf(Class<?> type, Class<?>... types) {
		for (Class<?> c : types) {
			if (c.isAssignableFrom(type))
				return true;
		}
		return false;
	}

	private boolean isNotAnyOf(Class<?> type, Class<?>... types) {
		return !isAnyOf(type, types);
	}

	protected void assertAtMostXFieldsOfType(Class<?> c, Class<?> type, int max, String msg) {

		int count = 0;
		for (Field f : c.getDeclaredFields()) {
			if (f.getType() == type)
				count++;
		}

		assertTrue(count <= max, String.format("Det fanns fler fält av typen %s i klassen %s än förväntat (%d/%d). %s",
				type.getName(), c.getName(), count, max, msg));
	}

	protected void assertNoStaticFields(Class<?> c, boolean constantsAllowed, Class<?>... exceptions) {
		for (Field f : c.getDeclaredFields()) {
			boolean isStatic = isStatic(f);
			boolean isConstant = isStatic(f) && isFinal(f);
			boolean isExcepted = isAnyOf(f.getType(), exceptions);

			boolean isOk = !isStatic || (constantsAllowed && isConstant) || isExcepted;

			assertTrue(isOk, String.format("Fältet %s i klassen %s är statiskt. "
					+ "Denna uppgift borde inte behöva något statiskt fält av den typen. "
					+ "Detta test kan ha fel, så om du har en god motivation till varför fältet ska vara statiskt så skicka ett meddelande till handledningsforumet så uppdaterar vi testet.",
					f.getName(), c.getName()));
		}
	}

	protected void assertNoFieldsOfTypes(Class<?> c, boolean constantsAllowed, Class<?>... types) {
		for (Field f : c.getDeclaredFields()) {
			assertTrue(isNotAnyOf(f.getType(), types) || (constantsAllowed && isConstant(f)), String.format(
					"Fältet %s i klassen %s är av en typ som inte borde behövas på klassnivå i denna uppgift. "
							+ "Det är troligt att detta borde vara en lokal variabel i en metod istället. "
							+ "Detta test kan ha fel, så om du har en god motivation till varför fältet ska finnas på klassnivå så skicka ett meddelande till handledningsforumet så uppdaterar vi testet.",
					f.getName(), c.getName()));
		}
	}

	private boolean isLambda(Method m) {
		return m.getName().startsWith("lambda$");
	}

	protected void assertNoStaticMethods(Class<?> c) {
		for (Method m : c.getDeclaredMethods()) {
			assertFalse(isStatic(m) && !isLambda(m), String.format(
					"Metoden %s i klassen %s är statiskt. Denna uppgift borde inte behöva någon statisk metod. Detta test kan ha fel, så om du har en god motivation till varför metoden ska vara statiskt så skicka ett meddelande till handledningsforumet så uppdaterar vi testet.",
					m.getName(), c.getName()));
		}
	}

	@Test
	public void protectionLevelSetOnClass() {
		assertTrue(Modifier.isPublic(cut.getModifiers()),
				"Klassen " + cut.getName() + " är inte publik, detta kan leda till problem med andra test");
	}

	@Test
	public void protectionLevelSetOnAllConstructors() {
		checkProtectionLevel(cut.getDeclaredConstructors(), true);
	}

	@Test
	public void protectionLevelSetOnAllMethods() {
		checkProtectionLevel(cut.getDeclaredMethods(), true);
	}

	@Test
	public void protectionLevelSetOnAllVariables() {
		checkProtectionLevel(cut.getDeclaredFields(), false);
	}

	@Test
	public void noFieldOfAnyAssignmentClassInCUT() {
		for (Field f : cut.getDeclaredFields()) {
			assertFalse(f.getName().toLowerCase().contains("assignment"), String.format(
					"Det finns ingen anledning att ha ett fält av någon \"Assignment\"-klass i %s. AssignmentXPointX-klasserna är bara till för uppdelning för testning, och det finns ingen anledning att skapa någon annan klass någonstans i uppgiften som heter något med \"Assignment\"",
					cut.getName()));
		}
	}
}
