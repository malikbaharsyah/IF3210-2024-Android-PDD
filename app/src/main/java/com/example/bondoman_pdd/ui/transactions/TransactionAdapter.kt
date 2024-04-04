
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bondoman_pdd.R
import com.example.bondoman_pdd.data.model.Transactions

class TransactionAdapter(private val dataSet: List<Transactions>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pembelian_transaction_design, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodMenu.text = dataSet[position].judul
        holder.nominal.text = dataSet[position].nominal.toString()
        holder.location.text = dataSet[position].lokasi
        holder.tipe.text = dataSet[position].kategori
        holder.tanggal.text = dataSet[position].tanggal
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Print isi dari R.id.foodmenu
        val location: TextView = view.findViewById(R.id.maps)
        val foodMenu: TextView = view.findViewById(R.id.foodmenu)
        val nominal: TextView = view.findViewById(R.id.harga)
        val tipe: TextView = view.findViewById(R.id.type)
        val tanggal : TextView = view.findViewById(R.id.tanggal)
    }

}
