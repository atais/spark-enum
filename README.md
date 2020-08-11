# Spark-enum
The project showcases approaches you can take on Spark missing `Enum` support. 

The limitation is caused by lack of possibility to encode an `ADT` in `DataSet` column.
The approach would require to provide a custom `Encoder`, which is not possible at the moment.

`Kryo` will not help you either, check "Fake case class parent" to understand why. 

Articles or SO posts you can find useful:
 - [Custom encoders](https://stackoverflow.com/a/39442829/1549135)
 - [Encode an ADT in Spark DataSet column](https://stackoverflow.com/a/41082540/1549135)
 
# What and how?

Each approach is showcased with a test suite that compares two situations:
 - Regular Scala collection with created objects
 - Spark-ingested `Dataset` based on the above collection
 
The test is linked in each title.
 
**Keep in mind that in some cases, `Spark` looses certain data during encoding/decoding process,
which is always reflected in the assertions!**

# Approaches

1. [Case class wrapper](1caseclass.md) 
2. [Extra field with primitive column](2extrafield.md) 
3. [Fake case class parent](3hacky.md) 
4. [Type alias](4typealias.md) 

