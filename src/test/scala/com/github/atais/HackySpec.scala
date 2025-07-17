package com.github.atais

import com.github.atais.HackySpec._
import com.github.atais.HackySpec.EnumLike._
import com.holdenkarau.spark.testing.DatasetSuiteBase
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.Assertions

class HackySpec extends AnyFlatSpec with Matchers with DatasetSuiteBase {

  import spark.implicits._

  import scala.collection.JavaConverters._

  val foos: Seq[TestContainer] = Seq(
    TestContainer(One),
    TestContainer(Two),
    TestContainer(Three)
  )

  it should "propely pattern match & have correct class in raw example" in {
    foos.map(_.v).foreach {
      case v @ One =>
        v.value shouldBe One.value
        v.getClass shouldBe One.getClass
        v.special shouldBe One.special
      case v @ Two =>
        v.value shouldBe Two.value
        v.getClass shouldBe Two.getClass
        v.special shouldBe Two.special
      case v @ Three =>
        v.value shouldBe Three.value
        v.getClass shouldBe Three.getClass
        v.special shouldBe Three.special
      case _ =>
        Assertions.fail("we do not have other cases!")
    }

  }

  it should "strangely pattern match in ds example" in {
    foos.toDS().show(false)

    foos
      .toDS()
      .collectAsList()
      .asScala
      .map(_.v)
      .foreach { v =>
        assertThrows[NotImplementedError] {
          v.special
        }
      }

    foos
      .toDS()
      .collectAsList()
      .asScala
      .map(_.v)
      .foreach {
        case v @ One =>
          v.value shouldBe One.value
          v.getClass shouldBe classOf[EnumLike]
          assertThrows[ClassCastException] {
            v.special
          }
        case v @ Two =>
          v.value shouldBe Two.value
          v.getClass shouldBe classOf[EnumLike]
          assertThrows[ClassCastException] {
            v.special
          }
        case v @ Three =>
          v.value shouldBe Three.value
          v.getClass shouldBe classOf[EnumLike]
          assertThrows[ClassCastException] {
            v.special
          }
        case _ =>
          Assertions.fail("we do not have other cases!")
      }
  }

}

private[atais] object HackySpec {

  trait SparkEnum extends Product with Serializable {

    val value: Any

    override def productElement(n: Int): Any = ???

    override def productArity: Int = ???

    override def canEqual(that: Any): Boolean = that.isInstanceOf[SparkEnum]

    override def hashCode(): Int = value.hashCode()

    override def equals(obj: Any): Boolean =
      canEqual(obj) && obj.asInstanceOf[SparkEnum].value == value

    override def toString: String = value.toString
  }

  sealed class EnumLike private (override val value: String) extends SparkEnum {
    def special: Int = ???
  }

  object EnumLike {

    case object One extends EnumLike("One") {
      override def special: Int = 1
    }

    case object Two extends EnumLike("Two") {
      override def special: Int = 2
    }

    case object Three extends EnumLike("Three") {
      override def special: Int = 3
    }

  }

  case class TestContainer(v: EnumLike)

}
