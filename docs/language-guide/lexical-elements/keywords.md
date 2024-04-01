# Documentation on Keywords

## Keywords in Argon

- `new`
- `this`
- `const`
- `cls`
- `stat`
- `void`

### New

- `new` keyword is only to be used to instantiating an instance of a class
- `MyClass classInstance = new MyClass();`

### This

- `this` keyword refers to a field name or method name in a class.
- When using `this`, it is refering to that class's instance of its field names or methods. 
- `this` refer to the class's properties and traits to differentiate.

- *example of this*

```
//field name
reserved name::string;

//@this method (built in to Argon)
@this(name::string) {
    //this.name is referring to field name above
    this.name = name;
}

reserved concatName(name1::string, name2::string) :: string {
    return name1 + name2;
}

public getFullName() :: string {
    fullName:: string = this.concatName(this.name, "lang");
    return fullName;
}
```

### Const

- When using `const`, it declares immutabilty to the variable.
- When prefixed with `const`, a variable cannot be reassigned to another value. 
- In this version, const does not make reference values immutable, this will be built in once the first version is released

### Stat

- When using `stat` this is also know as `static` in other languages such as java and javascript.
- `stat` assigns the given field or method it prefixes to the class itself, and will not be available to an instances of the class.
```
...
public StringClass {
    public stat concat(str::string, str2::string) {
            return str + str2;
    }
}
...

...another class
concatedStrings = StringClass.concat("hello", "world");
```

### Void 

- `void` is only for methods when they return nothing

### Cls 

- `cls` is used when creating a class
```
cls MyExampleClass {
    ... code 
}
```