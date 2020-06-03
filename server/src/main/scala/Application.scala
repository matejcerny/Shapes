import cats.data.{NonEmptyList, OptionT, ReaderT}
import cats.effect.{Blocker, ContextShift, Effect}
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging
import org.http4s.CacheDirective.`no-cache`
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.{`Cache-Control`, `Content-Type`}
import org.http4s.syntax.kleisli._
import org.http4s.{Charset, HttpRoutes, MediaType, Request, Response, StaticFile}

import scala.concurrent.ExecutionContext.global

object Application extends LazyLogging {

  private val supportedStaticExtensions = List(".html", ".js", ".map", ".css", ".png", ".ico")
  private val blocker = Blocker.liftExecutionContext(global)

  def service[F[_]](implicit F: Effect[F], cs: ContextShift[F]): ReaderT[F, Request[F], Response[F]] = {

    object dsl extends Http4sDsl[F]
    import dsl._

    HttpRoutes
      .of[F] {
        case GET -> Root => Ok(Page.index).map(withHeaders)

        case req if supportedStaticExtensions.exists(req.pathInfo.endsWith) =>
          staticRequest(req)
            .fold(NotFound())(_.pure[F])
            .flatten
      }
      .orNotFound
  }

  private def staticRequest[F[_]](req: Request[F])(implicit F: Effect[F], cs: ContextShift[F]): OptionT[F, Response[F]#Self] =
    StaticFile
    .fromResource[F](req.pathInfo, blocker, Some(req))
    .orElse(
      OptionT
        .liftF(F.delay(getClass.getResource(req.pathInfo)))
        .flatMap(StaticFile.fromURL[F](_, blocker, Some(req)))
    )
    .map(_.putHeaders(`Cache-Control`(NonEmptyList.of(`no-cache`()))))

  private def withHeaders[F[_]](response: Response[F]): Response[F] = {
    response
      .withContentType(`Content-Type`(MediaType.text.html, Charset.`UTF-8`))
      .putHeaders(`Cache-Control`(NonEmptyList.of(`no-cache`())))
  }
}
