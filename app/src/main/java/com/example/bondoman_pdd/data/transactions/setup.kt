package com.example.bondoman_pdd.data.transactions

// Gunakan package untuk menggunakan sqlite
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bondoman_pdd.data.model.Transactions

// Buat class setup yang berisi konfigurasi database
class setup {
    // Setup database transaction
    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "transactions.db", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE transactions (id INTEGER PRIMARY KEY AUTOINCREMENT, nim INTEGER, judul TEXT, nominal REAL, kategori TEXT, tanggal TEXT, lokasi TEXT)")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS transactions")
            onCreate(db)
        }

        // Fungsi untuk menambah data transaksi
        fun insertTransaction(transactions: Transactions) {
            val db = this.writableDatabase
            val query = "INSERT INTO transactions (nim, judul, nominal, kategori, tanggal, lokasi) VALUES ('${transactions.nim}', '${transactions.judul}', '${transactions.nominal}', '${transactions.kategori}', '${transactions.tanggal}', '${transactions.lokasi}')"

            db.execSQL(query)

            // Tulis di Logcat bahwa data berhasil ditambahkan
            println("Data berhasil ditambahkan")
        }

        // Fungsi untuk mengambil data-data transaksi berdasarkan nim
        fun getTransactions(nim: Int): ArrayList<Transactions> {
            val db = this.readableDatabase
            val query = "SELECT * FROM transactions WHERE nim = $nim"
            val result = db.rawQuery(query, null)
            val transactions = ArrayList<Transactions>()
            if (result.moveToFirst()) {
                do {
                    transactions.add(Transactions(result.getInt(0), result.getInt(1), result.getString(2), result.getFloat(3), result.getString(4), result.getString(5), result.getString(6)))
                    println("Data berhasil diambil dengan judul ${result.getString(2)}")
                } while (result.moveToNext())

            }
            result.close()
            db.close()
            return transactions
        }

        // Fungsi untuk mengembalikan total pemasukan
        fun getTotalPemasukan(nim: Int): Float {
            val db = this.readableDatabase
            val query = "SELECT SUM(nominal) FROM transactions WHERE nim = $nim AND kategori = 'Penjualan' GROUP BY nim"
            val result = db.rawQuery(query, null)
            var totalPemasukan = 0.0F
            if (result.moveToFirst()) {
                totalPemasukan = result.getFloat(0)
            }
            result.close()
            db.close()
            return totalPemasukan
        }

        fun getTotalPengeluaran(nim: Int): Float {
            val db = this.readableDatabase
            val query = "SELECT SUM(nominal) FROM transactions WHERE nim = $nim AND kategori = 'Pembelian' GROUP BY nim"
            val result = db.rawQuery(query, null)
            var totalPengeluaran = 0.0F
            if (result.moveToFirst()) {
                totalPengeluaran = result.getFloat(0)
            }
            result.close()
            db.close()
            return totalPengeluaran
        }
    }


}