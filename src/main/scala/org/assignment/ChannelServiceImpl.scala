package org.assignment

/**
  * Created by rahul on 8/5/17.
  */
class ChannelServiceImpl extends ChannelService{


  def follow(channel: Channel, user: User) = {
    val following = Following(channel.id,user.id)
    Followings += following
    addFollowingInGraph(following)
  }





}
