package org.assignment

/**
  * Created by rahul on 9/5/17.
  */
class CollisionDetectionAndHandlingService {

  def collisionDetectionAndUpdateCluster(cluster:Cluster, following: Following) : Option[Cluster] = {
    val user = Utils.userFromUserId(following.userId)
    if(cluster.isCollideForUser(user) && cluster.getCollisionNodeForUser(user).get.channel.id != following.channelId){
      val node1 = cluster.getCollisionNodeForUser(user)
      val node2 = cluster.channelIdToNodeMap.get(following.channelId)
      val nodeToMoveFromCluster = if(node2.isEmpty) {
        val newNode=Node(Utils.channelFromId(following.channelId))
        newNode.addUser(user)
        newNode
      }
      else if(node2.get.users.size < node1.get.users.size) node2.get
      else node1.get
      val newClusterForNode = getSuitableClusterForNode(nodeToMoveFromCluster)
      cluster.removeNode(nodeToMoveFromCluster)
      nodeToMoveFromCluster.channel.phoneNumber==newClusterForNode.phoneNumber
      newClusterForNode.addNode(nodeToMoveFromCluster)

      newClusterForNode
    }
    None
  }

}
