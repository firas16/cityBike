import argonaut.Argonaut._
import argonaut._

import scala.io.Source

case class Station(id: Int, name: String, address: String, latitude: Double, longitude: Double)

object Main {
  def main(args: Array[String]): Unit = {

//    val json =
//      """
//        |[
//        |{"number":122,"name":"122 - LOWER RIVER TCE / ELLIS ST","address":"Lower River Tce / Ellis St","latitude":-27.482279,"longitude":153.028723},
//        |{"number":91,"name":"91 - MAIN ST / DARRAGH ST","address":"Main St / Darragh St","latitude":-27.47059,"longitude":153.036046},
//        |{"number":88,"name":"88 - SYDNEY ST FERRY TERMINAL / PARK","address":"Sydney St Ferry Terminal / Park","latitude":-27.474531,"longitude":153.042728}
//        |]
//      """.stripMargin


    val json = Source.fromFile("src/main/resources/Brisbane_CityBike.json").mkString
    // parse
    //Define encoder
    implicit def StationCodecJson: CodecJson[Station] =
    casecodec5(Station.apply, Station.unapply)("number", "name", "address", "latitude", "longitude")

    val stations = Parse.decodeOption[List[Station]](json).getOrElse(Nil)
    println(stations)
  }
}