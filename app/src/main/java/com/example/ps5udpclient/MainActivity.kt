import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var udpClient: UDPClient
    lateinit var ps5Handler: PS5ControllerHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        udpClient = UDPClient("192.168.1.4", 5005)
        ps5Handler = PS5ControllerHandler(this) { bitmask, axes ->
            val packet = ByteArray(10)
            packet[0] = (bitmask shr 24).toByte()
            packet[1] = (bitmask shr 16).toByte()
            packet[2] = (bitmask shr 8).toByte()
            packet[3] = (bitmask and 0xFF).toByte()
            for(i in axes.indices) packet[4+i] = axes[i]
            udpClient.send(packet)
        }

        ps5Handler.startListening()
    }
}
