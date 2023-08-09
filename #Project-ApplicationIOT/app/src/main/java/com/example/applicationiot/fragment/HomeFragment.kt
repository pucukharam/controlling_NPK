package com.example.applicationiot.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.applicationiot.R
import com.example.applicationiot.activity.NotificationActivity
import com.example.applicationiot.databinding.FragmentHomeBinding
import com.example.applicationiot.model.DashboardModel
import com.example.applicationiot.model.PredictionModel
import com.example.applicationiot.util.BaseAppCompatActivity
import com.example.applicationiot.util.SessionManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private var mDatabase: DatabaseReference? = null
    private val dataN = ArrayList<Double>()
    private val dataK = ArrayList<Double>()
    private val dataP = ArrayList<Double>()
    private lateinit var binding: FragmentHomeBinding
    private val colors = intArrayOf(
        ColorTemplate.VORDIPLOM_COLORS[0],
        ColorTemplate.VORDIPLOM_COLORS[1],
        ColorTemplate.VORDIPLOM_COLORS[2]
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        val TAG = "home"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivNotification.setOnClickListener {
            (requireActivity() as BaseAppCompatActivity).goToPage(NotificationActivity::class.java)
        }
        binding.tvName.text = SessionManager.getName(requireContext())
        binding.chart.setViewPortOffsets(0f, 0f, 0f, 0f)
        binding.chart.description.isEnabled = false
        binding.chart.setTouchEnabled(true)
        binding.chart.isDragEnabled = true
        binding.chart.setScaleEnabled(true)
        binding.chart.setPinchZoom(false)
        binding.chart.setDrawGridBackground(false)
        binding.chart.maxHighlightDistance = 1f

        val x: XAxis = binding.chart.xAxis
        x.labelCount=4
        x.isEnabled = true


        val y: YAxis = binding.chart.axisLeft
        y.setLabelCount(6, false)
        y.textColor = ContextCompat.getColor(requireContext(), R.color.black)
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = ContextCompat.getColor(requireContext(), R.color.black)

        binding.chart.axisRight.isEnabled = false
        binding.chart.legend.isEnabled = false

        binding.chart.animateXY(2000, 2000)

        // don't forget to refresh the drawing

        // don't forget to refresh the drawing
        binding.chart.invalidate()
        getData()
        val l: Legend = binding.chart.getLegend()
        l.isEnabled=true
        setUpSwitch()
    }

    private fun setDataChart() {
        val dataSets = java.util.ArrayList<ILineDataSet>()
        for (z in 0 until 3) {
            val values = ArrayList<Entry>()
            var set1: LineDataSet
            when (z) {
                0 -> {
                    for (i in 0 until dataN.size) {
                        values.add(Entry(i.toFloat(), dataN[i].toFloat()))
                    }
                    set1 = LineDataSet(values, "DataSet N")
                }

                1 -> {
                    for (i in 0 until dataK.size) {
                        values.add(Entry(i.toFloat(), dataK[i].toFloat()))
                    }
                    set1 = LineDataSet(values, "DataSet K")
                }

                else -> {
                    for (i in 0 until dataP.size) {
                        values.add(Entry(i.toFloat(), dataP[i].toFloat()))
                    }
                    set1 = LineDataSet(values, "DataSet P")
                }
            }


            // create a dataset and give it a type
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(true)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            val color = colors[z % colors.size]
            set1.color = color
            set1.setCircleColor(color)
            set1.fillColor = ContextCompat.getColor(requireContext(), R.color.green_200_10opacity)
            set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(false)
            set1.fillFormatter =
                IFillFormatter { _, _ -> binding.chart.axisLeft.axisMinimum }
            dataSets.add(set1)

            val data = LineData(dataSets)
            binding.chart.data = data
        }
    }

    private fun getData() {
        mDatabase = FirebaseDatabase.getInstance().reference
        val dbUsers = mDatabase!!.child("prediksi")
        dbUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataK.clear()
                dataN.clear()
                dataP.clear()
                val predictionModel: PredictionModel? =
                    dataSnapshot.getValue(PredictionModel::class.java)
                predictionModel?.let {
                    dataN.add(it.n)
                    dataK.add(it.k)
                    dataP.add(it.p)
                }
                dataN.add(0.005)
                dataK.add(0.003)
                dataP.add(0.006)

                dataN.add(0.004)
                dataK.add(0.003)
                dataP.add(0.001)
                setDataChart()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                (requireActivity() as BaseAppCompatActivity).showToast(databaseError.message)
            }
        })
    }
    private fun setUpSwitch(){
        mDatabase = FirebaseDatabase.getInstance().reference
        val dbOtomasi = mDatabase!!.child("Otomasi")
        dbOtomasi.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dashboardModel: DashboardModel? =
                    dataSnapshot.getValue(DashboardModel::class.java)
                dashboardModel?.let {
                    binding.switchOtomatis.isChecked = it.Otomasi!="off"
                    if(it.Otomasi!="off"){
                        binding.switchPompaAir.isChecked=false
                        binding.switchPompaPupuk.isChecked=false
                        binding.switchPompaAir.isEnabled=false
                        binding.switchPompaPupuk.isEnabled=false
                    }else{
                        binding.switchPompaAir.isEnabled=true
                        binding.switchPompaPupuk.isEnabled=true
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                (requireActivity() as BaseAppCompatActivity).showToast(databaseError.message)
            }
        })
        val dbPupuk = mDatabase!!.child("PompaPupuk")
        dbPupuk.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dashboardModel: DashboardModel? =
                    dataSnapshot.getValue(DashboardModel::class.java)
                dashboardModel?.let {
                    binding.switchPompaPupuk.isChecked = it.PompaPupuk!="off"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                (requireActivity() as BaseAppCompatActivity).showToast(databaseError.message)
            }
        })
        val dbAir = mDatabase!!.child("PompaAir")
        dbAir.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dashboardModel: DashboardModel? =
                    dataSnapshot.getValue(DashboardModel::class.java)
                dashboardModel?.let {
                    binding.switchPompaAir.isChecked = it.PompaAir!="off"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                (requireActivity() as BaseAppCompatActivity).showToast(databaseError.message)
            }
        })
        binding.switchOtomatis.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                binding.switchPompaAir.isChecked=false
                binding.switchPompaPupuk.isChecked=false
                binding.switchPompaAir.isEnabled=false
                binding.switchPompaPupuk.isEnabled=false
                dbOtomasi.child("Otomasi").setValue("on")
            }else{
                binding.switchPompaAir.isEnabled=true
                binding.switchPompaPupuk.isEnabled=true
                dbOtomasi.child("Otomasi").setValue("off")
            }
        }
        binding.switchPompaAir.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                dbAir.child("PompaAir").setValue("on")
            }else{
                dbAir.child("PompaAir").setValue("off")
            }
        }
        binding.switchPompaPupuk.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                dbPupuk.child("PompaPupuk").setValue("on")
            }else{
                dbPupuk.child("PompaPupuk").setValue("off")
            }
        }
    }

}