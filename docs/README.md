# Argon Compiler

Argon is a programming language created by Jacob. This repository contains the compiler for the Argon language written in Java.

## Language Guide

The full language guide detailing the syntax and grammar of Argon can be found in the [docs/language-guide](docs/language-guide) directory.

## Versioning

Information about versioning features is available in the [docs/versioning](docs/versioning) folder.

## Compiler Architecture

Documentation on the overall compiler architecture can be found in the [docs/compiler-architecture](docs/compiler-architecture) directory.

## Components

- **Lexer**: The lexer implementation is located in [src/main/Lexer](src/main/Lexer).
- **Parser**: The parser implementation is located in [src/main/Parser](src/main/Parser).
- **Intermediate Abstract Syntax Tree (AST) Nodes**: These nodes, which form the hierarchy of the syntax, are located in [src/main/IntermediateASTNodes](src/main/IntermediateASTNodes).

### Abstract Syntax Tree (AST)

The AST is structured as follows:

- **Class Declaration Node**: Represents a class declaration. It contains lists of method and field name nodes.
  - **Method Node**: Represents a method declaration. It contains lists of statement nodes and parameter nodes.
  - **Parameter Node**: Represents a method parameter.

The parsing method utilizes a recursive descent approach.

## Current Status

This project is currently being developed solo. The parsing functionality has been completed, achieving consistent lexing and parsing times of 3-6ms. The focus now is on code generation and optimization.

## Future Work

Future versions of Argon may include additional features and optimizations.

## About Argon

Argon is a minimalistic programming language designed for simplicity and ease of use.

## Author

This compiler for Argon is authored by [Jacob].

Feel free to contribute to the project or provide feedback. Happy coding!
