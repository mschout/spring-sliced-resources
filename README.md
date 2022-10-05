# Spring SlicedModel and SlicedResourceAssembler Support 

This is a plugin for [Spring](https://spring.io) applications that enhances support for converting
[Slice](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Slice.html) objects
to models that work with Spring HATEOAS.  The main reason to do this instead of just using [Page]() collections is that
Slice's do not need to do a `SELECT COUNT(*)` query to determine the total number of elements.  Instead, Slice's only
need to know if there is a next element after the current slice or not.  When working with larger datasets, using
Slice's can be a huge performance improvement.

If you were using `Page<T>` collections, your controller code might look something like this:

```java
// Spring injects via constructor
private final PagedResourcesAssembler<Person> pagedResourcesAssembler;

@GetMapping
public PagedModel<EntityModel<Person>> someControllerMethod(Pageable pageable){
  Page<Person> people = personRepository.findAll(pageable);
  
  return pagedResourceAssembler.toModle(people);
}
```

If you wanted to instead use Slice's, it would look something like this:

```java
// Spring injects via constructor
private final SlicedResourcesAssembler<Person> slicedResourcesAssembler;

@GetMapping
public SlicedModel<EntityModel<Person>> someControllerMethod(Pageable pageable){
  Slice<Person> people = personRepository.findSlice(pageable);

  return slicedResourceAssembler.toModle(people);
}
```

Unfortunately, at this time, Spring does not have support for the sliced version because:

- There is no `SlicedModel` or equivalent available in Spring HATEOAS
- There is no `SlicedModelAssembler` available in Spring Data Commons and as a result no way for spring to auto inject one for you.

This package bridges the gap for you.

# Usage

Add this library as a dependency to your application.  I've published this to
Maven Central so no special repositories needed or anything.

For gradle:

```java
implementation "io.github.mschout:spring-sliced-resources:${latestVersion}"
```

Then in your Spring Boot application class, just import slice support:

```java
package your.spring.app;

// imports
@SpringBootApplication
@Import(SpringSlicedResources.class)
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

And that's it.  Spring will be able to auto-inject `SlicedResourcesAssembler<T>` automatically for you, and you will
have `SlicedModel` available.

Hopefully eventually this gets merged upstream into core eventually.  I do have PR's open to submit this work upstream.
We'll see what happens.
