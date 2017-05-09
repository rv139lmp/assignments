package org.assignment

/**
  * Created by rahul on 8/5/17.
  */

import java.util.UUID

import scala.collection._


/*
* Node => channel with all followers
* */
case class Node (channel:Channel){
  val users:mutable.Set[User]= mutable.Set.empty

  def addUser(user: User) = users += user
}


/*
* Cluster => Group of nodes (channels) that have same allocated phoneNumber
* */
case class Cluster(phoneNumber: PhoneNumber){
  val nodes: mutable.Set[Node]= mutable.Set.empty
  val userNodeMap :mutable.Map[User,Node] = mutable.Map.empty
  var channelIdToNodeMap:Map[UUID,Node] = nodes.map(node => node.channel.id -> node).toMap[UUID,Node]


  def addNode(node: Node) = {
    if(node.channel.phoneNumber != phoneNumber)
      throw new Exception("node doesn't belonds to this cluster")
    node.users.map{u =>
      if(isCollideForUser(u)) throw new Exception("Collision Occured")
    }
    nodes+=node
    channelIdToNodeMap += node.channel.id -> node
    userNodeMap ++= node.users.map( user => user -> node )
  }

  def removeNode(node: Node) ={
    nodes -= node
    channelIdToNodeMap -= node.channel.id
    userNodeMap --= node.users
  }

  def isCollideForUser(user: User) = userNodeMap.contains(user)
  def isCollideForNode(node: Node):Boolean = {
    node.users.map{ user => if(userNodeMap.contains(user)) return true }
    false
  }
  def getCollisionNodeForUser(user: User) = userNodeMap.get(user)
}



class PhoneNumberAllocationInfo{
  val clusters: mutable.Set[Cluster] = mutable.Set.empty
  var phoneNumberToClusterMap :Map[PhoneNumber,Cluster] =  clusters.map( cluster => cluster.phoneNumber -> cluster).toMap[PhoneNumber,Cluster]

  def addCluster(cluster: Cluster) = {
    clusters+=cluster
    phoneNumberToClusterMap += cluster.phoneNumber -> cluster
  }
}

