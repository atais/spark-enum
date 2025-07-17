# Wrapper case class

## Solution

This is by far the best solution that uses Scala 2.12 feature of [Allow user-defined [un]apply in case companion](https://github.com/scala/scala/releases/tag/v2.12.2).
Keeping it simple, wrap the value in a case class, for example:

```
sealed case class EnumLike private (value: String)

object EnumLike {
    final val One = new EnumLike("One")
    final val Two = new EnumLike("Two")
    final val Three = new EnumLike("Three")
}
```

[Source](src/test/scala/com/github/atais/SealedCaseClassSpec.scala) 

## Pros
 - simple
 - works as expected also with pattern matching
 - allows to limit the number of instances to predefined types
 
## Cons
 - wrapper is creating an overhead over the primitive value (impacting the performance)

## Source

