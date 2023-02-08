package User.AccountService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeImpTest {

    @Test
    void managerEngagement() {
    }

    private static Stream<Arguments> invalidParameterProvider() throws Exception {
        return Stream.of(
                Arguments.of("Test case email esistente e password non corretta", null, "password"),
                Arguments.of("Test case email non esistente e password non esistente", "", "password"),
                Arguments.of("Test case email non esistente e password esistente", "123", "password1!"),
                Arguments.of("Test case email non esistente e password esistente", "tommyrock99@hotmai.it", "napoli"),
                Arguments.of("Test case email non esistente e password esistente", "tommyrock99@hotmai.it", null),
                Arguments.of("Test case email non esistente e password esistente", "tommyrock99@hotmai.it", "")
        );
    }


    @Test
    void registration() {
    }
}