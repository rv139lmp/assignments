package org.assignment

import java.util.UUID

import org.assignment.data.DataStore
import org.assignment.model._
import org.assignment.service.ChannelServiceImpl

/**
  * Created by rahul on 10/5/17.
  */
object Main {

  def main(args: Array[String]): Unit = {

    addSampleData
    val channelService = new ChannelServiceImpl
    DataStore.allocationInfo.printInfo

    channelService.follow(DataStore.Channels.get(0).get,DataStore.Users.get(0).get)
    DataStore.allocationInfo.printInfo
    channelService.broadcastMsg(DataStore.Channels.get(0).get,"hellow")
    DataStore.allocationInfo.printInfo

    channelService.follow(DataStore.Channels.get(1).get,DataStore.Users.get(1).get)
    DataStore.allocationInfo.printInfo
    channelService.broadcastMsg(DataStore.Channels.get(1).get,"hellow")
    DataStore.allocationInfo.printInfo

    channelService.follow(DataStore.Channels.get(1).get,DataStore.Users.get(0).get)
    DataStore.allocationInfo.printInfo

    channelService.follow(DataStore.Channels.get(0).get,DataStore.Users.get(1).get)
    DataStore.allocationInfo.printInfo


    channelService.follow(DataStore.Channels.get(2).get,DataStore.Users.get(0).get)
    DataStore.allocationInfo.printInfo

    channelService.broadcastMsg(DataStore.Channels.get(2).get,"hellow")
    DataStore.allocationInfo.printInfo


  }

  def addSampleData={

    DataStore.Channels += Channel(UUID.randomUUID(),"ch1",None)
    DataStore.Channels += Channel(UUID.randomUUID(),"ch2",None)
    DataStore.Channels += Channel(UUID.randomUUID(),"ch3",None)

    DataStore.PhoneNumbers += PhoneNumber("1")
    DataStore.PhoneNumbers += PhoneNumber("2")
    DataStore.PhoneNumbers += PhoneNumber("3")

    DataStore.Users += User(UUID.randomUUID(),"U1")
    DataStore.Users += User(UUID.randomUUID(),"U2")
  }
}
