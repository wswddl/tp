package seedu.address.model.applicant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class RatingTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rating(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidRating = "";
        assertThrows(IllegalArgumentException.class, () -> new Rating(invalidRating));
    }

    @Test
    public void isValidRating() {
        // null rating
        assertThrows(NullPointerException.class, () -> Rating.isValidRating(null));

        // invalid rating
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating("a")); // not integer
        assertFalse(Rating.isValidRating("0")); // below lower boundary
        assertFalse(Rating.isValidRating("6")); // above upper boundary
        assertFalse(Rating.isValidRating("12")); // multiple digits
        assertFalse(Rating.isValidRating("1.5")); // floating point number, not integer


        // valid rating
        assertTrue(Rating.isValidRating("1")); // lower boundary
        assertTrue(Rating.isValidRating("5")); // upper boundary
        assertTrue(Rating.isValidRating("3")); // middle value
        assertTrue(Rating.isValidRating("-1")); // value representing no rating
    }

    @Test
    public void equals() {
        Rating Rating = new Rating("2");

        // same values -> returns true
        assertTrue(Rating.equals(new Rating("2")));

        // same object -> returns true
        assertTrue(Rating.equals(Rating));

        // null -> returns false
        assertFalse(Rating.equals(null));

        // different types -> returns false
        assertFalse(Rating.equals(5.0f));

        // different values -> returns false
        assertFalse(Rating.equals(new Rating("3")));
    }
}
