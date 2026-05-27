# Gemini Project Guidelines

This document provides guidelines for the Gemini AI assistant to follow when working on this project.

## Conventions

- **Code Style:** Adhere to the [Spring Java Format](httpss://github.com/spring-io/spring-java-format). Analyze existing
  code to maintain consistency in style and structure.
- **Libraries/Frameworks:** Utilize the existing libraries and frameworks. Do not introduce new ones without explicit
  instruction.

## Development Practices

- **Lombok:** Use Lombok for boilerplate code generation, especially for Plain Old Java Objects (POJOs) (e.g., using
  `@Data`, `@Value`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`).
- **Logging:** Use SLF4J for all logging purposes.
- **Testing:**
    - Write unit tests using JUnit 6.
    - Use AssertJ for assertions (e.g., `assertThat(result).isEqualTo(expected)`).
- **Building:**
    - Always use the Gradle wrapper (`./gradlew`) for all Gradle commands.
    - The project uses Gradle toolchains to manage the Java version. Ensure any new configurations respect this.

## Commits

- Follow conventional commit message standards.
- Ensure commits are atomic and represent a single logical change.
