Plugin for IntelliJ (2020.3 and later) to generate property enums for Entities
=============================

This is related to the project moprhium [https://github.com/sboesebeck/morphium] the caching mongodb POJO Object Mapper
and Messaging System. When querying mongodb, you usually need to define the field you are looking for as string - which
kind of breaks the whole abstraction.

The plugin is now available in the IntelliJ IDE! If from inside the IDE use these
directions [IntelliJ Plugin Management](https://www.jetbrains.com/help/idea/2020.3/managing-plugins.html)

Search for "Fields / Properties to Enumeration Generator" under "Marketplace".

_NOTE:_ Once installed just use `ALT + INSERT`/`CMD-N` or whatever shortcut brings up the "Generate"-Dialog and select "
Generate Property Enums"

# Use Enums instead of field name strings
Although you can both define the field string the way it's specified in java (so if you have a property `String firstName` you can either query `firstName` or `first_name`),
it's error prone. Every mistype would cause an Exception. And there is no auto completion available for those fields.

Morphium supports queries with Enums as field names. the idea is, to add a list of field names as enums for every entity.
Problem is: you'd need to type that. Hence I wrote this little plugin to make things a bit easier...

It will create an Enum called `Fields` for all properties in your entity. So you can easily use them in Morphium queries:

```java

@Entity
public class Person {
    private ObjectId id;
    private String firstName;
    private String lastName;
    //getter and Setter here

    //generated fields
    public enum Fields {firstName, id, lastName}
}
```

When querying mongo with morphium you would use it like this:

```java
   Query<Person> p=morphium.createQuery(Person.class);
        p=p.f(Person.Fields.firstName).eq("John");
        p=p.f(Person.Fields.lastName).eq("Doe");
        List<Person> lst=p.asList();
```

This makes it way less error prone, and you can use IntelliJ auto completion to access your fields.

See [https://github.com/sboesebeck/morphium] for more information.
