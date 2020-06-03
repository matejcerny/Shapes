import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Properties

object Server extends IOApp {

  private val defaultPort = 8080
  private val host = "localhost"

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(args)
      port <- IO(Properties.envOrNone("HTTP_PORT").map(_.toInt).getOrElse(defaultPort))
      exitCode <- BlazeServerBuilder[IO](global)
        .bindHttp(port, host)
        .withHttpApp(Application.service)
        .serve
        .compile
        .drain
        .as(ExitCode.Success)
    } yield exitCode

}
