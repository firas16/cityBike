package cityBike

class ClusterMainSuite extends SharedContext{

  "run" should "process pipeline correctly" in {

    val sqlCtx = sqlContext
    //Given
    val confParams = Map(
      "inputPath" -> "src/main/resources/Brisbane_CityBike.json",
      "outputPath" -> "target/test/result")

    //When
    val result = ClusterMain.run(sqlCtx, confParams)
    //Then
    result.get.show()
  }
}
