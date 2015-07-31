package org.scalatra.example.swagger

import org.scalatra._

// Swagger-specific Scalatra imports
import org.scalatra.swagger._

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra
import org.scalatra.json._

import org.joda.time._

import org.scalatra.example.swagger.utils._

class HoursController(implicit val swagger: Swagger) extends ScalatraServlet with NativeJsonSupport with SwaggerSupport  {
//NativeJsonSupport
  // Sets up automatic case class to JSON output serialization, required by
  // the JValueResult trait.
  protected implicit val jsonFormats: Formats = DefaultFormats

  // A description of our application. This will show up in the Swagger docs.
  protected val applicationDescription = "The Hours API. It exposes operations for browsing and searching lists of TimeEntry, Timers, Projects, Clients etc"

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  post("/project") {
    
     //request.body.extract[ProjectBig]
  }

  val getProjects =
    (apiOperation[List[Project]]("getProjects")
      summary "Show all projects"
      notes "Shows all the projects. You can search it too.")
      //parameter queryParam[Option[String]]("name").description("A name to search for"))

   get("/project", operation(getProjects)) {
      
     HoursData.projectList
   }
   
   /** Timers 
    *  
    */

   val getTimers = 
      (apiOperation[List[Timer]]("getTimers")
      summary "Show all Timers"
      notes "Shows all the Timers. You can search it too.")
      //parameter queryParam[Option[String]]("name").description("A name to search for"))

     get("/timer", operation(getTimers)) {
  
     HoursData.timerList
   }
   
   val createTimer = 
      (apiOperation[Timer]("createTimer")
      summary "Create a timer"
      notes "Shows all the Timers. You can search it too."
      parameter queryParam[String]("project").description("Project associated with this timer")
      parameter queryParam[Option[Task]]("tasks").description("Tasks associated with this timer")
      parameter queryParam[Option[List[TimeEntry]]]("timeEntries").description("Time entries associated with this timer") )
   
     post("/timer", operation(createTimer)) {
     
       val myClient =  Client(params("name"))
       val myProject = Project(params("name"), params("color"), Some(myClient))
       val myTimer = Timer( myProject, None, None)
       val newList = HoursData.timerList :+ myTimer
       Created("Created timer")     
     }
   
      val deleteTimer = 
      (apiOperation[Unit]("deleteTimer")
      summary "delete a timer"
      notes "Deletes timers."
      parameter queryParam[String]("id").description("Delete a timer with specified id") )

     delete("/timer/:id", operation(deleteTimer)) {
     
         
     }
      
      /***
       * Client
       */
      
      val getClients = 
      (apiOperation[List[Client]]("getClients")
      summary "Show all Clients"
      notes "Shows all the Clients.")
      
     get("/client", operation(getClients)) {
       
       HoursData.allClients
     }
      
      val createClient = 
      (apiOperation[Unit]("createClient")
      summary "Create a Client"
      notes "Create clients."
      parameter queryParam[String]("name").description("Client name") )
      
     post("/client", operation(createClient)) {
        
        val client = Client(params("name"))
        HoursData.allClients :+ client
        
        Created(123)
      }
      
    
      val getTimeEntry = (apiOperation[List[TimeEntry]]("getTimeEntry")
      summary "Show all TimeEntries"
      notes "Shows all the TimeEntries.")
      
    get("/timeEntry", operation(getTimeEntry)) {
      
       HoursData.allTimeEntries
    }
      
      val createTimeEntry = (apiOperation[TimeEntry]("createTimeEntry")
      summary "Create a TimeEntry"
      notes "Create a TimeEntry.")
      
    post("/timeEntry", operation(createTimeEntry)) {
        
        Created(123)
      }
    
    after() {
      contentType = formats("json")
    }
}

object HoursData {

   var hoursClient = Client("tapity" )
   var ryktrClient  = Client("tapity" )
   val project1 = Project("hours","blue", Some(hoursClient))
   val project2 = Project("Ryktr","red", Some(ryktrClient))
   var projectList = List ( project1, project2)
   val timeEntry1Start = new DateTime(2014, 12, 25, 0, 0, 0, 0)
   val timeEntry1End = new DateTime(2014, 12, 25, 10, 0, 0, 0)
   val duration1 = new Duration(timeEntry1Start, timeEntry1End)
   val duration2 = new Duration(timeEntry1Start, timeEntry1End)
   val client1 = Client("tapity")
   val allClients = List(client1)
   val developmentTask =  Task("development")
   val designTask = Task("design")
   val timeEntry1 = TimeEntry( Some( duration1 ), None, Some("firstEntry"), false )
   val timeEntry2 = TimeEntry( Some( duration2 ), None, Some("secondEntry"), false )
   val allTimeEntries = List (timeEntry1, timeEntry2)
   val timer1 = (project1, designTask, Some(List(timeEntry1)))
   val timer2 = (project1, developmentTask, Some(List(timeEntry2)))
   val timerList = List(timer1, timer2)
}

//Hours classes
case class Client(name: String)

case class Project(name: String, color: String, client: Option[Client])

case class TimeBlock(duration: Duration)

case class TimeEntry(entryDuration: Option[Duration], timeBlockDuration: Option[TimeBlock], notes: Option[String],
      submitted: Boolean)
      
case class Task(taskType: String)

case class Timer(project: Project, tasks: Option[Task], timeEntries: Option[List[TimeEntry]])

case class Login(username: String, password: String)

case class User(firstName: String, lastName: String, email: String)

case class Team(name: String, users: Option[List[User]])




