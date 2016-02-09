import components.set.Set;
import components.set.Set1L;

/**
 * Customized JUnit test fixture for {@code Set3}.
 */
public final class Set3Test extends SetTest {

    @Override
    protected final Set<String> constructorTest() {
        return new Set3<String>();
    }

    @Override
    protected final Set<String> constructorRef() {
        return new Set1L<String>();
    }

}
