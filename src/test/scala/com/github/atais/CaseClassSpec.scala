package com.github.atais

import com.github.atais.CaseClassSpec._
import com.github.atais.CaseClassSpec.EnumLike._
import com.holdenkarau.spark.testing.DatasetSuiteBase
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CaseClassSpec extends AnyFlatSpec with Matchers with DatasetSuiteBase {

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

object CaseClassSpec {

  case class EnumLike(value: String)

  object EnumLike {
    final val One = EnumLike("One")
    final val Two = EnumLike("Two")
    final val Three = EnumLike("Three")
  }

  def special(v: EnumLike): Int = v match {
    case One   => 1
    case Two   => 2
    case Three => 3
    case _     => ???
  }

  case class TestContainer(v: EnumLike)

}
