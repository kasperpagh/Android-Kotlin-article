package cphbusiness.kasper.pagh.httpexample

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

import com.google.gson.Gson

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by kaspe on 2016-10-22.
 */

class Putter(internal var app: AppCompatActivity, internal var jsonString: DummyJSON) : AsyncTask<String, Void, String>()
{
    internal var gson: Gson
    internal var view: View? = null
    internal var context: Context? = null


    init
    {
        gson = Gson()

    }//        this.view = view;

    override fun doInBackground(vararg urls: String): String
    {
        // params comes from the execute() call: params[0] is the url.
        try
        {

            return downloadUrl(urls[0])
        }
        catch (e: IOException)
        {
            return "Unable to retrieve web page. URL may be invalid."
        }

    }


    override fun onPostExecute(result: String)
    {
        val textView = app.findViewById(R.id.responseText) as TextView
        textView.text = result
    }

    @Throws(IOException::class)
    private fun downloadUrl(myurl: String): String
    {
        var `is`: InputStream? = null
        var out: OutputStream? = null
        // Only display the first 500 characters of the retrieved
        // web page content.
        val len = 500
        var jsonString1: String = gson.toJson(jsonString);

        try
        {
            val url = URL(myurl)
            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 10000
            conn.connectTimeout = 15000
            conn.requestMethod = "PUT"

            //set the sending type and receiving type to json
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Accept", "application/json")
            conn.doOutput = true
            conn.doInput = true



            out = conn.outputStream
            out!!.write(jsonString1.toByteArray(charset("UTF-8")))
            out.close()


            // Starts the query
            conn.connect()

            `is` = conn.inputStream

            val bufferedReader = BufferedReader(InputStreamReader(`is`!!))
            val sb = StringBuilder()
            var line: String?
            do
            {
                line = bufferedReader.readLine()
                sb.append(line)
                if (line == null)
                    break

            }
            while (true)
            bufferedReader.close()

            return sb.toString()
        }
        finally
        {

            if (`is` != null)
            {
                `is`.close()
            }

        }
    }

}
