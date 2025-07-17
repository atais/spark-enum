package com.github.atais

import com.github.atais.SealedCaseClassSpec._
import com.github.atais.SealedCaseClassSpec.EnumLike._
import com.holdenkarau.spark.testing.DatasetSuiteBase
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SealedCaseClassSpec
    extends AnyFlatSpec
    with Matchers
    with DatasetSuiteBase {

  import spark.implicits._

  import scala.collection.JavaConverters._

  val foos: Seq[TestContainer] = Seq(
    TestContainer(One),
    TestContainer(Two),
    TestContainer(Three)
  )

  private val scenario: EnumLike => Assertion = {
    case v @ One =>
      v.value shouldBe One.value
      v.getClass shouldBe One.getClass
      special(v) shouldBe 1
    case v @ Two =>
      v.value shouldBe Two.value
      v.getClass shouldBe Two.getClass
      special(v) shouldBe 2
    case v @ Three =>
      v.value shouldBe Three.value
      v.getClass shouldBe Three.getClass
      special(v) shouldBe 3
    case _ =>
      fail("we do not have other cases!")
  }

  it should "propely pattern match in raw example" in {
    foos.map(_.v).foreach {
      scenario.apply
    }
  }

  it should "propely pattern match in ds example" in {
    foos.toDS().show(false)

    foos
      .toDS()
      .collectAsList()
      .asScala
      .map(_.v)
      .foreach {
        scenario.apply
      }
  }

}

object SealedCaseClassSpec {

  sealed case class EnumLike private (value: String)

  object EnumLike {
    final val One = new EnumLike("One")
    final val Two = new EnumLike("Two")
    final val Three = new EnumLike("Three")

    final val values: Seq[EnumLike] = Seq(One, Two, Three)

    def apply(in: String): EnumLike =
      values
        .find(_.value == in)
        .getOrElse(throw new RuntimeException(s"no value $in"))

    def special(v: EnumLike): Int = v match {
      case One   => 1
      case Two   => 2
      case Three => 3
      case _     => ???
    }
  }

  case class TestContainer(v: EnumLike)

}
