# Documentation of Access Modifiers

1. `pub`
2. `prot`
3. `reserved`

## General Rules

**ALL ACCESS MODIFIERS MUST PREFIX AND COME FIRST IN A CLASS, METHOD, OR VARIABLE DECLARATION**

## pub access modifier

### Description For Classes:

- The `pub` access modifier globally exposes a class to a project

### Description For Methods:

- The `pub` access modifier globally exposes methods, but if a method is declared as `pub`, but its parent class is not public, it will throw an error such as:
  - `child method: [METHOD NAME] cannot be globally exposed because of its parent's visibility.`

### Description For Variables:

- The `pub` access modifier globally exposes a variable, but can only be exposed by `pub` if the parent class is public, if not, it will throw an error such as:
  - `variable: [variable name] cannot be globally exposed because of its parent's visibility.`

- The `pub` access modifier does nothing if a variable is initialized within a method and will throw an error because the variables are block scoped.
  - `variable: [variable name] cannot be globally exposed within method.`

## prot access modifier

### Description For Classes:

- The `prot` access modifier has no effect on classes (will throw error)

### Description For Methods:

- The `prot` access modifier has no effect on method (will throw error)

### Description For Variables:

- The `prot` access modifier makes a variable read-only and package private. It is only visible to the other classes in the umbrella of the parent directory.
- has no effect on variables in methods

## reserved access modifier

### Description For Classes:

- The `reserved` access modifier has no effect on classes (will throw error) that are not nested classes

### Description For Methods:

- The `reserved` access modifier has no effect on methods (will throw error)

### Description For Variables:

- The `reserved` access modifier a variable private and makes it private to its class.
- This is the recommended procedure for initializing variables
- Has no effect on variables in methods


