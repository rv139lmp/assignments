package org.assignment

import java.util.UUID

/**
  * Created by rahul on 7/5/17.
  */


case class Channel(id: UUID, name: String, phoneNumber: Option[PhoneNumber])

case class User(id: UUID, name: String)

case class Following(channelId: UUID, userId: UUID)

case class PhoneNumber(number: String)
