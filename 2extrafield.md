# A primitive column with extra field

## Solution

This way you actually avoid serializing `Enum` value, instead
you need to keep the actual column value to a primitive and dynamically 
instantiate `Enum` based on this value. 

```
case class TestContainer(_v: String) {
  val v: EnumLike = EnumLike(_v)
}
```

[Source](src/test/scala/com/github/atais/ExtraFieldSpec.scala) 

## Pros:
 - 100% vanilla Scala `Enum` support
 
## Cons
 - extra field may be confusing
 - primitive field can't be `private` (Spark limitation)
 - overhead of creating extra objects

## Source: 
 - [SPARK-17248](https://issues.apache.org/jira/browse/SPARK-17248)
 - [Titanic Dataset](https://github.com/dongjinleekr/spark-dataset/blob/master/src/main/scala/com/github/dongjinleekr/spark/dataset/Titanic.scala)

