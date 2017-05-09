package org.assignment.model

import scala.collection.mutable

/**
  * Created by rahul on 9/5/17.
  */


/*
 * Node => represent channel with all followers
 *
 * */
case class Node (channel:Channel){
  val followers:mutable.Set[User]= mutable.Set.empty

  def addFollower(user: User) = followers += user
}
