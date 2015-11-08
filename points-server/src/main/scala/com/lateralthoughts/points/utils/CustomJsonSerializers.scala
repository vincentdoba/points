package com.lateralthoughts.points.utils

import java.time.OffsetDateTime

import com.lateralthoughts.points.model.ErrorCode
import org.json4s.CustomSerializer
import org.json4s.JsonAST.{JNull, JString}

/**
  * All the custom JSON serializers of application should be put here
  */
object CustomJsonSerializers {
  val all = List(OffsetDateTimeSerializers, ErrorCodeSerializers)
}

/**
  * Serializer for new java 8 OffsetDateTime type
  */
case object OffsetDateTimeSerializers extends CustomSerializer[OffsetDateTime](format => ( {
  case JString(s) => OffsetDateTime.parse(s)
  case JNull => null
}, {
  case x: OffsetDateTime => JString(x.toString)
}
  )
)

case object ErrorCodeSerializers extends CustomSerializer[ErrorCode](format => ( {
  case JString(s) => ErrorCode.parse(s)
  case JNull => null
}, {
  case x: ErrorCode => JString(x.name)
}
  )
)