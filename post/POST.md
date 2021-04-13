---
title: Property-Based Matrix Testing in Java
published: true
description: Property-Based Matrix Testing in Java.
tags: ["java", "test", "pbt", "junit"]
---

## Property-Based testing with a dynamic matrix

A while back, I had to roll out a dynamic matrix to fully test some feature I was working on at the time.</br>

I can't even remember what was the case or the project, but ever since then, I've been using the same technique to accomplish matrix testing.</br>
It's not very pretty, or clean, but it gets the job done. :sunglasses:</br>

I've been using [JUnit's ParameterizedTest annotation][1] to incorporate property-based testing (PBT), and a [MethodSource annotation][2] pointing to a method that spits out a stream representing the matrix.</br>
It's pretty straightforward, but the more parameter types I have in my matrix, the harder it is to read or write the method supplying them. :dizzy_face:</br>

Lately, I discovered [JUnit Pioneer][3], which is a *JUnit 5 Extension Pack*, amongst all the goodies introduced, you can find the [CartesianProductTest annotation][4].</br>
Incorporated with a couple of a [CartesianEnumSource annotations][5] and the matrix implementation becomes much more simple and elegant. :smiley:</br>

Let's have a look.</br>

Creating a dynamic property-based matrix from the following *Enums* should spit out 6 tests:

```java
  enum Direction {
    INCOMING,
    OUTGOING
  }

  enum Status {
    SUCCESS,
    FAILURE,
    WAITING
  }
```

Implementing a matrix from these *Enums* with [JUnit's ParameterizedTest][1] and a [MethodSource][2]:

```java
  @ParameterizedTest
  @MethodSource("getArguments")
  void using_junit_parameterized_test_with_method_source(
      final Direction direction, final Status status) {
    assertTrue(true);
  }

  static Stream<Arguments> getArguments() {
    return Stream.of(Direction.values())
        .flatMap(d -> Stream.of(Status.values()).map(s -> arguments(d, s)));
  }
```

As you can see, adding members to the existing *Enums* will dynamically increase the matrix and therefore the number of tests performed.</br>

But, adding a third element to the matrix, and the `getArguments` method, will start losing its readability.</br>
Nevertheless, that gets the job done, and I've been using this technique throughout my projects for a long time now.

Now, let's accomplish the same with [JUnit Pioneer's CartesianProductTest][4] and a couple of [CartesianEnumSource][5]s:

```java
  @CartesianProductTest
  @CartesianEnumSource(Direction.class)
  @CartesianEnumSource(Status.class)
  void using_junit_pioneer_cartesian_product_test_with_enum_source(
      final Direction direction, final Status status) {
    assertTrue(true);
  }
```

This will spit out the same matrix, only now, adding a third element is quite simple, just add another annotation, you can find other types of sources beside *Enums*, in [JUnit Pioneer's Documentation][6].</br>

As demonstrated in [this repository][0], executing both test cases in the same test class, using JUnit's platform, will print out:

```text
[INFO] '-- JUnit Jupiter [OK]
[INFO]   '-- Property Based Matrix Test [OK]
[INFO]     +-- using junit pioneer cartesian product test with enum source (Direction, Status) [OK]
[INFO]     | +-- [1] INCOMING, SUCCESS [OK]
[INFO]     | +-- [2] INCOMING, FAILURE [OK]
[INFO]     | +-- [3] INCOMING, WAITING [OK]
[INFO]     | +-- [4] OUTGOING, SUCCESS [OK]
[INFO]     | +-- [5] OUTGOING, FAILURE [OK]
[INFO]     | '-- [6] OUTGOING, WAITING [OK]
[INFO]     '-- using junit parameterized test with method source (Direction, Status) [OK]
[INFO]       +-- [1] INCOMING, SUCCESS [OK]
[INFO]       +-- [2] INCOMING, FAILURE [OK]
[INFO]       +-- [3] INCOMING, WAITING [OK]
[INFO]       +-- [4] OUTGOING, SUCCESS [OK]
[INFO]       +-- [5] OUTGOING, FAILURE [OK]
[INFO]       '-- [6] OUTGOING, WAITING [OK]
```

You can check out the code for this tutorial in [Github][0].

**:wave: See you in the next tutorial :wave:**

[0]: https://github.com/TomerFi/property-based-matrix-testing-tutorial
[1]: https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
[2]: https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-MethodSource
[3]: https://junit-pioneer.org/
[4]: https://junit-pioneer.org/docs/cartesian-product/
[5]: https://junit-pioneer.org/docs/cartesian-product/#cartesianenumsource
[6]: https://junit-pioneer.org/docs/cartesian-product/#annotating-your-test-method
