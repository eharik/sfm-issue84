import java.io._
import java.util.Date
import org.sfm.csv.CsvParser

object TestIssue84 {

  class TestClass(var firstField: String, var secondField: String)
  class TestDateClass(var firstField: String, var secondField: Date) 

  def main(args: Array[String]) {
    // with strings
    println("Test with values")
    test("first_field,second_field\nfoo,bar")

    println("Test with nulls")
    test("first_field,second_field\n,,")

    println("Test from file")
    val testFile = new File("testFile.csv")
    val bw = new BufferedWriter(new FileWriter(testFile))
    bw.write("first_field,second_field\n,,")
    bw.flush
    bw.close
    test(testFile)

    // with date
    println("Test date with values")
    testDate("first_field,second_field\nfoo,1999-01-01 00:00:00")

    println("Test date with nulls")
    testDate("first_field,second_field\n,,")

    println("Test date from file")
    testDate(testFile)
  }

  def test(str: String) = {
    val it = CsvParser
      .mapTo(classOf[TestClass])
      .iterate(new StringReader(str))

    while (it.hasNext) { println(it.next.firstField) }
  }

  def test(file: File) = {
    val reader = new FileReader(file)
    val it = CsvParser
      .mapTo(classOf[TestClass])
      .iterate(reader)

    while (it.hasNext) { println(it.next.firstField) }
  }

  def testDate(str: String) = {
    val it = CsvParser
      .mapTo(classOf[TestDateClass])
      .iterate(new StringReader(str))

    while (it.hasNext) { println(it.next.firstField) }
  }

  def testDate(file: File) = {
    val reader = new FileReader(file)
    val it = CsvParser
      .mapTo(classOf[TestDateClass])
      .iterate(reader)

    while (it.hasNext) { println(it.next.firstField) }
  }
} 
