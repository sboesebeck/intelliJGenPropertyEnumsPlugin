Plugin for IntelliJ (12.x) to genereate property enums for Entities
=============================

This is related to the project moprhium [https://github.com/sboesebeck/morphium] the caching mongodb POJO Object Mapper.
When querying mongodb, you usually need to define the field you are looking for as string - which kind of breaks the whole abstraction.

# Use Enums instead of field name strings
Although you can both define the field string the way it's specified in java (so if you have a property `String firstName` you can either query `firstName` or `first_name`),
it's error prone. Every mistype would cause an Exception.
And there is no autocompletion whats or ever

Morphium supports queries with Enums as field names. the idea is, to add a list of field names as enums for every entity.
Problem is: you'd need to type that.
hence i wrote this little pkugin to make things a bit easier...

It will create an Enum called `Fields` for all properties in your entity. So you can easily use them in Morphium queries:
```java
  @Entity
  public class Persion {
      private ObjectId id;
      private String firstName;
      private String lastName;
      //getter and Setter here
      
      //generated fields
      public enum Fields { id, firstName, lastName }
  }
```

When querying mongo with morpium you would use it like this:
```java
   Query<Person> p=morphium.createQuery(Person.class);
   p=p.f(Person.Fields.firstName).eq("John");
   p=p.f(Person.Fields.lastName).eq("Doe");
   List<Person> lst=p.asList();
```

This makes it way less error prone and you can use IntelliJ auto completion to access your fields.

See [https://github.com/sboesebeck/morphium] for more information.
