import org.apache.spark.sql.{Dataset, SQLContext}
import org.apache.spark.mllib.clustering.{KMeans}
import org.apache.spark.mllib.linalg.Vectors
import station.{Station, StationParser}

import scala.io.Source


object ClusterMain extends Main{

  override def appName: String = "CityBike"

  def run(sqlContext: SQLContext, argsMap: Map[String, String]): Option[Dataset[_]] = {

    logger.info("reading configuration parameters...")
    val inputPath: String = argsMap("inputPath")
    val outputPath: String = argsMap("outputPath")
    logger.info("input path => " + inputPath)
    logger.info("output path => " + outputPath)

    logger.info("reading data...")
    //read json
    val json = Source.fromFile(inputPath).mkString
    //parse json
    val stations = StationParser.parse(json)

    //Convert to Dataset
    import sqlContext.sparkSession.implicits._
    val stationDS = stations.toDF.as[Station]

    logger.info("Calculate approximate coordinates...")
    //Calculate approximate (x, y) coordinates
    import org.apache.spark.sql.functions.{col, udf}
    val toX :Double => Double= lon => 340 + lon * 340/180
    val toY :Double => Double= lat => 240 - lat * 240/180
    val xUDF = udf(toX)
    val yUDF = udf(toY)
    val stationsWithXY = stationDS
      .withColumn("x", xUDF(col("longitude")))
      .withColumn("y", yUDF(col("latitude")))


    //Clustering
    logger.info("Applying Kmeans...")
    // Cluster the data into 4 classes using KMeans
    val numClusters = 4
    val numIterations = 20
    val data = stationsWithXY.select("x", "y").rdd.map{ case row =>
      Vectors.dense(row.toSeq.toArray.map{
        x => x.asInstanceOf[Double]
      })
    }
    val clusters = KMeans.train(data, numClusters, numIterations)

    // Evaluate clustering by computing Within Set Sum of Squared Errors
    val WSSSE = clusters.computeCost(data)
    println("Within Set Sum of Squared Errors = " + WSSSE)

    //assign nearest center for each station
    val centers: List[(Double, Double)] = clusters.clusterCenters.map(x => (x(0), x(1))).toList

    def nearestCenter(centers:List[(Double, Double)]): (Double, Double) => Int = (x, y) =>
      centers.map(c => (c._1 - x)*(c._1 - x) + (c._2 - y)*(c._2 - y))
          .zipWithIndex
          .sortBy(x => x._1)
          .head._2
    val clusterUDF = udf(nearestCenter(centers))
    val result = stationsWithXY.withColumn("center", clusterUDF(col("x"), col("y")))

    logger.info("writing results to " + outputPath)
    result.coalesce(1).write.option("header", true).option("sep", ";").csv(outputPath)
    Some(stationDS)
  }
}