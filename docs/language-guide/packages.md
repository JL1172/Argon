# Documentation for packages

- In order to import a package from a different package:
```
Project

-
 |_ 
    AnimalClasses (different package)
-
 |_ 
   ReptileClasses (different package)
  
```

- At the top of the file you must use this syntax
```
link AnimalClasses from "../package-route";
```

