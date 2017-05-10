package org.assignment.service

import org.assignment.model._
import org.assignment.Utils

/**
  * Created by rahul on 9/5/17.
  */
class CollisionHandlingService {


  def isCollide(cluster:Cluster, following: Following): Boolean ={
    val user = Utils.userFromUserId(following.userId)
    cluster.isCollideForUser(user)
  }




  def handleCollision(cluster:Cluster, following: Following, allocationInfo:AllocationInfo): Cluster ={
    val user = Utils.userFromUserId(following.userId)
    val newlyFollowedNode = cluster.channelIdToNodeMap.get(following.channelId)
    val existingNode= cluster.getCollisionNodeForUser(user,newlyFollowedNode)

    def nodeToMoveFromCluster = {
      if(newlyFollowedNode.isEmpty) {
        val newNode=Node(Utils.channelFromId(following.channelId))
        newNode.addFollower(user)
        newNode
      }
      else if(newlyFollowedNode.get.followers.size < existingNode.get.followers.size) newlyFollowedNode.get
      else existingNode.get
    }


    val allocationService=new AllocationService
    allocationService.updateAndGetCluster(nodeToMoveFromCluster,allocationInfo)
  }





  def collisionDetectionAndHandling(cluster:Cluster, following: Following,allocationInfo:AllocationInfo) : Option[Cluster] = {
    if(isCollide(cluster,following))
      Option(handleCollision(cluster,following,allocationInfo))
    else None
  }

}
