package com.github.atais

import com.github.atais.TypeSpec._
import com.holdenkarau.spark.testing.DatasetSuiteBase
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TypeSpec extends AnyFlatSpec with Matchers with DatasetSuiteBase {

  import spark.implicits._

  import scala.collection.JavaConverters._

  val foos: Seq[TestContainer] = Seq(
    TestContainer(One),
    TestContainer(Two),
    TestContainer(Three)
  )

  private val scenario: EnumLike => Assertion = {
    case v@One =>
      v shouldBe One
      v.getClass shouldBe One.getClass
      special(v) shouldBe 1
    case v@Two =>
      v shouldBe Two
      v.getClass shouldBe Two.getClass
      special(v) shouldBe 2
    case v@Three =>
      v shouldBe Three
      v.getClass shouldBe Three.getClass
      special(v) shouldBe 3
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

private[atais] object TypeSpec {

  type EnumLike = String

  final val One: EnumLike = "One"
  final val Two: EnumLike = "Two"
  final val Three: EnumLike = "Three"

  def special(v: EnumLike): Int = v match {
    case One => 1
    case Two => 2
    case Three => 3
    case _ => ???
  }

  case class TestContainer(v: EnumLike)

}
