package org.assignment.service

/**
  * Created by rahul on 9/5/17.
  */

import org.assignment.Utils
import org.assignment.data.DataStore._
import org.assignment.model._

//Allocate Cluster (PhoneNumbers) for channel
class AllocationService {


  def updateAndGetCluster(node: Node, allocationInfo: AllocationInfo):Cluster ={
    val phoneNumber=node.channel.phoneNumber.getOrElse(Utils.emptyPhoneNumber)
    val cluster=allocationInfo.clusterForPhoneNumber(phoneNumber)
    var removedNode : Node= null
    if(cluster.nonEmpty && cluster.get.isNodeExist(node)){
        removedNode=cluster.get.nodeForChannelId(node.channel.id).get
        cluster.get.removeNode(node)
    }
    else {
      // because for every channel even with single following
      // we are adding node to the corresponding cluster
      throw NoFollowerForChannelException("Node does not belong to any cluster")
    }

    val newClusterForNode = allocateCluster(removedNode,allocationInfo)
    removedNode.channel.updatePhoneNumber(newClusterForNode.phoneNumber)
    newClusterForNode.addNode(removedNode)
    newClusterForNode
  }

  def allocateCluster(node: Node, allocationInfo: AllocationInfo) :Cluster = {
    val eligibleClusters=allocationInfo.clusters.filter(cluster =>  ! cluster.isCollideForNode(node) && cluster.phoneNumber != Utils.emptyPhoneNumber)
    if(eligibleClusters.isEmpty){
      val newPhoneNumber=PhoneNumbers.head
      PhoneNumbers = PhoneNumbers.tail
      val newCluster=Cluster(newPhoneNumber)
      allocationInfo.addCluster(newCluster)
      newCluster
    }
    else getOrderedClusterList(eligibleClusters.toList).head

  }

  //Balance : to minimize chances of future collision
  //TODO: need to consider number of nodes too for scoring
  def getOrderedClusterList(cluster: List[Cluster]) = {
    cluster.sortBy( x => x.nodes.flatMap(node => node.followers).size)
  }


}
case class NoFollowerForChannelException(msg:String) extends Exception
