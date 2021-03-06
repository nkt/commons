package im.dlg.storage

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.FiniteDuration

trait Connector {
  def run[R](action: api.Action[R]): Future[R]

  def createTableIfNotExists(name: String, createReverseIndex: Boolean): Unit

  final def runSync[R](action: api.Action[R])(implicit timeout: FiniteDuration): R =
    Await.result(run(action), timeout)
}

class SimpleStorage(val name: String) {
  import api._

  final def get(key: String) = GetAction(name, key)

  final def getByPrefix(keyPrefix: String) = GetByPrefixAction(name, keyPrefix)

  final def upsert(key: String, value: Array[Byte]) = UpsertAction(name, key, value)

  final def delete(key: String) = DeleteAction(name, key)

  final def getKeys = GetKeysAction(name)

  final def getKeysForValue(value: Array[Byte]) = GetKeysForValue(name, value)
}

object api {
  sealed trait Action[R] {
    def name: String
  }

  final case class GetAction(name: String, key: String) extends Action[Option[Array[Byte]]]

  final case class GetByPrefixAction(name: String, keyPrefix: String) extends Action[Vector[(String, Array[Byte])]]

  final case class UpsertAction(name: String, key: String, value: Array[Byte]) extends Action[Int]

  final case class DeleteAction(name: String, key: String) extends Action[Int]

  final case class GetKeysAction(name: String) extends Action[Seq[String]]

  final case class GetKeysForValue(name: String, value: Array[Byte]) extends Action[Vector[String]]

}
