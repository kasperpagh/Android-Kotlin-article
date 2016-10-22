package cphbusiness.kasper.pagh.httpexample

/**
 * Created by kaspe on 2016-10-22.
 */

import android.content.Context
import android.nfc.Tag
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.google.gson.Gson
import com.google.gson.JsonElement

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.Reader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by kaspe on 2016-10-22.
 */

class Poster(internal var app: AppCompatActivity, internal var jsonString: DummyJSON) : AsyncTask<String, Void, String>()
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
            //            Log.d("i catch i doBG");
            //            Toast.makeText(app.getApplicationContext(), "Unable to complete request (IOException)", Toast.LENGTH_SHORT).show();
            //            Log.d(getClass().getSimpleName(), e.getCause().toString());
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
        val jsonString1 = gson.toJson(jsonString)
        try
        {
            Log.d(javaClass.getSimpleName(), "Her er json string og url : $jsonString1 url: $myurl")
            val url = URL(myurl)
            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 10000
            conn.connectTimeout = 15000
            conn.requestMethod = "POST"

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
