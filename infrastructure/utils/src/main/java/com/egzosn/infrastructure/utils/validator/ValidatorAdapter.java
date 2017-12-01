/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.egzosn.infrastructure.utils.validator;

import org.springframework.validation.*;
import org.springframework.validation.beanvalidation.CustomValidatorBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * Adapter that takes a JSR-303 {@code javax.validator.Validator}
 * and exposes it as a Spring {@link org.springframework.validation.Validator}
 * while also exposing the original JSR-303 Validator interface itself.
 *
 * <p>Can be used as a programmatic wrapper. Also serves as base class for
 * {@link CustomValidatorBean} and {@link LocalValidatorFactoryBean}.
 *
 * @author Juergen Hoeller
 * @since 3.0
 */
public class ValidatorAdapter extends SpringValidatorAdapter {



	/**
	 * Create a new SpringValidatorAdapter for the given JSR-303 Validator.
	 * @param targetValidator the JSR-303 Validator to wrap
	 */
	public ValidatorAdapter(javax.validation.Validator targetValidator) {
		super(targetValidator);
	}


	public void validateProperty(Object object, String propertyName, Errors errors) {
		Set<ConstraintViolation<Object>> violations = super.validateProperty(object, propertyName);
		processConstraintViolations(violations, errors);
	}
}
