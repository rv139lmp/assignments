package org.assignment.service

import org.assignment.model._
import org.assignment.data.DataStore._
import org.assignment.Utils

/**
  * Created by rahul on 9/5/17.
  */
class FollowingHandler {

  val collisionHandlingService : CollisionHandlingService = new CollisionHandlingService

  def handleNewFollowing(following: Following) :Unit = {
    val phoneNumber = Utils.channelIdToPhoneNumber(following.channelId).getOrElse(Utils.emptyPhoneNumber)
    var cluster=allocationInfo.clusterForPhoneNumber(phoneNumber)

    if(cluster.isEmpty){
      cluster = Option(Cluster(phoneNumber))
      allocationInfo.addCluster(cluster.get)
    }
    var node = cluster.get.nodeForChannelId(following.channelId)
    if(node.isEmpty){
      node = Option(Node(Utils.channelFromId(following.channelId)))
      cluster.get.addNode(node.get)
    }

    if(node.get.followers.contains(Utils.userFromUserId(following.userId))){
      println("following is already present")
      return
    }

    node.get.addFollower(Utils.userFromUserId(following.userId))

    if(phoneNumber != Utils.emptyPhoneNumber){
      val newCluster = collisionHandlingService.collisionDetectionAndHandling(cluster.get,following,allocationInfo)
      if(newCluster.isDefined) cluster = newCluster
    }
  }
}
