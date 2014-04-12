/*
 * EtherNet/IP
 * Copyright (C) 2014 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.digitalpetri.ethernetip.cip

object CipServiceCodes {

  val GetAttributesAll = 0x01
  val SetAttributesAll = 0x02
  val GetAttributeList = 0x03
  val SetAttributeList = 0x04
  val Reset = 0x05
  val Start = 0x06
  val Stop = 0x07
  val Create = 0x08
  val Delete = 0x09
  val MultipleServicePacket = 0x0A
  val ApplyAttributes = 0x0D
  val GetAttributeSingle = 0x0E
  val SetAttributeSingle = 0x10
  val FindNextObjectInstance = 0x011
  val Restore = 0x15
  val Save = 0x16
  val Nop = 0x17
  val GetMember = 0x18
  val SetMember = 0x19
  val InsertMember = 0x1A
  val RemoveMember = 0x1B
  val GroupSync = 0x1C

}
