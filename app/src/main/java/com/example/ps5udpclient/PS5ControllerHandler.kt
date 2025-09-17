import android.app.Activity
import android.hardware.usb.*
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast

class PS5ControllerHandler(
    private val activity: Activity,
    private val udpCallback: (bitmask: Int, axes: ByteArray) -> Unit
) {
    private val buttonsMap = mutableMapOf<String, Button>()

    fun startListening() {
        val grid = activity.findViewById<GridLayout>(R.id.ps5_grid)
        for (i in 0 until grid.childCount) {
            val btn = grid.getChildAt(i) as Button
            buttonsMap[btn.text.toString().lowercase()] = btn
        }

        val manager = activity.getSystemService(Activity.USB_SERVICE) as UsbManager
        val deviceList = manager.deviceList
        val controller = deviceList.values.firstOrNull { it.vendorId == 1356 } // Sony VID
        if(controller == null){
            Toast.makeText(activity, "Controller chưa kết nối OTG", Toast.LENGTH_LONG).show()
            return
        } else {
            Toast.makeText(activity, "Controller OTG đã kết nối", Toast.LENGTH_SHORT).show()
        }

        // TODO: đọc dữ liệu USB HID Controller
        // ví dụ: bitmask = 0b00001111, axes = ByteArray(6)
        // udpCallback(bitmask, axes)
        // cập nhật UI: gọi updateUIButton(name, pressed)
    }

    fun updateUIButton(name: String, pressed: Boolean){
        val btn = buttonsMap[name.lowercase()] ?: return
        activity.runOnUiThread {
            btn.setBackgroundColor(
                if(pressed) activity.getColor(R.color.button_active)
                else activity.getColor(R.color.button_default)
            )
        }
    }
}
