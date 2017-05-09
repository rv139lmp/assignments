package org.assignment.data

import org.assignment.model._

/**
  * Created by rahul on 7/5/17.
  */
object DataStore {
  import scala.collection.mutable.{MutableList => MList}

  val Channels: MList[Channel] = new MList[Channel]

  val Users: MList[User] = new MList[User]

  val Followings: MList[Following] = new MList[Following]

  var PhoneNumbers: MList[PhoneNumber] = new MList[PhoneNumber]

  val allocationInfo : AllocationInfo = new AllocationInfo

}
