package com.spoqn.server.api.json.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.spoqn.server.api.util.NullToEmptyAdapter;

import lombok.Builder;
import lombok.Singular;

/**
 * Annotated classes will have their collections initialized to empty rather
 * than null when deserialized.
 * <p>
 * This improves compatibility between GSON and Lombok's @{@link Builder}
 * and @{@link Singular} annotations.
 * 
 * @see NullToEmptyAdapter
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DeserializeNullToEmpty {
}
