package org.assignment.model

import java.util.UUID

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
  val userNodeMap :mutable.Map[User,Node] = mutable.Map.empty
  var channelIdToNodeMap:Map[UUID,Node] = nodes.map(node => node.channel.id -> node).toMap[UUID,Node]


  def addNode(node: Node) = {
    if(node.channel.phoneNumber != phoneNumber)
      throw new Exception("node doesn't belonds to this cluster")
    node.followers.map{ u =>
      if(isCollideForUser(u)) throw new Exception("Collision Occured")
    }
    nodes+=node
    channelIdToNodeMap += node.channel.id -> node
    userNodeMap ++= node.followers.map(user => user -> node )
  }

  def removeNode(node: Node) ={
    nodes -= node
    channelIdToNodeMap -= node.channel.id
    userNodeMap --= node.followers
  }

  def isNodeExist(node: Node) = channelIdToNodeMap.get(node.channel.id).nonEmpty
  def nodeForChannelId(channelId : UUID) = channelIdToNodeMap.get(channelId)

  def isCollideForUser(user: User) = userNodeMap.contains(user)
  def isCollideForNode(node: Node):Boolean = {
    node.followers.map{ user => if(userNodeMap.contains(user)) return true }
    false
  }
  def getCollisionNodeForUser(user: User) = userNodeMap.get(user)


}