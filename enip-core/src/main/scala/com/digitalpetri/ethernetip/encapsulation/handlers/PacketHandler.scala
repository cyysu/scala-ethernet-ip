/*
 * Copyright 2014 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.ethernetip.encapsulation.handlers

import java.nio.ByteOrder
import java.util

import com.digitalpetri.ethernetip.encapsulation.EncapsulationPacket
import com.typesafe.scalalogging.StrictLogging
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec

import scala.util.{Failure, Success}

class PacketHandler extends ByteToMessageCodec[EncapsulationPacket] with StrictLogging {

  def encode(ctx: ChannelHandlerContext, msg: EncapsulationPacket, out: ByteBuf): Unit = {
    val buffer = out.order(ByteOrder.LITTLE_ENDIAN)

    EncapsulationPacket.encode(msg, buffer)
  }

  def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit = {
    var startIndex = in.readerIndex()

    while (in.readableBytes() >= PacketHandler.HeaderSize &&
           in.readableBytes() >= PacketHandler.HeaderSize + getLength(in, startIndex)) {

      val buffer = in.order(ByteOrder.LITTLE_ENDIAN)

      EncapsulationPacket.decode(buffer) match {
        case Success(packet) =>
          out.add(packet)

        case Failure(ex) =>
          logger.error("Error decoding packet.", ex)

          // Advance past any bytes we should have read but didn't...
          val endIndex = startIndex + PacketHandler.HeaderSize + getLength(in, startIndex)
          buffer.readerIndex(endIndex)
      }

      startIndex = buffer.readerIndex()
    }
  }

  private def getLength(in: ByteBuf, startIndex: Int): Int = {
    in.order(ByteOrder.LITTLE_ENDIAN).getUnsignedShort(startIndex + PacketHandler.LengthOffset)
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    logger.error(s"Exception caught at encapsulation layer: ${cause.getMessage}", cause)
    ctx.close()
  }

}

object PacketHandler {
  val HeaderSize = 24
  val LengthOffset = 2
}
