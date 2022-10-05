/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.web;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;

/**
 * Custom {@link SlicedResourcesAssembler} that is aware of the {@link MethodParameter} it
 * shall create links for.
 *
 * @author Michael Schout
 */
public class MethodParameterAwareSlicedResourcesAssembler<T> extends SlicedResourcesAssembler<T> {
	private final MethodParameter parameter;

	/**
	 * Creates a new {@link MethodParameterAwareSlicedResourcesAssembler} using the given
	 * {@link MethodParameter}, {@link HateoasPageableHandlerMethodArgumentResolver} and base
	 * URI.
	 *
	 * @param parameter must not be {@literal null}.
	 * @param resolver can be {@literal null}.
	 * @param baseUri can be {@literal null}.
	 */
	public MethodParameterAwareSlicedResourcesAssembler(MethodParameter parameter,
			@Nullable HateoasPageableHandlerMethodArgumentResolver resolver, @Nullable UriComponents baseUri) {

		super(resolver, baseUri);

		Assert.notNull(parameter, "Method parameter must not be null");
		this.parameter = parameter;
	}

	@NonNull
	@Override
	protected MethodParameter getMethodParameter() {
		return parameter;
	}
}
