package org.assignment.model

import java.util.UUID

import org.assignment.Utils

import scala.collection.{Map, mutable}

/**
  * Created by rahul on 9/5/17.
  */


/*
  * Cluster => Group of nodes (channels) having same allocated phoneNumber
  * Note : there will be cluster of channels that is not assigned phoneNumber yet
  * */
case class Cluster(phoneNumber: PhoneNumber){
  val nodes: mutable.Set[Node]= mutable.Set.empty
  var channelIdToNodeMap:Map[UUID,Node] = nodes.map(node => node.channel.id -> node).toMap[UUID,Node]


  def addNode(node: Node) = {
    if((node.channel.phoneNumber.isEmpty && this.phoneNumber != Utils.emptyPhoneNumber) &&
      node.channel.phoneNumber != phoneNumber)
      throw new Exception("node doesn't belonds to this cluster")

    node.followers.map{ u =>
      if(isCollideForUser(u)) throw new Exception("Collision Occured")
    }
    nodes+=node
    channelIdToNodeMap += node.channel.id -> node
  }

  def removeNode(node: Node) ={
    nodes -= node
    channelIdToNodeMap -= node.channel.id
  }

  def isNodeExist(node: Node) = channelIdToNodeMap.get(node.channel.id).nonEmpty
  def nodeForChannelId(channelId : UUID) = channelIdToNodeMap.get(channelId)

  def isCollideForUser(user: User):Boolean = {
    nodes.map{
      node => if(node.followers.contains(user)) return true
    }
    false
  }
  def isCollideForNode(node: Node):Boolean = {
    node.followers.map{ user => if(isCollideForUser(user)) return true }
    false
  }
  def getCollisionNodeForUser(user: User,newlyFollowedNodeByThisUser:Option[Node]) :Option[Node]= {
    var userFollowedNodes=nodes.filter( node => node.followers.contains(user))
    if(userFollowedNodes.size>1 && newlyFollowedNodeByThisUser.nonEmpty){
      userFollowedNodes=userFollowedNodes.filter(node => node != newlyFollowedNodeByThisUser.get)
    }
    Option(userFollowedNodes.head)
  }


}
