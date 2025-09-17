import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import kotlin.concurrent.thread

class UDPClient(private val ip: String, private val port: Int) {
    private val socket = DatagramSocket()

    fun send(data: ByteArray) {
        thread {
            try {
                val packet = DatagramPacket(data, data.size, InetAddress.getByName(ip), port)
                socket.send(packet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
