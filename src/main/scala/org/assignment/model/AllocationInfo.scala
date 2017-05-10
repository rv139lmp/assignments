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

  def printInfo={
    println()
    println()
    println()
    println(s"===============================================" )
    println(s"====================Cluster Info===============" )
    println(s"===============================================" )
    println()

    if(clusters.size < 1) println("cluster empty")
    clusters.map(
      c => {
        println(s"====================${c.phoneNumber} Cluster===============" )
        c.nodes.map(
          n => {
            println(s"Node = ${n.channel.name}")
            n.followers.map(
              f => println(s"\t  Follower = ${f.name}")
            )
          }
        )
      }
    )
  }
}