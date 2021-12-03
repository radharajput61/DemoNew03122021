package com.app.demo.marker

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.ViewGroup
import com.app.demo.MaimnListActivity
import com.app.demo.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.custom_map_marker.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val rootViewGroup: ViewGroup by lazy { findViewById<ViewGroup>(android.R.id.content) }

    private var customMarkerList: MutableList<MapMarker> = mutableListOf()
    private var markerMap: MutableMap<Marker, MapMarker> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setMarker()
    }

    private fun setMarker() {

        for(item in MaimnListActivity.list){
            var colorCode :String= "" ;
            if(item.lastRunningState.truckRunningState==0 && item.lastWaypoint.ignitionOn ){
                colorCode="1"

            }  else if(item.lastRunningState.truckRunningState==0 &&  !item.lastWaypoint.ignitionOn ){
                colorCode="2"

            } else if(item.lastRunningState.truckRunningState==1){
                colorCode="3"

             }
            else if(item.lastRunningState.truckRunningState==1 && dateDiff(item.lastWaypoint.createTime)>4){
                colorCode="4"

             }
          //  convertDate()



            setTruckMarker(item.lastRunningState.lat,
                item.lastRunningState.lng,
                item.truckNumber,item.name
            ,item.lastRunningState.truckRunningState,colorCode)
        }
    }

    private fun dateDiff(createTime: Long): Int {
        val time = System.currentTimeMillis()
        val diff: Long = time - createTime
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
return  hours.toInt();
    }

    fun convertDate(dateInMilliseconds: String, dateFormat: String?): String? {
        return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
    }
    private fun setTruckMarker(
        lat: Double,
        long: Double,
        truckNumber: String,
        truckName: String,
        currentState: Int,
        colorCode: String
    ) {
        var destinationMarker : MapMarker? =null
         if(colorCode.equals("1")){
             destinationMarker = MapMarker(rootViewGroup, MapMarker.Type.YELLOW("") )
         } else if(colorCode.equals("2")){
             destinationMarker = MapMarker(rootViewGroup, MapMarker.Type.BLUE("") )
         } else if(colorCode.equals("3")){
             destinationMarker = MapMarker(rootViewGroup, MapMarker.Type.GREEN("") )
         }else if(colorCode.equals("4")){
             destinationMarker = MapMarker(rootViewGroup, MapMarker.Type.RED("") )
         }


        val mark = LatLng(lat, long)
        val options = MarkerOptions()
            .position(mark )
            .icon(destinationMarker!!.getGoogleMapsMarker())
        val marker = mMap.addMarker(options)
        marker.tag = colorCode
        markerMap[marker] = destinationMarker

      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(mark))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(  LatLng(lat,long), 8.0f))
        customMarkerList.add(destinationMarker)

    }


    override fun onDestroy() {
        super.onDestroy()
        customMarkerList.forEach { it.remove() }
        markerMap.clear()
        customMarkerList.clear()
    }
}
