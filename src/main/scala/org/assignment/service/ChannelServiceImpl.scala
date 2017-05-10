package org.assignment.service

/**
  * Created by rahul on 8/5/17.
  */

import org.assignment.data.DataStore._
import org.assignment.model._

class ChannelServiceImpl extends ChannelService{

  val followingHandler = new FollowingHandler
  val allocationService = new AllocationService

  def follow(channel: Channel, user: User) = {
    val following = Following(channel.id,user.id)
    Followings += following
    followingHandler.handleNewFollowing(following)
  }

  override def unfollow(channel: Channel, user: User): Unit = ???

  override def broadcastMsg(channel: Channel, msg: String): Unit = {
    if(channel.phoneNumber.isEmpty){
      try{
        allocationService.updateAndGetCluster(Node(channel),allocationInfo)
      }catch {
        case ex:NoFollowerForChannelException => println(s"No followers exists for channel id ${channel.id}")
      }
    }
    println(s"msg = $msg broadcasted for channel = ${channel.name}")
  }
}
