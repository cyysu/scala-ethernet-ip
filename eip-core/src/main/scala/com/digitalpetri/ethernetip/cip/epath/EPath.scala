package com.digitalpetri.ethernetip.cip.epath

import com.digitalpetri.ethernetip.util.Buffers
import io.netty.buffer.ByteBuf

sealed abstract class EPath {
  def segments: Seq[EPathSegment]

  override def toString: String = {
    s"${getClass.getSimpleName}(${segments.mkString(",")})"
  }
}

abstract class EPathSegment

case class PackedEPath(segments: EPathSegment*) extends EPath
case class PaddedEPath(segments: EPathSegment*) extends EPath

object PackedEPath {
  def encode(path: PackedEPath, buffer: ByteBuf) {
    // TODO
  }

  def decode(buffer: ByteBuf): PackedEPath = {
    ??? // TODO
  }
}

object PaddedEPath {
  def encode(path: PaddedEPath, buffer: ByteBuf = Buffers.unpooled()): ByteBuf = {
    // length placeholder...
    val lengthStartIndex = buffer.writerIndex()
    buffer.writeByte(0)

    // encode the path segments...
    val dataStartIndex = buffer.writerIndex()

    path.segments.foreach {
      case s: LogicalSegment[_] => LogicalSegment.encode(s, padded = true, buffer)
      case s: PortSegment       => PortSegment.encode(s, buffer)
    }

    // go back and update the length
    val bytesWritten = buffer.writerIndex() - dataStartIndex
    val wordsWritten = bytesWritten / 2
    buffer.markWriterIndex()
    buffer.writerIndex(lengthStartIndex)
    buffer.writeByte(wordsWritten.asInstanceOf[Short])
    buffer.resetWriterIndex()
  }

  def decode(buffer: ByteBuf): PaddedEPath = {
    ??? // TODO
  }
}
