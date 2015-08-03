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

package pl.dagger.testing.app;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import pl.dagger.testing.MainActivity;
import pl.dagger.testing.StringHolder;

/**
 * {@link Application} with Dagger 2 component and module.
 * <p>
 * Contains {@link App#setTestComponent(AppComponent)} method to override the default component
 * with test version.
 *
 * @author Tomasz Rozbicki
 */
public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerApp_AppComponent.create();
    }

    public AppComponent component() {
        return mAppComponent;
    }

    /**
     * Visible only for testing purposes.
     */
    @VisibleForTesting
    public void setTestComponent(AppComponent appComponent) {
        mAppComponent = appComponent;
    }

    @Singleton
    @Component(modules = StringHolderModule.class)
    public interface AppComponent {

        void inject(MainActivity activity);
    }

    @Module
    public static class StringHolderModule {

        @Provides
        StringHolder provideString() {
            return new StringHolder("Release string");
        }
    }
}
