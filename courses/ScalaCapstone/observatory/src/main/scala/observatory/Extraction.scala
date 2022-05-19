package observatory
import java.time.LocalDate
import org.apache.log4j.{Level, Logger}                                  // TODO
import org.apache.spark.rdd.RDD                                          // TODO
import org.apache.spark.{SparkConf, SparkContext}                        // TODO
import scala.io.Source                                                   // TODO

/**
  * 1st milestone: data extraction
  */
object Extraction extends ExtractionInterface:
  Logger                                                                 // TODO
    .getLogger("org.apache.spark")
    .setLevel(Level.WARN)

  val conf: SparkConf =                                                  // TODO
    new SparkConf()
      .setMaster("local")
      .setAppName("Observatory")

  val sc: SparkContext = new SparkContext(conf)                          // TODO

  /**
    * Load the resource from filesystem as RDD[String]
    * @param resource the resource path
    * @return         the resource content as RDD[String]
    */
  def getRDDFromResource(resource: String): RDD[String] =
    val fileStream =
      Source
        .getClass
        .getResourceAsStream(resource)

    sc.makeRDD(
      Source
        .fromInputStream(fileStream)
        .getLines
        .toSeq)

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use 
    *                         (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use 
    *                         (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year,                                     // TODO
                 stationsFile: String,
             temperaturesFile: String)
                             : Iterable[(LocalDate, Location, Temperature)] =

    /* We need to create Pair RDDs to join them on their (STN, WBAN) pairs */
    val statRDD: RDD[((String, String), Location)] =
      //sc.textFile(stationsFile)                     // to run on local machine
      getRDDFromResource(stationsFile)                 // this is for the grader
        .map(line => line.split(",", -1))       // prevent dropping trailing ""s
        .filter {
          case Array(_, _, "", _) => false           // filter out stations with
          case Array(_, _, _, "") => false            // missing GPS coordinates
          case _                  => true }
        .map(a => (
          (a(0), a(1)), Location(a(2).toDouble, a(3).toDouble)
        ))

    val tempRDD: RDD[((String, String), (LocalDate, Temperature))] =
      //sc.textFile(temperaturesFile)                 // to run on local machine
      getRDDFromResource(temperaturesFile)             // this is for the grader
        .map(line => line.split(",", -1))       // prevent dropping trailing ""s
        .map(a => (
          (a(0), a(1)),
          (LocalDate.of(year, a(2).toInt, a(3).toInt), a(4).toDouble)
        ))

    statRDD
      .join(tempRDD)
      .mapValues { case (loc, (date, temp)) =>
        (date, loc, (temp - 32.0) / 1.8) }                   // LAZY up to here!
      .values
      .collect

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature 
    *         over the year.
    */
  def locationYearlyAverageRecords(
    records: Iterable[(LocalDate, Location, Temperature)])
           : Iterable[(Location, Temperature)] =                         // TODO
    records
      .groupBy(_._2)                                        // group by location
      .view
      .mapValues(iter => iter.map(_._3))            // get ONLY the temperatures
      .mapValues(iter => iter.sum / iter.size)   // sum and average temperatures

