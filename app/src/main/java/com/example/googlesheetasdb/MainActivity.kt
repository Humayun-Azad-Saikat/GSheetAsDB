package com.example.googlesheetasdb

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.googlesheetasdb.ui.theme.GoogleSheetAsDBTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoogleSheetAsDBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        val nameText = nameText()
                        val addressText = addressText()

                        Button(
                            onClick = {
                                addItemToSheet(nameText,addressText,this@MainActivity)
                            }
                        ) {
                            Text("Add to Sheet")
                        }



                    }




                }
            }
        }
    }
}


@Composable
fun nameText():String{

    var nameText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = nameText,
        onValueChange = {nameText = it},
        label = { Text("name") },
        modifier = Modifier.fillMaxWidth()
    )
    return nameText;
}


@Composable
fun addressText():String{

    var addressText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = addressText,
        onValueChange = {addressText = it},
        label = { Text("address") },
        modifier = Modifier.fillMaxWidth()
    )
    return addressText;
}

//function for adding items and necessery work with spreadsheet
fun addItemToSheet(nameText:String,addressText:String,context: Context){
    val url = "https://script.google.com/macros/s/AKfycbxOdK89GTGL5zn9qCz9goKjJ0IhXstW3OeKQGmwUTh9kNsMdaBUr8JcD70qBybMw4Z8/exec"

    val stringRequest = object: StringRequest(Request.Method.POST,url,
        {
            //listener
        }, {
            //error listener
            //val context = LocalContext.current
            Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT).show()

        }){
        override fun getParams(): MutableMap<String, String> {


            val params = HashMap<String,String>()

            params.put("action","addItem")
            params.put("name",nameText)
            params.put("address",addressText)

            return params
        }
    }
    val timeout = 5000
    val retryPolicy = DefaultRetryPolicy(timeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
    stringRequest.setRetryPolicy(retryPolicy)

    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(stringRequest)
}

@Composable
fun ErrorToast(){
    val context = LocalContext.current
    Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT).show()
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoogleSheetAsDBTheme {

    }
}