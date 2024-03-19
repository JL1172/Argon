# Object-Oriented Language Design Considerations

## Types:

### Type System:

- statically typed

#### Primitive Data Types:

- number (the reason for this choice is because this is compiling down to javascript and javascript's number type is a double precision 64 bit floating point number)
- string
- boolean
- void
- date

#### Composite Data Types:

- static array
- dynamic array
- object
- stack
- linked-list
- hash map
- queue
- set
- graph
- tree
- priority queue
- regex

## Commenting

```
//
single
/**/
multiline
```

## Syntax:

### Example of Syntax

```
// for main class need pub

//this class not being prefixed by anything implies it package private
cls IntroductionClass {

    //singular means these variables or properties are instantiated as new every single instance, they are not pooled
    //similar to constructor in javascript
    //this is built in

    singular(name::string, username::string) {
        name::string = this.name; //private by default //means you need a getter function
        pub username::string = this.username //fleshed out by pub access modifier //you can access this directly without a getter function
    }

    //multiple means these are shared across all classes
    //this is built in

    multiple() {

        //variable construction

        /*
            if not in method  and in either singular or multiple
            access modifier + variableName (must be camel case):: type = value

            if it is a composite datatype
            access modifier + variableName(must be camelCase)::ArrayD(dynamic array)<type of composite data type> = new ArrayD();
        */

        pub listOfNames::ArrayD<string>[] = new ArrayD();
    }


        //method construction
        /*

            access modifier + methodName + <return type of method> + (argument::argumentType) {

            }

        */
    isNameUnique<boolean>(name::string) {
        for (i::number = 0; i < this.listOfNames.length; i++) {
            if (this.listOfNames[i] === name) {
                return false;
            }
        }
        return true;
    }

    pub addName<void>(name::string) {

        // note, you never have to add any access modifiers to variables declared inside method because they are block scoped
        if (this.isNameUnique(this.name)) {
            nameToInsert::string = name;
            this.listOfNames.push(nameToInsert);
        } else {
            console.out("This Name Already Exists In The List.");
        }

    }

    pub viewName<void>() {
        console.out("Hello My Name Is: (without concatentation or interpolation)", this.name);
        console.out("Hello My Name Is (with concatenation) " + this.name);
        console.out(`Hello My Name Is (with interpolation) ${this.name}.`)
    }


    pub viewNameList<void>() {
        console.out(this.listOfNames);
    }
    stat globalMethod<void>() {
        console.out("this is a static method only available through accessing the class itself");
    }
}

pub cls ExampleClass {
    myIntroClass::IntroductionClass = new IntroductionClass("jacob","jacoblang11"); //inserted into singular (or constructor) to instantiate for this instance

    //because name is not prefixed with pub it will be unaccessible
    myPrivateUnreachableVariable::string = myIntroClass.name;
    console.out(myPrivateUnreachableVariable);
    //will throw an access error

    //because username is prefixed with pub it will be accesssible
    myPublicUsername::string = myIntroClass.username;
    console.out(myPublicUsername);
    //will output "jacoblang11";

    myIntroClass.viewName();
    //will output the following
    /*
        Hello My Name Is: (without concatenation or interpolation) jacob
        Hello My Name Is (with concatenation) jacob
        Hello My Name Is (with interpolation) jacob.
    */

    mySecondIntroClass::IntroductionClass = new IntroductionClass("patrick","patricklang96");

    myIntroClass.addName("jacob");
    mySecondIntroClass.addName("patrick");
    
    myIntroClass.viewNameList();
    //outputs ["jacob","patrick"];

    mySecondIntroClass.viewNameList();
    //outputs ["jacob","patrick"];

    myIntroClass.addName("jacob");
    //outputs This Name Already Exists In The List.

    myIntroClass.isNameUnique("jacob");
    //will throw an access error

    IntroductionClass.globalMethod();
    //outputs "this is a static method only available through accessing the class itself"
}
```

### Class Method Property Naming:

- Pascal Casing for classes
- camelCasing for methods
- camel or snake casing for properties

### access modifiers:

- pub
- prot (visible to each class)
- stat (specific to class only, short for static) (implies public as well so no need to do pub stat)
- no prefix is same as java and is package specific so file only
- all classes are package-private by default
- all properties are private by default unless specified as pub

### properties

```
...{

myVar::boolean = true;
prot my_var::number = 2;
pub name::string = "hello world";

...
}
```

### arithemetic

```
+   // add
++  // increment
-   // subtract
--  // decrement
*   // multiply
**  // exponentiate
/   //division
/** //square root
%   // modulus

```

### for loops

```
names::ArrayS<string>[] = ["jacob","patrick","alaina","louis"];
for (i::number; i < names.length; i++) {
    console.out(names[i]);
    //when i = 0 outputs jacob, so on so forth
}

for (num::string: names) {
    console.log(num) //jacob patrick 
}

for (i=>names) {
    console.log(names[i])
}
```

### while
```
names::ArrayS<string>[] = ["jacob","patrick","alaina","louis"];
while (condition) {
    
}

```

### hashmaps 

```
map::HashMap<number> = new HashMap();
```

#### Hashmap Method
- set
- autoSet (automatically compounds)
- get
- remove
- size

### linked lists

```
listNode::LinkedList<number> = new LinkedList();
```

#### Linked Lists Methods
- removeAt
- removeValue
- addAt
- atWhen
- sort
- reverse


### Arrays

```
two different types and 2 ways to instantiate both

Static array
ArrayS

dynamic array
ArrayD

listOfNames::ArrayD<string>[] = new ArrayD();
//or can instantiate like this
listOfNames::ArrayD<string>[] = ["jacob","patrick"];
METHODS:
//empty
//push
//shift
//unshift
//pop


listOfOtherNames::ArrayS<string>[] = new ArrayS(5);
//have to specify length or you can predefine the array as such
listOfOtherNames::ArrayS<string>[] = ["hello","goodnight"];
//no methods except mutability at index
```

#### Dynamic Array Methods
- push
- pop
- unshift
- shift
- includes
- filter
- forEach
- map
- indexOf
- at
- copy (full copy)
- remove (index specific)
- slice (start,endpoint)
- reverse
- toString() (turns everything to string); (like join(""))
- length
- append

#### Static Array Methods
- includes
- forEach
- map
- filter
- indexOf
- at
- copy
- slice (start,endpoint)
- reverse
- length
- append

### Strings

#### String Methods
- slice(start,endpoint)
- toArray (same as split)
- replace(letter to replace, what to replace it with)
- upperCase
- lowerCase
- UpperCase
- reverse
- indexOf
- length
- trim
- append
- upperCaseAt
- lowerCaseAt 

### Objects

#### Object Methods
- Object.from(array).entries
- Object.from(array).values
- Object.from(array).keys
- hasProperty
- hasValue
- delete(key) 
- size
- instanceOf() //returns inheritence tree
- copy
- restrict (immutable)
- unrestrict
- isRestricted() boolean


### Error Handling:

- Design clear and meaningful error messages for semantic errors to aid in debugging and troubleshooting.

### Scope and Lifetime:

- block scoping

### Assignment

```
=
/=
*=
**=
%/
+=
-=
++=
--=
```

### Polymorphism

```
cls MyClass {

    pub draw<void>() {

        console.out("Hello world From Jacob");

    }
}


cls MySecondClass extends MyClass {

    @override(draw, ()::void {console.out("Hello World From My Second Class")})
}


pub cls Main {

    stat main<void>() {

        myFirstClass::MyClass = new MyClass();

        mySecondClass::MySecondClass = new MySecondClass();

        console.out("This is my first class's draw method", myFirstClass.draw());
        //outputs "Hello world From Jacob"

        console.out("This is my second class's draw method that was overridden", mySecondClass.draw());
        //outputs "Hello World From My Second Class"
    }
}
```

### Prototypical Inheritence is the norm 

## Features
- prototypical inheritence
- polymorphism
- access modifiers
- static typing 

## Library Usage:

### Standard Library:

- Determine the scope and functionality of the standard library provided with the language, including common utilities, data structures, and algorithms.

### Interoperability:

- Ensure compatibility and interoperability with existing libraries and ecosystems to facilitate integration with other software components.

## Extensibility:

### Language Extensions:

- Design mechanisms for extending the language with new features and constructs, such as macros, language plugins, or metaprogramming facilities.

### Customization:

- Provide options for customizing language behavior and syntax to suit specific use cases or preferences of developers.

## Performance:

### Efficiency:

- Optimize language features and runtime behavior for performance, including minimizing memory usage, reducing overhead, and optimizing execution speed.

### Compilation Time:

- Consider the impact of language design decisions on compilation time and explore strategies for improving compilation speed without sacrificing correctness or expressiveness.

## Security:

### Memory Safety:

- Design language features and runtime mechanisms to prevent memory-related vulnerabilities, such as buffer overflows, memory leaks, and dangling pointers.

### Input Validation:

- Enforce input validation and sanitization to prevent injection attacks, buffer overflows, and other security vulnerabilities.

## Documentation and Tooling:

### Documentation:

- Provide comprehensive documentation for language features, syntax, semantics, and usage guidelines to assist developers in learning and using the language effectively.

### Development Tools:

- Develop integrated development environments (IDEs), debuggers, profilers, and other development tools to support language development and facilitate software development workflows.
