package cityBike.station

import argonaut.Argonaut.casecodec5
import argonaut.{CodecJson, Parse}

object StationParser {

  implicit def StationCodecJson: CodecJson[Station] =
    casecodec5(Station.apply, Station.unapply)("number", "name", "address", "latitude", "longitude")

  def parse(json: String): List[Station] = {
    Parse.decodeOption[List[Station]](json).getOrElse(Nil)
  }
}
