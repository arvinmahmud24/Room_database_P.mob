package com.example.roomdatabase_140

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.roomdatabase_140.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: KewarganegaraanDatabase
    private lateinit var dao: DataKewarganegaraanDao
    private lateinit var appExecutor: AppExecutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = KewarganegaraanDatabase.getDatabase(applicationContext)
        dao = database.dataKewarganegaraanDao()
        appExecutor = AppExecutor()

        // Setup Spinner
        val dropdown = binding.status
        val items = arrayOf("Belum Menikah", "Menikah")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = adapter

        observeData()

        binding.btnSimpanData.setOnClickListener {
            simpanData()
        }

        binding.btnResetData.setOnClickListener {
            deleteData()
        }
    }

    private fun deleteData() {
        appExecutor.diskIO.execute {
            dao.deleteAll()

            runOnUiThread {
                Toast.makeText(this, "Semua data berhasil dihapus!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeData() {
        val dataList: LiveData<List<DataKewarganegaraan>> = dao.getAll()

        dataList.observe(this, Observer { listWarga ->
            val daftarTampilan: List<String>

            if  (listWarga.isEmpty()) {
                daftarTampilan = listOf("Belum ada data warga yang tersimpan.")
            } else {
                daftarTampilan = listWarga.mapIndexed { index, warga ->
                    """
                    ${index + 1}. ${warga.nama} (${warga.jenisKelamin}) - ${warga.statusPernikahan}
                    NIK: ${warga.nik}
                    Alamat: RT ${warga.rt}/RW ${warga.rw}, ${warga.desa}, ${warga.kecamatan}, ${warga.kabupaten}""".trimIndent()
                }
            }

            val listViewAdapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_list_item_1,
                daftarTampilan
            )

            binding.Data.adapter = listViewAdapter
        })
    }


    private fun simpanData() {
        val nama = binding.txtNama.text.toString()
        val nik = binding.txtNik.text.toString()
        val kabupaten = binding.txtKabupaten.text.toString()
        val kecamatan = binding.txtKecamatan.text.toString()
        val desa = binding.txtDesa.text.toString()
        val rt = binding.txtRT.text.toString()
        val rw = binding.txtRW.text.toString()

        var jenisKelamin = ""
        if (binding.radioJantan.isChecked) {
            jenisKelamin = "Laki-laki"
        } else if (binding.radioBetina.isChecked) {
            jenisKelamin = "Perempuan"
        }

        val status = binding.status.selectedItem.toString()

        if (nama.isEmpty() || nik.isEmpty() || kabupaten.isEmpty() || kecamatan.isEmpty() || desa.isEmpty() || rt.isEmpty() || rw.isEmpty() || jenisKelamin.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val newKewarganegaraan = DataKewarganegaraan(
            nama = nama, nik = nik, kabupaten = kabupaten, kecamatan = kecamatan,
            desa = desa, rt = rt, rw = rw, jenisKelamin = jenisKelamin,
            statusPernikahan = status
        )

        appExecutor.diskIO.execute {
            dao.insert(newKewarganegaraan)
            runOnUiThread {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                resetInputFields()
            }
        }
    }

    private fun resetInputFields() {
        binding.txtNama.text.clear()
        binding.txtNik.text.clear()
        binding.txtKabupaten.text.clear()
        binding.txtKecamatan.text.clear()
        binding.txtDesa.text.clear()
        binding.txtRT.text.clear()
        binding.txtRW.text.clear()
        binding.radioGroupGender.clearCheck()
    }
}
