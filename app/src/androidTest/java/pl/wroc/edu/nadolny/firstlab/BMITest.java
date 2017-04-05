package pl.wroc.edu.nadolny.firstlab;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import pl.wroc.edu.nadolny.firstlab.BMICounter.CountBMIKGM;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BMITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void BMIIsCountedCorrectly() throws Exception{

        CountBMIKGM bmi = new CountBMIKGM();

        onView(withId(R.id.heightTxt)).perform(replaceText("1.8"));
        onView(withId(R.id.massTxt)).perform(replaceText("80"));
        onView(withId(R.id.countBMIBtn)).perform(click());

        onView(withId(R.id.returnTxt)).check(matches(withText("BMI : 24.0")));
    }

    public void DataIsSavedCorrectly() throws Exception{
        onView(withId(R.id.heightTxt)).perform(replaceText("1.8"));
        onView(withId(R.id.massTxt)).perform(replaceText("95"));
        onView(withId(R.id.saveButton)).perform(click());

        pressBack();
    }
}
