package org.assignment.model

import java.util.UUID

/**
  * Created by rahul on 7/5/17.
  */


case class Channel(id: UUID, name: String, var phoneNumber: Option[PhoneNumber]){
  def updatePhoneNumber(newPhoneNUmber:PhoneNumber) = phoneNumber=Option(newPhoneNUmber)
}

case class User(id: UUID, name: String)

case class Following(channelId: UUID, userId: UUID)

case class PhoneNumber(number: String)
