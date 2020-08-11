package com.github.atais

import com.github.atais.ExtraFieldSpec.ExampleEnum._
import com.github.atais.ExtraFieldSpec._
import com.holdenkarau.spark.testing.DatasetSuiteBase
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExtraFieldSpec extends AnyFlatSpec with Matchers with DatasetSuiteBase {

  import spark.implicits._

  import scala.collection.JavaConverters._

  val foos: Seq[TestContainer] = Seq(
    TestContainer(One),
    TestContainer(Two),
    TestContainer(Three)
  )

  private val scenario: ExampleEnum => Assertion = {
    case v@One =>
      v.value shouldBe One.value
      v.getClass shouldBe One.getClass
      v.special() shouldBe One.special()
    case v@Two =>
      v.value shouldBe Two.value
      v.getClass shouldBe Two.getClass
      v.special() shouldBe Two.special()
    case v@Three =>
      v.value shouldBe Three.value
      v.getClass shouldBe Three.getClass
      v.special() shouldBe Three.special()
    case _ =>
      fail("we do not have other cases!")
  }

  it should "propely pattern match & have correct class in raw example" in {
    foos.map(_.v).foreach {
      scenario.apply
    }
  }

  it should "propely pattern match in ds example" in {
    foos.toDS().show(false)

    foos.toDS()
      .collectAsList().asScala
      .map(_.v)
      .foreach {
        scenario.apply
      }
  }

}

private[atais] object ExtraFieldSpec {

  sealed abstract class ExampleEnum(val value: String) extends Product with Serializable {
    def special(): Int
  }

  object ExampleEnum {

    case object One extends ExampleEnum("One") {
      override def special(): Int = 1
    }

    case object Two extends ExampleEnum("Two") {
      override def special(): Int = 2
    }

    case object Three extends ExampleEnum("Three") {
      override def special(): Int = 3
    }

    case class Unknown(override val value: String) extends ExampleEnum(value) {
      override def special(): Int = ???
    }

    def apply(str: String): ExampleEnum = {
      str match {
        case One.value => One
        case Two.value => Two
        case Three.value => Three
        case x => Unknown(x)
      }
    }

    case class TestContainer(_v: String) {
      val v: ExampleEnum = ExampleEnum(_v)
    }

    object TestContainer {
      def apply(v: ExampleEnum): TestContainer =
        TestContainer(v.value)
    }

  }

}
