package net.coru.kloadgen.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.avro.Schema.Field;
import org.apache.avro.SchemaBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RandomToolTest {

  private static final LocalDateTime FIXED_DATE = LocalDateTime.of(2019,12,06,12,00,00);

  private static Stream<Arguments> parametersForGenerateSingleRandomValue() {
    return Stream.of(
        Arguments.of("string", 1, Collections.singletonList("testString"), "testString"),
        Arguments.of("int", 1, Collections.singletonList("1"), 1),
        Arguments.of("long", 1, Collections.singletonList("1"), 1L),
        Arguments.of("short", 1, Collections.singletonList("1"), (short)1),
        Arguments.of("double", 1, Collections.singletonList("1.0"), 1.0),
        Arguments.of("timestamp", 1, Collections.singletonList("2019-12-06T12:00:00"), FIXED_DATE),
        Arguments.of("longTimestamp", 1, Collections.singletonList("2019-12-06T12:00:00"), FIXED_DATE.toInstant(ZoneOffset.UTC).toEpochMilli()),
        Arguments.of("stringTimestamp", 1, Collections.singletonList("2019-12-06T12:00:00"), "2019-12-06T12:00"),
        Arguments.of("uuid", 1, Collections.singletonList("0177f035-e51c-4a46-8b82-5b157371c2a5"), UUID.fromString("0177f035-e51c-4a46-8b82-5b157371c2a5")),
        Arguments.of("boolean", 1, Collections.singletonList("true"), Boolean.TRUE)
    );
  }

  @ParameterizedTest
  @MethodSource("parametersForGenerateSingleRandomValue")
  void generateSingleRandomValue(String valueExpression, Integer valueLength, List<String> fieldValuesList, Object expected) {
    assertThat(RandomTool.generateRandom(valueExpression, valueLength, fieldValuesList)).isEqualTo(expected);
  }

  private static Stream<Arguments> parametersForGenerateArrayRandomValue() {
    return Stream.of(
        Arguments.of("string-array", 1, Collections.singletonList("testString"), "testString"),
        Arguments.of("int-array", 1, Collections.singletonList("1"), 1),
        Arguments.of("long-array", 1, Collections.singletonList("1"), 1L),
        Arguments.of("short-array", 1, Collections.singletonList("1"), (short)1),
        Arguments.of("double-array", 1, Collections.singletonList("1.0"), 1.0),
        Arguments.of("uuid-array", 1, Collections.singletonList("0177f035-e51c-4a46-8b82-5b157371c2a5"), UUID.fromString("0177f035-e51c-4a46-8b82-5b157371c2a5"))
    );
  }

  @ParameterizedTest
  @MethodSource("parametersForGenerateArrayRandomValue")
  void generateArrayRandomValue(String valueExpression, Integer valueLength, List<String> fieldValuesList, Object expected) {
    assertThat((List<Object>)RandomTool.generateRandom(valueExpression, valueLength, fieldValuesList))
        .allMatch(value -> value.equals(expected));
  }

  private static Stream<Arguments> parametersForGenerateRandomValueForField() {
    return Stream.of(
        Arguments.of("string", 1, Collections.singletonList("testString"), new Field("name", SchemaBuilder.builder().stringType()), "testString"),
        Arguments.of("int", 1, Collections.singletonList("1"), new Field("name", SchemaBuilder.builder().intType()), 1),
        Arguments.of("long", 1, Collections.singletonList("1"), new Field("name", SchemaBuilder.builder().longType()), 1L),
        Arguments.of("short", 1, Collections.singletonList("1"), new Field("name", SchemaBuilder.builder().intType()), (short)1),
        Arguments.of("double", 1, Collections.singletonList("1.0"), new Field("name", SchemaBuilder.builder().doubleType()), 1.0),
        Arguments.of("timestamp", 1, Collections.singletonList("2019-12-06T12:00:00"), new Field("name", SchemaBuilder.builder().stringType()), FIXED_DATE),
        Arguments.of("longTimestamp", 1, Collections.singletonList("2019-12-06T12:00:00"), new Field("name", SchemaBuilder.builder().longType()), FIXED_DATE.toInstant(ZoneOffset.UTC).toEpochMilli()),
        Arguments.of("stringTimestamp", 1, Collections.singletonList("2019-12-06T12:00:00"), new Field("name", SchemaBuilder.builder().stringType()), "2019-12-06T12:00"),
        Arguments.of("uuid", 1, Collections.singletonList("0177f035-e51c-4a46-8b82-5b157371c2a5"), new Field("name", SchemaBuilder.builder().stringType()), UUID.fromString("0177f035-e51c-4a46-8b82-5b157371c2a5")),
        Arguments.of("boolean", 1, Collections.singletonList("true"), new Field("name", SchemaBuilder.builder().booleanType()), Boolean.TRUE)
      );
  }

  @ParameterizedTest
  @MethodSource("parametersForGenerateRandomValueForField")
  void testGenerateRandomValueForField(String valueExpression, Integer valueLength, List<String> fieldValuesList, Field field, Object expected) {
    assertThat(RandomTool.generateRandom(valueExpression, valueLength, fieldValuesList, field)).isEqualTo(expected);
  }
}