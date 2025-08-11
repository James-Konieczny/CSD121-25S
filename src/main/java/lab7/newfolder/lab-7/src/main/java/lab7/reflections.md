My program uses dependency injection and interface-based programming to make testing flexible and efficient with lambda expressions.

My searchRecipes method receives (injection) its DataService rather than creating one. This shows dependency injection and allows for injecting test doubles (the mock objects).

The DataService interface defines a single method contract (getRecipes()). This allows multiple implementations without changing dependent code.
By defining a DataService interface, the code follows polymorphism, allowing any and multiple implementations (real database connection, a lambda expression, anonymous class) to be used interchangeably.

This decouples the searchRecipes method from concrete implementations, so that it depends on only the abstract DataService interface.
My searchRecipes method knows nothing (decoupled) about the database implementations, file systems, or network connections. Again, it only depends on the abstract DataService interface.

Lambda expression and anonymous classes are treated the same as real implementations.
During testing, mock objects (via lambdas - like what I did - or anonymous class) simulate the behaviour of real dependencies, creating controlled test data without needing a database.
This approach exemplifies how interfaces and dependency injection promote testability while maintaining clean, modular design.
