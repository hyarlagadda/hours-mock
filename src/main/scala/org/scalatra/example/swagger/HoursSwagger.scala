package org.scalatra.example.swagger

import org.scalatra.ScalatraServlet
import org.scalatra.swagger.{ApiInfo, NativeSwaggerBase, Swagger}


class ResourcesApp(implicit val swagger: Swagger) extends ScalatraServlet with NativeSwaggerBase

object HoursSwagger{
  val Info = ApiInfo(
    "The Hours API",
    "Docs for the Hours API",
    "http://scalatra.org",
    "apiteam@scalatra.org",
    "MIT",
    "http://opensource.org/licenses/MIT")
}
class HoursSwagger extends Swagger(Swagger.SpecVersion, "1", HoursSwagger.Info)
