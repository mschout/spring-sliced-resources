package io.github.mschout.spring.slicedresources.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.util.Lazy;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SlicedResourcesAssembler;
import org.springframework.data.web.SlicedResourcesAssemblerArgumentResolver;
import org.springframework.data.web.config.HateoasAwareSpringDataWebConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class SpringSlicedResources extends HateoasAwareSpringDataWebConfiguration {
	private final Lazy<SlicedResourcesAssemblerArgumentResolver> slicedResourcesArgumentResolver;
	private final Lazy<HateoasPageableHandlerMethodArgumentResolver> pageableResolver;

	public SpringSlicedResources(ApplicationContext context,
			@Qualifier("mvcConversionService") ObjectFactory<ConversionService> conversionService) {
		super(context, conversionService);

		this.pageableResolver = Lazy
				.of(() -> context.getBean("pageableResolver", HateoasPageableHandlerMethodArgumentResolver.class));

		this.slicedResourcesArgumentResolver = Lazy.of(() -> context.getBean("slicedResourcesAssemblerArgumentResolver",
				SlicedResourcesAssemblerArgumentResolver.class));
	}

	@Bean
	public SlicedResourcesAssembler<?> slicedResourcesAssembler() {
		return new SlicedResourcesAssembler<>(pageableResolver.get(), null);
	}

	@Bean
	public SlicedResourcesAssemblerArgumentResolver slicedResourcesAssemblerArgumentResolver() {
		return new SlicedResourcesAssemblerArgumentResolver(pageableResolver.get());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);

		argumentResolvers.add(slicedResourcesArgumentResolver.get());
	}
}
