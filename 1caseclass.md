# Wrapper case class

## Solution

Keeping it simple, wrap the value in a case class, for example:

```
case class EnumLike(value: String)

object EnumLike {
    final val One = EnumLike("One")
    final val Two = EnumLike("Two")
    final val Three = EnumLike("Three")
}
```

[Source](src/test/scala/com/github/atais/CaseClassSpec.scala) 

## Pros:
 - simple
 - works as expected also with pattern matching
 
## Cons
 - wrapper is creating an overhead over the primitive value (impacting the performance)
 - you can't actually separate logic for different cases, since there is only one main class

## Source
 - [Enums in Spark Datasets](http://monkeythinkmonkeycode.com/eums-in-spark-datasets/)
