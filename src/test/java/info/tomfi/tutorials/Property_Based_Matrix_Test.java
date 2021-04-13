package info.tomfi.tutorials;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junitpioneer.jupiter.CartesianEnumSource;
import org.junitpioneer.jupiter.CartesianProductTest;

class Property_Based_Matrix_Test {
  enum Direction {
    INCOMING,
    OUTGOING
  }

  enum Status {
    SUCCESS,
    FAILURE,
    WAITING
  }

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

  @CartesianProductTest
  @CartesianEnumSource(Direction.class)
  @CartesianEnumSource(Status.class)
  void using_junit_pioneer_cartesian_product_test_with_enum_source(
      final Direction direction, final Status status) {
    assertTrue(true);
  }
}
