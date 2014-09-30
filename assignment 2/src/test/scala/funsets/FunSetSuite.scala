package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val positiveUnder1001 : Set = (el: Int) => el >= 0 && el < 1001
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersection of s1 + s2 and s2 + s3 is s2") {
    new TestSets {
      val s12 = union(s1, s2)
      val s23 = union(s2, s3)
      val intersection = intersect(s12, s23)
      assert(contains(intersection, 2), "Intersection contains 2")
      assert(!contains(intersection, 1), "Intersection doesn't contain 1")
      assert(!contains(intersection, 3), "Intersection doesn't contain 3")
    }
  }

  test("diff of s1 + s2 and s2 + s3 is s1") {
    new TestSets {
      val s12 = union(s1, s2)
      val s23 = union(s2, s3)
      val difference = diff(s12, s23)
      assert(contains(difference, 1), "Diff contains 1")
      assert(!contains(difference, 2), "Diff doesn't contain 2")
      assert(!contains(difference, 3), "Diff doesn't contain 3")
    }
  }

  test("filter works") {
    new TestSets {
      val s12 = union(s1, s2)
      val filtered = filter(s12, (el) => el != 2)
      assert(contains(filtered, 1), "Filtered contains 1")
      assert(!contains(filtered, 2), "Filtered doesn't contain 2")
    }
  }

  test("forall works") {
    new TestSets {
      val positiveInts = (el: Int) => el >= 0
      assert(forall(positiveUnder1001, positiveInts), "Positive ints under 1001 are positive")
    }
  }

  test("exists works") {
    new TestSets {
      val positiveInts : Set = (el: Int) => el >= 0
      assert(exists(positiveInts, union(s1, s2)), "positive ints contains at least 1 and 2")
      assert(exists(positiveInts, s2), "positive ints contains at least 2")
    }
  }

  test("map works") {
    new TestSets {
      val s100 = singletonSet(100)
      val s99 = map(s100, _ - 1)
      assert(contains(s99, 99), "set with 100 - 1 should contain 99")

      val positiveUnder1001AndEven = map(positiveUnder1001, _ * 2)
      assert(forall(positiveUnder1001AndEven, _ % 2 == 0))
    }
  }

}
