# Lexer Architecture Overview

## Token List

The lexer starts with defining a list of tokens that correspond to different features of the language. Each token is represented by an enum called `TokenType`, which includes:

- Keywords such as `CLS` (class), `PUB` (public), `PROT` (protected), etc.
- Output statements like `CONSOLE_OUT`.
- Identifiers for method, class, or property names (`IDENTIFIER`).
- Primitive types such as `NUMBER`, `STRING`, `BOOLEAN`, and `DATE`.
- Operators including arithmetic operators (`PLUS`, `MINUS`, etc.), assignment operators (`ASSIGNMENT`, `DIVIDED_EQUALS_SIGN`, etc.), and comparison operators (`STRICTLY_EQUALS`, `GREATER_THAN`, etc.).
- Punctuation symbols like `SEMICOLON`, `LPAREN` (left parenthesis), `RPAREN` (right parenthesis), etc.
- Composite data types such as `STATIC_ARRAY` and `DYNAMIC_ARRAY`.

## TokenType Class

A `TokenType` class is created to handle the token type from the enum along with the associated value.

## Patterns

The lexer uses a series of patterns, corresponding 1:1 in index with the `TokenType` enum, to match the correct token type.

## Tokenization Algorithm

The main part of the lexer consists of a tokenization method. This method iterates through the input code, matches each value with the correct pattern, and packages it into a token with the help of the `Token` class. These tokens are then added to an array, which is returned as a comprehensive list of tokens representing the segmented code.

