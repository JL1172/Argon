# Documentation on Literals

## Important Note On Float And Double (IEEE 754 standard)

- ***floats***
  - 1 bit for sign
  - 8 bits for exponent
  - 23 bits for the fraction

- ***double***
  - 1 bit for the sign
  - 11 bits for the exponent
  - 52 bits for the fraction

### Floating Point Numbers (32 bit floating point number)

- The tokenization in the lexer first tokenizes any floating point number as a numeric value.
- The parser will then later differentiate the types of numbers

### Double (64 bit floating point)

- The tokenization in the lexer first tokenizes any double precision floating point number as a numeric value.
- The parser will then later differentiate the types of numbers

### Int (32 bit signed integer)

- The tokenization in the lexer first tokenizes any int as a numeric value. 
- The parser will then later differentiate the types of numbers

### Boolean

- The lexer will tokenize anything with true and false. 
- There Are no implicit falsey or truthy values in this version.
- `isPasswordCorrect::boolean = true;`

### Null

- The lexer will tokenize anything using null

### String

- The lexer will tokenize strings with the following regex: `"\"(?:\\\\\"|[^\"])*\"|'(?:\\\\'|[^'])*'"`

### Array (static)

- The lexer will tokenize strings with the following regex: 
```
[a-zA-Z_][a-zA-Z0-9_]*::ArrayS\\\\[.*?\\\\]\\\\s*=\\\\s*new\\\\s+ArrayS\\\\(.*?\\\\);|[a-zA-Z_][a-zA-Z0-9_]*::ArrayS\\\\[.*?\\\\]\\\\s*=\\\\s*\\\\[.*?\\\\];
```

- *two ways to instantiate dynamic arrays*

```
prot expenseList::ArrayS[int] = new ArrayD(10);

// also

prot expenseList::ArrayS[int](10); //expects on initialization an integer array of length 10
```

- *or*

```
prot expenseList::ArrayS[int] = [1,5,3,2,7];
```

### Array (dynamic)

- The lexer will tokenize strings with the following regex: 
```
[a-zA-Z_][a-zA-Z0-9_]*::ArrayS\\\\[.*?\\\\]\\\\s*=\\\\s*new\\\\s+ArrayS\\\\(.*?\\\\);|[a-zA-Z_][a-zA-Z0-9_]*::ArrayS\\\\[.*?\\\\]\\\\s*=\\\\s*\\\\[.*?\\\\];
```

- *two ways to instantiate dynamic arrays*

```
prot expenseList::ArrayD[int] = new ArrayD();
```

- *or*

```
prot expenseList::ArrayD[int] = [1,5,3,2,7];
```