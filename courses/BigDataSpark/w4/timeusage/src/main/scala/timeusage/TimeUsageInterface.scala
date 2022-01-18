package timeusage

import org.apache.spark.sql._
import org.apache.spark.sql.types._

/**
 * The interface used by the grading infrastructure. Do not change signatures
 * or your submission will fail with a NoSuchMethodError.
 */
trait TimeUsageInterface {
  val spark: SparkSession
  def classifiedColumns(columnNames: List[String]): (List[Column], List[Column], List[Column])
  def row(line: List[String]): Row
  def timeUsageGrouped(summed: DataFrame): DataFrame
  def timeUsageGroupedSql(summed: DataFrame): DataFrame
  def timeUsageGroupedSqlQuery(viewName: String): String
  def timeUsageGroupedTyped(summed: Dataset[TimeUsageRow]): Dataset[TimeUsageRow]
  def timeUsageSummary(primaryNeedsColumns: List[Column], workColumns: List[Column], otherColumns: List[Column], df: DataFrame): DataFrame
  def timeUsageSummaryTyped(timeUsageSummaryDf: DataFrame): Dataset[TimeUsageRow]
}
