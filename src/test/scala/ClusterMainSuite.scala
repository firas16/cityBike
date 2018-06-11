import station.{SharedContext}

class ClusterMainSuite extends SharedContext{

  "run" should "process pipeline correctly" in {

    val sqlCtx = sqlContext
    import sqlCtx.implicits._
    //Given
    val confParams = Map(
      "inputPath" -> "src/main/resources/Brisbane_CityBike.json",
      "outputPath" -> "target/result")

    //When
    val result = ClusterMain.run(sqlCtx, confParams)
    //Then
    result.get.show()
  }
}
