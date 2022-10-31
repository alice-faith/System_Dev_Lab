package Activities;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.system_dev_lab.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class EspressoActivityTest {
    // rule specifies that we are
    // running test on EspressoActivity
    @Rule
    public ActivityScenarioRule<EspressoActivity> activityScenarioRule = new ActivityScenarioRule<>(EspressoActivity.class);

    @Test
    public void selectLanguageAndCheck() {
        onView(withId(R.id.german)) // ViewMatchers - withId(R.id.german) is to
                // specify that we are looking for Button
                // with id = R.id.german
                .perform(click()); // ViewActions - Performs click action on view.
        onView(withId(R.id.preferred_language)) // ViewMatchers - withId(R.id.preferred_language)
                // is to specify that we are looking for a TextView
                // with id = R.id.preferred_language
                .check(matches(withText("German")));


    }
}