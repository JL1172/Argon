# Documentation on Static Typing Methods

## General Rules

- For this version, there is only one typing per variable or method, no multiple type options yet

## Types

#### Primitive Types

- Date
- number (double precision 64 bit floating point number)
- string 
- boolean 
- void
- null

#### Composite Types

- ArrayD
- ArrayS

## How Typing is Statically Declared (primitive types)

### Description for Methods

```
reserved getName() :: string {

}
```

- basic pattern:
  - `methodName + () + ::Type {...code body}`

### Description for Variables

```
cls AccountDetails {

    reserved age::number;
    reserved accountHolder::string;

    //built in method (constructor method in javascript)
    @this(name::string) {
        this.accountHolder = name;
        //if the input was anything else it would throw error
    }
}
```

## Typing for Composite Types

```
cls AccountDetails {

    //reserved makes these private (recommended)
    //you can say this runningExpenses variable is going to be an ArrayD[with type number nested in these square brackets]
    reserved runningExpenses::ArrayD[number];

    //can also add more constraints by initializing like this: reserved pastExpenses::ArrayS[number](10); which says it expects a static array with a length of 10
    reserved pastExpenses::ArrayS[number];

    //const makes this value immutable (like const or final) (cannot be changed)
    reserved const accountHolder::string; 

}

```

