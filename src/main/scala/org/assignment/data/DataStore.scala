package org.assignment.data

import org.assignment.model._

/**
  * Created by rahul on 7/5/17.
  */
object DataStore {
  import scala.collection.mutable.{MutableList => MList}

  val Channels: MList[Channel] = _

  val Users: MList[User] = _

  val Followings: MList[Following] = _

  var PhoneNumbers: MList[PhoneNumber] = _

  val allocationInfo : AllocationInfo = _

}
