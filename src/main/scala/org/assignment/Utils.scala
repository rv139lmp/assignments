package org.assignment

import java.util.UUID

import org.assignment.data.DataStore
import DataStore._
import org.assignment.model.{PhoneNumber, User, Channel}

/**
  * Created by rahul on 9/5/17.
  */
object Utils {

  def userFromUserId(id:UUID) : User = Users.find( x => x.id == id).get

  def channelFromId(id:UUID) : Channel = Channels.find( x => x.id==id).get

  def channelIdToPhoneNumber(channelId : UUID): Option[PhoneNumber] = {
    val ch = Channels.find( x => x.id == channelId )
    if(ch.isDefined) ch.get.phoneNumber else None
  }

  def emptyPhoneNumber=PhoneNumber("NOT_ASSIGNED")
}
