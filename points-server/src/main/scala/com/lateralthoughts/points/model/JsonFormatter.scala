package com.lateralthoughts.points.model

import com.lateralthoughts.points.utils.CustomJsonSerializers
import org.json4s.ext.JavaTypesSerializers
import org.json4s.{DefaultFormats, Formats}

/**
  * Trait containing the Json format used to serialize/deserialize json
  */
trait JsonFormatter {

  protected implicit val jsonFormats: Formats = DefaultFormats ++ JavaTypesSerializers.all ++ CustomJsonSerializers.all

}
