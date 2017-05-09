package org.assignment

/**
  * Created by rahul on 9/5/17.
  */
trait ChannelService {

  def follow(channel: Channel, user: User)
  def unfollow(channel: Channel, user: User)
  def broadcastMsg(channel:Channel, msg:String)
}
