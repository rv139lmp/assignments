package org.assignment.model

import scala.collection.{Map, mutable}

/**
  * Created by rahul on 9/5/17.
  */




/*
* Node     =>  Corresponds to channel which have set of followers
* Cluster  =>  Set of nodes
* */
class AllocationInfo{
  val clusters: mutable.Set[Cluster] = mutable.Set.empty
  var phoneNumberToClusterMap :Map[PhoneNumber,Cluster] =  clusters.map( cluster => cluster.phoneNumber -> cluster).toMap[PhoneNumber,Cluster]

  def addCluster(cluster: Cluster) = {
    clusters+=cluster
    phoneNumberToClusterMap += cluster.phoneNumber -> cluster
  }

  def clusterForPhoneNumber(phoneNumber: PhoneNumber)=phoneNumberToClusterMap.get(phoneNumber)
}