package station

class StationParserSuite extends SharedContext {

  "parseJson" should "get the right station.Station entities from string" in {
    //Given
    val json =
      """
        |[
        |{"number":122,"name":"122 - LOWER RIVER TCE / ELLIS ST","address":"Lower River Tce / Ellis St","latitude":-27.482279,"longitude":153.028723},
        |{"number":91,"name":"91 - MAIN ST / DARRAGH ST","address":"Main St / Darragh St","latitude":-27.47059,"longitude":153.036046},
        |{"number":88,"name":"88 - SYDNEY ST FERRY TERMINAL / PARK","address":"Sydney St Ferry Terminal / Park","latitude":-27.474531,"longitude":153.042728}
        |]
      """.stripMargin

    val expected = List(
      Station(122, "122 - LOWER RIVER TCE / ELLIS ST", "Lower River Tce / Ellis St", -27.482279, 153.028723),
      Station(91, "91 - MAIN ST / DARRAGH ST", "Main St / Darragh St", -27.47059, 153.036046),
      Station(88, "88 - SYDNEY ST FERRY TERMINAL / PARK", "Sydney St Ferry Terminal / Park", -27.474531, 153.042728)
    )

    //When
    val result = StationParser.parse(json)
    //Then
    assert(expected == result)
  }
}
