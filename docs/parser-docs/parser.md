# Overview Of Parser

## ***Note that these notes are not official and are documentation for my own use***

## Flow of Parser

#### Recieve Tokens 

#### Parse Tokens 

#### Build Abstract Syntax Tree Comprised Of Differing Nodes With hierarchical Relationships (parent & child)

#### Below is a flow of all areas for the parsing algorithm to test for.

- at first index, view *access modifier*, ensure it is not `reserved` or `prot`, increment
  - else {throw runtimeErrorException}

- if there is no *access modifier* ensure the very next keyword is `cls`, increment
  - else {throw runtimeErrorException}

- ensure that the next token is type `IDENTIFIER` and follows the naming conventions (Pascal Case) increment
  - else if either of those criterion are not met, throw runtimeErrorException

- next token should be type `LBrace` increment. 
  - else throw runErrorException

- then section out code body from that index to end index which should be a `RBrace`
  - else throw run Error Exception

- then section code body into two sections : 
  1. field names.
  2. methodName. 

- then go through code body. 
                        
  1. Test for field name = `_access modifier? + stat? + const? + identifier + :: + typeName;` 



  2. Test for method name = `_access modifier? + stat? + identifier + ( + ...parameters::type) :: + typeName {...codebody and return type matches typeName};`

- end tree should be 
![AST](<Screenshot from 2024-04-03 09-01-15.png>)
                                        