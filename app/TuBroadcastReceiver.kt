import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class TuBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // La lógica que deseas ejecutar cuando se activa la alarma
        Toast.makeText(context, "¡La alarma está sonando!", Toast.LENGTH_SHORT).show()
    }
}
