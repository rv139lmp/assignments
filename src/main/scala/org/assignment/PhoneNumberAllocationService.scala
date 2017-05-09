package org.assignment

/**
  * Created by rahul on 9/5/17.
  */
class PhoneNumberAllocationService {

  val graph:PhoneNumberAllocationInfo = new PhoneNumberAllocationInfo

  def addFollowingInGraph(following: Following) = {
    val phoneNumber = Utils.channelIdToPhoneNumber(following.channelId).get

    var cluster=graph.phoneNumberToClusterMap.get(phoneNumber)

    if(cluster.isEmpty){
      cluster = Option(Cluster(phoneNumber))
      graph.addCluster(cluster.get)
    }else{
      val newCluster = collisionDetectionAndUpdateCluster(cluster.get,following)
      if(newCluster.isDefined) cluster = newCluster
    }

    var node = cluster.get.channelIdToNodeMap.get(following.channelId)
    if(node.isEmpty){
      val node = Node(Utils.channelFromId(following.channelId))
      cluster.get.addNode(node)
    }

    node.get.addUser(Utils.userFromUserId(following.userId))

  }

  def getSuitableClusterForNode(node: Node, graph: PhoneNumberAllocationInfo) :Cluster = {
    val eligibleClusters=graph.clusters.filter(cluster =>  ! cluster.isCollideForNode(node))
    if(eligibleClusters.isEmpty){
      val newPhoneNumber=PhoneNumbers.head
      PhoneNumbers = PhoneNumbers.tail
      val newCluster=Cluster(newPhoneNumber)
      newCluster
    }
    else getOrderedClusterList(eligibleClusters.toList).head

  }

  //Balance : to minimize chances of future collision
  //TODO: need to consider number of nodes too for scoring
  def getOrderedClusterList(cluster: List[Cluster]) = {
    cluster.sortBy( x => x.userNodeMap.size)
  }


}
