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

/**
 * Just a simple holder for {@link String}. Acts as a dependency which behaviour can be mocked
 * using e.g. Mockito.
 *
 * @author Tomasz Rozbicki
 */
public class StringHolder {

    private final String mString;

    public StringHolder(String string) {
        mString = string;
    }

    public String getString() {
        return mString;
    }
}
