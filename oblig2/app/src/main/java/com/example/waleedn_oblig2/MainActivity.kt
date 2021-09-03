package com.example.waleedn_oblig2

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import dmax.dialog.SpotsDialog
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    private val URL =
            "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v21/obligatoriske-oppgaver/alpakkaland/alpacaparties.json/"
    private val url1 =
            "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v21/obligatoriske-oppgaver/alpakkaland/district1.json"
    private val url2 =
            "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v21/obligatoriske-oppgaver/alpakkaland/district2.json"
    private val url3 =
            "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v21/obligatoriske-oppgaver/alpakkaland/district3.xml"


    lateinit var rc_view: RecyclerView
    lateinit var dialog: AlertDialog
    lateinit var spinner: Spinner
    lateinit var parties: MutableList<AlpacaParty>
    lateinit var selectedParties: MutableList<AlpacaParty>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        spinner=findViewById(R.id.spinner)
        rc_view = findViewById(R.id.rc_view)
        rc_view.setHasFixedSize(true)
        rc_view.layoutManager = LinearLayoutManager(this)
        parties = mutableListOf()
        selectedParties= mutableListOf()
        dialog = SpotsDialog.Builder().setCancelable(false).setContext(this).build()
        dialog.show()
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(spinner.selectedItem.toString().equals("District 1"))
                {
                    dialog.show()
                    getVotes(url1)
                }
                else if(spinner.selectedItem.toString().equals("District 2"))
                {
                    dialog.show()
                    getVotes(url2)
                }
                else if(spinner.selectedItem.toString().equals("District 3"))
                {
                    dialog.show()
                    getVotesxml(url3)
                }
            }

        }

    }



    private fun getVotes(url:String) {

        var vote1 = 0
        var vote2 = 0
        var vote3 = 0
        var vote4 = 0


        try{


            Ion.getDefault(this).conscryptMiddleware.enable(false);
            Ion.with(this)
                    .load(url)
                    .asJsonArray()
                    .setCallback { _, response ->
                        Log.d("Votes1", response.toString())
                        for (id in response) {
                            when (id.asJsonObject.get("id").asInt) {
                                1 -> vote1++
                                2 -> vote2++
                                3 -> vote3++
                                4 -> vote4++
                            }
                        }
                                                Log.d("TotalV1", vote1.toString())
                                                Log.d("TotalV2", vote2.toString())
                                                Log.d("TotalV3", vote3.toString())
                                                Log.d("TotalV4", vote4.toString())

                                                val totalVotes = vote1 + vote2 + vote3 + vote4

                                                Ion.with(this)
                                                        .load(URL)
                                                        .asJsonObject()
                                                        .setCallback { _, response ->
                                                            Log.d("Parties", response.toString())

                                                            parties = Gson().fromJson(
                                                                    response.getAsJsonArray("parties"),
                                                                    Array<AlpacaParty>::class.java
                                                            ).toMutableList()

                                                            parties[0].votes =
                                                                    "$vote1 - " + String.format(
                                                                            "%.1f",
                                                                            ((vote1 * 100.0) / totalVotes)
                                                                    ) + "%"
                                                            parties[1].votes =
                                                                    "$vote2 - " + String.format(
                                                                            "%.1f",
                                                                            ((vote2 * 100.0) / totalVotes)
                                                                    ) + "%"
                                                            parties[2].votes =
                                                                    "$vote3 - " + String.format(
                                                                            "%.1f",
                                                                            ((vote3 * 100.0) / totalVotes)
                                                                    ) + "%"
                                                            parties[3].votes =
                                                                    "$vote4 - " + String.format(
                                                                            "%.1f",
                                                                            ((vote4 * 100.0) / totalVotes)
                                                                    ) + "%"

                                                            val adapter = PartyAdapter(baseContext, parties)
                                                            adapter.notifyDataSetChanged()
                                                            rc_view.adapter = adapter

                                                            dialog.dismiss()
                                                        }
                                            }




        }
        catch (e: Exception)
        {
            Toast.makeText(this,""+e.message,Toast.LENGTH_LONG).show()
        }

    }

    private fun getVotesxml(url:String) {

        var vote1 = 0
        var vote2 = 0
        var vote3 = 0
        var vote4 = 0


        try{
                        Ion.with(this)
                                .load(url)
                                .asString()
                                .setCallback { _, response2 ->
                                    Log.d("Votes3", response2)
                                    val data = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                                            .parse(InputSource(StringReader(response2)))
                                            .getElementsByTagName("party")
                                    for (i in 0 until data.length) {
                                        if (data.item(0).nodeType == Node.ELEMENT_NODE) {
                                            val element = data.item(i) as Element
                                            when (getValue(element, "id").toInt()) {
                                                1 -> vote1 += getValue(element, "votes").toInt()
                                                2 -> vote2 += getValue(element, "votes").toInt()
                                                3 -> vote3 += getValue(element, "votes").toInt()
                                                4 -> vote4 += getValue(element, "votes").toInt()
                                            }
                                        }
                                    }

                                    Log.d("TotalV1", vote1.toString())
                                    Log.d("TotalV2", vote2.toString())
                                    Log.d("TotalV3", vote3.toString())
                                    Log.d("TotalV4", vote4.toString())

                                    val totalVotes = vote1 + vote2 + vote3 + vote4

                                    Ion.with(this)
                                            .load(URL)
                                            .asJsonObject()
                                            .setCallback { _, response ->
                                                Log.d("Parties", response.toString())

                                                parties = Gson().fromJson(
                                                        response.getAsJsonArray("parties"),
                                                        Array<AlpacaParty>::class.java
                                                ).toMutableList()

                                                parties[0].votes =
                                                        "$vote1 - " + String.format(
                                                                "%.1f",
                                                                ((vote1 * 100.0) / totalVotes)
                                                        ) + "%"
                                                parties[1].votes =
                                                        "$vote2 - " + String.format(
                                                                "%.1f",
                                                                ((vote2 * 100.0) / totalVotes)
                                                        ) + "%"
                                                parties[2].votes =
                                                        "$vote3 - " + String.format(
                                                                "%.1f",
                                                                ((vote3 * 100.0) / totalVotes)
                                                        ) + "%"
                                                parties[3].votes =
                                                        "$vote4 - " + String.format(
                                                                "%.1f",
                                                                ((vote4 * 100.0) / totalVotes)
                                                        ) + "%"

                                                val adapter = PartyAdapter(baseContext, parties)
                                                adapter.notifyDataSetChanged()
                                                rc_view.adapter = adapter

                                                dialog.dismiss()
                                            }
                                }




        }
        catch (e: Exception)
        {
            Toast.makeText(this,""+e.message,Toast.LENGTH_LONG).show()
        }

    }

    fun getValue(element: Element, tag: String): String {
        try {
            val data = element.getElementsByTagName(tag).item(0)
            if (data.hasChildNodes()) {
                if (data.firstChild.nodeType == Node.TEXT_NODE) {
                    return data.firstChild.nodeValue
                }
            }
        }
        catch (e: Exception)
        {
            Toast.makeText(this,""+e.message,Toast.LENGTH_LONG).show()

        }

        return ""
    }
}