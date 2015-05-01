/*
 * Copyright (C) 2015 Tomasz Rozbicki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.dagger.testing;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import pl.dagger.testing.app.App;
import pl.dagger.testing.app.App.AppComponent;
import pl.dagger.testing.util.DaggerActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {

    public static final String TEST_STRING = "Test string";

    private TestAppComponent mTestAppComponent;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new DaggerActivityTestRule<>(MainActivity.class, (application, activity) -> {
                mTestAppComponent = DaggerMainActivityEspressoTest_TestAppComponent.create();
                ((App) application).setTestComponent(mTestAppComponent);
            });

    @Component(modules = StringHolderModule.class)
    interface TestAppComponent extends AppComponent {

    }

    @Module
    static class StringHolderModule {

        @Provides
        StringHolder provideString() {
            return new StringHolder(TEST_STRING);
        }
    }

    @Test
    public void setsProperStringWhenButtonIsClicked() {
        // given
        String expectedString = TEST_STRING;

        // when
        onView(withId(R.id.main_get_string))
                .perform(click());

        // then
        onView(withId(R.id.main_string))
                .check(matches(withText(expectedString)));
    }
}
