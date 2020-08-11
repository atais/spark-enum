# Hacky - fake case class parent

## Solution

You can learn how *Spark* serialization/deserialization works checking this solution, however 
I find it very tricky and would not use it on production.

The solution comes down to having:

```
sealed class EnumLike(val value: String) extends Product with Serializable 
```

Notice, that it is not an `abstract class`. It works because *Spark*
 - serializes the object of a child
 - deserializes as an instance of a **parent**!
 
And this is where things are getting tricky, check the source for more information.

[Source](src/test/scala/com/github/atais/HackySpec.scala) 

## Pros
 - it seems like it is working correctly, and to some extent it does
 
## Cons
 - hacky
 - may cause a lot of confusion on production

## Extra sources
 - [Should I use the final modifier when declaring case classes?](https://stackoverflow.com/questions/34561614)