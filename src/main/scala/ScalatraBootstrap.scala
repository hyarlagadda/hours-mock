import org.scalatra.example.swagger._
import org.scalatra.LifeCycle
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new HoursSwagger

  override def init(context: ServletContext) {
    context.mount(new HoursController, "/hours-api", "hours-api")
    context.mount (new ResourcesApp, "/api-docs")
  }
}
