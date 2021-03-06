/**
 * Copyright 2014-2018 Q24
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.kahu.hawaii.util.spring;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext context;

    public ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> beanClass) {
        assert context != null : "Spring Application Context is null.";

        String[] beanNamesForType = context.getBeanNamesForType(beanClass);
        assert beanNamesForType.length != 0 : "Found no bean for '" + beanClass + "'.";
        assert beanNamesForType.length == 1 : "Found multiple beans for '" + beanClass + "' namely '" + Arrays.toString(beanNamesForType) + "'.";

        return (T) context.getBean(beanNamesForType[0]);
    }

    public static <T> T getBean(final String beanName, final Class<T> beanClass) {
        return context.getBean(beanName, beanClass);
    }

    public static <T> Collection<T> getBeans(Class<T> beanClass) {
        Map<String, T> beansOfType = context.getBeansOfType(beanClass);
        return beansOfType.values();
    }
}