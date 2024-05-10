package com.justbelieveinmyself.library.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.stream.Stream;

public class ModelUtils {

    public static void copyPropertiesIgnoreNull(Object source, Object target, String... ignoreProperties) {
        String[] nullProperties = getNullPropertyNames(source);
        int nullPropsLength = nullProperties.length;
        int ignorePropsLength = ignoreProperties.length;
        String[] resultIgnoreProps = new String[nullPropsLength + ignorePropsLength];

        System.arraycopy(nullProperties, 0, resultIgnoreProps, 0, nullPropsLength);
        System.arraycopy(ignoreProperties, 0, resultIgnoreProps, nullPropsLength, ignorePropsLength);

        BeanUtils.copyProperties(source, target, resultIgnoreProps);
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrapperSource = new BeanWrapperImpl(source);
        return Stream.of(wrapperSource.getPropertyDescriptors())
                .map((pd) -> pd.getName())
                .filter((pd) -> wrapperSource.getPropertyValue(pd) == null)
                .toArray(String[]::new);
    }

}
