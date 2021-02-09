Plugin for IntelliJ (2020.3 and later) to generate property enums for Entities
=============================

This is related to the project moprhium [https://github.com/sboesebeck/morphium] the caching mongodb POJO Object Mapper
and Messaging System. When querying mongodb, you usually need to define the field you are looking for as string - which
kind of breaks the whole abstraction.

The plugin is now available in the IntelliJ IDE! If you want to install the plugin from inside the IDE use these
directions [IntelliJ Plugin Management](https://www.jetbrains.com/help/idea/2020.3/managing-plugins.html)

Search for "Fields / Properties to Enumeration Generator" under "Marketplace".

_NOTE:_ Once installed, just use `ALT + INSERT`/`CMD-N` or whatever shortcut brings up the "Generate"-Dialog and
select "Generate Property Enums"

# Use Enums instead of field name strings

Usually, when querying mongodb, you need to define the field as a String. With the help of Morphium, you might also use
the java-name of the property instead of the field in mongodb (e.g. the `_id` Field in Mongodb, and the field name in
your java class, in the example below it would be `identifier`).

Although both ways to specify a field in a query are possible with morphium (you can use both `query.f("_id")`
and `query.f("identifier")`), it is still error prone and there is no help from the IDE, like auto completion.

Morphium supports queries with enum values as field names. To get the best out of this feature, you need to add an enum
for every field in your entity. Problem is: you'd need to type that. Hence, I wrote this little plugin to make things a
bit easier...

It will create an Enum called `Fields` for all properties in your entity. So you can easily use them in Morphium
queries:

```java

@Entity
public class Person {
    @Id
    private ObjectId identifier;
    private String firstName;
    private String lastName;
    //getter and Setter here

    //generated fields
    public enum Fields {firstName, identifier, lastName}
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

## Property names

Morphium also supports naming of fields, so if you do not like the default name that morphium generates, you can change
that. This would cause your queries to fail. Let's take the example from above and change the name for `lastName`:

```java

@Entity
public class Person {
    @Id
    private ObjectId identifier;
    private String firstName;
    @Property("surname")
    private String lastName;
    //getter and Setter here

    //generated fields
    public enum Fields {firstName, identifier, lastName}
}
```

now, in MongoDB each Person will have a field called `surname` containing the lastName. When creating a query for
mongodb directly , you need to define how it is called in the database. With morphium you can just use the enum. The
query from above will work in both cases.

_Attention_: migration of data is not part of morphium. If you rename an already existing field, this will likely cause
problems

## updating / refactoring

To update your fields, whenever you change property names, you should just remove the `Fields` enum from your entity and
re-generate it using the plugin. This way you will get compiler errors, for every place you used a field name that was
renamed.

See [https://github.com/sboesebeck/morphium] for more information.
