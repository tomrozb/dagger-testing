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

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import pl.dagger.testing.app.App;
import pl.dagger.testing.app.App.AppComponent;

import static org.assertj.android.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 21, reportSdk = 21, constants = BuildConfig.class)
public class MainActivityRobolectricTest {

    public static final String TEST_STRING = "Test string";

    @Before
    public void setTestComponent() {
        // DaggerMainActivityRobolectricTest_TestAppComponent may not be visible in Android Studio
        // but the code compiles and works. This issue should be addressed in gradle plugin 1.3.x
        // https://bitbucket.org/hvisser/android-apt/issue/36/no-dagger2-generated-files-for-junit-tests
        AppComponent appComponent = DaggerMainActivityRobolectricTest_TestAppComponent.create();
        ((App) RuntimeEnvironment.application).setTestComponent(appComponent);
    }

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
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);

        // when
        mainActivity.findViewById(R.id.main_get_string).performClick();

        // then
        assertThat(((EditText) mainActivity.findViewById(R.id.main_string)))
                .containsText(TEST_STRING);
    }
}
